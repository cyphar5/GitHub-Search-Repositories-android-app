package com.java.githubsearch.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.java.githubsearch.R;
import com.java.githubsearch.utility.Constants;

public class RepoWebViewActivity extends AppCompatActivity {

    private ImageView cross;
    private TextView htmlUrl;
    private WebView webView;
    private String  url = "";
    private ProgressBar progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_web_view);

        cross = findViewById(R.id.arw_cross);
        htmlUrl = findViewById(R.id.arw_repo_url);
        webView = findViewById(R.id.arw_webview);
        progressDialog = findViewById(R.id.arw_progress_dialog);
        swipeRefreshLayout = findViewById(R.id.avsp_swipe);


        Intent i = getIntent();
        url = i.getStringExtra(Constants.REPO_HTML_URL);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progressDialog.setVisibility(View.GONE);
            }
        });

        htmlUrl.setText(url);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                progressDialog.setVisibility(View.VISIBLE);
                webView.reload();
            }
        });



    }


}
