package com.example.demo.MarvelApi;

import static com.example.demo.Utils.HelpersFunction.MD5;
import static com.example.demo.Utils.HelpersFunction.getTimestamp;

public class Constants {
    public static final String PUBLIC_KEY = "8ae16d26087f599520ef9488fda7bc78";
    public static final String PRIVATE_KEY = "b1647f757a1c7bdef35529deae36a7aec38b5d48";

    public static String getHash() {
        String md5 = getTimestamp()+PRIVATE_KEY+PUBLIC_KEY;
        return MD5(md5);
    }
}
