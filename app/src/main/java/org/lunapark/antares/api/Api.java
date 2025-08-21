package org.lunapark.antares.api;

import android.content.Context;

import org.lunapark.antares.api.model.Item;
import org.lunapark.antares.api.model.TestModel;
import org.lunapark.antares.api.model.TestOutModel;
import org.lunapark.anteres.network.IUserView;
import org.lunapark.anteres.network.NetCallback;
import org.lunapark.anteres.network.command.Command;
import org.lunapark.anteres.network.utils.FileUtils;
import org.lunapark.anteres.network.utils.PdfUtils;

import java.io.File;
import java.util.List;

public class Api extends RestApi {

    public Api(String url) {
        super(url);
    }

    public void getFile(Context context, IUserView userView, String url, NetCallback<String> callback) {

        Command<String> command = new Command<>(userView, callback) {
            @Override
            public String request() throws Exception {
                byte[] bytes = networkCore.getByteStream(url);
                File file = FileUtils.saveBytesAsFileToDownloadFolder(context, bytes, "temp", "tmp");
                return PdfUtils.getHtmlFromPdf(file);
            }
        };
        executorService.execute(command);
    }

    public void testGetRequest(IUserView userView, NetCallback<List<Item>> callback) {
        Command<List<Item>> command = new Command<>(userView, callback) {
            @Override
            public List<Item> request() throws Exception {
                return getRequest();
            }
        };
        executorSingleService.execute(command);
    }

    public void testGetParamRequest(IUserView userView, String param, NetCallback<TestModel> callback) {
        Command<TestModel> command = new Command<>(userView, callback) {
            @Override
            public TestModel request() throws Exception {
                return getRequest(param);
            }
        };
        executorService.execute(command);
    }

    public void testPostRequest(IUserView userView, TestOutModel param, NetCallback<TestModel> callback) {
        Command<TestModel> command = new Command<>(userView, callback) {
            @Override
            public TestModel request() throws Exception {
                String json = gson.toJson(param, TestOutModel.class);
                return postRequest(json);
            }
        };
        executorService.execute(command);
    }

}