package com.example.demo.MarvelApi;

import static com.example.demo.Utils.HelpersFunction.MD5;
import static com.example.demo.Utils.HelpersFunction.getTimestamp;

public class Constants {

    //public static final String PUBLIC_KEY = "8ae16d26087f599520ef9488fda7bc78";
    //public static final String PRIVATE_KEY = "b1647f757a1c7bdef35529deae36a7aec38b5d48";

    //public static final String PUBLIC_KEY = "c5597a95cf56d0c4abd2bceeacdd107f";
    //public static final String PRIVATE_KEY = "04eca731eb752c1c90fd7d701ac8dc34c06bd24b";

    //public static final String PUBLIC_KEY = "d04841aac42a0529d921e8456ee7a9a1";
    //public static final String PRIVATE_KEY = "0467430e35af5d6730e4972e8a30b9a984cbd562";

    public static final String PUBLIC_KEY = "4b9cc7dcd5fd853c00c39a22bffa2363";
    public static final String PRIVATE_KEY = "1073f3508e48fc642b32f10a6fd70ae50cb653b7";

    public static final String API_MARVEL_URL = "http://gateway.marvel.com/v1/public/";

    public static String getHash() {
        String md5 = getTimestamp()+PRIVATE_KEY+PUBLIC_KEY;
        return MD5(md5);
    }
}
