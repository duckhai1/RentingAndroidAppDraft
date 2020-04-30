package com.example.book2play.api;

import com.example.book2play.api.utils.SqlTimeGsonDeserializer;
import com.example.book2play.api.utils.SqlTimeGsonSerializer;
import com.example.book2play.api.utils.SqlTimestampGsonDeserializer;
import com.example.book2play.api.utils.SqlTimestampGsonSerializer;
import com.example.book2play.db.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class APITestSetup {

    public final static String HOST = "localhost";
    public final static int PORT = 6666;

    protected final static Logger LOG = Logger.getLogger("API_TEST");
    protected final static Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd")
            .registerTypeAdapter(Time.class, new SqlTimeGsonSerializer())
            .registerTypeAdapter(Time.class, new SqlTimeGsonDeserializer())
            .registerTypeAdapter(Timestamp.class, new SqlTimestampGsonSerializer())
            .registerTypeAdapter(Timestamp.class, new SqlTimestampGsonDeserializer())
            .create();

    protected static MockAuthenticator AUTH;
    protected static MockBookingModel BOOKING;
    protected static MockCityModel CITY;
    protected static MockCourtModel COURT;
    protected static MockPlayerModel PLAYER;
    protected static MockSportCenterModel SPORT_CENTER;
    protected static MockStaffModel STAFF;

    private static MockServer SRV;

    @BeforeClass
    public static void runAPIServer() throws Exception {
        BOOKING = MockBookingModel.getInstance();
        CITY = MockCityModel.getInstance();
        COURT = MockCourtModel.getInstance();
        PLAYER = MockPlayerModel.getInstance();
        SPORT_CENTER = MockSportCenterModel.getInstance();
        STAFF = MockStaffModel.getInstance();
        AUTH = new MockAuthenticator(PLAYER, STAFF);

        SRV = MockServer.getInstance(PORT);
        SRV.start();
    }

    @AfterClass
    public static void stopAPIServer() throws Exception {
        SRV.stop();
    }

    @Before
    public void clearData() {
        BOOKING.clearBookings();
        CITY.clearCities();
        COURT.clearCourts();
        PLAYER.clearPlayers();
        SPORT_CENTER.clearSportCenters();
        STAFF.clearStaff();
    }

    protected CompletableFuture<HttpResponse<String>> asyncGetJSON(URI uri, Map<String, List<String>> query) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(uri.toString() + composeQuery(query)))
                .header("Accept", "application/json")
                .build();

        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    protected CompletableFuture<HttpResponse<String>> asyncPostJSON(URI uri, Object obj) {
        var request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(obj)))
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
