package com.example.demo.Rest.Characters;

import com.example.demo.MarvelApi.Creators.Entities.Creator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CharactersResponse {

    private String character;
    private LocalDateTime last_sync;
    private List<CharacterComics> characters;

    public CharactersResponse(){
        this.last_sync = LocalDateTime.now();
        this.characters = new ArrayList<>();
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getLast_sync() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return last_sync.format(formatter);
    }

    public void setLast_sync(LocalDateTime last_sync) {
        this.last_sync = last_sync;
    }

    public List<CharacterComics> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterComics> characters) {
        this.characters = characters;
    }
}
