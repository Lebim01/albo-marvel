package com.example.demo.MarvelApi.Creators.Entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class APICreatorDataContainer {
    private Integer offset; // (int, optional): The requested offset (number of skipped results) of the call.,
    private Integer limit; // (int, optional): The requested result limit.,
    private Integer total; // (int, optional): The total number of resources available given the current filter set.,
    private Integer count; // (int, optional): The total number of results returned by this call.,
    private List<APICreator> results; // (Array[Comic], optional): The list of comics returned by the call

    public APICreatorDataContainer(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            this.offset = jsonNode.get("offset").asInt();
            this.limit = jsonNode.get("limit").asInt();
            this.total = jsonNode.get("total").asInt();
            this.count = jsonNode.get("count").asInt();
            this.results = new ArrayList<>();

            Iterator<JsonNode> items = jsonNode.get("results").elements();
            while(items.hasNext()){
                this.results.add(new APICreator(items.next().toString()));
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

    public List<APICreator> getResults() {
        return results;
    }

    public void setResults(List<APICreator> results) {
        this.results = results;
    }
}
