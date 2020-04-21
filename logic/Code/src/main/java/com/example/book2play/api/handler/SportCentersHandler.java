package com.example.book2play.api.handler;

import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.SportCenterModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

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
        } else {
            exchange.sendResponseHeaders(HTTPStatus.METHOD_NOT_ALLOWED, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }

    private void execGet(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        if (!params.containsKey("cityId")) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        var cityId = params.get("cityId");
        if (cityId.size() != 1) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
            model.getSportCentersInCity(cityId.get(0));
        } catch (MySQLException e) {
            var statusCode = e.getStatusCode();
            var body = new JsonObject();
            body.addProperty("statusCode", e.getStatusCode());
            responseWithJSON(exchange, HTTPStatus.BAD_REQUEST, body);
        }
    }

    private void execPost(HttpExchange exchange) {
    }

    private void execPut(HttpExchange exchange) {
    }

    private void execDelete(HttpExchange exchange) {
    }
}
