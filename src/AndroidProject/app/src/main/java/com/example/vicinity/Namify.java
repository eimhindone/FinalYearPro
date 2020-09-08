package com.example.vicinity;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Namify {

    public static String namify(String address){

        String name="";
        Pattern pattern = Pattern.compile("^(.+?),", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(address);
        while (matcher.find()){
            name = matcher.group(1);
        }
        return name;
    }
}
