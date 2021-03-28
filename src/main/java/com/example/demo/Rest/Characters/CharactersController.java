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
    public CharactersResponse getCharacters(@PathVariable("character") String short_name){

        Optional<Characters> optionalCharacters = charactersService.getCharacterByShortName(short_name);
        if(!optionalCharacters.isPresent()){
            throw new IllegalStateException("Character not available");
        }

        Characters character = optionalCharacters.get();

        SyncCharacter sync = new SyncCharacter(character, charactersService, comicsService, creatorsService);
        sync.sync();

        CharactersResponse response = new CharactersResponse();
        response.setLast_sync(character.getLast_sync());
        response.setCharacter(character.getName());
        response.setCharacters(charactersService.getCharactersRelated(character.getId()));

        return response;
    }
}
