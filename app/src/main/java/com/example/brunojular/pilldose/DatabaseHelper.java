package com.example.brunojular.pilldose;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String Tag = "DatabaseHelper";

    public static final String TABLE_NAME = "people_table";
    public static final String COL1 = "ID_people";
    public static final String COL2 = "name";

    public static final String TABLE_NAME2 = "pill_table";
    public static final String COL3 = "ID_pill";
    public static final String COL4 = "pill";
    public static final String COL5 = "modulo";
    public static final String COL6 = "horario";



    public DatabaseHelper(Context context) {

        super(context, "DatabaseHelper", null, 1);
    }

    /*
    TABLA
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ( " + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT);";

        String createTable2 = "CREATE TABLE " + TABLE_NAME2 + " ( "
                + COL3 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL4 + " TEXT,"
                + COL5 + " INTEGER,"
                + COL1 + " INTEGER,"
                + COL6 + " TEXT, "
                + " FOREIGN KEY (" + COL1 + ") REFERENCES "
                + TABLE_NAME + "(" + COL1 + "));";

        db.execSQL(createTable);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);

        Log.d(Tag, "addData: Adding " + item + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {

            return true;
        }
    }



    public boolean addData2(String newEntry1, String newEntry2, String horarios, Integer id_ppl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4, newEntry1);
        contentValues.put(COL5, newEntry2);
        contentValues.put(COL1, id_ppl);
        contentValues.put(COL6, horarios);



        long result = db.insert(TABLE_NAME2, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    /*
    Configuration for enabling foreign keys
     */

    @Override
    public void onConfigure (SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        } else {
            db.execSQL("pragma foreign_keys = ON");
        }
    }

}


