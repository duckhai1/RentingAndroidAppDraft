package com.example.book2play.api.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class SqlTimeGsonSerializer implements JsonSerializer<Time> {
    public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
        var fmt = new SimpleDateFormat();
        fmt.applyPattern("HH:mm");
        return new JsonPrimitive(fmt.format(src));
    }
}

