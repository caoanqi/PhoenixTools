package com.phoenix.ulin.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.navigation.NavigationView;
import com.phoenix.ulin.R;
import com.phoenix.ulin.adapter.CountSectionAdapter;
import com.phoenix.ulin.entity.DeviceBean;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.truizlop.sectionedrecyclerview.SectionedSpanSizeLookup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView recycler;
    List<DeviceBean> deviceBeanList;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        initView();
        initListener();
        initData();
        setupRecycler();


    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        recycler = findViewById(R.id.recycler);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initListener() {
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void initData() {
        deviceBeanList = new ArrayList<>();

        DeviceBean deviceBean = new DeviceBean();
        deviceBean.setDeviceTypeName("路由器");
        List<DeviceBean.DeviceInfo> deviceInfos = new ArrayList<>();
        DeviceBean.DeviceInfo deviceInfo = new DeviceBean.DeviceInfo();
        deviceInfo.setDeviceName("tp_link");
        deviceInfo.setImgName("ic_launcher");
        deviceInfos.add(deviceInfo);
        deviceBean.setDeviceInfos(deviceInfos);
        deviceBeanList.add(deviceBean);

        DeviceBean deviceBean2 = new DeviceBean();
        deviceBean2.setDeviceTypeName("路由器2");
        List<DeviceBean.DeviceInfo> deviceInfos2 = new ArrayList<>();
        DeviceBean.DeviceInfo deviceInfo2 = new DeviceBean.DeviceInfo();
        deviceInfo2.setDeviceName("tp_link");
        deviceInfo2.setImgName("ic_launcher");
        deviceInfos2.add(deviceInfo2);
        deviceInfo2 = new DeviceBean.DeviceInfo();
        deviceInfo2.setDeviceName("上海");
        deviceInfo2.setImgName("ic_launcher");
        deviceInfos2.add(deviceInfo2);
        deviceBean2.setDeviceInfos(deviceInfos2);
        deviceBeanList.add(deviceBean2);

        DeviceBean deviceBean3 = new DeviceBean();
        deviceBean3.setDeviceTypeName("路由器3");
        List<DeviceBean.DeviceInfo> deviceInfos3 = new ArrayList<>();
        DeviceBean.DeviceInfo deviceInfo3 = new DeviceBean.DeviceInfo();
        deviceInfo3.setDeviceName("tp_link");
        deviceInfo3.setImgName("ic_launcher");
        deviceInfos3.add(deviceInfo3);
        deviceInfo3 = new DeviceBean.DeviceInfo();
        deviceInfo3.setDeviceName("tengda");
        deviceInfo3.setImgName("ic_launcher");
        deviceInfos3.add(deviceInfo3);
        deviceInfo3 = new DeviceBean.DeviceInfo();
        deviceInfo3.setDeviceName("上海");
        deviceInfo3.setImgName("ic_launcher");
        deviceInfos3.add(deviceInfo3);
        deviceBean3.setDeviceInfos(deviceInfos3);
        deviceBeanList.add(deviceBean3);

        DeviceBean deviceBean4 = new DeviceBean();
        deviceBean4.setDeviceTypeName("路由器4");
        List<DeviceBean.DeviceInfo> deviceInfos4 = new ArrayList<>();
        DeviceBean.DeviceInfo deviceInfo4 = new DeviceBean.DeviceInfo();
        deviceInfo4.setDeviceName("tp_link");
        deviceInfo4.setImgName("ic_launcher");
        deviceInfos4.add(deviceInfo4);
        deviceInfo4 = new DeviceBean.DeviceInfo();
        deviceInfo4.setDeviceName("tengda");
        deviceInfo4.setImgName("ic_launcher");
        deviceInfos4.add(deviceInfo4);
        deviceBean4.setDeviceInfos(deviceInfos4);
        deviceBeanList.add(deviceBean4);

        DeviceBean deviceBean5 = new DeviceBean();
        deviceBean5.setDeviceTypeName("路由器5");
        List<DeviceBean.DeviceInfo> deviceInfos5 = new ArrayList<>();
        DeviceBean.DeviceInfo deviceInfo5 = new DeviceBean.DeviceInfo();
        deviceInfo5.setDeviceName("tp_link");
        deviceInfo5.setImgName("ic_launcher");
        deviceInfos5.add(deviceInfo5);
        deviceInfo5 = new DeviceBean.DeviceInfo();
        deviceInfo5.setDeviceName("tengda");
        deviceInfo5.setImgName("ic_launcher");
        deviceInfos5.add(deviceInfo5);
        deviceBean5.setDeviceInfos(deviceInfos5);
        deviceBeanList.add(deviceBean5);
    }

    protected void setupRecycler() {
        CountSectionAdapter adapter = new CountSectionAdapter(this, deviceBeanList);
        recycler.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(adapter, layoutManager);
        layoutManager.setSpanSizeLookup(lookup);
        recycler.setLayoutManager(layoutManager);
    }

    @SuppressLint("CheckResult")
    private void checkPermission() {
        RxPermissions permissions = new RxPermissions(this);
        permissions.setLogging(true);
        permissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        LogUtils.e(TAG, "checkPermissionRequestEach--:" + "-permission-:" + permission.name + "---------------");
                        if (permission.name.equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            if (permission.granted) {//同意后调用
                                LogUtils.e(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-:" + true);
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                //禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                                LogUtils.e(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
                            } else {//禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
                                LogUtils.e(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-:" + false);
                            }
                        }
                        if (permission.name.equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            if (permission.granted) {
                                LogUtils.e(TAG, "checkPermissionRequestEach--:" + "-WRITE_EXTERNAL_STORAGE-:" + true);
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                LogUtils.e(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
                            } else {
                                LogUtils.e(TAG, "checkPermissionRequestEach--:" + "-WRITE_EXTERNAL_STORAGE-:" + false);
                            }
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
