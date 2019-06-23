package com.yanshun.mfluitmarket.base.presenter;


import com.yanshun.mfluitmarket.base.view.IView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<T extends IView> implements IPresenter<T>{

    private WeakReference<T> iWeakView;
    private CompositeDisposable compositeDisposable;

    @Override
    public void subscribe(Disposable d) {
        if (null == compositeDisposable) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(d);
    }

    @Override
    public void unsubscribe() {
        if (null != compositeDisposable) {
            if(compositeDisposable.isDisposed()) {
                compositeDisposable.dispose();
            }
            compositeDisposable = null;
        }
        if (null != iWeakView){
            iWeakView.clear();
            iWeakView = null;
        }
    }

    @Override
    public void setView(T view) {
        if (null == iWeakView) {
            iWeakView = new WeakReference<T>(view);
        }
    }

    @Override
    public T getView() {
        if (null != iWeakView){
            return iWeakView.get();
        }
        return null;
    }
}
