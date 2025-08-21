package org.lunapark.anteres.network.requests;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public class TextHttpRequest extends HttpRequest {

    private final String text;

    public TextHttpRequest(String text) {
        this.text = text;
    }

    @Override
    public String getBodyString() {
        return this.text;
    }

    @Override
    public void prepareConnection(HttpURLConnection connection) throws IOException {
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "text/plain");
        try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
            os.writeBytes(getBodyString());
            os.flush();
        }
    }
}