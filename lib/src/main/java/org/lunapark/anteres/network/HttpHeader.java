package org.lunapark.anteres.network;

import org.lunapark.anteres.network.model.AuthTokenModel;

public class HttpHeader {
    private String token;

    private AuthTokenModel authTokenModel;
    private String userId;
    private String deviceId;
    private String xRequestId;
    private String xOperationId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getxRequestId() {
        return xRequestId;
    }

    public void setxRequestId(String xRequestId) {
        this.xRequestId = xRequestId;
    }

    public String getxOperationId() {
        return xOperationId;
    }

    public void setxOperationId(String xOperationId) {
        this.xOperationId = xOperationId;
    }

    public AuthTokenModel getAuthTokenModel() {
        return authTokenModel;
    }

    public void setAuthTokenModel(AuthTokenModel authTokenModel) {
        this.authTokenModel = authTokenModel;
    }
}
