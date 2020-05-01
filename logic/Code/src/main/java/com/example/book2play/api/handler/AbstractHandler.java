package com.example.book2play.api.handler;

import com.example.book2play.api.utils.SqlTimeGsonDeserializer;
import com.example.book2play.api.utils.SqlTimeGsonSerializer;
import com.example.book2play.api.utils.SqlTimestampGsonDeserializer;
import com.example.book2play.api.utils.SqlTimestampGsonSerializer;
import com.example.book2play.db.Authenticator;
import com.example.book2play.db.exceptions.MySQLException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;

/**
 * Predefined handler methods for dealing with encoded uri and json data
 */
public abstract class AbstractHandler implements HttpHandler {

    protected final static Logger LOG = Logger.getLogger("HTTPHandler");
    protected final static Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd")
            .registerTypeAdapter(Time.class, new SqlTimeGsonSerializer())
            .registerTypeAdapter(Time.class, new SqlTimeGsonDeserializer())
            .registerTypeAdapter(Timestamp.class, new SqlTimestampGsonSerializer())
            .registerTypeAdapter(Timestamp.class, new SqlTimestampGsonDeserializer())
            .create();
    protected Authenticator authModel;

    public AbstractHandler(Authenticator authModel) {
        this.authModel = authModel;
    }

    protected void responseWithJson(HttpExchange exchange, int statusCode, Object body) throws IOException {
        var headers = exchange.getResponseHeaders();
        headers.add("content-type", "application/json");

        var bodyBuf = GSON.toJson(body).getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bodyBuf.length);

        var ostream = exchange.getResponseBody();
        ostream.write(bodyBuf);
        ostream.flush();
        ostream.close();
    }

    protected void responseErrorAsJson(HttpExchange exchange, int statusCode, MySQLException e) throws IOException {
        var jsonObj = new JsonObject();
        jsonObj.addProperty("hasError", true);
        jsonObj.addProperty("statusCode", e.getStatusCode());
        jsonObj.addProperty("message", e.getMessage());

        responseWithJson(exchange, statusCode, jsonObj);
    }

    protected void responseErrorAsJson(HttpExchange exchange, int statusCode, Exception e) throws IOException {
        var jsonObj = new JsonObject();
        jsonObj.addProperty("hasError", true);
        jsonObj.addProperty("message", e.getMessage());

        responseWithJson(exchange, statusCode, jsonObj);
    }

    // convert from query in URI to Json
    protected Map<String, List<String>> splitQuery(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));

    }

    private String decode(final String encoded) {
        return encoded == null ? null : URLDecoder.decode(encoded, StandardCharsets.UTF_8);
    }
}
