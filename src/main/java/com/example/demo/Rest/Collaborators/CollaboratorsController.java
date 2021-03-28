package com.example.demo.Rest.Collaborators;

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
@RequestMapping(path="marvel/collaborators")
public class CollaboratorsController {

    private final CharactersService charactersService;
    private final CreatorsService creatorsService;
    private final ComicsService comicsService;

    @Autowired
    CollaboratorsController(CharactersService charactersService, ComicsService comicsService, CreatorsService creatorsService){
        this.charactersService = charactersService;
        this.comicsService = comicsService;
        this.creatorsService = creatorsService;
    }

    @GetMapping(path="{character}")
    public CollaboratorsResponse getCollaborators(@PathVariable("character") String short_name){

        Optional<Characters> optionalCharacters = charactersService.getCharacterByShortName(short_name);
        if(!optionalCharacters.isPresent()){
            throw new IllegalStateException("Character not available");
        }
        Characters character = optionalCharacters.get();

        SyncCharacter sync = new SyncCharacter(charactersService, comicsService, creatorsService);
        sync.sync(character);

        CollaboratorsResponse response = new CollaboratorsResponse();
        response.setLast_sync(character.getLast_sync());
        response.setCharacter(character.getName());
        response.setWriters(charactersService.getWriters(character.getId()));
        response.setColorists(charactersService.getColorists(character.getId()));
        response.setEditors(charactersService.getEditors(character.getId()));

        return response;
    }
}
