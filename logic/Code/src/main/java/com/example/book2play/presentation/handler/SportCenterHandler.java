package com.example.book2play.presentation.handler;

import com.example.book2play.db.SportCenterModel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class SportCenterHandler implements HttpHandler {

    SportCenterModel model;

    public SportCenterHandler(SportCenterModel model) {
        this.model = model;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
        } else if("POST".equals(exchange.getRequestMethod())) {
        } else if("PUT".equals(exchange.getRequestMethod())) {
        } else if("DELETE".equals(exchange.getRequestMethod())) {
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }
}
