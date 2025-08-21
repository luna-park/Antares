package org.lunapark.anteres.network.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RawRes;
import androidx.core.content.FileProvider;

import org.lunapark.anteres.network.ApiErrors;
import org.lunapark.anteres.network.ApiException;
import org.lunapark.anteres.network.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static File saveBytesAsFileToDownloadFolder(Context context, byte[] bytes, String fileName, String fileExtension) throws ApiException {

//        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File directory = context.getFilesDir();
        String targetPdf = fileName + "." + fileExtension;
        File filePath = new File(directory, targetPdf);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            fos.write(bytes);
        } catch (IOException e) {
            Log.e("Error: %s", e.getMessage());
            throw new ApiException(ApiErrors.FILE_SYSTEM_ERROR, "Не удалось сохранить файл");
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ioe) {
                Log.e("Error while closing stream: ", ioe);
            }
        }
        return filePath;
    }

    @SuppressLint("ObsoleteSdkInt")
    public static Uri getUri(Context context, String authority, File file) {
        if (Build.VERSION.SDK_INT < 24) {
            return Uri.fromFile(file);
        } else {
            return FileProvider.getUriForFile(context, authority, file);
        }
    }

    /**
     * @noinspection ResultOfMethodCallIgnored
     */
    public static byte[] getBytesFromRaw(Context context, @RawRes int resId) {
        byte[] buffer = null;
        try {
            InputStream is = context.getResources().openRawResource(resId);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (Exception e) {
            Log.e("Error: %s", e.getMessage());
        }
        return buffer;
    }
}

