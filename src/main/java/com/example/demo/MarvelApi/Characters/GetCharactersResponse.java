package com.example.demo.MarvelApi.Characters;

import com.example.demo.MarvelApi.Characters.Entities.CharacterDataContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetCharactersResponse {
    private Integer code;
    private String status;
    private String copyright;
    private String attributionText;
    private String attributionHTML;
    private CharacterDataContainer data;

    public GetCharactersResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            this.code = jsonNode.get("code").asInt();
            this.status = jsonNode.get("status").asText();
            this.copyright = jsonNode.get("copyright").asText();
            this.attributionText = jsonNode.get("attributionText").asText();
            this.attributionHTML = jsonNode.get("attributionHTML").asText();
            this.data = new CharacterDataContainer(jsonNode.get("data").toString());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public CharacterDataContainer getData() {
        return data;
    }

    public void setData(CharacterDataContainer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetCharactersResponse{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", copyright='" + copyright + '\'' +
                ", attributionText='" + attributionText + '\'' +
                ", attributionHTML='" + attributionHTML + '\'' +
                ", data=" + data.getTotal() +
                '}';
    }
}

