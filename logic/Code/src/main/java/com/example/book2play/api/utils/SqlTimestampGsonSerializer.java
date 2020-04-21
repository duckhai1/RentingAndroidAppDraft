package com.example.book2play.api.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SqlTimestampGsonSerializer implements JsonSerializer<Timestamp> {
    public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
        var fmt = new SimpleDateFormat();
        fmt.applyPattern("yyyy-MM-dd HH:mm:ss");
        return new JsonPrimitive(fmt.format(src));
    }
}

