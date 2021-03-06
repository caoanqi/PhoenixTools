package com.phoenix.ulin.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.phoenix.ulin.BuildConfig;
import com.phoenix.ulin.services.DestroyServices;
import com.phoenix.ulin.R;
import com.phoenix.ulin.databinding.ActivityInstallAppBinding;
import com.ulin.baselib.utils.InputMethodMemoryUtil;

import java.io.File;

/**
 * 本地查找apk，进行安装
 */
public class InstallAppActivity extends AppCompatActivity {

    ActivityInstallAppBinding activityInstallAppBinding;
    String filePath = "/sdcard/apk/phoenix.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInstallAppBinding = DataBindingUtil.setContentView(this, R.layout.activity_install_app);

//        activityInstallAppBinding.btnInstallLocalApk.setOnClickListener(v -> installAPK());
//        activityInstallAppBinding.btnGoAppSetting.setOnClickListener(view -> getAppDetailSettingIntent(this));

        activityInstallAppBinding.btnInstallLocalApk.setOnClickListener(v -> {
            Intent intent = new Intent(this, DestroyServices.class);
            startService(intent);
        });
    }

    /**
     * 安装apk
     */
    protected void installAPK() {
        File apkFile = new File("/sdcard/apk/app-release.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);


    }


    /**
     * 前往应用设置页面
     *
     * @param context
     */
    private void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InputMethodMemoryUtil.fixFocusedViewLeak(getApplication());
    }
}
