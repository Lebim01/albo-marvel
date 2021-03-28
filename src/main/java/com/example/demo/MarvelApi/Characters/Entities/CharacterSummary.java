package com.example.demo.MarvelApi.Characters.Entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CharacterSummary {
    private String resourceURI; // (string, optional): The path to the individual character resource.,
    private String name; // (string, optional): The full name of the character.,
    private String role; // (string, optional): The role of the creator in the parent entity.

    CharacterSummary(String json){
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
