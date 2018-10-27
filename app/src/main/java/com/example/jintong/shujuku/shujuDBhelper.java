package com.example.jintong.shujuku;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class shujuDBhelper extends SQLiteOpenHelper {
    public shujuDBhelper(Context context) {
        super(context,"Shuju.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Shuju(id integer primary key autoincrement, title char(20), date char(20),message char(50))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
