package com.example.week02;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;



public class OKHttpTest {
    private static String BASE_URL = "http://localhost:8801";

    private OkHttpClient client = new OkHttpClient();

    @Test
    public void whenGetRequest_thenCorrect() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        // 下面的调用会报java.net.SocketException: Connection reset
        System.out.println(response.body().string());
        Assertions.assertEquals(200, response.code());
    }
}
