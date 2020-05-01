package com.example.book2play.api.handler;

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
        try {
            var cities = model.getCities();
            responseWithJson(exchange, HTTPStatus.OK, cities);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPost(HttpExchange exchange) throws IOException {
        try {
            var city = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), City.class);
            if (city.getCityId() == null) {
                var e = new Exception("Invalid JSON");
                LOG.warning("Request was unsuccessful " + e.getMessage());
                responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
            }

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
}
