package com.example.demo.MarvelApi.Comics;

import com.example.demo.MarvelApi.Comics.Entities.APIComicDataContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIGetComicsResponse {
    private APIComicDataContainer data;

    public APIGetComicsResponse(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            this.data = new APIComicDataContainer(jsonNode.get("data").toString());

        } catch (JsonProcessingException e){
            System.out.println("JSON ERROR " + json);
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
