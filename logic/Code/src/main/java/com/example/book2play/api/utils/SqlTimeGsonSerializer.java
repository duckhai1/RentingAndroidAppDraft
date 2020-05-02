package com.example.book2play.api.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * com.java.sql.Time json serializer for use with Gson library
 */
public class SqlTimeGsonSerializer implements JsonSerializer<Time> {
    public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return new JsonPrimitive("");
        }
        var fmt = new SimpleDateFormat();
        fmt.applyPattern("HH:mm:ss");
        return new JsonPrimitive(fmt.format(src));
    }
}

