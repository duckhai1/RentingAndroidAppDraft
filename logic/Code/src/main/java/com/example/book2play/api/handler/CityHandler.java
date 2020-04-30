package com.example.book2play.api.handler;

import com.example.book2play.api.handler.utils.ConfirmToken;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.Authenticator;
import com.example.book2play.db.CityModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.City;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;

public class CityHandler extends AbstractHandler {

    CityModel model;

    public CityHandler(CityModel model, Authenticator authModel) {
        super(authModel);
        this.model = model;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if ("GET".equals(exchange.getRequestMethod())) {
                execGet(exchange);
            } else if ("POST".equals(exchange.getRequestMethod())) {
                execPost(exchange);
            } else if ("PUT".equals(exchange.getRequestMethod())) {
                execPut(exchange);
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                execDelete(exchange);
            } else {
                exchange.sendResponseHeaders(HTTPStatus.METHOD_NOT_ALLOWED, -1); // NOT ALLOWED
            }
        } catch (RuntimeException e) {
            LOG.severe("Unexpected exception " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.INTERNAL_SERVER_ERROR, e);
        }
        exchange.close();
    }

    private void execGet(HttpExchange exchange) throws IOException {
        var token = exchange.getRequestHeaders().get("Token");
        if (token == null || token.size() != 1) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }
        try {
            var id = ConfirmToken.getId(token.get(0));
            if (authModel.isPlayer(id)) {
                var cities = model.getCities();
                responseWithJson(exchange, HTTPStatus.OK, cities);
            } else {
                exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
            }
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPost(HttpExchange exchange) throws IOException {
        try {
            var city = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), City.class);
            model.createCity(city.getCityId());
            exchange.sendResponseHeaders(HTTPStatus.CREATED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        } catch (IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPut(HttpExchange exchange) {
    }

    private void execDelete(HttpExchange exchange) {
    }
}
