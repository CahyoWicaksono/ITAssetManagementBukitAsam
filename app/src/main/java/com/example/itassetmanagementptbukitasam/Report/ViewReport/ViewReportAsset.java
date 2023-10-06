package com.example.itassetmanagementptbukitasam.Report.ViewReport;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.R;

import java.net.URLEncoder;

public class ViewReportAsset extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_report_asset);

        webView = findViewById(R.id.view_asset_report);
        webView.getSettings().setJavaScriptEnabled(true);
        String filename = getIntent().getStringExtra("filename");
        String fileurl = getIntent().getStringExtra("fileurl");

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(filename);
        pd.setMessage("Please wait...");

        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String url = "";
        try {
            url = URLEncoder.encode(fileurl,"UTF-8");
        }catch (Exception ex)
        {}
        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
