package com.phoenix.ulin.view.encrypt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.phoenix.ulin.R;
import com.phoenix.ulin.db.DBencrypt;

import net.sqlcipher.database.SQLiteDatabase;

/**
 * 数据库加密
 */
public class EncryptDbActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt_db);
        SQLiteDatabase.loadLibs(this);
        findViewById(R.id.bt_add_encrypt_db).setOnClickListener(v -> {
            // DBencrypt.getInstences().encrypt(this, "123456");
            DBencrypt.getInstences().test(this);
        });

    }

}