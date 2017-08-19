package com.steveq.communication;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.steveq.communication.models.FromClientRequest;
import com.steveq.communication.models.ToClientResponse;

import java.io.StringReader;

/**
 * Created by Adam on 2017-08-03.
 */
public class JsonProcessor {
    private Gson gson;
    private static JsonProcessor instance;
    public enum Method{
        SCAN,
        UPLOAD,
        DOWNLOAD,
        WEATHER,
        SENSORS
    }

    private JsonProcessor(){
        gson = new Gson();
    }

    public static JsonProcessor getInstance(){
        if(instance == null){
            instance = new JsonProcessor();
        }
        return instance;
    }

    public FromClientRequest parseClientRequest(String json){
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return gson.fromJson(reader, FromClientRequest.class);
    }

    public String getResponseString(ToClientResponse response){
        return gson.toJson(response);
    }
}
