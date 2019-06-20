package com.phoenix.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.phoenix.myapplication.view.ExpandListViewActivity;
import com.phoenix.myapplication.view.QuneeActivity;
import com.phoenix.myapplication.view.SqliteActivity;
import com.phoenix.myapplication.view.UsbSocketActivity;
import com.truizlop.sectionedrecyclerview.SectionedSpanSizeLookup;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recycler;
    List<DeviceBean> deviceBeanList;
    private FloatingActionButton fab;
    Button btnUsbSocket;
    Button btnQunee;
    Button btSqlite;
    Button bt_expand_list_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler = findViewById(R.id.recycler);
        fab = findViewById(R.id.fab);
        btnUsbSocket = findViewById(R.id.btn_usb_socket);
        btnQunee = findViewById(R.id.bt_qunee);
        bt_expand_list_view = findViewById(R.id.bt_expand_list_view);
        btSqlite = findViewById(R.id.bt_sqlite);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
        btnQunee.setOnClickListener(v -> {
            startActivity(new Intent().setClass(this, QuneeActivity.class));
        });
        btSqlite.setOnClickListener(v -> startActivity(new Intent().setClass(this,
                SqliteActivity.class)));

        btnUsbSocket.setOnClickListener(v -> {
            startActivity(new Intent().setClass(MainActivity.this, UsbSocketActivity.class));
        });
        bt_expand_list_view.setOnClickListener(v -> {
            startActivity(new Intent().setClass(MainActivity.this, ExpandListViewActivity.class));
        });

        setupRecycler();


    }

    private void showAlertDialog() {
        CustomDialog infoDialog = new CustomDialog.Builder(this)
                .setDeviceList(deviceBeanList)
                .setSureButton(new CustomDialog.DialogCallback() {
                    @Override
                    public void onSureClickListener(List<DeviceBean> deviceBeans) {
                        Log.e("TestData:", JSON.toJSON(deviceBeans).toString());
                    }
                })
                .create();
        infoDialog.show();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
