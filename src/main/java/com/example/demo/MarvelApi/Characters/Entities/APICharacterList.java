package com.example.demo.MarvelApi.Characters.Entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class APICharacterList {
    private Integer available; // (int, optional): The number of total available characters in this list. Will always be greater than or equal to the "returned" value.,
    private Integer returned; // (int, optional): The number of characters returned in this collection (up to 20).,
    private String collectionURI; // (string, optional): The path to the full list of characters in this collection.,
    private List<APICharacterSummary> items; // (Array[CharacterSummary], optional): The list of returned characters in this collection.

    public APICharacterList(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            this.available = jsonNode.get("available").asInt();
            this.returned = jsonNode.get("returned").asInt();
            this.collectionURI = jsonNode.get("collectionURI").asText();
            this.items = new ArrayList<>();

            Iterator<JsonNode> items = jsonNode.get("items").elements();
            while(items.hasNext()){
                this.items.add(new APICharacterSummary(items.next().toString()));
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getReturned() {
        return returned;
    }

    public void setReturned(Integer returned) {
        this.returned = returned;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public List<APICharacterSummary> getItems() {
        return items;
    }

    public void setItems(List<APICharacterSummary> items) {
        this.items = items;
    }
}
