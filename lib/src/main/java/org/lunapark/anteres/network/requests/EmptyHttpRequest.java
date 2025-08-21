package org.lunapark.anteres.network.requests;

import android.text.TextUtils;

import java.io.IOException;
import java.net.HttpURLConnection;

public class EmptyHttpRequest extends HttpRequest {
    private final String contentType;

    public EmptyHttpRequest(String contentType) {
        this.contentType = contentType;
    }

    public EmptyHttpRequest() {
        this(null);
    }

    @Override
    public void prepareConnection(HttpURLConnection connection) throws IOException {
        if (!TextUtils.isEmpty(contentType)) {
            connection.setRequestProperty("Content-Type", contentType);
        }
    }

    @Override
    public String getBodyString() {
        return null;
    }
}
