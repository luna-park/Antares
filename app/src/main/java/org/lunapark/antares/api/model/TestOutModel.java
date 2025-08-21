package org.lunapark.antares.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestOutModel {

    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("url")
    @Expose
    private String url;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
