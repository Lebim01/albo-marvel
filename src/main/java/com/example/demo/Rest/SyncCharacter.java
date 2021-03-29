package com.example.demo.Rest;

import com.example.demo.Database.Characters.Characters;
import com.example.demo.Database.Characters.CharactersService;
import com.example.demo.Database.Comics.*;
import com.example.demo.Database.Creators.Creators;
import com.example.demo.Database.Creators.CreatorsService;
import com.example.demo.MarvelApi.Characters.Entities.APICharacter;
import com.example.demo.MarvelApi.Comics.APIGetComicsCharactersResponse;
import com.example.demo.MarvelApi.Comics.APIGetComicsResponse;
import com.example.demo.MarvelApi.Comics.Entities.APIComic;
import com.example.demo.MarvelApi.Creators.Entities.APICreator;
import com.example.demo.MarvelApi.Creators.Entities.APICreatorSummary;
import com.example.demo.Utils.Curl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class SyncCharacter {

    private final CharactersService charactersService;
    private final ComicsService comicsService;
    private final CreatorsService creatorsService;

    public SyncCharacter(
            CharactersService charactersService,
            ComicsService comicsService,
            CreatorsService creatorsService
    ){
        this.charactersService = charactersService;
        this.comicsService = comicsService;
        this.creatorsService = creatorsService;
    }

    public void syncAll(){
        System.out.println("Syncing all characters");
        List<Characters> characters = this.charactersService.getCharactersToSync();
        for(Characters character: characters){
            this.sync(character);
        }
    }

    public void sync(Characters character){
        System.out.println("Syncing " + character.getName());

        if(!needSync(character.getId())){
            System.out.println(character.getName() + " don't need sync");
            return;
        }

        /**
         * Update last_sync
         */
        charactersService.updateCharacter(character.getId(), character.getName(), LocalDateTime.now());

        /**
         * Get all comics pages, by 100 records per page
         * only bring from the last modification using filter modifiedSince
         *
         * @param modifiedSince (format is: 2014-06-10T16:12:58-0400)
         */
        Optional<Comics> last_comic = charactersService.getLastComic(character.getId());
        String modifiedSince = "";
        if(last_comic.isPresent()){
            modifiedSince = last_comic.get().getModified().format(DateTimeFormatter.ISO_DATE_TIME);
        }

        String filter = modifiedSince.isEmpty() ? "" : "&modifiedSince="+modifiedSince;
        int offset = 0;
        int pages = 1;
        int current_page = 1;
        int limit = 100;

        do {
            System.out.println("characters/"+character.getApi_id()+"/comics?" + filter + "&limit="+limit+"&offset="+offset+"&orderBy=modified");
            Curl curlComics = new Curl("characters/"+character.getApi_id()+"/comics", filter + "&limit="+limit+"&offset="+offset+"&orderBy=modified");
            APIGetComicsResponse apiGetComicsResponse = new APIGetComicsResponse(curlComics.getResult());

            /**
             * TODO: I don't understand why Math.ceil() doesn't work
             */
            pages = apiGetComicsResponse.getData().getTotal() / limit + ((apiGetComicsResponse.getData().getTotal() % limit == 0) ? 0 : 1);

            for(APIComic comic : apiGetComicsResponse.getData().getResults()){
                System.out.println("APIComic: " + comic.getTitle());
                Comics _comic = comicsService.getComicCreateIfNotExists(comic);

                /**
                 * Get characters related by comic
                 */
                Curl curlComicsCharacters = new Curl("comics/"+_comic.getApi_id()+"/characters", "&limit=100");
                APIGetComicsCharactersResponse apiGetComicsCharacters = new APIGetComicsCharactersResponse(curlComicsCharacters.getResult());
                for (APICharacter apiCharacter : apiGetComicsCharacters.getData().getResults()) {
                     Characters _character_related = charactersService.getCharacterCreateIfNotExists(apiCharacter);
                     ComicsCharacters _comicsCharacters = new ComicsCharacters(_comic, _character_related);
                     comicsService.addComicCharacter(_comicsCharacters);
                }

                /**
                 * Get creators related by comic
                 */
                for (APICreatorSummary crs : comic.getCreators().getItems()) {
                    String[] parts = crs.getResourceURI().split("/");
                    String id = parts[parts.length-1];
                    APICreator creator = new APICreator(Integer.valueOf(id), crs.getName());

                    switch (crs.getRole()) {
                        case "colorist":
                        case "editor":
                        case "writer":
                            Creators _creator = creatorsService.getCreatorCreateIfNotExists(creator);
                            ComicsCreators _comicsCreators = new ComicsCreators(_comic, _creator, crs.getRole());
                            creatorsService.addComicCreator(_comicsCreators);
                            break;
                    }
                }
            }

            current_page++;
            offset += limit;
        }while(current_page <= pages);
    }

    private boolean needSync(Long id){
        /**
         * only sync when character updated more than 1 day ago
         */
        Optional<Characters> character = this.charactersService.isNeedSync(id);

        if(character.isPresent()){
            return false;
        }

        return true;
    }
}

