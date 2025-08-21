package org.lunapark.antares.api;

import android.net.Uri;

import org.lunapark.antares.api.model.Item;
import org.lunapark.antares.api.model.TestModel;
import org.lunapark.anteres.network.HttpMethod;
import org.lunapark.anteres.network.HttpSecurity;
import org.lunapark.anteres.network.RestClient;
import org.lunapark.anteres.network.json.DefaultJsonHandler;
import org.lunapark.anteres.network.json.GenericListJsonHandler;

import java.util.List;

public class RestApi extends RestClient {

    public RestApi(String baseUrl) {
        super(baseUrl);
    }

    protected List<Item> getRequest() throws Exception {
        return networkCore.getJsonResponse(
                new Uri.Builder()
                        .path("echo/get/json/page/1")
                        .build(),
                HttpMethod.GET,
                HttpSecurity.SECURED,
//                new DefaultJsonHandler<>(gson, TestModel.class)
        new GenericListJsonHandler<>(gson, "items", Item.class)
        );
    }

    protected TestModel getRequest(String param) throws Exception {
        return networkCore.getJsonResponse(
                new Uri.Builder()
                        .path("echo/get/json")
                        .appendQueryParameter("param", param)
                        .build(),
                HttpMethod.GET,
                HttpSecurity.SECURED,
                new DefaultJsonHandler<>(gson, TestModel.class)
        );
    }

    protected TestModel postRequest(String json) throws Exception {
        return networkCore.getJsonResponse(
                new Uri.Builder()
                        .path("/echo/post/json")
                        .build(),
                json,
                HttpMethod.POST,
                HttpSecurity.SECURED,
                new DefaultJsonHandler<>(gson, TestModel.class)
        );
    }
}