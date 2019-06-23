package com.yanshun.mfluitmarket.base.util;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

public class InterceptorUtil {
    //日志拦截器
    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w("FMarket","log:" + message);
           }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

}
