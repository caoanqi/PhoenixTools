package com.phoenix.ulin.view.Dialog;

import android.content.DialogInterface;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.phoenix.ulin.R;
import com.phoenix.ulin.databinding.ActivityAlertDialogBinding;

public class AlertDialogActivity extends AppCompatActivity {

    ActivityAlertDialogBinding activityAlertDialogBinding;
    String[] data = new String[]{"张三", "里斯", "呵呵"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAlertDialogBinding = DataBindingUtil.setContentView(this, R.layout.activity_alert_dialog);
        activityAlertDialogBinding.btShowAlert.setOnClickListener(v -> {
            showAlertDialog();
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(data, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToastUtils.showShort(i);
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToastUtils.showShort(i);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
