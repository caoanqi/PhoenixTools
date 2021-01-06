package com.phoenix.ulin.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.phoenix.ulin.R;
import com.phoenix.ulin.databinding.ActivityDataBindingBinding;
import com.phoenix.ulin.model.UserModel;

public class DataBindingActivity extends AppCompatActivity {

    ActivityDataBindingBinding activityDataBindingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDataBindingBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);

        UserModel userModel = new UserModel();
        userModel.setName("孙悟空");
        activityDataBindingBinding.setData(userModel);

        activityDataBindingBinding.btGet.setOnClickListener(v -> {
            activityDataBindingBinding.tvLogout.setText(userModel.getName());
        });
    }
}
