package com.example.demo.Rest.Characters;

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

    public void addCharacterComic(String characterName, String comicTitle){
        if(!characterName.equals(this.character)) {
            boolean exists = false;
            int index = -1;

            for (CharacterComics cc : characters) {
                index++;
                if (cc.getCharacter().equals(characterName)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                this.characters.get(index).addComic(comicTitle);
            } else {
                CharacterComics characterComics = new CharacterComics(characterName);
                characterComics.addComic(comicTitle);
                this.characters.add(characterComics);
            }
        }
    }
}
