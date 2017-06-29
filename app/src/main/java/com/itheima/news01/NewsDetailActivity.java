package com.itheima.news01;

import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.itheima.news01.Bean.NewsEntity;

/**
 * Created by yls on 2017/6/28.
 */

public class NewsDetailActivity extends BaseActivity {
    private WebView mWebView;
    private ProgressBar mProgressBar;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initViews() {
       mProgressBar= (ProgressBar) findViewById(R.id.pb_01);
        initWebview();

    }

    private void initWebview() {
        mWebView= (WebView) findViewById(R.id.web_view);
        // 当点击WebView显示的网页的链接时，禁止使用其它浏览器打开
        mWebView.setWebViewClient(new WebViewClient());
        //设置webview支持javascript脚本
        mWebView.getSettings().setJavaScriptEnabled(true);
        //当webView加载网页时，显示加载网页的进度
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress==100){
                    mProgressBar.setVisibility(View.GONE);
                }else{
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                    System.out.print("-------------percent:"+newProgress);
                }
            }
        });




    }

    @Override
    public void initListener() {

    }

   @Override
    public void initData() {
       NewsEntity.ResultBean newsBean= (NewsEntity.ResultBean) getIntent().getSerializableExtra("news");
       mWebView.loadUrl(newsBean.getUrl());
       // 显示标题栏左上角的返回图标
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // 显示标题栏
       getSupportActionBar().setTitle(newsBean.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();   // 标题栏左上角的返回按钮，退出当前界面
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
