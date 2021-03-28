package com.example.demo.MarvelApi.Creators;

import com.example.demo.MarvelApi.Creators.Entities.APICreatorDataContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetCreatorResponse {
    private APICreatorDataContainer data;

    public GetCreatorResponse(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            this.data = new APICreatorDataContainer(jsonNode.get("data").toString());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public APICreatorDataContainer getData() {
        return data;
    }

    public void setData(APICreatorDataContainer data) {
        this.data = data;
    }
}
