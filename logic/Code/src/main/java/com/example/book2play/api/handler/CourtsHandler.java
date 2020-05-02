package com.example.book2play.api.handler;

import com.example.book2play.api.TokenAuthenticator;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.CourtModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Court;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class CourtsHandler extends AbstractHandler {

    CourtModel model;

    public CourtsHandler(CourtModel model, TokenAuthenticator authModel) {
        super(authModel);
        this.model = model;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            LOG.info(exchange.getRequestMethod());
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
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var token = exchange.getRequestHeaders().get("Token");
        var cityId = params.get("cityId");
        var sportCenterId = params.get("sportCenterId");

        if ((token == null || token.size() != 1)
                || (cityId != null && cityId.size() != 1)
                || (sportCenterId != null && sportCenterId.size() != 1)
        ) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        Collection<Court> courts;
        try {
            if (cityId != null && sportCenterId == null) {
                courts = model.getCityCourts(cityId.get(0));
            } else if (cityId != null) {
                if (auth.validatePlayer(token.get(0)) == null) {
                    exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
                    return;
                }
                courts = model.getSportCenterCourts(
                        sportCenterId.get(0),
                        cityId.get(0)
                );
            } else {
                exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
                return;
            }
            responseWithJson(exchange, HTTPStatus.OK, courts);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        } catch (IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPost(HttpExchange exchange) throws IOException {
        try {
            var court = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), Court.class);
            if (court.getCourtId() == null || court.getSportCenterId() == null || court.getCityId() == null) {
                var e = new Exception("Invalid JSON");
                LOG.warning("Request was unsuccessful " + e.getMessage());
                responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
                return;
            }
            model.createCityCenterCourt(
                    court.getCourtId(),
                    court.getCityId(),
                    court.getSportCenterId()
            );
            exchange.sendResponseHeaders(HTTPStatus.CREATED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        } catch (IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPut(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var newCourtId = params.get("newCourtId");
        var oldCourtId = params.get("oldCourtId");
        var sportCenterId = params.get("sportCenterId");
        var cityId = params.get("cityId");

        if ((newCourtId != null && newCourtId.size() != 1)
                || (oldCourtId != null && oldCourtId.size() != 1)
                || (sportCenterId != null && sportCenterId.size() != 1)
                || (cityId != null && cityId.size() != 1)
        ) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
            if (newCourtId != null && oldCourtId != null && sportCenterId != null && cityId != null) {
                model.updateCourtId(
                        newCourtId.get(0),
                        oldCourtId.get(0),
                        cityId.get(0),
                        sportCenterId.get(0)
                );
            } else {
                exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
                return;
            }
            exchange.sendResponseHeaders(HTTPStatus.ACCEPTED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        } catch (IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }

    }
}
