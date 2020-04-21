package com.example.book2play.api.handler;

import com.example.book2play.db.SportCenterModel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SportCentersHandler extends AbstractHandler {

    SportCenterModel model;

    public SportCentersHandler(SportCenterModel model) {
        this.model = model;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            execGet(exchange);
        } else if ("POST".equals(exchange.getRequestMethod())) {
            execPost(exchange);
        } else if ("PUT".equals(exchange.getRequestMethod())) {
            execPut(exchange);
        } else if ("DELETE".equals(exchange.getRequestMethod())) {
            execDelete(exchange);
        }
        exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        exchange.close();
    }

    private void execGet(HttpExchange exchange) {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        if (!params.containsKey("cityId")) {

        }
    }

    private void execPost(HttpExchange exchange) {
    }

    private void execPut(HttpExchange exchange) {
    }

    private void execDelete(HttpExchange exchange) {
    }
}
