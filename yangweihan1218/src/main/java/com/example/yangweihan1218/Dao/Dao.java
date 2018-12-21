package com.example.yangweihan1218.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Dao {
    private SQLiteHelper helper_ho;
    private SQLiteDatabase ab;

    public Dao(Context context){
        helper_ho = new SQLiteHelper(context);
        ab = helper_ho.getReadableDatabase();
    }
    //添加
    public void add(String main_text){
        ContentValues values = new ContentValues();
        values.put("main_text",main_text);
        ab.insert("exeue",null,values);
    }

}
