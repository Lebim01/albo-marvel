package com.example.demo.MarvelApi.Characters;

import com.example.demo.Utils.Curl;
import com.example.demo.MarvelApi.Characters.Entities.APICharacter;

public class APIGetCharacters {

    public static APIGetCharactersResponse getByName(String characterName){
        Curl curl = new Curl("characters", "&name="+characterName);
        String response = curl.getResult();

        if(response.isEmpty()){
            throw new IllegalStateException("No se han encontrado heroes usando: " + characterName);
        }

        APIGetCharactersResponse data = new APIGetCharactersResponse(response);

        return data;
    }

    public static APICharacter getCharacterByName(String characterName){
        APIGetCharactersResponse charactersResponse = getByName(characterName);

        if(charactersResponse.getData().getResults().isEmpty()){
            throw new IllegalStateException("No se han encontrado heroes usando: " + characterName);
        }

        return charactersResponse.getData().getResults().get(0);
    }
}
