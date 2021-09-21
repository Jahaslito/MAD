package com.jaffer.mad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "breather.db";
    public static final String TABLE_NAME = "Notifications";
    public static final String COL1 = "notification_id";
    public static final String COL2 = "notification_date";
    public static final String COL3 = "notification_message";
    public static final String COL4 = "notification_title";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("+ COL1 +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " VARCHAR(20) NOT NULL, "+ COL3 +" VARCHAR(50) NOT NULL, " +COL4 +" VARCHAR(50) NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String time, String message, String title) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, time);
        contentValues.put(COL3, message);
        contentValues.put(COL4, title);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data is inserted incorrectly it will return -1
        return result != -1;
    }
    public boolean updateData(int id, String time, String message, String title) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, time);
        contentValues.put(COL3, message);
        contentValues.put(COL4, title);
        long result= db.update(TABLE_NAME,contentValues,"notification_id=?", new String[]{String.valueOf(id)});

        //if data is updated incorrectly it will return -1
        return result != -1;
    }
    public boolean deleteData(int transaction_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result= db.delete(TABLE_NAME,"notification_id=?", new String[]{String.valueOf(transaction_id)});

        //if date is not deleted it will return -1
        return result != -1;
    }
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
    public Cursor getLastRow(){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition= " WHERE notification_id = (SELECT MAX(notification_id) FROM "+TABLE_NAME+" );";
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + condition, null);
        return data;
    }
}