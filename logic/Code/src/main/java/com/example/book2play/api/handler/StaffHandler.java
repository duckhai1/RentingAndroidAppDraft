package com.example.book2play.api.handler;

import com.example.book2play.api.TokenAuthenticator;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.StaffModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Staff;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;

public class StaffHandler extends AbstractHandler {
    StaffModel model;

    public StaffHandler(StaffModel model, TokenAuthenticator authModel) {
        super(authModel);
        this.model = model;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if ("POST".equals(exchange.getRequestMethod())) {
                execPost(exchange);
            } else if ("PUT".equals(exchange.getRequestMethod())) {
                execPut(exchange);
            } else {
                exchange.sendResponseHeaders(HTTPStatus.METHOD_NOT_ALLOWED, -1); // NOT ALLOWED
            }
        } catch (RuntimeException e) {
            LOG.severe("Unexpected exception " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.INTERNAL_SERVER_ERROR, e);
        }
        exchange.close();
    }

    private void execPost(HttpExchange exchange) throws IOException {
        var token = exchange.getRequestHeaders().get("Token");
        try {
            var id = auth.getId(token.get(0));
            if (id == null) {
                exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
                return;
            }

            var staff = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), Staff.class);
            if (staff.getSportCenterId() == null || staff.getCityId() == null) {
                var e = new Exception("Invalid JSON");
                LOG.warning("Request was unsuccessful " + e.getMessage());
                responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
                return;
            }

            model.createStaff(id, staff.getCityId(), staff.getSportCenterId());
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
        var newStaffId = params.get("newStaffId");
        var oldStaffId = params.get("oldStaffId");
        var cityId = params.get("cityId");
        var sportCenterId = params.get("sportCenterId");

        if ((newStaffId != null && newStaffId.size() != 1)
                || (oldStaffId != null && oldStaffId.size() != 1)
                || (cityId != null && cityId.size() != 1)
                || (sportCenterId != null && sportCenterId.size() != 1)
        ) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
            if (newStaffId != null && oldStaffId != null && cityId != null && sportCenterId != null) {
                model.updateStaffId(
                        newStaffId.get(0),
                        oldStaffId.get(0),
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
