package com.hvlin.myapplication.view;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.hvlin.myapplication.R;
import com.hvlin.myapplication.databinding.ActivitySqliteBinding;
import com.hvlin.myapplication.db.DBOperate;

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
