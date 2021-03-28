package com.example.demo.MarvelApi.Comics.Entities;

import com.example.demo.MarvelApi.Characters.Entities.APICharacterList;
import com.example.demo.MarvelApi.Creators.Entities.APICreatorList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class APIComic {
    private Integer id; // (int, optional): The unique ID of the comic resource.,
    private String title; // (string, optional): The canonical title of the comic.,
    private APICreatorList creators; // (CreatorList, optional): A resource list containing the creators associated with this comic.,
    private APICharacterList characters; // (CharacterList, optional): A resource list containing the characters which appear in this comic.,

    APIComic(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            this.id = jsonNode.get("id").asInt();
            this.title = jsonNode.get("title").asText();
            this.creators = new APICreatorList(jsonNode.get("creators").toString());
            this.characters = new APICharacterList(jsonNode.get("characters").toString());

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

    public APICreatorList getCreators() {
        return creators;
    }

    public void setCreators(APICreatorList creators) {
        this.creators = creators;
    }

    public APICharacterList getCharacters() {
        return characters;
    }

    public void setCharacters(APICharacterList characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return title;
    }
}
