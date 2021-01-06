package com.phoenix.ulin.view.custom_view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.ToastUtils;
import com.phoenix.ulin.R;
import com.phoenix.ulin.widget.RationaleDialogFragment;
import com.phoenix.ulin.widget.RingProgressBarView;

import java.util.List;

/**
 * custome view demo
 *
 * @author caoyl
 */
public class CustomViewActivity extends AppCompatActivity implements RationaleDialogFragment.PermissionCallbacks {
    private int mTotalProgress = 90;
    private int mCurrentProgress = 0;
    //进度条
    private RingProgressBarView mTasksView;
    public static final String TAG = "MainActivity";

    private static final int RC_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
//        mTasksView =  findViewById(R.id.tasks_view);
        showCamera();

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.i(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.i(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
    }

    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;
                mTasksView.setProgress(mCurrentProgress);
                try {
                    Thread.sleep(90);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // 我们应该给用户个解释?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // 向用户显示一个解释，要以异步非阻塞的方式
                // 该线程将等待用户响应！等用户看完解释后再继续尝试请求权限
                RationaleDialogFragment.newInstance(android.R.string.ok, android.R.string.cancel,
                                "需要摄像头权限", RC_CAMERA,
                                new String[]{Manifest.permission.CAMERA})
                        .show(getSupportFragmentManager(), RationaleDialogFragment.TAG);
            } else {

                // 不需要向用户解释了，我们可以直接请求该权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        RC_CAMERA);
            }
        } else {
            ToastUtils.showShort("已经授权");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RC_CAMERA: {
                // 如果请求被取消了，那么结果数组就是空的
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被授予了
                    ToastUtils.showShort("已经授权");
                }
                return;
            }
        }
    }
}