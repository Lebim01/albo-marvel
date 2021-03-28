package com.example.demo.MarvelApi.Creators;

import com.example.demo.MarvelApi.Creators.Entities.CreatorDataContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetCreatorResponse {
    private CreatorDataContainer data;

    public GetCreatorResponse(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            this.data = new CreatorDataContainer(jsonNode.get("data").toString());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public CreatorDataContainer getData() {
        return data;
    }

    public void setData(CreatorDataContainer data) {
        this.data = data;
    }
}
