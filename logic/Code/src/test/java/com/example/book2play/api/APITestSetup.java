package com.example.book2play.api;

import com.example.book2play.api.utils.SqlTimeGsonDeserializer;
import com.example.book2play.api.utils.SqlTimeGsonSerializer;
import com.example.book2play.api.utils.SqlTimestampGsonDeserializer;
import com.example.book2play.api.utils.SqlTimestampGsonSerializer;
import com.example.book2play.db.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Before;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class APITestSetup {

    public final static String HOST = "localhost";
    public final static int PORT = 6666;

    public final static URI BOOKING_API_PATH = URI.create("http://" + HOST + ':' + PORT + Server.BOOKING_BASE_URL);
    public final static URI CITY_API_PATH = URI.create("http://" + HOST + ':' + PORT + Server.CITY_BASE_URL);
    public final static URI COURT_API_PATH = URI.create("http://" + HOST + ':' + PORT + Server.COURT_BASE_URL);
    public final static URI PLAYER_API_PATH = URI.create("http://" + HOST + ':' + PORT + Server.PLAYER_BASE_URL);
    public final static URI SPORT_CENTER_API_PATH = URI.create("http://" + HOST + ':' + PORT + Server.SPORT_CENTER_BASE_URL);
    public final static URI STAFF_API_PATH = URI.create("http://" + HOST + ':' + PORT + Server.STAFF_BASE_URL);

    protected final static Logger LOG = Logger.getLogger("API_TEST");
    protected final static Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd")
            .registerTypeAdapter(Time.class, new SqlTimeGsonSerializer())
            .registerTypeAdapter(Time.class, new SqlTimeGsonDeserializer())
            .registerTypeAdapter(Timestamp.class, new SqlTimestampGsonSerializer())
            .registerTypeAdapter(Timestamp.class, new SqlTimestampGsonDeserializer())
            .create();

    protected static MockModelDataSource DS;
    protected static MockAuthenticator AUTH;
    protected static MockBookingModel BOOKING;
    protected static MockCityModel CITY;
    protected static MockCourtModel COURT;
    protected static MockPlayerModel PLAYER;
    protected static MockSportCenterModel SPORT_CENTER;
    protected static MockStaffModel STAFF;

    protected static ArrayList<String> cityIDs;
    protected static ArrayList<String> sportCenterIDs;
    protected static ArrayList<String> courtIDs;
    protected static ArrayList<String> staffIDs;
    protected static ArrayList<String> playerIDs;

    private static MockServer SRV;

    @Before
    public void runAPIServer() throws Exception {
        cityIDs = new ArrayList<>();
        cityIDs.add("City01");
        cityIDs.add("City02");
        cityIDs.add("City03");

        sportCenterIDs = new ArrayList<>();
        sportCenterIDs.add("SportCenter01");
        sportCenterIDs.add("SportCenter02");
        sportCenterIDs.add("SportCenter03");

        courtIDs = new ArrayList<>();
        courtIDs.add("Court01");
        courtIDs.add("Court02");
        courtIDs.add("Court03");

        staffIDs = new ArrayList<>();
        staffIDs.add("Staff01");
        staffIDs.add("Staff02");
        staffIDs.add("Staff03");

        playerIDs = new ArrayList<>();
        playerIDs.add("Player01");
        playerIDs.add("Player02");
        playerIDs.add("Player03");


        DS = new MockModelDataSource();
        BOOKING = new MockBookingModel(DS);
        CITY = new MockCityModel(DS);
        COURT = new MockCourtModel(DS);
        PLAYER = new MockPlayerModel(DS);
        SPORT_CENTER = new MockSportCenterModel(DS);
        STAFF = new MockStaffModel(DS);
        AUTH = new MockAuthenticator(DS);

        SRV = new MockServer(PORT, BOOKING, CITY, COURT, PLAYER, SPORT_CENTER, STAFF, AUTH);
        SRV.start();
    }

    @After
    public void stopAPIServer() {
        SRV.stop();
    }

    protected CompletableFuture<HttpResponse<String>> asyncGetJSON(URI uri, String token, Map<String, List<String>> query) {
        if (query != null) {
            uri = URI.create(uri.toString() + composeQuery(query));
        }
        var reqBuilder = HttpRequest.newBuilder(uri);

        if (token != null) {
            reqBuilder.header("Token", token);
        }

        var request = reqBuilder
                .header("Accept", "application/json")
                .GET()
                .build();

        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    protected CompletableFuture<HttpResponse<String>> asyncPostJSON(URI uri, String token, Object obj) {
        var reqBuilder = HttpRequest.newBuilder(uri);

        if (token != null) {
            reqBuilder.header("Token", token);
        }

        var request = reqBuilder
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(obj)))
                .build();

        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    protected CompletableFuture<HttpResponse<String>> asyncPut(URI uri, String token, Map<String, List<String>> query) {
        if (query != null) {
            uri = URI.create(uri.toString() + composeQuery(query));
        }
        var reqBuilder = HttpRequest.newBuilder(uri);

        if (token != null) {
            reqBuilder.header("Token", token);
        }

        var request = reqBuilder
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    protected CompletableFuture<HttpResponse<String>> asyncDelete(URI uri, String token, Map<String, List<String>> query) {
        if (query != null) {
            uri = URI.create(uri.toString() + composeQuery(query));
        }
        var reqBuilder = HttpRequest.newBuilder(uri);

        if (token != null) {
            reqBuilder.header("Token", token);
        }

        var request = reqBuilder
                .header("Accept", "application/json")
                .DELETE()
                .build();

        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    protected String composeQuery(Map<String, List<String>> data) {
        var query = new StringBuilder();
        for (var k : data.keySet()) {
            for (var v : data.get(k)) {
                query.append('&');
                query.append(encode(k));
                query.append('=');
                query.append(encode(v));
            }
        }
        query.replace(0, 1, "?");

        return query.toString();
    }

    private String encode(final String decoded) {
        return decoded == null ? null : URLEncoder.encode(decoded, StandardCharsets.UTF_8);
    }

}
