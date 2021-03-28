package com.example.demo.MarvelApi.Comics.Entities;

import com.example.demo.MarvelApi.Characters.Entities.CharacterList;
import com.example.demo.MarvelApi.Creators.Entities.CreatorList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Comic {
    private Integer id; // (int, optional): The unique ID of the comic resource.,
    private String title; // (string, optional): The canonical title of the comic.,
    private CreatorList creators; // (CreatorList, optional): A resource list containing the creators associated with this comic.,
    private CharacterList characters; // (CharacterList, optional): A resource list containing the characters which appear in this comic.,

    Comic(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            this.id = jsonNode.get("id").asInt();
            this.title = jsonNode.get("title").asText();
            this.creators = new CreatorList(jsonNode.get("creators").toString());
            this.characters = new CharacterList(jsonNode.get("characters").toString());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CreatorList getCreators() {
        return creators;
    }

    public void setCreators(CreatorList creators) {
        this.creators = creators;
    }

    public CharacterList getCharacters() {
        return characters;
    }

    public void setCharacters(CharacterList characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return title;
    }
}
