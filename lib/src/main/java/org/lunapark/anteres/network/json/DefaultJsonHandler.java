package org.lunapark.anteres.network.json;


import com.google.gson.Gson;

import org.lunapark.anteres.network.Log;

public class DefaultJsonHandler<T> implements JsonHandler<T> {
    private final Gson gson;
    private final Class<T> clazz;

    public DefaultJsonHandler(Gson gson, Class<T> clazz) {
        this.gson = gson;
        this.clazz = clazz;
    }

    @Override
    public T handle(String json) {
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            Log.e("Error: %s", e.getMessage());
            return null;
        }
    }
}
