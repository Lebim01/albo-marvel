package com.example.demo.MarvelApi.Comics;

import com.example.demo.MarvelApi.Characters.Entities.APICharacterDataContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIGetComicsCharactersResponse {
    private APICharacterDataContainer data;

    public APIGetComicsCharactersResponse(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            this.data = new APICharacterDataContainer(jsonNode.get("data").toString());

        } catch (JsonProcessingException e) {
            System.out.println("JSON ERROR " + json);
            e.printStackTrace();
        }
    }

    public APICharacterDataContainer getData() {
        return data;
    }

    public void setData(APICharacterDataContainer data) {
        this.data = data;
    }
}
