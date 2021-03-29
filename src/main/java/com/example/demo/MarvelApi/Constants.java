package com.example.demo.MarvelApi;

import static com.example.demo.Utils.HelpersFunction.MD5;
import static com.example.demo.Utils.HelpersFunction.getTimestamp;

public class Constants {
    public static final String PUBLIC_KEY = "f3f5e56cbb8efb790342d06767cc80d6";
    public static final String PRIVATE_KEY = "a3fa4c6fbba958d42d073506333503bc841a9acf";

    public static final String API_MARVEL_URL = "http://gateway.marvel.com/v1/public/";

    public static String getHash() {
        String md5 = getTimestamp()+PRIVATE_KEY+PUBLIC_KEY;
        return MD5(md5);
    }
}
