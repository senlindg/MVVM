package com.yanshun.mfluitmarket.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.yanshun.mfluitmarket.base.http.CookieReadInterceptor;
import com.yanshun.mfluitmarket.base.http.CookiesSaveInterceptor;
import com.yanshun.mfluitmarket.base.util.InterceptorUtil;

import org.xutils.x;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by hasee on 2017/9/4.
 * 描述：app退出acitity退出机制
 */

public class FMBaseApplication extends Application{

    private static FMBaseApplication instance;

    private static Stack<Activity> activityStack;
    public static final int TIMEOUT = 60;
    private static OkHttpClient mOkHttpClient;

    public static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        // 全局默认信任所有https域名 或 仅添加信任的https域名
        // 使用RequestParams#setHostnameVerifier(...)方法可设置单次请求的域名校验
        x.Ext.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        appContext = this;
    }

    public FMBaseApplication(){

    }

    public static FMBaseApplication getInstance(){
        if (instance == null) {
            synchronized (FMBaseApplication.class){
                if(instance==null){
                    instance = new FMBaseApplication();
                    activityStack = new Stack<Activity>();
                }
            }

        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        try {
            Activity activity = null;
            if (activityStack != null && activityStack.size() > 0) {
                 activity = activityStack.lastElement();
            }
            return activity;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前Activity的前一个Activity
     */
    public Activity preActivity() {
        if(activityStack == null) {
            return null ;
        }
        int index = activityStack.size() - 2;
        if (index < 0) {
            return null;
        }
        Activity activity = activityStack.get(index);
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if(activityStack == null) {
            return;
        }
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && activityStack != null && activityStack.size() > 0) {
            activityStack.remove(activity);
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null && activityStack != null && activityStack.size() > 0) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 返回到指定的activity
     *
     * @param cls
     */
    public void returnToActivity(Class<?> cls) {
        while (activityStack.size() != 0){
            if (activityStack.peek().getClass() == cls) {
                break;
            } else {
                finishActivity(activityStack.peek());
            }
        }
    }


    /**
     * 是否已经打开指定的activity
     * @param cls
     * @return
     */
    public boolean isOpenActivity(Class<?> cls) {
        if (activityStack!=null){
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (cls == activityStack.peek().getClass()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 退出应用程序
     *
     * @param isBackground 是否开开启后台运行
     */
    public void AppExit(Boolean isBackground) {
        try {
            finishAllActivity();
        } catch (Exception e) {
        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                System.exit(0);
            }
        }
    }

    /**
     *  全局httpClient
     */
    public OkHttpClient initOkHttp() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT,TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT,TimeUnit.SECONDS)
                    .addInterceptor(InterceptorUtil.LogInterceptor())
                    //cookie
                    .addInterceptor(new CookieReadInterceptor())
                    .addInterceptor(new CookiesSaveInterceptor())
                    .build();
        }

        return mOkHttpClient;
    }

}
