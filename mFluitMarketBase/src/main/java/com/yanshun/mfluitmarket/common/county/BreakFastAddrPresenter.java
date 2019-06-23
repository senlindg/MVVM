package com.yanshun.mfluitmarket.common.county;


import com.yanshun.mfluitmarket.base.entity.HttpResponse;
import com.yanshun.mfluitmarket.base.http.BaseObserver;
import com.yanshun.mfluitmarket.base.presenter.BasePresenter;
import com.yanshun.mfluitmarket.common.entity.ZiXunAll;
import com.yanshun.mfluitmarket.common.http.FMarkAddressApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author hasee
 */
public class BreakFastAddrPresenter extends BasePresenter<BreakFastAddrContract.BreakFastAddrView> implements
                BreakFastAddrContract.BreakFastAddrPresenter{
    @Override
    public void requestData() {
        FMarkAddressApi.getInstance().getApiService()
                .getZixunData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ZiXunAll>>(getView(),"加载中...") {
                    @Override
                    protected void onSuccess(HttpResponse<List<ZiXunAll>> t) throws Exception {
                        getView().updateView(t.getData());
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
