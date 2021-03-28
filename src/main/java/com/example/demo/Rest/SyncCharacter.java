package com.example.demo.Rest;

import com.example.demo.Database.Characters.Characters;
import com.example.demo.Database.Characters.CharactersService;
import com.example.demo.Database.Comics.Comics;
import com.example.demo.Database.Comics.ComicsService;
import com.example.demo.Database.Creators.CreatorsService;
import com.example.demo.MarvelApi.Characters.Entities.Character;
import com.example.demo.MarvelApi.Characters.Entities.CharacterSummary;
import com.example.demo.MarvelApi.Comics.Entities.Comic;
import com.example.demo.MarvelApi.Comics.Entities.ComicSummary;
import com.example.demo.MarvelApi.Comics.GetComicResponse;
import com.example.demo.MarvelApi.Creators.Entities.Creator;
import com.example.demo.MarvelApi.Creators.Entities.CreatorSummary;
import com.example.demo.MarvelApi.Creators.GetCreatorResponse;
import com.example.demo.Rest.Characters.CharactersResponse;
import com.example.demo.Utils.Curl;

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

        Characters newCharter = new Characters(character.getId(), character.getName(), character.getName());
        charactersService.addNewCharacter(newCharter);

        for(ComicSummary cs:character.getComics().getItems()){
            Curl curlComic  = new Curl();
            GetComicResponse comicResponse = new GetComicResponse(curlComic.getResultUrl(cs.getResourceURI()));

            for(Comic c:comicResponse.getData().getResults()){
                for(CharacterSummary chs:c.getCharacters().getItems()){
                    charactersResponse.addCharacterComic(chs.getName(), c.getTitle());
                }

                for(CreatorSummary crs:c.getCreators().getItems()){
                    Curl curlCreator  = new Curl();
                    GetCreatorResponse creatorResponse = new GetCreatorResponse(curlCreator.getResultUrl(cs.getResourceURI()));
                    Creator creator = creatorResponse.getData().getResults().get(0);

                    if(crs.getRole().equals("colorist")){
                        charactersResponse.addColorist(creator);
                    }
                    else if(crs.getRole().equals("editor")){
                        charactersResponse.addEditor(creator);
                    }
                    else if(crs.getRole().equals("writer")){
                        charactersResponse.addWriter(creator);
                    }
                }
            }
        }

        for(ComicSummary comicSummary: character.getComics().getItems()){
            Curl curlComic  = new Curl();
            GetComicResponse comicResponse = new GetComicResponse(curlComic.getResultUrl(comicSummary.getResourceURI()));

            for(Comic comic: comicResponse.getData().getResults()) {
                Comics _comic = comicsService.getComicCreateIfNotExists(comic);

                

                //_comic.setComicsCreators();
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

