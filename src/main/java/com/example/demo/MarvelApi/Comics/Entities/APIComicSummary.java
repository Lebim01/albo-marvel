package com.example.demo.MarvelApi.Comics.Entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIComicSummary {
    private String resourceURI;// (string, optional): The path to the individual comic resource.,
    private String name;// (string, optional): The canonical name of the comic.

    APIComicSummary(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            this.resourceURI = jsonNode.get("resourceURI").asText();
            this.name = jsonNode.get("name").asText();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
