package org.lunapark.antares;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.TextView;

import org.lunapark.antares.api.Api;
import org.lunapark.antares.api.model.Item;
import org.lunapark.antares.api.model.TestModel;
import org.lunapark.antares.api.model.TestOutModel;
import org.lunapark.anteres.network.IUserView;
import org.lunapark.anteres.network.Log;
import org.lunapark.anteres.network.NetCallback;
import org.lunapark.anteres.network.ServerResponse;

import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    private Api api;

    private IUserView userView;
    private final Handler handler = new Handler();
    private TextView tvResult;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        webView = findViewById(R.id.webview);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        api = new Api(getString(R.string.base_url));
        userView = new IUserView() {
            @Override
            public void showError(String errorMessage) {
                Log.e("Error: %s", errorMessage);
                appendText(errorMessage);
            }

            @Override
            public Handler getUiHandler() {
                return handler;
            }

            @Override
            public void showProgressDialog() {
                Log.e("Show progress");
            }

            @Override
            public void hideProgressDialog() {
                Log.e("Hide progress");
            }
        };

//        Log.setEnable(false);
        testGet();
        getPdf();
    }

    private void appendText(String str) {
        tvResult.post(() -> {
            StringBuilder stringBuilder = new StringBuilder(tvResult.getText().toString());
            stringBuilder.append(str).append("\n");
            tvResult.setText(stringBuilder);
        });
    }

    private void getPdf() {
        api.getFile(this, userView, getString(R.string.pdf_url), new NetCallback<>() {
            @Override
            public void onFail(ServerResponse<String> response) {
                Log.e("Get PDF error");
            }

            @Override
            public void onDone(String html) {
                webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", "");
            }
        });
    }

    private void testGet() {
        appendText("Test GET");
        api.testGetRequest(userView, new NetCallback<>() {
            @Override
            public void onFail(ServerResponse<List<Item>> response) {
                Log.e("onFail");
                appendText("FAIL");
            }

            @Override
            public void onDone(List<Item> items) {
                Log.e("onDone. items: %s", items.size());
                appendText("OK");
                appendText(String.format(Locale.ROOT, "List ITEMS size: %d", items.size()));

                testGetParam();
            }
        });
    }

    private void testGetParam() {
        appendText("Test GET with param");
        api.testGetParamRequest(userView, "value", new NetCallback<>() {
            @Override
            public void onFail(ServerResponse<TestModel> response) {
                appendText("FAIL");
            }

            @Override
            public void onDone(TestModel testModel) {
                Log.e("onDone. Limit: %s", testModel.getSuccess());
                appendText("OK");
                appendText(testModel.getSuccess());
                testPost1();
            }
        });
    }

    private void testPost1() {
        appendText("Test POST");
        TestOutModel model = new TestOutModel();
        model.setOrigin("OR1GiN");
        model.setUrl("https://www.luna-park.org/");
        api.testPostRequest(userView, model, new NetCallback<>() {
            @Override
            public void onFail(ServerResponse<TestModel> response) {
                Log.e("Fail");
                appendText("POST FAIL");
            }

            @Override
            public void onDone(TestModel testModel) {
                Log.e("POST Ok");
                appendText("POST OK\n" + testModel.toString());
            }
        });
    }
}