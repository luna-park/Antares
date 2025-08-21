package org.lunapark.anteres.network;

public interface NetCallback<T> extends GenericCallback<T> {
    void onFail(ServerResponse<T> response);
}
