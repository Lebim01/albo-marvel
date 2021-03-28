package com.example.demo.Rest;

import com.example.demo.Database.Characters.Characters;
import com.example.demo.Database.Characters.CharactersService;
import com.example.demo.Database.Comics.*;
import com.example.demo.Database.Creators.Creators;
import com.example.demo.Database.Creators.CreatorsService;
import com.example.demo.MarvelApi.Characters.Entities.APICharacter;
import com.example.demo.MarvelApi.Characters.Entities.APICharacterSummary;
import com.example.demo.MarvelApi.Characters.APIGetCharacter;
import com.example.demo.MarvelApi.Characters.APIGetCharacterResponse;
import com.example.demo.MarvelApi.Characters.APIGetCharactersResponse;
import com.example.demo.MarvelApi.Comics.Entities.APIComic;
import com.example.demo.MarvelApi.Comics.Entities.APIComicSummary;
import com.example.demo.MarvelApi.Comics.APIGetComicResponse;
import com.example.demo.MarvelApi.Creators.Entities.APICreator;
import com.example.demo.MarvelApi.Creators.Entities.APICreatorSummary;
import com.example.demo.MarvelApi.Creators.APIGetCreatorResponse;
import com.example.demo.Utils.Curl;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        charactersService.updateCharacter(character.getId(), character.getName(), LocalDateTime.now());

        APIGetCharacter getCharacter = new APIGetCharacter();
        APIGetCharacterResponse getCharacterResponse = getCharacter.getCharacter(character.getApi_id());
        APICharacter apiCharacter = getCharacterResponse.getData().getResults().get(0);

        for(APIComicSummary cs: apiCharacter.getComics().getItems()){
            Curl curlComic  = new Curl();
            APIGetComicResponse comicResponse = new APIGetComicResponse(curlComic.getResultUrl(cs.getResourceURI()));

            for(APIComic comic: comicResponse.getData().getResults()){
                /**
                 * Sync new comic if does not exists
                 */
                boolean isNew = !comicsService.isExistsByApiId(comic.getId());

                if(isNew) {
                    Comics _comic = comicsService.getComicCreateIfNotExists(comic);

                    List<ComicsCreators> comicsCreators = new ArrayList<>();
                    List<ComicsCharacters> comicsCharacters = new ArrayList<>();

                    /**
                     * Get characters related by comic
                     */
                    for (APICharacterSummary chs : comic.getCharacters().getItems()) {
                        Curl curlCharacter = new Curl();
                        APIGetCharactersResponse characterResponse = new APIGetCharactersResponse(curlCharacter.getResultUrl(chs.getResourceURI()));
                        APICharacter character_related = characterResponse.getData().getResults().get(0);

                        Characters _character_related = charactersService.getCharacterCreateIfNotExists(character_related);

                        ComicsCharacters _comicsCharacters = new ComicsCharacters(_comic, _character_related);
                        comicsService.addComicCharacter(_comicsCharacters);
                    }

                    /**
                     * Get creators (by role [writer, editor, colorist]) and save by comic
                     */
                    for (APICreatorSummary crs : comic.getCreators().getItems()) {
                        Curl curlCreator = new Curl();
                        APIGetCreatorResponse creatorResponse = new APIGetCreatorResponse(curlCreator.getResultUrl(crs.getResourceURI()));
                        APICreator creator = creatorResponse.getData().getResults().get(0);

                        switch (crs.getRole()) {
                            case "colorist":
                            case "editor":
                            case "writer":
                                Creators _creator = creatorsService.getCreatorCreateIfNotExists(creator);
                                ComicsCreators _comicsCreators = new ComicsCreators(_comic, _creator, crs.getRole());
                                creatorsService.addComicCreator(_comicsCreators);
                                comicsCreators.add(_comicsCreators);
                                break;
                        }
                    }

                    _comic.setComicsCreators(comicsCreators);
                    _comic.setComicsCharacters(comicsCharacters);
                }
            }
        }
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

