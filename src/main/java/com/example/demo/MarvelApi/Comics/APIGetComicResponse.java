package com.example.demo.MarvelApi.Comics;

import com.example.demo.MarvelApi.Comics.Entities.APIComicDataContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIGetComicResponse {
    private APIComicDataContainer data;

    public APIGetComicResponse(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            this.data = new APIComicDataContainer(jsonNode.get("data").toString());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public APIComicDataContainer getData() {
        return data;
    }

    public void setData(APIComicDataContainer data) {
        this.data = data;
    }
}
