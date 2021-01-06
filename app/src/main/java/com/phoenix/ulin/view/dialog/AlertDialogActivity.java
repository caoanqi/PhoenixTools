package com.phoenix.ulin.view.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
            showBottomSheetDialog();
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

    public void showBottomSheetDialog() {
        View view = View.inflate(this, R.layout.dialog_botom_sheet, null);
        view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300));
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}
