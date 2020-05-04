package com.example.book2play.api.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Timestamp;

/**
 * com.java.sql.Timestamp json deserializer for use with Gson library
 */
public class SqlTimestampGsonDeserializer implements JsonDeserializer<Timestamp> {
    public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return Timestamp.valueOf(json.getAsJsonPrimitive().getAsString());
        } catch (Exception e) {
            return null;
        }
    }
}
