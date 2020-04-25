package com.example.book2play.api.handler;

import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.BookingModel;
import com.example.book2play.db.models.CourtModel;
import com.example.book2play.types.Court;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Collection;

public class CourtsHandler extends AbstractHandler {

    CourtModel model;

    public CourtsHandler(CourtModel model) {
        super();
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
                exchange.sendResponseHeaders(HTTPStatus.METHOD_NOT_ALLOWED, -1);// 405 Method Not Allowed
            }
        } catch (RuntimeException e) {
            LOG.severe("Unexpected exception " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.INTERNAL_SERVER_ERROR, e);
        }

        exchange.close();
    }

    public void execGet(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var cityId = params.get("cityId");
        var sportCenterId = params.get("sportCenterId");

        if ((cityId != null || cityId.size() != 1)
            || (sportCenterId != null || sportCenterId.size() != 1)
        ) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        Collection<Court> courts;
        try{
            if(cityId != null && sportCenterId == null) {
                courts = model.getCityCourts(cityId.get(0));
            }
            else if(cityId != null && sportCenterId != null){
                courts = model.getSportCenterCourts(
                        sportCenterId.get(0),
                        cityId.get(0)
                );
            } else {
                exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
                return;
            }
            responseWithJson(exchange, HTTPStatus.OK, courts);
        } catch (MySQLException | IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    public void execPost(HttpExchange exchange){
    }

    public void execPut(HttpExchange exchange){
    }

    public void execDelete(HttpExchange exchange){
    }
}
