package org.lunapark.anteres.network;

import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import org.lunapark.anteres.network.json.JsonHandler;
import org.lunapark.anteres.network.requests.HttpRequest;
import org.lunapark.anteres.network.requests.JsonHttpRequest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkCore {

    private String userAgent = "Android";
    private int timeout = 0;
    private String baseUrl = "";

    private NetworkCore() {
    }

    public NetworkCore(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public NetworkCore(String baseUrl, int timeout) {
        this.baseUrl = baseUrl;
        this.timeout = timeout;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public <T> T getJsonResponse(
            Uri uri,
            @NonNull HttpMethod httpMethod,
            @NonNull HttpSecurity httpSecurity,
            JsonHandler<T> jsonHandler)
            throws Exception {
        return getJsonResponse(uri, null, httpMethod, httpSecurity, null, jsonHandler);
    }

    public  <T> T getJsonResponse(
            Uri uri,
            String json,
            @NonNull HttpMethod httpMethod,
            @NonNull HttpSecurity httpSecurity,
            JsonHandler<T> jsonHandler)
            throws Exception {
        return getJsonResponse(uri, json, httpMethod, httpSecurity, null, jsonHandler);
    }

    public <T> T getJsonResponse(
            Uri uri,
            @NonNull HttpMethod httpMethod,
            @NonNull HttpSecurity httpSecurity,
            HttpHeader httpHeader,
            JsonHandler<T> jsonHandler)
            throws Exception {
        return getJsonResponse(uri, null, httpMethod, httpSecurity, httpHeader, jsonHandler);
    }

    public <T> T getJsonResponse(
            Uri uri,
            String json,
            @NonNull HttpMethod httpMethod,
            @NonNull HttpSecurity httpSecurity,
            HttpHeader httpHeader,
            JsonHandler<T> jsonHandler)
            throws Exception {

        JsonHttpRequest httpRequest = new JsonHttpRequest();
        httpRequest.setMethod(httpMethod.toString());
        httpRequest.setBody(json);
        httpRequest.setHeader(httpHeader);
        return getJsonResponse(uri, httpRequest, httpSecurity, jsonHandler);
    }

    protected <T> T getJsonResponse(
            Uri uri,
            @NonNull HttpRequest httpRequest,
            @NonNull HttpSecurity httpSecurity,
            JsonHandler<T> jsonHandler)
            throws ApiException {

        String url = uri.toString();
        if (!url.startsWith("http"))
            url = getBaseUrl() + url;
        httpRequest.setUrl(url);
        String answer = null;
        Log.d("Request: %s %s", httpRequest.getMethod(), httpRequest.getUrl());
        String requestBodyString = httpRequest.getBodyString();
        if (!TextUtils.isEmpty(requestBodyString))
            Log.d("Request body: %s", requestBodyString);
//            if (httpSecurity == HttpSecurity.SECURED) answer = getResponse(httpRequest);
//            else answer = getResponse(httpRequest);
        answer = getResponse(httpRequest);
        return jsonHandler.handle(answer);
    }

    public String getResponse(HttpRequest request) throws ApiException {
        StringBuilder response = new StringBuilder();
        HttpURLConnection connection = null;
        try {
//            URL url = new URI(request.getUrl()).toURL();
            URL url = new URL(request.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request.getMethod());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", userAgent);
            if (timeout != 0)
                connection.setConnectTimeout(timeout);

            HttpHeader httpHeader = request.getHeader();
            if (httpHeader != null) {

//                AuthTokenModel authTokenModel = httpHeader.getAuthTokenModel();
//
//                if (authTokenModel != null) {
//                    String authParam = null;
//                    if (authTokenModel.getTokenType() != null && authTokenModel.getAccessToken() != null) {
//                        authParam = String.format("%s %s", authTokenModel.getTokenType(), authTokenModel.getAccessToken());
//                    } else if (authTokenModel.getAccessToken() != null) {
//                        authParam = authTokenModel.getAccessToken();
//                    }
//                    if (authParam != null)
//                        connection.setRequestProperty("Authorization", authParam);
//                }

                if (httpHeader.getToken() != null)
                    connection.setRequestProperty("Authorization", String.format("Bearer %s", httpHeader.getToken()));

//                if (httpHeader.getUserId() != null)
//                    connection.setRequestProperty("User-id", httpHeader.getUserId());
//
//                if (httpHeader.getDeviceId() != null)
//                    connection.setRequestProperty("Device-id", httpHeader.getDeviceId());
//
//                if (httpHeader.getxRequestId() != null)
//                    connection.setRequestProperty("X-Request-Id", httpHeader.getxRequestId());
//
//                if (httpHeader.getxOperationId() != null)
//                    connection.setRequestProperty("X-Operation-Id", httpHeader.getxOperationId());

                Log.d("Request header: %s", connection.getRequestProperties().toString());
            }

            request.prepareConnection(connection);
            int responseCode = connection.getResponseCode();

            Log.w("HTTP Response code: %s", responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK ||
                    responseCode == HttpURLConnection.HTTP_CREATED ||
                    responseCode == HttpURLConnection.HTTP_NO_CONTENT) {

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line); // Adds every line to response till the end of file.
                    }
                }
                Log.w("Response: %s", response.toString());
            } else {
//                Log.e("HTTP Response code - %s", responseCode);
                throw new ApiException(ApiErrors.HTTP_ERROR, "Check URL");
            }
//            connection.disconnect();
        } catch (Exception e) {
            Log.e("Error: %s", e.getMessage());
            throw new ApiException(ApiErrors.CONNEXION_PROBLEM, "Нет соединения");
        } finally {
            Log.d("Close connexion");
            if (connection != null)
                connection.disconnect();
        }
        return response.toString();
    }

    public byte[] getByteStream(String fileUrl) throws Exception {
        Log.e("Get file: %s", fileUrl);

        InputStream inputStream = null;
        HttpURLConnection connection = null;
        byte[] result = null;
        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                inputStream = new BufferedInputStream(connection.getInputStream());
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[16384];

                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                result = buffer.toByteArray();
            }

        } catch (Exception e) {
            Log.e("Error: %s", e.getMessage());
            throw new ApiException(ApiErrors.CONNEXION_PROBLEM, "Ошибка при получении файла");
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return result;
    }
}