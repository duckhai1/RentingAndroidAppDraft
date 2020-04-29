package com.example.book2play.api;

import com.example.book2play.types.City;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CityTest extends SetupAPITest {
    @Test
    public void testPostCity() throws Exception {
        var city = new City("HCM");

        // TODO: modularized and simplified this
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.uri(URI.create("http://localhost:6666/api/cities"));
        builder.POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(city)));
        HttpRequest request = builder
                .build();

        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        System.out.println(response.statusCode());
    }
}
