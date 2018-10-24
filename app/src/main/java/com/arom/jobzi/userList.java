package com.arom.jobzi;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class userList extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "jobzi-arom";
    public static final String TABLE_NAME = "products";
    public static final String COLUMN_USER = "user";


    public userList(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addUser (User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_USER, user.getUserName());
        values.put(COLUMN_SKU, product.getSku());
        //insert(String table, String nullColumnHack, ContentValues values)
        db.insert(COLUMN_USER, null, values);

        db.close();
    }

}
