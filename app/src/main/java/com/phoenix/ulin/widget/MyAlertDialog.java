package com.phoenix.ulin.widget;


import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;

/**
 * 自定义alertDialog
 */
public class MyAlertDialog extends AlertDialog {
    protected MyAlertDialog(Context context) {
        super(context);
    }

    protected MyAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected MyAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
