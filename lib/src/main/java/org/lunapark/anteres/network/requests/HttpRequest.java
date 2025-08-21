package org.lunapark.anteres.network.requests;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;

public abstract class HttpRequest {
    private String method;
    private String url;
    private HashMap<String, String> httpHeader;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public abstract String getBodyString();

    public abstract void prepareConnection(HttpURLConnection connection) throws IOException;

    public HashMap<String, String> getHeader() {
        return httpHeader;
    }

    public void setHeader(HashMap<String, String> httpHeader) {
        this.httpHeader = httpHeader;
    }
}
