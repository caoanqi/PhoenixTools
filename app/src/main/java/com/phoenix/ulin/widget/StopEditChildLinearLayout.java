package com.phoenix.ulin.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 子控件是否可以接受点击事件
 */
public class StopEditChildLinearLayout extends LinearLayout {

    private boolean childClickable = true;

    public StopEditChildLinearLayout(Context context) {
        super(context);
    }

    public StopEditChildLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StopEditChildLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StopEditChildLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //返回true则拦截子控件所有点击事件，如果childclickable为true，则需返回false
        return !childClickable;
    }

    public void setChildClickable(boolean clickable) {
        childClickable = clickable;
    }

    /**
     * 获取子节点是否可点击
     *
     * @return
     */
    public boolean getChildClickable() {
        return childClickable;
    }
}
