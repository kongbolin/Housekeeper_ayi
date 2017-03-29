package com.edu.xhu.housekeeper.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.xhu.housekeeper.R;

/**
 * Created by skysoft on 2017/3/28.
 */
public class XieyiActivity extends BaseActivity implements View.OnClickListener {
    private WebView webview;
    ImageView back;
    TextView title1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xieyi);
        webview = (WebView) findViewById(R.id.webView1);
        webview.loadUrl("http://m.ayilaile.com/ayapp/agreement.html");
        back = (ImageView)findViewById(R.id.BtBack);
        back.setOnClickListener(this);
        title1 = (TextView) findViewById(R.id.title);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                // TODO Auto-generated method stub
                title1.setText(title);
                super.onReceivedTitle(view, title);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub

                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
                webview.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.BtBack:
                    XieyiActivity.this.finish();
                break;
        }
    }
}
