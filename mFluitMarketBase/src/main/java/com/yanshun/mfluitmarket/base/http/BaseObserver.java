package com.yanshun.mfluitmarket.base.http;

import android.accounts.NetworkErrorException;

import com.yanshun.mfluitmarket.base.entity.HttpResponse;
import com.yanshun.mfluitmarket.base.presenter.BasePresenter;
import com.yanshun.mfluitmarket.base.view.IView;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<HttpResponse<T>> {
    private BasePresenter mPresenter;

    private String labelTxt;

    private IView iWeakRef;

    public BaseObserver(BasePresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    public BaseObserver(IView iWeakRef,String text) {
        this.iWeakRef = iWeakRef;
        this.labelTxt = text;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (null != iWeakRef) {
            iWeakRef.getPresenter().subscribe(d);
            iWeakRef.showLoading(labelTxt);
        }
    }

    @Override
    public void onNext(HttpResponse<T> t) {
        try{
            onSuccess(t);
        } catch (Exception e) {

        }
    }

    @Override
    public void onError(Throwable e) {
        iWeakRef.dismissLoading();
        try {
            if (e instanceof ConnectException ||e instanceof TimeoutException
                    || e instanceof NetworkErrorException || e instanceof UnknownHostException) {
                onFailure(e,true);
            } else {
                onFailure(e,false);
            }
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
        iWeakRef.dismissLoading();
    }

    /**
     * 返回成功
     */
    protected abstract void onSuccess(HttpResponse<T> t) throws Exception;

    /**
     * 返回失败
     */
    protected abstract void onFailure(Throwable e,boolean isNetWorkError) throws Exception;

}
