package com.yanshun.mfluitmarket.common.util;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * @Date 2019/6/16 11:14
 * @eMail 6331177@163.com
 * @description
 */
public class ProgressHUD {
    /**
     * 显示加载HUD 需要手动取消
     *
     * @param context 上下文
     * @return KProgressHUD
     */
    public static KProgressHUD show(Context context) {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setDimAmount(0.5f)
                .show();
    }

    /**
     * 显示带文字的加载HUD 需要手动取消
     *
     * @param context   上下文
     * @param tipString 提示文字
     * @return KProgressHUD
     */
    public static KProgressHUD show(Context context, String tipString) {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(tipString)
                .setCancellable(true)
                .setDimAmount(0.5f)
                .show();
    }
}
