package com.example.demo.MarvelApi.Characters;

import com.example.demo.MarvelApi.Characters.Entities.APICharacterDataContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIGetCharactersResponse {
    private APICharacterDataContainer data;

    public APIGetCharactersResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            this.data = new APICharacterDataContainer(jsonNode.get("data").toString());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public APICharacterDataContainer getData() {
        return data;
    }

    public void setData(APICharacterDataContainer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetCharactersResponse{" +
                ", data=" + data.getTotal() +
                '}';
    }
}

