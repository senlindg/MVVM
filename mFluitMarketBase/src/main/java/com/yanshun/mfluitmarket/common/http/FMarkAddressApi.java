package com.yanshun.mfluitmarket.common.http;

import com.yanshun.mfluitmarket.base.http.BaseRetrofit;
import com.yanshun.mfluitmarket.base.http.BaseUrl;

/**
 * @Date 2019/6/16 17:29
 * @eMail 6331177@163.com
 * @description
 */
public class FMarkAddressApi extends BaseRetrofit<FMarkAddressSerice> {

    private static FMarkAddressApi mIntance;

    public static FMarkAddressApi getInstance(){
        if (null == mIntance) {
            synchronized (BaseRetrofit.class){
                if(mIntance==null){
                    mIntance = new FMarkAddressApi();
                }
            }
        }
        return mIntance;
    }

    @Override
    public Class<FMarkAddressSerice> setApiClass() {
        return FMarkAddressSerice.class;
    }

    @Override
    public FMarkAddressSerice getApiService() {
        return initRetrofit(BaseUrl.api);
    }

}
