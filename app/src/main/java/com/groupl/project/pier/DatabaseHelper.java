package com.groupl.project.pier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Statement;

public class DatabaseHelper extends SQLiteOpenHelper{
    // Day,Month,Year,Description,Category,Value,Balance
    private  static final int DATABASE_VERSION = 1;
    private  static final String DATABASE_NAME = "PierInfo";
    private  static final String TABLE_STATEMENT = "PierInfo";
    private  static final String KEY_ID = "id";
    private  static final String KEY_DAY = "day";
    private  static final String KEY_MONTH = "month";
    private  static final String KEY_YEAR = "year";
    private  static final String KEY_DESCRIPTION = "description";
    private  static final String KEY_CATEGORY = "category";
    private  static final String KEY_VALUE = "value";
    private  static final String KEY_BALANCE = "balance";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STATEMENT_TABLE = "CREATE TABLE " + TABLE_STATEMENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DAY + " TEXT, "
                + KEY_MONTH + " TEXT, " + KEY_YEAR + "TEXT," + KEY_DESCRIPTION + "TEXT, "
                + KEY_CATEGORY + " TEXT, " + KEY_VALUE + "TEXT," + KEY_BALANCE + "TEXT" + ")";
        db.execSQL(CREATE_STATEMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATEMENT);

        onCreate(db);
    }

    public boolean addData(String Day, String Month, String Year, String Description, String Category, String Value, String Balance){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DAY, Day);
        values.put(KEY_MONTH, Month);
        values.put(KEY_YEAR, Year);
        values.put(KEY_DESCRIPTION, Description);
        values.put(KEY_CATEGORY, Category);
        values.put(KEY_VALUE, Value);
        values.put(KEY_BALANCE, Balance);

        long result = db.insert(TABLE_STATEMENT,null,values);

        //if data is inserted incorrectly it will return -1
        if (result == -1){
            return false;
        } else {
           return true;
        }
    }
}
