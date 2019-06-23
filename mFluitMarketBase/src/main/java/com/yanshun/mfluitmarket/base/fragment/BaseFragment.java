package com.yanshun.mfluitmarket.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yanshun.mfluitmarket.base.presenter.IPresenter;
import com.yanshun.mfluitmarket.base.view.IView;
import com.yanshun.mfluitmarket.common.util.ProgressHUD;

import java.util.List;

/**
 * Created by hasee on 2017/9/3.
 */

public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IView<T> {

    private KProgressHUD progressHUD;

    private T mPresenter;

    public Context mContext;

    private View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(mContext).inflate(getLayoutId(),null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        mPresenter = initPresenter();
        if (null != mPresenter) {
            mPresenter.setView(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setPresenter(T presenter) {

    }

    @Override
    public T getPresenter() {
        return mPresenter;
    }

    public abstract int getLayoutId();

    public abstract T initPresenter();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(String labelTxt) {
        if (progressHUD == null) {
            progressHUD = ProgressHUD.show(getContext());
        }
        onRequestStart(labelTxt);
    }

    @Override
    public void dismissLoading() {
        onRequestEnd();
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void showNetError() {

    }

    @Override
    public void showEmptyView(String msg) {

    }

    @Override
    public void hasNoMoreDate() {

    }

    @Override
    public void loadMoreFinish(List dates) {

    }

    @Override
    public void showRefreshFinish(List score) {

    }

    @Override
    public void showToastError() {

    }

    /**
     * 请求progress
     */
    protected void onRequestStart(String labelTxt) {
        if (progressHUD != null) {
            progressHUD.setLabel(labelTxt);
        }
    }

    /**
     * 关闭progress
     */
    protected void onRequestEnd() {
        if (progressHUD != null) {
            progressHUD.dismiss();
            progressHUD = null;
        }
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
