package com.yanshun.mfluitmarket.base.http;

import com.yanshun.mfluitmarket.base.app.FMBaseApplication;
import com.yanshun.mfluitmarket.base.util.SharedPreferencesUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 2019/6/16 10:33
 * E-Mail Address：6331177@163.com
 */
public class CookieReadInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Cookie", SharedPreferencesUtils.getInstance().getParam(FMBaseApplication.appContext,"cookiess","").toString());
        builder.addHeader("Content-Type", "application/json");
        return chain.proceed(builder.build());
    }
}
