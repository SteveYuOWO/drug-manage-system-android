package com.littlepage.drug_manage_system_android.util;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple util for Rest API
 */
public class RestUtil {
    private static OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final String prefixUrl = "http://120.78.206.78:8081/drug/api/";

    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(prefixUrl + url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static String delete(String url) throws IOException {
        Request request = new Request.Builder()
                .url(prefixUrl + url)
                .delete()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(prefixUrl + url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
