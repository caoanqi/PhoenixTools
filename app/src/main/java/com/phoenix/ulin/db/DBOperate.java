package com.phoenix.ulin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.phoenix.ulin.R;
import com.phoenix.ulin.entity.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * auther: MO
 * Date: 2017/1/11
 * Time:18:41
 * Des:
 */

public class DBOperate {
    private SQLiteHelper dbhelper;
    private Context context;

    //要操作数据库操作实例首先得得到数据库操作实例
    public DBOperate(Context context) {
        this.context = context;
        this.dbhelper = SQLiteHelper.getInstance(context);
    }

    public void saveImage() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("_id", 1);
        cv.put("avatar", bitmabToBytes(context));//图片转为二进制
        db.insert("User", null, cv);
        db.close();
    }

    public byte[] readImage() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cur = db.query("User", new String[]{"_id", "avatar"}, null, null, null, null, null);
        byte[] imgData = null;
        if (cur.moveToNext()) {
            //将Blob数据转化为字节数组
            imgData = cur.getBlob(cur.getColumnIndex("avatar"));
        }
        return imgData;
    }

    //图片转为二进制数据
    public byte[] bitmabToBytes(Context context) {
        //将图片转化为位图
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_round);
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        //创建一个字节数组输出流,流的大小为size
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        try {
            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();
            return imagedata;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bitmap.recycle();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    public void insertData(String name) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        db.insert("User", null, cv);
        db.close();
    }

    public void updateData(User user) {

    }

    public void deleteData(User user) {

    }

    public void getData() {

    }

}
