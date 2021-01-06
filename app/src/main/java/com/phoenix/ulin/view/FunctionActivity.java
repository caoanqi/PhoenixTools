package com.phoenix.ulin.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phoenix.ulin.R;
import com.phoenix.ulin.adapter.FunctionAdapter;

import java.util.Arrays;

/**
 * 功能列表
 *
 * @author caoyl
 */
public class FunctionActivity extends AppCompatActivity implements FunctionAdapter.FuncCallback {

    private FunctionAdapter functionAdapter;
    private RecyclerView rvFunction;

    public String[] funcString = new String[]{
            "权限管理",
            "拍照相册",
            "多线程"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        rvFunction = findViewById(R.id.rv_function);
        initData();
    }

    private void initData() {
        functionAdapter = new FunctionAdapter(this, Arrays.asList(funcString), this);
        rvFunction.setLayoutManager(new LinearLayoutManager(this));
        rvFunction.setAdapter(functionAdapter);
    }

    @Override
    public void onFuncItemClick(String funcName) {
        switch (funcName) {
            case "权限管理":
                startActivity(new Intent().setClass(this,PermissionActivity.class));
                break;
            case "拍照相册":
                break;
            case "多线程":
                break;
            case "弹出框":
                break;
            default:
                break;
        }
    }
}