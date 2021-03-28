package com.example.demo.MarvelApi.Characters;

import com.example.demo.Utils.Curl;

public class APIGetCharacter {

    public APIGetCharacter(){

    }

    public APIGetCharacterResponse getCharacter(Integer api_id){
        Curl curl = new Curl("characters/"+api_id);
        String response = curl.getResult();

        return new APIGetCharacterResponse(response);
    }
}
