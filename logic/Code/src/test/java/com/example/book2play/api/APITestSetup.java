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
import org.junit.BeforeClass;

import java.io.IOException;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
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
    private static Executor EXEC;
    private static HttpClient CLIENT;

    @BeforeClass
    public static void setup() {
        EXEC = Executors.newFixedThreadPool(4);
        cityIDs = new ArrayList<>();
        cityIDs.add("City01");
        cityIDs.add("City02");

        sportCenterIDs = new ArrayList<>();
        sportCenterIDs.add("SportCenter01");
        sportCenterIDs.add("SportCenter02");

        courtIDs = new ArrayList<>();
        courtIDs.add("Court01");
        courtIDs.add("Court02");

        staffIDs = new ArrayList<>();
        staffIDs.add("Staff01");
        staffIDs.add("Staff02");

        playerIDs = new ArrayList<>();
        playerIDs.add("Player01");
        playerIDs.add("Player02");

        CLIENT = HttpClient.newHttpClient();
    }

    @Before
    public void runAPIServer() throws Exception {
        DS = new MockModelDataSource();
        BOOKING = new MockBookingModel(DS);
        CITY = new MockCityModel(DS);
        COURT = new MockCourtModel(DS);
        PLAYER = new MockPlayerModel(DS);
        SPORT_CENTER = new MockSportCenterModel(DS);
        STAFF = new MockStaffModel(DS);
        AUTH = new MockAuthenticator(DS);

        SRV = new MockServer(PORT, BOOKING, CITY, COURT, PLAYER, SPORT_CENTER, STAFF, AUTH);
        SRV.start(EXEC);
    }

    @After
    public void stopAPIServer() {
        SRV.stop();
    }

    protected HttpResponse<String> postJSON(URI uri, String token, Object obj) throws InterruptedException, IOException {
        var reqBuilder = HttpRequest.newBuilder(uri)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json");

        if (token != null) {
            reqBuilder.header("Token", token);
        }

        if (obj != null) {
            reqBuilder.POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(obj)));
        } else {
            reqBuilder.POST(HttpRequest.BodyPublishers.noBody());
        }

        return CLIENT.send(reqBuilder.build(), HttpResponse.BodyHandlers.ofString());
    }

    protected HttpResponse<String> getJSON(URI uri, String token, Map<String, List<String>> query) throws InterruptedException, IOException {
        if (query != null) {
            LOG.info(composeQuery(query));
            uri = URI.create(uri.toString() + composeQuery(query));
        }

        var reqBuilder = HttpRequest.newBuilder(uri)
                .GET()
                .header("Accept", "application/json");

        if (token != null) {
            reqBuilder.header("Token", token);
        }

        return CLIENT.send(reqBuilder.build(), HttpResponse.BodyHandlers.ofString());
    }


    protected HttpResponse<String> put(URI uri, String token, Map<String, List<String>> query) throws IOException, InterruptedException {
        if (query != null) {
            uri = URI.create(uri.toString() + composeQuery(query));
        }

        var reqBuilder = HttpRequest.newBuilder(uri)
                .PUT(HttpRequest.BodyPublishers.noBody())
                .header("Accept", "application/json");

        if (token != null) {
            reqBuilder.header("Token", token);
        }

        return CLIENT.send(reqBuilder.build(), HttpResponse.BodyHandlers.ofString());
    }

    protected HttpResponse<String> delete(URI uri, String token, Map<String, List<String>> query) throws IOException, InterruptedException {
        if (query != null) {
            uri = URI.create(uri.toString() + composeQuery(query));
        }

        var reqBuilder = HttpRequest.newBuilder(uri)
                .DELETE()
                .header("Accept", "application/json");

        if (token != null) {
            reqBuilder.header("Token", token);
        }

        return CLIENT.send(reqBuilder.build(), HttpResponse.BodyHandlers.ofString());
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
