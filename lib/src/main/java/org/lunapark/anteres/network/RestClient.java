package org.lunapark.anteres.network;

import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RestClient {
    public final ExecutorService executorService, executorSingleService;
    public final Gson gson = new Gson();
    public final NetworkCore networkCore;

    public RestClient(String baseUrl) {
        executorSingleService = Executors.newSingleThreadExecutor();
        executorService = Executors.newFixedThreadPool(5);
        networkCore = new NetworkCore(baseUrl);
    }
}
