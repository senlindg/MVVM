package com.yanshun.mfluitmarket.base.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yanshun.mfluitmarket.base.app.FMBaseApplication;
import com.yanshun.mfluitmarket.base.presenter.IPresenter;
import com.yanshun.mfluitmarket.base.view.IView;
import com.yanshun.mfluitmarket.common.util.ProgressHUD;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by hasee on 2017/9/3.
 */

public abstract class BaseActivity<T extends IPresenter> extends FragmentActivity implements IView<T> {

    private T mPresenter;

    private KProgressHUD progressHUD;

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        FMBaseApplication.getInstance().addActivity(this);
        bindView(DataBindingUtil.setContentView(this,getLayoutId()));
        mContext = this;
        mPresenter = initPresenter();
        if (null != mPresenter){
            mPresenter.setView(this);
        }
        this.initView();
        this.initData();
    }

    public abstract @android.support.annotation.LayoutRes int getLayoutId();

    public abstract T initPresenter();

    public abstract void initView();

    public abstract void initData();

    public abstract void bindView(ViewDataBinding view);

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        FMBaseApplication.getInstance().removeActivity(this);
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
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
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过反射的方式获取状态栏高度
     * @return
     */
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public T getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(T presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading(String labelTxt) {
        if (progressHUD == null) {
            progressHUD = ProgressHUD.show(mContext);
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

    @Override
    public void showLoading() {

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
}
