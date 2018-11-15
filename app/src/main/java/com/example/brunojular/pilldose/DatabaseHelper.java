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

    public static final String people_table = "people_table";
    public static final String iD_people = "ID_people";
    public static final String name = "name";

    public static final String pill_table = "pill_table";
    public static final String iD_pill = "ID_pill";
    public static final String pill = "pill";
    public static final String modulo = "modulo";
    public static final String horario = "horario";


    public DatabaseHelper(Context context) {

        super(context, "DatabaseHelper", null, 1);
    }

    /*
    TABLA
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + people_table + " ( " + iD_people + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                name + " TEXT);";

        String createTable2 = "CREATE TABLE " + pill_table + " ( "
                + iD_pill + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + pill + " TEXT,"
                + modulo + " INTEGER,"
                + horario + " TEXT,"
                + name + " TEXT);";

        String InsertDefault = "INSERT INTO people_table (ID_people, name) VALUES ('0' , ' ');";

        db.execSQL(createTable);
        db.execSQL(createTable2);
        db.execSQL(InsertDefault);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + people_table);
        db.execSQL("DROP TABLE IF EXISTS " + pill_table);
        onCreate(db);
    }

    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(name, item);

        Log.d(Tag, "addData: Adding " + item + " to " + people_table);
        long result = db.insert(people_table, null, contentValues);

        if (result == -1) {
            return false;
        } else {

            return true;
        }
    }


    public boolean addData2(String newEntry1, String newEntry2, String horarios, Integer id_ppl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(pill, newEntry1);
        contentValues.put(modulo, newEntry2);
        contentValues.put(horario, horarios);
        contentValues.put(name, id_ppl);



        long result = db.insert(pill_table, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + people_table;
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + people_table;

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


