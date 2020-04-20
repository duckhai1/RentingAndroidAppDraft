package com.example.book2play;

import com.example.book2play.presentation.RestServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        var app = new App();
        app.runExample();
        System.out.println("Comming to me my book2play guest, come on!!");
        try {
            RestServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
