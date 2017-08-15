package com.steveq.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by Adam on 2017-08-07.
 */
public class FileUtils {
    public static List<String> getDaysProperties(){
        Properties prop = new Properties();
        InputStream inputStream = null;
        String days = "";

        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("days.properties");

            prop.load(inputStream);
            days = prop.getProperty("days");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(Arrays.asList(days.split(",")));
    }

    public static String getApiKey(){
        Properties prop = new Properties();
        InputStream inputStream = null;
        String key = "";

        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("secret.properties");

            prop.load(inputStream);
            key = prop.getProperty("api");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public static void saveDurationProperty(String duration){
        Properties prop = new Properties();
        InputStream inputStream = null;
        String days = "";

        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties");

            prop.load(inputStream);
            prop.setProperty("duration", duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDurationProperty(){
        Properties prop = new Properties();
        InputStream inputStream = null;
        String duration = "";

        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties");

            prop.load(inputStream);
            duration = prop.getProperty("duration");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }
}
