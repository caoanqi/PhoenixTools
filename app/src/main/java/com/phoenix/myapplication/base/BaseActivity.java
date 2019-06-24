package com.phoenix.myapplication.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * activity 基类
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected T mBinding;
    protected Activity mActivity;

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mActivity, getLayoutId());
        initView();
        initListener();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected void onBindingData() {
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
