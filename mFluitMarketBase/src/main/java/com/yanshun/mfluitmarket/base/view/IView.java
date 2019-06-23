package com.yanshun.mfluitmarket.base.view;


import com.yanshun.mfluitmarket.base.presenter.IPresenter;

import java.util.List;

public interface IView<T extends IPresenter> {
    void setPresenter(T presenter);
    /**
     * 获取P
     */
    T getPresenter();

    void showLoading();

    void showLoading(String labelTxt);

    void dismissLoading();

    void refreshView();

    void showNetError();

    void showEmptyView(String msg);

    void hasNoMoreDate();

    void loadMoreFinish(List dates);

    void showRefreshFinish(List score);

    void showToastError();
}
