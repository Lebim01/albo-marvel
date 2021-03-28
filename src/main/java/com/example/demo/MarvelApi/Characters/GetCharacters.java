package com.example.demo.MarvelApi.Characters;

import com.example.demo.Utils.Curl;
import com.example.demo.MarvelApi.Characters.Entities.APICharacter;

public class GetCharacters {

    public static GetCharactersResponse getByName(String characterName){
        Curl curl = new Curl("characters", "&name="+characterName);
        String response = curl.getResult();

        if(response.isEmpty()){
            throw new IllegalStateException("No se han encontrado heroes usando: " + characterName);
        }

        GetCharactersResponse data = new GetCharactersResponse(response);

        return data;
    }

    public static APICharacter getCharacterByName(String characterName){
        GetCharactersResponse charactersResponse = getByName(characterName);

        if(charactersResponse.getData().getResults().isEmpty()){
            throw new IllegalStateException("No se han encontrado heroes usando: " + characterName);
        }

        return charactersResponse.getData().getResults().get(0);
    }
}
