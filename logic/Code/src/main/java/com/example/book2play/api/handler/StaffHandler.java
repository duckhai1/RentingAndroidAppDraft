package com.example.book2play.api.handler;

import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.StaffModel;
import com.example.book2play.types.Player;
import com.example.book2play.types.Staff;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;

public class StaffHandler extends AbstractHandler {
    private StaffModel model;

    public StaffHandler(StaffModel model){
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
                exchange.sendResponseHeaders(HTTPStatus.METHOD_NOT_ALLOWED, -1); // NOT ALLOWED
            }
        } catch (RuntimeException e) {
            LOG.severe("Unexpected exception " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.INTERNAL_SERVER_ERROR, e);
        }
        exchange.close();
    }

    private void execGet(HttpExchange exchange){}
    private void execPost(HttpExchange exchange) throws IOException {
        try{
            var staff = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), Staff.class);
            model.createStaff(
                    staff.getStaffId(),
                    staff.getCityId(),
                    staff.getSportCenterId()
            );
            exchange.sendResponseHeaders(HTTPStatus.CREATED, -1);
        }catch (MySQLException | IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }
    private void execPut(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var newStaffId = params.get("newStaffId");
        var oldStaffId = params.get("oldStaffId");
        var cityId = params.get("cityId");
        var sportCenterId = params.get("sportCenterId");

        if((newStaffId != null && newStaffId.size() != 1)
                || (oldStaffId != null && oldStaffId.size() != 1)
                || (cityId != null && cityId.size() != 1)
                || (sportCenterId != null && sportCenterId.size() != 1)
        ){
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try{
            if(newStaffId != null && oldStaffId != null && cityId != null && sportCenterId != null){
                model.updateStaffId(
                        newStaffId.get(0),
                        oldStaffId.get(0),
                        cityId.get(0),
                        sportCenterId.get(0)
                );
            } else{
                exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
                return;
            }
            exchange.sendResponseHeaders(HTTPStatus.ACCEPTED, -1);
        } catch (MySQLException | IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }
    private void execDelete(HttpExchange exchange){}
}
