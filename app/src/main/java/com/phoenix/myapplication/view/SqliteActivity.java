package com.phoenix.myapplication.view;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Button;

import com.phoenix.myapplication.R;
import com.phoenix.myapplication.databinding.ActivitySqliteBinding;
import com.phoenix.myapplication.db.DBOperate;

public class SqliteActivity extends AppCompatActivity {

    ActivitySqliteBinding activitySqliteBinding;
    DBOperate dbOperate;

    Button btRead;
    Button btWrite;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySqliteBinding = DataBindingUtil.setContentView(this, R.layout.activity_sqlite);

        init();
    }

    private void init() {
        dbOperate = new DBOperate(this);

        activitySqliteBinding.btReadImage.setOnClickListener(v -> readImage());
        activitySqliteBinding.btWriteImage.setOnClickListener(v -> saveImage(url));

        activitySqliteBinding.btInsertData.setOnClickListener(v -> insertData());
        activitySqliteBinding.btUpdateData.setOnClickListener(v -> updateData());
        activitySqliteBinding.btDeleteData.setOnClickListener(v -> deleteData());
        activitySqliteBinding.btGetData.setOnClickListener(v -> getData());
    }

    private void insertData() {
        new DBOperate(this).insertData("张三");
    }

    private void updateData() {


    }

    private void deleteData() {

    }

    private void getData() {

    }


    private void readImage() {

        byte[] imgData = dbOperate.readImage();
        if (imgData != null) {
            //将字节数组转化为位图
            Bitmap imagebitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
            //将位图显示为图片
            activitySqliteBinding.ivShow.setImageBitmap(imagebitmap);
        } else {
            activitySqliteBinding.ivShow.setBackgroundResource(android.R.drawable.menuitem_background);
        }
    }

    private void saveImage(String url) {
        dbOperate.saveImage();
    }

}
