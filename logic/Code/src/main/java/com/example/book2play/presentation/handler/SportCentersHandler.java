package com.example.book2play.presentation.handler;

import com.example.book2play.db.SportCenterModel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class SportCentersHandler implements HttpHandler {

    SportCenterModel model;

    public SportCentersHandler(SportCenterModel model) {
        this.model = model;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            handleGet(exchange);
            return;
        }

        if ("POST".equals(exchange.getRequestMethod())) {
        }

        if ("PUT".equals(exchange.getRequestMethod())) {
        }

        if ("DELETE".equals(exchange.getRequestMethod())) {
        }

        exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        exchange.close();
    }

    private void handleGet(HttpExchange exchange) throws IOException {

    }
}
