package com.example.nasaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteManager extends SQLiteOpenHelper {

    public static SQLiteManager sqLiteManager;
    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME ="Nasa2DB";
    public static final String TABLE_NAME = "Nasa";
    public static final String COUNTER = "Counter";
    public static final String ID_FIELD="id";
    public static final String URL_FIELD="API";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context){
        if (sqLiteManager ==null)
            sqLiteManager =new SQLiteManager(context);


        return sqLiteManager;
    }
    // Called First time database is accessed, and creates a new database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append(" (id INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(URL_FIELD)
                .append(" STRING);");
        sqLiteDatabase.execSQL(sql.toString());

    }
    // called when version of database changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
