package com.yanshun.mfluitmarket.base.http;

import com.yanshun.mfluitmarket.base.app.FMBaseApplication;
import com.yanshun.mfluitmarket.base.util.SharedPreferencesUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @Date 2019/6/16 10:41
 * @eMail 6331177@163.com
 * @description 保存cookies
 */
public class CookiesSaveInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            String header = originalResponse.header("Set-Cookie");
            SharedPreferencesUtils.getInstance().putParam(FMBaseApplication.appContext,"cookiess",header);
        }
        return originalResponse;
    }
}
