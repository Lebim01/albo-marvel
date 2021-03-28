package com.example.demo.MarvelApi;

import static com.example.demo.Utils.HelpersFunction.MD5;
import static com.example.demo.Utils.HelpersFunction.getTimestamp;

public class Constants {

    public static final String PUBLIC_KEY = "c5597a95cf56d0c4abd2bceeacdd107f";
    public static final String PRIVATE_KEY = "04eca731eb752c1c90fd7d701ac8dc34c06bd24b";

    public static String getHash() {
        String md5 = getTimestamp()+PRIVATE_KEY+PUBLIC_KEY;
        return MD5(md5);
    }
}
