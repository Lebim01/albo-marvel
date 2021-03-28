package com.example.demo.MarvelApi.Characters;

import com.example.demo.MarvelApi.Characters.Entities.APICharacterDataContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIGetCharactersResponse {
    private Integer code;
    private String status;
    private String copyright;
    private String attributionText;
    private String attributionHTML;
    private APICharacterDataContainer data;

    public APIGetCharactersResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            this.code = jsonNode.get("code").asInt();
            this.status = jsonNode.get("status").asText();
            this.copyright = jsonNode.get("copyright").asText();
            this.attributionText = jsonNode.get("attributionText").asText();
            this.attributionHTML = jsonNode.get("attributionHTML").asText();
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
                "code=" + code +
                ", status='" + status + '\'' +
                ", copyright='" + copyright + '\'' +
                ", attributionText='" + attributionText + '\'' +
                ", attributionHTML='" + attributionHTML + '\'' +
                ", data=" + data.getTotal() +
                '}';
    }
}

