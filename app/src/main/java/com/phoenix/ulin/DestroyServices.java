package com.phoenix.ulin;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;

/**
 * 销毁服务
 */
public class DestroyServices extends Service {
    /**
     * 24hours
     */
    private long timeCount = 24 * 60 * 60 * 1000;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (SPUtils.getInstance().contains("DestroyTimerCount")) {
            timeCount = SPUtils.getInstance().getLong("DestroyTimerCount");
        }
        startCountDown();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startCountDown() {
        if (countDownTimer != null) {
            countDownTimer.start();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private CountDownTimer countDownTimer = new CountDownTimer(timeCount, 1000) {
        @Override
        public void onTick(long l) {
            LogUtils.e(l);
            if (l > 0) {
                SPUtils.getInstance().put("DestroyTimerCount", l);
            }
        }

        @Override
        public void onFinish() {
            SPUtils.getInstance().put("DestroyTimerCount", 0);
            LogUtils.e("CountDownTimer onFinish");
        }
    };
}
