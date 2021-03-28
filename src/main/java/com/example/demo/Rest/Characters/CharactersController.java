package com.example.demo.Rest.Characters;

import com.example.demo.Database.Characters.Characters;
import com.example.demo.Database.Characters.CharactersService;
import com.example.demo.Database.Comics.ComicsService;
import com.example.demo.Database.Creators.CreatorsService;
import com.example.demo.Rest.SyncCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path="marvel/characters")
public class CharactersController {

    private final CharactersService charactersService;
    private final CreatorsService creatorsService;
    private final ComicsService comicsService;

    @Autowired
    CharactersController(CharactersService charactersService, ComicsService comicsService, CreatorsService creatorsService){
        this.charactersService = charactersService;
        this.comicsService = comicsService;
        this.creatorsService = creatorsService;
    }

    @GetMapping(path="{character}")
    public CharactersResponse getCharacters(@PathVariable("character") String characterName){

        SyncCharacter sync = new SyncCharacter(charactersService, comicsService, creatorsService);
        sync.sync(characterName);

        Optional<Characters> optionalCharacters = charactersService.getCharacterByName(characterName);
        if(!optionalCharacters.isPresent()){
            throw new IllegalStateException("Character does not exists");
        }

        Characters character = optionalCharacters.get();

        CharactersResponse response = new CharactersResponse();
        response.setCharacter(character.getName());
        response.setCharacters(charactersService.getCharactersRelated(character.getId()));

        return response;
    }
}
