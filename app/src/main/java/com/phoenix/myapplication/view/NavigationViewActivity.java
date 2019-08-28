package com.phoenix.myapplication.view;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.phoenix.myapplication.R;
import com.phoenix.myapplication.adapter.DrawerAdapter;

public class NavigationViewActivity extends AppCompatActivity {


    DrawerLayout drawerlayout;

    RecyclerView rvDrawer;
    DrawerAdapter drawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);

        drawerlayout = findViewById(R.id.drawerlayout);
        rvDrawer = findViewById(R.id.rv_drawer);
        init();
    }

    private void init() {
        //抽屉初始化
        drawerAdapter = new DrawerAdapter(this);
        drawerAdapter.setOnItemClickListener(new MyOnItemClickListener());
        rvDrawer.setLayoutManager(new LinearLayoutManager(this));
        rvDrawer.setAdapter(drawerAdapter);
    }

    /**
     * drawer item 点击事件
     */
    public class MyOnItemClickListener implements DrawerAdapter.OnItemClickListener {

        @Override
        public void itemClick(DrawerAdapter.DrawerItemNormal drawerItemNormal) {
            switch (drawerItemNormal.titleRes) {
                case R.string.drawer_menu_home://首页
                    ToastUtils.showShort("首页");
                    drawerItemNormal.checked = true;
                    drawerAdapter.updateData(drawerItemNormal);
                    drawerAdapter.notifyDataSetChanged();
                    break;
                case R.string.drawer_menu_rank://排行榜
                    drawerlayout.closeDrawer(GravityCompat.START);
                    break;
                case R.string.drawer_menu_column://栏目
                    drawerlayout.closeDrawer(GravityCompat.START);
                    break;
                case R.string.drawer_menu_search://搜索
                    drawerlayout.closeDrawer(GravityCompat.START);
                    break;
                case R.string.drawer_menu_setting://设置
                    drawerlayout.closeDrawer(GravityCompat.START);
                    break;
                case R.string.drawer_menu_night://夜间模式
                    drawerlayout.closeDrawer(GravityCompat.START);
                    break;
                case R.string.drawer_menu_offline://离线
                    drawerlayout.closeDrawer(GravityCompat.START);
                    break;
            }

        }
    }
}
