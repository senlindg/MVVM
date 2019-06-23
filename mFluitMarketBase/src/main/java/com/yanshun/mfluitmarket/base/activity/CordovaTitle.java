package com.yanshun.mfluitmarket.base.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.yanshun.mfluitmarket.R;
import com.yanshun.mfluitmarket.base.entity.CordovaUrlParams;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import java.util.List;

/**
 * 包含标题的CordovaActivity
 */
public class CordovaTitle extends CordovaActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cordova_title);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        List<CordovaUrlParams> cordovaUrlParamList = intent.getParcelableArrayListExtra("cordovaUrlParamList");
        TextView titleText = (TextView)findViewById(R.id.cordova_title);
        titleText.setText(title);
        loadUrl(appendParams(url,cordovaUrlParamList));

    }

    /**
     * 拼接传递的参数
     */
    private String appendParams(String url, List<CordovaUrlParams> cordovaUrlParamList){
        StringBuffer urlappend = new StringBuffer() ;
        if (null != cordovaUrlParamList){
            urlappend.append(url).append("?");
            for (int i = 0,j = cordovaUrlParamList.size(); i < j; i++){
                if (null != cordovaUrlParamList.get(i)){
                    CordovaUrlParams cordovaUrlParams = cordovaUrlParamList.get(i);
                    if ( 0 == i){
                        urlappend.append(cordovaUrlParams.getKey()).append("=").append(cordovaUrlParams.getValue());
                    }else{
                        urlappend.append("&").append(cordovaUrlParams.getKey()).append("=").append(cordovaUrlParams.getValue());
                    }
                }
            }
        }else {
            urlappend.append(url);
        }
        return urlappend.toString();
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void createViews() {
        if (preferences.contains("BackgroundColor")) {
            try {
                int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
                // Background of activity:
                appView.getView().setBackgroundColor(backgroundColor);
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        appView.getView().requestFocusFromTouch();
    }

    @Override
    protected CordovaWebView makeWebView() {
        SystemWebView webView = (SystemWebView)findViewById(R.id.cordovaWebView);
        CordovaWebView cordovaWebView = new CordovaWebViewImpl(new SystemWebViewEngine(webView));
        return cordovaWebView;
    }
}
