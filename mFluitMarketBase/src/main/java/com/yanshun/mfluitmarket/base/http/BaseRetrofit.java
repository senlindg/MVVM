package com.yanshun.mfluitmarket.base.http;

import com.yanshun.mfluitmarket.base.app.FMBaseApplication;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseRetrofit<T> {
    private T apiService;

    public abstract Class<T> setApiClass();

    public abstract T getApiService();
    /**
     * 初始化Retrofit
     */
    protected T initRetrofit (String baseUrl) {
        if (apiService == null) {
            Retrofit mRetrofit = new Retrofit.Builder()
                    .client(FMBaseApplication.getInstance().initOkHttp())
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiService = mRetrofit.create(setApiClass());
        }
        return apiService;
    }
}

