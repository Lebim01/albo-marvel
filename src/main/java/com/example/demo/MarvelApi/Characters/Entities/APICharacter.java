package com.example.demo.MarvelApi.Characters.Entities;

import com.example.demo.MarvelApi.Comics.Entities.APIComicList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APICharacter {
    private Integer id; // (int, optional): The unique ID of the character resource.,
    private String name; // (string, optional): The name of the character.,
    private APIComicList comics; // (ComicList, optional): A resource list containing comics which feature this character.,

    APICharacter(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            this.id = jsonNode.get("id").asInt();
            this.name = jsonNode.get("name").asText();
            this.comics = new APIComicList(jsonNode.get("comics").toString());

            System.out.println("Character " + this.toString());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public APIComicList getComics() {
        return comics;
    }

    public void setComics(APIComicList comics) {
        this.comics = comics;
    }

    @Override
    public String toString() {
        return name;
    }
}
