package com.example.demo.Rest.Characters;

import java.util.ArrayList;
import java.util.List;

public class CharacterComics {
    private String character;
    private List<String> comics;

    CharacterComics(String characterName){
        this.character = characterName;
        this.comics = new ArrayList<>();
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public List<String> getComics() {
        return comics;
    }

    public void addComic(String comicName) {
        if(!this.comics.contains(comicName))
            this.comics.add(comicName);
    }
}
