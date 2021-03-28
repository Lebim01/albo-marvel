package com.example.demo.MarvelApi.Characters.Entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class APICharacterDataContainer {
    private Integer offset;
    private Integer limit;
    private Integer total;
    private Integer count;
    private List<APICharacter> results;

    public APICharacterDataContainer(String response){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            this.offset = jsonNode.get("offset").asInt();
            this.limit = jsonNode.get("limit").asInt();
            this.total = jsonNode.get("total").asInt();
            this.count = jsonNode.get("count").asInt();
            this.results = new ArrayList<>();

            Iterator<JsonNode> items = jsonNode.get("results").elements();
            while(items.hasNext()){
                this.results.add(new APICharacter(items.next().toString()));
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<APICharacter> getResults() {
        return results;
    }

    public void setResults(List<APICharacter> results) {
        this.results = results;
    }
}
