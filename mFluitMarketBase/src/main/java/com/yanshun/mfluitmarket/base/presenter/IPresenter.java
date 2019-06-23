package com.yanshun.mfluitmarket.base.presenter;

import com.yanshun.mfluitmarket.base.view.IView;

import io.reactivex.disposables.Disposable;

public interface IPresenter<T extends IView> {
    /**
     * 订阅
     */
    void subscribe(Disposable d);

    /**
     * 取消订阅
     */
    void unsubscribe();

    /**
     * 设置View
     */
    void setView(T view);

    /**
     * 设置View
     */
    T getView();
}
