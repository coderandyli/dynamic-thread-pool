package com.coderandyli.dtp.core.utils;

import okhttp3.*;

import java.io.IOException;

/**
 * the utils for OKHTTP
 *
 * @Date 2021/9/6 4:38 下午
 * @Created by lizhenzhen
 */
public class OkHttpUtils {

    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    public static String get(String url) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        ResponseBody body = response.body();
        if (body == null) return null;
        return body.string();
    }

    public static String post(String url, String json) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = httpClient.newCall(request).execute();
        ResponseBody body = response.body();
        if (body == null) return null;
        return body.string();
    }

}
