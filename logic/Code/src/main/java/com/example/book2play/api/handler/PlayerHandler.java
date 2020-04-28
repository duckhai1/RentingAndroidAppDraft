package com.example.book2play.api.handler;

import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.AuthenticateModel;
import com.example.book2play.db.models.PlayerModel;
import com.example.book2play.types.Player;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;

public class PlayerHandler extends AbstractHandler {

    PlayerModel model;
    AuthenticateModel authenticatedModel;

    public PlayerHandler (PlayerModel model, AuthenticateModel authenticateModel) {
        super();
        this.model = model;
        this.authenticatedModel = authenticateModel;
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

    private void execGet(HttpExchange exchange){
    }
    private void execPost(HttpExchange exchange) throws IOException {
        try{
            var player = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), Player.class);
            model.createPlayer(player.getPlayerId());
            exchange.sendResponseHeaders(HTTPStatus.CREATED, -1);
        }catch (MySQLException | IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }
    private void execPut(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var newPlayerId = params.get("newPlayerId");
        var oldPlayerId = params.get("oldPlayerId");

        if((newPlayerId != null && newPlayerId.size() != 1)
            || (oldPlayerId != null && oldPlayerId.size() != 1)
        ){
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try{
            if(newPlayerId != null && oldPlayerId != null){
                model.updatePlayerId(newPlayerId.get(0), oldPlayerId.get(0));
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
