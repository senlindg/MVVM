package com.yanshun.mfluitmarket.base.activity;

import android.content.Intent;
import android.os.Bundle;

import com.yanshun.mfluitmarket.base.entity.CordovaUrlParams;

import org.apache.cordova.CordovaActivity;

import java.util.List;

/**
 * 不包含标题的CordovaActivity
 */
public class CordovaNoTitle extends CordovaActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        List<CordovaUrlParams> cordovaUrlParamList = intent.getParcelableArrayListExtra("cordovaUrlParamList");
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
}
