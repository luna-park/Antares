package org.lunapark.anteres.network.requests;

import android.text.TextUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public class JsonHttpRequest extends HttpRequest {
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public void prepareConnection(HttpURLConnection connection) throws IOException {
//        connection.setDoOutput(true);
//        connection.setRequestProperty("Content-Type", "application/json");
        if (getBody() != null) {
            connection.setDoOutput(true);
            try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
                os.writeBytes(getBody());
                os.flush();
            }
        }
    }

    @Override
    public String getBodyString() {
        return body;
    }

    public boolean hasBody() {
        return !TextUtils.isEmpty(body);
    }
}
