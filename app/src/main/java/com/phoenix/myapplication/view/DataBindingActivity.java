package com.phoenix.myapplication.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.phoenix.myapplication.R;
import com.phoenix.myapplication.databinding.ActivityDataBindingBinding;
import com.phoenix.myapplication.model.UserModel;

public class DataBindingActivity extends AppCompatActivity {

    ActivityDataBindingBinding activityDataBindingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDataBindingBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_data_binding);

        UserModel userModel = new UserModel();
        userModel.setName("孙悟空");
        activityDataBindingBinding.setData(userModel);

        activityDataBindingBinding.btGet.setOnClickListener(v -> {
            activityDataBindingBinding.tvLogout.setText(userModel.getName());
        });
    }
}
