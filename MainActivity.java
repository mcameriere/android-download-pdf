package com.example.pdfexperiment;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edUrl = findViewById(R.id.edUrl);
        edUrl.setText("https://www.website.be/name.pdf");
    }

    public void onClickPDF(View view) {
        String pdfUrl = edUrl.getText().toString();
        Toast.makeText(this, "CLICKED", Toast.LENGTH_SHORT).show();
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Uri parsedUrl = Uri.parse(pdfUrl);
            intent.setDataAndType(parsedUrl, "application/pdf");
            startActivity(intent);
        } catch (ActivityNotFoundException noPdfReaderFound) {
            Toast.makeText(this, noPdfReaderFound.getMessage(), Toast.LENGTH_SHORT).show();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl));
            startActivity(browserIntent);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickDownload(View view) {
        Uri uri = Uri.parse(edUrl.getText().toString());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("Downloading a file");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "file.pdf");
        ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
    }
}
