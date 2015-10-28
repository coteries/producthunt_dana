package com.example.kianfar.producthunt_danakianfar.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Makes HTTP calls
 */
public class HttpUtils {

    public static String GET = "GET";
    public static String PUT = "PUT";

    public static String makeHttpRequest(String call, String method) throws IOException {

        StringBuffer response = null;
        URL url = new URL(call);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

        // optional default is GET
        httpConnection.setRequestMethod(method);
        httpConnection.setReadTimeout(10 * 1000); // 10 seconds timeout

        //add request header
        httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

        httpConnection.setRequestProperty("Accept-Charset", "UTF-8");
        httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
        String inputLine;
        response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();

    }
}
