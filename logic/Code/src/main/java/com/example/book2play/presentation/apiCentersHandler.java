package com.example.book2play.presentation;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class apiCentersHandler extends apiHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            // TODO
        } else if("POST".equals(exchange.getRequestMethod())) {

        } else if("PUT".equals(exchange.getRequestMethod())) {

        } else if("DELETE".equals(exchange.getRequestMethod())) {

        }
        else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }
}
