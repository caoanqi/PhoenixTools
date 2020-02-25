package com.phoenix.ulin.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.phoenix.ulin.R;
import com.phoenix.ulin.adapter.ChangeSkinAdapter;
import com.phoenix.ulin.entity.ChangeSkinBean;
import com.phoenix.ulin.listener.ScreenListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 换肤
 */

public class ChangeSkinActivity extends AppCompatActivity {

    private ChangeSkinAdapter changeSkinAdapter;
    private List<ChangeSkinBean> changeSkinBeanList = new ArrayList<>();
    private RecyclerView rvSkin;
    private MyTask task;
    private Timer timer;
    private int currentTime;
    private TextView tvTime;

    private ScreenListener screenListener ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SPUtils.getInstance().getString("theme", "dayTheme").equals("dayTheme")) {
            //默認是白天主題
            setTheme(R.style.dayTheme);
        } else {
            //否则是晚上主題
            setTheme(R.style.nightTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_skin);
        rvSkin = findViewById(R.id.rl_skin);
        tvTime = findViewById(R.id.time_tv);
        initData();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mBatInfoReceiver, filter);
        screenListener = new ScreenListener( ChangeSkinActivity.this ) ;
        screenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                Toast.makeText( ChangeSkinActivity.this , "屏幕打开了" , Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onScreenOff() {
                finish();
                Toast.makeText( ChangeSkinActivity.this , "屏幕关闭了" , Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onUserPresent() {
                Toast.makeText( ChangeSkinActivity.this , "解锁了" , Toast.LENGTH_SHORT ).show();
            }
        });
    }

    private void initData() {

        ChangeSkinBean changeSkinBean = new ChangeSkinBean();
        changeSkinBean.setName("dayTheme");
        changeSkinBean.setChecked(true);
        changeSkinBean.setSkinColor(R.color.bg_color);
        ChangeSkinBean changeSkinBean2 = new ChangeSkinBean();
        changeSkinBean2.setName("nightTheme");
        changeSkinBean2.setChecked(false);
        changeSkinBean2.setSkinColor(R.color.colorAccent);
        changeSkinBeanList.add(changeSkinBean);
        changeSkinBeanList.add(changeSkinBean2);

        changeSkinAdapter = new ChangeSkinAdapter(this, changeSkinBeanList, new ChangeSkinAdapter.SkinCallback() {
            @Override
            public void onChooseSkin(String name, int color, int pos, int lastPos) {
                changeSkinBeanList.get(pos).setChecked(true);
                SPUtils.getInstance().put("theme", name);
                resetSkinList(name, color, pos, lastPos);
                changeSkinAdapter.notifyDataSetChanged();
                recreate();
            }
        });
        rvSkin.setLayoutManager(new GridLayoutManager(this, 4));
        rvSkin.setAdapter(changeSkinAdapter);

    }

    private void resetSkinList(String name, int color, int pos, int lastPos) {
        for (int i = 0, size = changeSkinBeanList.size(); i < size; i++) {
            if (i == lastPos) {
                changeSkinBeanList.get(i).setChecked(false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //startTimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("按下");
                stopTimer();
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.e("抬起");
                startTimer();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initTimer() {
        // 初始化计时器
        task = new MyTask();
        timer = new Timer();
    }

    private void unInitTimer() {
        // 初始化计时器
        task = null;
        timer = null;
    }

    class MyTask extends TimerTask {
        @Override
        public void run() {
            // 初始化计时器
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    currentTime++;
                    tvTime.setText(String.valueOf(currentTime));
                    if (currentTime == 10) {
                        //在这里弹窗然后停止计时
                        ToastUtils.showShort("你去哪里了?");
                        finish();
                        stopTimer();
                    }
                }
            });
        }
    }

    private void startTimer() {
        //启动计时器
        /**
         * java.util.Timer.schedule(TimerTask task, long delay, long period)：
         * 这个方法是说，delay/1000秒后执行task,然后进过period/1000秒再次执行task，
         * 这个用于循环任务，执行无数次，当然，你可以用timer.cancel();取消计时器的执行。
         */
        initTimer();
        try {
            timer.schedule(task, 1000, 1000);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            initTimer();
            timer.schedule(task, 1000, 1000);
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
        currentTime = 0;
        if (tvTime != null) {
            tvTime.setText(String.valueOf(currentTime));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //当activity不在前台是停止定时
        stopTimer();
    }

    @Override
    protected void onDestroy() {
        //销毁时停止定时
        stopTimer();
        super.onDestroy();

        if(mBatInfoReceiver != null){
            try {
                unregisterReceiver(mBatInfoReceiver);
            } catch (Exception e) {
                Log.e("123", "unregisterReceiver mBatInfoReceiver failure :"+e.getCause());

            }
        }
    }

    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            final String action = intent.getAction();

            if (Intent.ACTION_SCREEN_OFF.equals(action)) {

                finish();
            }

        }


    };

}
