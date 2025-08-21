package org.lunapark.anteres.network.utils;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.Base64;

import org.lunapark.anteres.network.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PdfUtils {
    public static ArrayList<Bitmap> createBitmapsFromPdf(File file) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        PdfRenderer pdfRenderer = null;
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            // This is the PdfRenderer we use to render the PDF.
            if (parcelFileDescriptor != null) {
                pdfRenderer = new PdfRenderer(parcelFileDescriptor);
                int totalPages = pdfRenderer.getPageCount();
                Log.e("Total pages: %s", totalPages);
                if (totalPages > 0)
                    for (int i = 0; i < totalPages; i++) {
                        PdfRenderer.Page currentPage = pdfRenderer.openPage(i);
                        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
                        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                        // We are ready to show the Bitmap to user.
                        bitmaps.add(bitmap);
                        currentPage.close();
                    }
            }
        } catch (Exception e) {
            Log.e("Error: %s", e.getMessage());
        } finally {
            try {
                if (parcelFileDescriptor != null) parcelFileDescriptor.close();
                if (pdfRenderer != null) pdfRenderer.close();
            } catch (IOException e) {
                Log.e("Error: %s", e.getMessage());
            }
        }
        return bitmaps;
    }

    public static String getHtmlFromPdf(File file) {
        ArrayList<Bitmap> bitmaps = createBitmapsFromPdf(file);
        if (!bitmaps.isEmpty()) {
            StringBuilder builder = new StringBuilder("<html><body>");
            for (Bitmap bitmap : bitmaps) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                String imageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                builder.append("<img src='");
                builder.append("data:image/png;base64,");
                builder.append(imageBase64);
                builder.append("'/><br><br>");
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    Log.e("Error: %s", e.getMessage());
                }
            }
            builder.append("</body></html>");
            return builder.toString();
        }

        return null;
    }
}
