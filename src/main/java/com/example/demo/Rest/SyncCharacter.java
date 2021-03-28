package com.example.demo.Rest;

import com.example.demo.Database.Characters.Characters;
import com.example.demo.Database.Characters.CharactersService;
import com.example.demo.Database.Comics.*;
import com.example.demo.Database.Creators.Creators;
import com.example.demo.Database.Creators.CreatorsService;
import com.example.demo.MarvelApi.Characters.Entities.APICharacter;
import com.example.demo.MarvelApi.Characters.Entities.APICharacterSummary;
import com.example.demo.MarvelApi.Characters.GetCharacter;
import com.example.demo.MarvelApi.Characters.GetCharacterResponse;
import com.example.demo.MarvelApi.Characters.GetCharactersResponse;
import com.example.demo.MarvelApi.Comics.Entities.APIComic;
import com.example.demo.MarvelApi.Comics.Entities.APIComicSummary;
import com.example.demo.MarvelApi.Comics.GetComicResponse;
import com.example.demo.MarvelApi.Creators.Entities.APICreator;
import com.example.demo.MarvelApi.Creators.Entities.APICreatorSummary;
import com.example.demo.MarvelApi.Creators.GetCreatorResponse;
import com.example.demo.Utils.Curl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SyncCharacter {

    private final CharactersService charactersService;
    private final ComicsService comicsService;
    private final CreatorsService creatorsService;
    private final Characters character;

    public SyncCharacter(
            Characters character,
            CharactersService charactersService,
            ComicsService comicsService,
            CreatorsService creatorsService
    ){
        this.character = character;
        this.charactersService = charactersService;
        this.comicsService = comicsService;
        this.creatorsService = creatorsService;
    }

    public void sync(){
        if(!needSync(this.character.getId())){
            return;
        }

        GetCharacter getCharacter = new GetCharacter();
        GetCharacterResponse getCharacterResponse = getCharacter.getCharacter(this.character.getApi_id());
        APICharacter character = getCharacterResponse.getData().getResults().get(0);

        for(APIComicSummary cs: character.getComics().getItems()){
            Curl curlComic  = new Curl();
            GetComicResponse comicResponse = new GetComicResponse(curlComic.getResultUrl(cs.getResourceURI()));

            for(APIComic comic: comicResponse.getData().getResults()){
                boolean isNew = !comicsService.isExists(comic.getId());

                if(isNew) {
                    Comics _comic = comicsService.getComicCreateIfNotExists(comic);

                    List<ComicsCreators> comicsCreators = new ArrayList<>();
                    List<ComicsCharacters> comicsCharacters = new ArrayList<>();

                    for (APICharacterSummary chs : comic.getCharacters().getItems()) {
                        Curl curlCharacter = new Curl();
                        GetCharactersResponse characterResponse = new GetCharactersResponse(curlCharacter.getResultUrl(chs.getResourceURI()));
                        APICharacter character_related = characterResponse.getData().getResults().get(0);

                        Characters _character_related = charactersService.getCharacterCreateIfNotExists(character_related);

                        ComicsCharacters _comicsCharacters = new ComicsCharacters(_comic, _character_related);
                        comicsService.addComicCharacter(_comicsCharacters);
                    }

                    for (APICreatorSummary crs : comic.getCreators().getItems()) {
                        Curl curlCreator = new Curl();
                        GetCreatorResponse creatorResponse = new GetCreatorResponse(curlCreator.getResultUrl(crs.getResourceURI()));
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
        Optional<Characters> character = this.charactersService.isNeedSync(id);

        if(character.isPresent()){
            return false;
        }

        return true;
    }
}

