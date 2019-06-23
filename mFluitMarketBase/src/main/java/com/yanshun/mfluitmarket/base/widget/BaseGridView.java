package com.yanshun.mfluitmarket.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by hasee on 2017/9/9.
 */

public class BaseGridView extends GridView{
    public BaseGridView(Context context) {
        super(context);
    }

    public BaseGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
