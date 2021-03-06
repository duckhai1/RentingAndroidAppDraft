package com.example.book2play.api.handler;

import com.example.book2play.api.TokenAuthenticator;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.PlayerModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class PlayerHandler extends AbstractHandler {

    PlayerModel model;

    public PlayerHandler(PlayerModel model, TokenAuthenticator authModel) {
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
                exchange.sendResponseHeaders(HTTPStatus.METHOD_NOT_ALLOWED, -1);// 405 Method Not Allowed
            }
        } catch (RuntimeException e) {
            LOG.severe("Unexpected exception " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.INTERNAL_SERVER_ERROR, e);
        }

        exchange.close();
    }

    private void execPost(HttpExchange exchange) throws IOException {
        var token = exchange.getRequestHeaders().get("Token");
        var tokenType = exchange.getRequestHeaders().get("TokenType");
        if ((token == null || token.size() != 1)
                || (tokenType == null || tokenType.size() != 1)
        ) {
            LOG.warning("Request was unsuccessful token not found");
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }


        String id = null;
        try {
            id = auth.getId(token.get(0), tokenType.get(0));
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
        if (id == null) {
            LOG.warning("Request was unsuccessful invalid token");
            exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
            return;
        }
        try {
            model.createPlayer(id);
            exchange.sendResponseHeaders(HTTPStatus.CREATED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPut(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var newPlayerId = params.get("newPlayerId");
        var oldPlayerId = params.get("oldPlayerId");

        if ((newPlayerId != null && newPlayerId.size() != 1)
                || (oldPlayerId != null && oldPlayerId.size() != 1)) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        if (newPlayerId == null || oldPlayerId == null) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
            model.updatePlayerId(newPlayerId.get(0), oldPlayerId.get(0));
            exchange.sendResponseHeaders(HTTPStatus.ACCEPTED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }
}
