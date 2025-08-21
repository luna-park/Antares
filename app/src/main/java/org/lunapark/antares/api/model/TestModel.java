package org.lunapark.antares.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TestModel implements Serializable {

    @SerializedName("items")
    @Expose
    private List<Item> items;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("success")
    @Expose
    private String success;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSuccess() {
        return success;
    }
}