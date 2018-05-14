package com.example.brunojular.pilldose;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PillVO {

    private long ID_pill;
    private String modulo;
    private String pill;
    private String horario;
    private Integer ID_people;
    private DatabaseHelper dataBase;


    public PillVO(String modulo, String pill, String horario, Integer ID_people) {
        this.modulo = modulo;
        this.pill = pill;
        this.horario = horario;
        this.ID_people = ID_people;
        this.dataBase = GlobalVariables.getmDatabaseHelper();
    }

    public boolean save(){

        SQLiteDatabase db = this.dataBase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL4, this.pill);
        contentValues.put(DatabaseHelper.COL5, this.modulo);
        contentValues.put(DatabaseHelper.COL1, this.ID_people);
        contentValues.put(DatabaseHelper.COL6, this.horario);


        long result = db.insert(DatabaseHelper.TABLE_NAME2, null, contentValues);

        if (result == -1) {
            return false;
        } else {
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

    public Integer getID_people() {
        return ID_people;
    }

    public void setID_people(Integer ID_people) {
        this.ID_people = ID_people;
    }

    public static ArrayList<PillVO> getPills() {

        SQLiteDatabase db = GlobalVariables.getmDatabaseHelper().getWritableDatabase();
        ArrayList<PillVO> list = new ArrayList<PillVO>();

        String query= "SELECT * FROM "+DatabaseHelper.TABLE_NAME2+";";

        Cursor data = db.rawQuery(query,null);
        data.moveToFirst();
        while (data.moveToNext()) {
            list.add(
                    new PillVO(
                            data.getString(data.getColumnIndex(DatabaseHelper.COL5)),
                            data.getString(data.getColumnIndex(DatabaseHelper.COL4)),
                            data.getString(data.getColumnIndex(DatabaseHelper.COL6)),
                            data.getInt(data.getColumnIndex(DatabaseHelper.COL1))
                    )
            );
        }
        return list;
    }

}
