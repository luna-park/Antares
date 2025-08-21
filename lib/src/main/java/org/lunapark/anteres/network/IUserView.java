package org.lunapark.anteres.network;

import android.os.Handler;

public interface IUserView {

    void showError(String errorMessage);

    Handler getUiHandler();

    void showProgressDialog();

    void hideProgressDialog();
}
