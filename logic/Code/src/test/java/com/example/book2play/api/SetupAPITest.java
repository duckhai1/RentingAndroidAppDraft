package com.example.book2play.api;

import com.example.book2play.api.utils.SqlTimeGsonDeserializer;
import com.example.book2play.api.utils.SqlTimeGsonSerializer;
import com.example.book2play.api.utils.SqlTimestampGsonDeserializer;
import com.example.book2play.api.utils.SqlTimestampGsonSerializer;
import com.example.book2play.db.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class SetupAPITest {

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

        SRV = new MockServer(PORT, AUTH, BOOKING, CITY, COURT, PLAYER, SPORT_CENTER, STAFF);
        SRV.start();
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

    protected CompletableFuture<HttpResponse<String>> postJSON(URI uri, Object obj) throws IOException {
        var request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(obj)))
                .build();

        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }
}
