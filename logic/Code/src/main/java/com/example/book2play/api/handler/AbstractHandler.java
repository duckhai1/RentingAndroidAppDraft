package com.example.book2play.api.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;

public abstract class AbstractHandler implements HttpHandler {

    protected final static Logger LOG = Logger.getLogger("HTTPHandler");
    protected final static Gson GSON = new Gson();

    protected void responseWithJson(HttpExchange exchange, int statusCode, Object body) throws IOException {
        var ostream = exchange.getResponseBody();
        var bodyBuf = GSON.toJson(body).getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bodyBuf.length);
        ostream.write(bodyBuf);
        ostream.flush();
        ostream.close();
    }

    protected void responseWithJsonException(HttpExchange exchange, int statusCode, Exception e) throws IOException {
        var ostream = exchange.getResponseBody();
        var jsonObj = new JsonObject();
        jsonObj.addProperty("error", true);
        jsonObj.addProperty("message", e.getMessage());

        var bodyBuf = GSON.toJson(jsonObj).getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bodyBuf.length);
        ostream.write(bodyBuf);
        ostream.flush();
        ostream.close();
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
