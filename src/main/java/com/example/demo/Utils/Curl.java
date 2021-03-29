package com.example.demo.Utils;

import java.io.*;

import static com.example.demo.MarvelApi.Constants.*;
import static com.example.demo.Utils.HelpersFunction.getTimestamp;

public class Curl {
    private String params = "apikey=" + PUBLIC_KEY + "&ts=" + getTimestamp() + "&hash=" + getHash();
    private String method;

    public Curl(){

    }

    public Curl(String method){
        this.method = method;
    }

    public Curl(String method, String params){
        this.method = method;
        this.params += params;
    }

    public String getResult() {
        String[] commands = new String[]{"curl", "-X", "GET", API_MARVEL_URL + this.method + "?" + this.params};
        return curl(commands);
    }

    public String getResultUrl(String url){
        System.out.println("EXECUTING CURL");
        String[] commands = new String[]{"curl", "-X", "GET", url + "?" + this.params};
        return curl(commands);
    }

    public String curl(String[] commands){
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(commands);
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));
            String line = "";
            String response = "";
            while ((line = reader.readLine()) != null) {
                response += line;
            }

            return response;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
