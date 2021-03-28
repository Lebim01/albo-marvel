package com.example.demo.MarvelApi.Characters;

import com.example.demo.Utils.Curl;

public class GetCharacter {

    public GetCharacter(){

    }

    public GetCharacterResponse getCharacter(Integer api_id){
        Curl curl = new Curl("characters/"+api_id);
        String response = curl.getResult();

        return new GetCharacterResponse(response);
    }
}
