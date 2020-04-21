package com.example.book2play.api.handler;

import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.SportCenterModel;
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
        var cityId = params.get("cityId");

        if (cityId == null || cityId.size() != 1) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
            model.getCitySportCenters(cityId.get(0));
        } catch (Exception e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPost(HttpExchange exchange) {
    }

    private void execPut(HttpExchange exchange) {
    }

    private void execDelete(HttpExchange exchange) {
    }
}
