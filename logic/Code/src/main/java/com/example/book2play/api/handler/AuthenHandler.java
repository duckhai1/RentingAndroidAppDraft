package com.example.book2play.api.handler;

import com.example.book2play.api.TokenAuthenticator;
import com.example.book2play.api.utils.HTTPStatus;

import com.example.book2play.db.PlayerModel;
import com.example.book2play.db.StaffModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Booking;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;

public class AuthenHandler extends AbstractHandler {

    PlayerModel pmodel;
    StaffModel smodel;

    public AuthenHandler(PlayerModel pmodel, StaffModel smodel, TokenAuthenticator authModel) {
        super(authModel);
        this.pmodel = pmodel;
        this.smodel = smodel;
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
            responseErrorAsJson(exchange, HTTPStatus.INTERNAL_SERVER_ERROR, e);
        }

        exchange.close();
    }

    private void execGet(HttpExchange exchange) {
    }

    private void execPost(HttpExchange exchange) throws IOException {
        try {
            var playerInfo = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), JsonObject.class);
            pmodel.signupPlayer(
                    playerInfo.get("playerId").getAsString(),
                    playerInfo.get("password").getAsString()
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
        try {
            var playerInfo = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), JsonObject.class);
            String token = pmodel.loginPlayer(
                    playerInfo.get("playerId").getAsString(),
                    playerInfo.get("password").getAsString()
            );
            LOG.info("generate token: " + token);
            responseWithJson(exchange, HTTPStatus.OK, token);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        } catch (IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }

    }

    private void execDelete(HttpExchange exchange) throws IOException{
        var token = exchange.getRequestHeaders().get("Token");
        if ((token == null || token.size() != 1)) {
            exchange.sendResponseHeaders(HTTPStatus.OK, -1);
            return;
        }

        try {
            pmodel.logoutPlayer(token.get(0));
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
