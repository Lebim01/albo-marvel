package com.example.demo.Rest;

import com.example.demo.Database.Characters.Characters;
import com.example.demo.Database.Characters.CharactersService;
import com.example.demo.Database.Comics.*;
import com.example.demo.Database.Creators.Creators;
import com.example.demo.Database.Creators.CreatorsService;
import com.example.demo.MarvelApi.Characters.Entities.Character;
import com.example.demo.MarvelApi.Characters.Entities.CharacterSummary;
import com.example.demo.MarvelApi.Characters.GetCharactersResponse;
import com.example.demo.MarvelApi.Comics.Entities.Comic;
import com.example.demo.MarvelApi.Comics.Entities.ComicSummary;
import com.example.demo.MarvelApi.Comics.GetComicResponse;
import com.example.demo.MarvelApi.Creators.Entities.Creator;
import com.example.demo.MarvelApi.Creators.Entities.CreatorSummary;
import com.example.demo.MarvelApi.Creators.GetCreatorResponse;
import com.example.demo.Rest.Characters.CharactersResponse;
import com.example.demo.Utils.Curl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.MarvelApi.Characters.GetCharacters.getCharacterByName;

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

    public void sync(String characterName){
        Character character = getCharacterByName(characterName);

        if(!needSync(character.getId())){
            return;
        }

        CharactersResponse charactersResponse = new CharactersResponse();

        charactersResponse.setCharacter(character.getName());

        Characters _character = charactersService.getCharacterCreateIfNotExists(character);

        for(ComicSummary cs:character.getComics().getItems()){
            Curl curlComic  = new Curl();
            GetComicResponse comicResponse = new GetComicResponse(curlComic.getResultUrl(cs.getResourceURI()));

            for(Comic comic: comicResponse.getData().getResults()){
                boolean isNew = !comicsService.isExists(comic.getId());

                if(isNew) {
                    Comics _comic = comicsService.getComicCreateIfNotExists(comic);

                    List<ComicsCreators> comicsCreators = new ArrayList<>();
                    List<ComicsCharacters> comicsCharacters = new ArrayList<>();

                    for (CharacterSummary chs : comic.getCharacters().getItems()) {
                        Curl curlCharacter = new Curl();
                        GetCharactersResponse characterResponse = new GetCharactersResponse(curlCharacter.getResultUrl(chs.getResourceURI()));
                        Character character_related = characterResponse.getData().getResults().get(0);

                        Characters _character_related = charactersService.getCharacterCreateIfNotExists(character_related);

                        ComicsCharacters _comicsCharacters = new ComicsCharacters(_comic, _character_related);
                        comicsService.addComicCharacter(_comicsCharacters);
                    }

                    for (CreatorSummary crs : comic.getCreators().getItems()) {
                        Curl curlCreator = new Curl();
                        GetCreatorResponse creatorResponse = new GetCreatorResponse(curlCreator.getResultUrl(crs.getResourceURI()));
                        Creator creator = creatorResponse.getData().getResults().get(0);

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

    private boolean needSync(Integer id){
        Optional<Characters> character = this.charactersService.getCharacter(id);
        if(character.isPresent()){
            return false;
        }

        return true;
    }
}

