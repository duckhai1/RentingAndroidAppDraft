package com.example.book2play.api.handler;

import com.example.book2play.api.TokenAuthenticator;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.SportCenterModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.SportCenter;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;

public class SportCentersHandler extends AbstractHandler {

    SportCenterModel model;

    public SportCentersHandler(SportCenterModel model, TokenAuthenticator authModel) {
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
            } else {
                exchange.sendResponseHeaders(HTTPStatus.METHOD_NOT_ALLOWED, -1);// 405 Method Not Allowed
            }
        } catch (RuntimeException e) {
            LOG.severe("Unexpected exception " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.INTERNAL_SERVER_ERROR, e);
        }

        exchange.close();
    }

    private void execGet(HttpExchange exchange) throws IOException {
        var token = exchange.getRequestHeaders().get("Token");
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var cityId = params.get("cityId");

        if ((token == null || token.size() != 1)
                || (cityId == null || cityId.size() != 1)) {
            LOG.warning("Request was unsuccessful, invalid query");
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        if (auth.validatePlayer(token.get(0)) == null) {
            exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
            return;
        }

        try {
            var sportCenters = model.getCitySportCenters(cityId.get(0));
            responseWithJson(exchange, HTTPStatus.OK, sportCenters);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPost(HttpExchange exchange) throws IOException {
        SportCenter sportCenter;
        try {
            sportCenter = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), SportCenter.class);
        } catch (JsonParseException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        if (sportCenter.getCityId() == null || sportCenter.getSportCenterId() == null) {
            LOG.warning("Request was unsuccessful, invalid query");
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
            model.createCityCenter(
                    sportCenter.getSportCenterId(),
                    sportCenter.getCityId()
            );
            exchange.sendResponseHeaders(HTTPStatus.CREATED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPut(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var newSportCenterId = params.get("newSportCenterId");
        var oldSportCenterId = params.get("oldSportCenterId");
        var cityId = params.get("cityId");

        if ((newSportCenterId != null && newSportCenterId.size() != 1)
                || (oldSportCenterId != null && oldSportCenterId.size() != 1)
                || (cityId != null && cityId.size() != 1)) {
            LOG.warning("Request was unsuccessful, invalid query");
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }
        if (newSportCenterId == null || oldSportCenterId == null || cityId == null) {
            LOG.warning("Request was unsuccessful, invalid query");
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
            model.updateSportCenterId(newSportCenterId.get(0), oldSportCenterId.get(0), cityId.get(0));
            exchange.sendResponseHeaders(HTTPStatus.ACCEPTED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }
}
