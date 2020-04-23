package com.example.book2play.api.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Time;

/**
 * com.java.sql.Time json deserializer for use with Gson library
 */
public class SqlTimeGsonDeserializer implements JsonDeserializer<Time> {
    public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Time.valueOf(json.getAsJsonPrimitive().getAsString());
    }
}
