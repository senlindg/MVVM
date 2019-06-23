package com.yanshun.mfluitmarket.common.http;

import com.yanshun.mfluitmarket.base.entity.HttpResponse;
import com.yanshun.mfluitmarket.common.entity.ZiXunAll;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @Date 2019/6/16 17:30
 * @eMail 6331177@163.com
 * @description
 */
public interface FMarkAddressSerice {

    /**
     * 最新资讯
     */
    @GET("mobile/listArticles.do")
    Observable<HttpResponse<List<ZiXunAll>>> getZixunData();
}
