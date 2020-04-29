package com.example.book2play.api;

import com.example.book2play.api.utils.SqlTimeGsonDeserializer;
import com.example.book2play.api.utils.SqlTimeGsonSerializer;
import com.example.book2play.api.utils.SqlTimestampGsonDeserializer;
import com.example.book2play.api.utils.SqlTimestampGsonSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.logging.Logger;

public class SetupAPITest {
    private final int PORT = 6666;
    private static MockServer SRV;

    protected final static Logger LOG = Logger.getLogger("API_TEST");
    protected final static Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd")
            .registerTypeAdapter(Time.class, new SqlTimeGsonSerializer())
            .registerTypeAdapter(Time.class, new SqlTimeGsonDeserializer())
            .registerTypeAdapter(Timestamp.class, new SqlTimestampGsonSerializer())
            .registerTypeAdapter(Timestamp.class, new SqlTimestampGsonDeserializer())
            .create();

    @Before
    public void runAPIServer() throws Exception {
        SRV = new MockServer(PORT);
        SRV.start();
    }
}
