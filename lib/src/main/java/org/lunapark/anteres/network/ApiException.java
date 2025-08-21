package org.lunapark.anteres.network;

import androidx.annotation.Nullable;

public class ApiException extends Exception {

    private int errorCode = ApiErrors.GENERAL_ERROR;
    private String errorMessage = "Ошибка";

    public ApiException() {
    }

    public ApiException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Nullable
    @Override
    public String getMessage() {
        return errorMessage;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
