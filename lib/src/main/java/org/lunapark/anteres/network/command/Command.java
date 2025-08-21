package org.lunapark.anteres.network.command;


import org.lunapark.anteres.network.ApiErrors;
import org.lunapark.anteres.network.ApiException;
import org.lunapark.anteres.network.IUserView;
import org.lunapark.anteres.network.Log;
import org.lunapark.anteres.network.NetCallback;
import org.lunapark.anteres.network.ServerResponse;

public abstract class Command<T> implements Runnable {

    private final IUserView userView;
    private final NetCallback<T> callback;

    protected Command(IUserView userView, NetCallback<T> callback) {
        this.userView = userView;
        this.callback = callback;
    }

    @Override
    public void run() {
        ServerResponse<T> serverResponse = new ServerResponse<>();
        try {
            serverResponse.setResult(request());
        } catch (Exception e) {
            Log.e("Error: %s", e.getMessage());
            int errorCode = ApiErrors.GENERAL_ERROR;
            if (e instanceof ApiException) errorCode = ((ApiException) e).getErrorCode();
            serverResponse.setErrorCode(errorCode);
            serverResponse.setErrorMessage(e.getMessage());
        }
        if (userView != null) userView.getUiHandler().post(() -> {
            userView.hideProgressDialog();
            Log.e("3rror code: %s", serverResponse.getErrorCode());
            if (serverResponse.getErrorCode() != 0) {
                userView.showError(serverResponse.getErrorMessage());
                callback.onFail(serverResponse);
            } else {
                callback.onDone(serverResponse.getResult());
            }
        });
        else if (callback != null) {
            if (serverResponse.getErrorCode() == 0) {
                callback.onDone(serverResponse.getResult());
            } else {
                callback.onFail(serverResponse);
            }
        } else Log.e("Null user view");
    }

    abstract public T request() throws Exception;

}