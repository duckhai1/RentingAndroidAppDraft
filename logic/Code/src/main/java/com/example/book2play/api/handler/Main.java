package com.example.book2play.api.handler;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String args[]) throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/api/hello", (exchange ->{
            if("GET".equals(exchange.getRequestMethod())) {
                String respText = "Hello!";
                exchange.sendResponseHeaders(200, respText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(respText.getBytes());
                output.flush();
            } else{

            } exchange.sendResponseHeaders(405, -1);

            exchange.close();
        }));
        server.setExecutor(null);
        server.start();
    }

    public static Map<String, List<String>> splitQuery(String query) {
        //if(query == null || "".equals(query)) {
        return Collections.emptyMap();
        //}
        /*
        return Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="),2 ))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));
    }

         */
    }
}
