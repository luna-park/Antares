package org.lunapark.anteres.network.json;

public interface JsonHandler<T> {
    T handle(String json);
}
