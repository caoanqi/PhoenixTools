package com.phoenix.ulin.db;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;

import java.io.File;

/**
 * 数据库加密
 */
public class DBencrypt {
    public static DBencrypt dBencrypt;
    private Boolean isOpen = true;

    public static DBencrypt getInstences() {
        if (dBencrypt == null) {
            synchronized (DBencrypt.class) {
                if (dBencrypt == null) {
                    dBencrypt = new DBencrypt();
                }
            }
        }
        return dBencrypt;
    }

    /**
     * 如果有旧表 先加密数据库
     *
     * @param context
     * @param passphrase
     */
    public void encrypt(Context context, String passphrase) {
        File file = new File("/sdcard/topo/topo.db");
        if (file.exists()) {
            if (isOpen) {
                try {
                    File newFile = File.createTempFile("sqlcipherutils", "tmp", context.getCacheDir());

                    net.sqlcipher.database.SQLiteDatabase db = net.sqlcipher.database.SQLiteDatabase
                            .openDatabase(file.getAbsolutePath(), "", null, SQLiteDatabase.OPEN_READWRITE);

                    db.rawExecSQL(String.format("ATTACH DATABASE '%s' AS encrypted KEY '%s';", newFile.getAbsolutePath(), passphrase));
                    db.rawExecSQL("SELECT sqlcipher_export('encrypted')");
                    db.rawExecSQL("DETACH DATABASE encrypted;");

                    int version = db.getVersion();
                    db.close();

                    db = net.sqlcipher.database.SQLiteDatabase
                            .openDatabase(newFile.getAbsolutePath(), passphrase, null, SQLiteDatabase.OPEN_READWRITE);

                    db.setVersion(version);
                    db.close();
                    file.delete();
                    newFile.renameTo(file);
                    isOpen = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    isOpen = false;
                }
            }
        }
    }

    public void test(Context context){
        Context mContext = context;
        SQLiteDatabase.loadLibs(mContext);

        //SQLiteDatabase.getBytes()
        SQLiteDatabaseHook hook = new SQLiteDatabaseHook() {
            @Override
            public void preKey(SQLiteDatabase database) {

            }

            @Override
            public void postKey(SQLiteDatabase database) {
                // database.execSQL("PRAGMA cipher_use_hmac = OFF");
                //database.execSQL("PRAGMA cipher_page_size = 4096");
                //database.changePassword("");
            }
        };
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase("/sdcard/topo/topo.db",
                "qwer1234", null, hook);

//        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase("/sdcard/topo/Demo100.db",
//                "", null, hook);

        database.execSQL("create table t1(a,b)");
        //database.execSQL("create table t2(a,b)");
        //database.execSQL("create table t4(a,b)");
        for (int i = 0; i < 2000; i++) {
            database.execSQL("insert into t1(a,b) values (? , ?)", new Object[]{"a" + i, "b" + i});
            //database.execSQL("insert into t2(a,b) values (? , ?)", new Object[]{"a" + i, "b" + i});
            //database.execSQL("insert into t3(a,b) values (? , ?)", new Object[]{"a" + i, "b" + i});
            //database.execSQL("insert into t4(a,b) values (? , ?)", new Object[]{"a" + i, "b" + i});
        }
    }
}
