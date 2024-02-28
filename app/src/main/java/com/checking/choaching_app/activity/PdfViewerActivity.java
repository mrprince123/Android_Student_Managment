package com.checking.choaching_app.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.checking.choaching_app.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfViewerActivity extends AppCompatActivity {
    private PDFView pdfView;
    private String pdfUrl;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        getSupportActionBar().hide();
        progressBar = findViewById(R.id.progressBar);

        pdfView = findViewById(R.id.pdfView);
        pdfUrl = getIntent().getStringExtra("pdfUrl");
        Log.e("PDF URL", pdfUrl);

        DownloadPdfTask downloadTask = new DownloadPdfTask();
        downloadTask.execute(pdfUrl);
    }

    private class DownloadPdfTask extends AsyncTask<String, Void, File> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected File doInBackground(String... urls) {
            String pdfUrl = urls[0];
            InputStream inputStream = null;
            BufferedInputStream bufferedInputStream = null;
            FileOutputStream fileOutputStream = null;
            File pdfFile = null;

            try {
                URL url = new URL(pdfUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = urlConnection.getInputStream();
                    bufferedInputStream = new BufferedInputStream(inputStream);

                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                    pdfFile = new File(storageDir, "temp.pdf");

                    fileOutputStream = new FileOutputStream(pdfFile);

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                    return pdfFile;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null)
                        fileOutputStream.close();
                    if (bufferedInputStream != null)
                        bufferedInputStream.close();
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(File pdfFile) {
            progressBar.setVisibility(View.GONE);
            if (pdfFile != null) {
                displayPdf(pdfFile);
            } else {
                // Handle the case when the PDF file download fails
                // Show an error message or take appropriate action
            }
        }
    }

    private void displayPdf(File pdfFile) {
        pdfView.fromFile(pdfFile)
                .defaultPage(0)
                .load();
    }
}


