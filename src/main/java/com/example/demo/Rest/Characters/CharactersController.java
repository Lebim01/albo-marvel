package com.example.demo.Rest.Characters;

import com.example.demo.Utils.Curl;
import com.example.demo.MarvelApi.Characters.Entities.Character;
import com.example.demo.MarvelApi.Characters.Entities.CharacterSummary;
import com.example.demo.MarvelApi.Comics.Entities.Comic;
import com.example.demo.MarvelApi.Comics.Entities.ComicSummary;
import com.example.demo.MarvelApi.Comics.GetComicResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.MarvelApi.Characters.GetCharacters.getCharacterByName;

@RestController
@RequestMapping(path="marvel/characters")
public class CharactersController {

    @GetMapping(path="{character}")
    public CharactersResponse getCharacters(@PathVariable("character") String characterName){
        Character character = getCharacterByName(characterName);

        CharactersResponse charactersResponse = new CharactersResponse();

        charactersResponse.setCharacter(character.getName());

        for(ComicSummary cs:character.getComics().getItems()){
            Curl curlComic  = new Curl();
            GetComicResponse comicResponse = new GetComicResponse(curlComic.getResultUrl(cs.getResourceURI()));
            for(Comic c:comicResponse.getData().getResults()){
                for(CharacterSummary chs:c.getCharacters().getItems()){
                    charactersResponse.addCharacterComic(chs.getName(), c.getTitle());
                }
            }
        }

        return charactersResponse;
    }
}
