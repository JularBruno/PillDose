package com.example.brunojular.pilldose;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.PipedInputStream;
import java.util.ArrayList;

public class PillVO {

    private long ID_pill;
    private String modulo;
    private String pill;
    private String horario;
    private String name;
    private DatabaseHelper dataBase;


    public PillVO(String modulo, String pill, String horario, String name) {
        this.modulo = modulo;
        this.pill = pill;
        this.horario = horario;
        this.name = name;
        this.dataBase = GlobalVariables.getmDatabaseHelper();
    }

    public boolean save(){

        SQLiteDatabase db = this.dataBase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.pill, this.pill);
        contentValues.put(DatabaseHelper.modulo, this.modulo);
        contentValues.put(DatabaseHelper.horario, this.horario);
        contentValues.put(DatabaseHelper.name, this.name);

        long result = db.insert(DatabaseHelper.pill_table, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            Log.d("databaseHelper", "FITLER: " + MainActivity.filter);
            Log.d("databaseHelper", "Se guardó");
            this.ID_pill = result;
            return true;
        }
    }

    public long getID_pill() {
        return ID_pill;
    }

    public void setID_pill(Integer ID_pill) {
        this.ID_pill = ID_pill;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getPill() {
        return pill;
    }

    public void setPill(String pill) {
        this.pill = pill;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<PillVO> getPillsForBluetooth(String person) {

        SQLiteDatabase db = GlobalVariables.getmDatabaseHelper().getWritableDatabase();

        ArrayList<PillVO> list = new ArrayList<>();

        String query = "SELECT * FROM " + DatabaseHelper.pill_table + " WHERE name == '" + person + "';";

        Cursor data = db.rawQuery(query, null);

        data.moveToFirst();

        while (data.moveToNext()) {
            list.add(
                    new PillVO(
                            data.getString(data.getColumnIndex(DatabaseHelper.modulo)),
                            data.getString(data.getColumnIndex(DatabaseHelper.pill)),
                            data.getString(data.getColumnIndex(DatabaseHelper.horario)),
                            data.getString(data.getColumnIndex(DatabaseHelper.name))
                    )
            );

        }

        return list;

    }

    public static ArrayList<PillVO> getPills(String person){

        SQLiteDatabase db = GlobalVariables.getmDatabaseHelper().getWritableDatabase();
        ArrayList<PillVO> list = new ArrayList<PillVO>();

        String query = "SELECT * FROM " + DatabaseHelper.pill_table + " WHERE name == '" + person + "';";

        Cursor data = db.rawQuery(query,null);

        data.moveToFirst();

        while (data.moveToNext()) {
            list.add(
                    new PillVO(
                            data.getString(data.getColumnIndex(DatabaseHelper.modulo)),
                            data.getString(data.getColumnIndex(DatabaseHelper.pill)),
                            data.getString(data.getColumnIndex(DatabaseHelper.horario)),
                            data.getString(data.getColumnIndex(DatabaseHelper.name))
                    )
            );

        }

        return list;
    }

}

