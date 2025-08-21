package org.lunapark.anteres.network.requests;


import org.lunapark.anteres.network.HttpHeader;

import java.io.IOException;
import java.net.HttpURLConnection;

public abstract class HttpRequest {
    private String method;
    private String url;
    private HttpHeader httpHeader;

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

    public HttpHeader getHeader() {
        return httpHeader;
    }

    public void setHeader(HttpHeader httpHeader) {
        this.httpHeader = httpHeader;
    }
}
