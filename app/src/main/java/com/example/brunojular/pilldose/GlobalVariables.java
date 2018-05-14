package com.example.brunojular.pilldose;

public final class GlobalVariables {

    private static DatabaseHelper mDatabaseHelper;

    public static DatabaseHelper getmDatabaseHelper() {
        return mDatabaseHelper;
    }

    public static void setmDatabaseHelper(DatabaseHelper mDatabaseHelper) {
        GlobalVariables.mDatabaseHelper = mDatabaseHelper;
    }

}
