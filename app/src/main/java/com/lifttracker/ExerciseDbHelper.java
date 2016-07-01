package com.lifttracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExerciseDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ExerciseDbContract.ExerciseEntry.TABLE_NAME + " (" +
                    ExerciseDbContract.ExerciseEntry._ID + " INTEGER PRIMARY KEY," +
                    ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_ID + TEXT_TYPE + COMMA_SEP +
                    ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_NAME + TEXT_TYPE + COMMA_SEP +
                    ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_TYPE + TEXT_TYPE + COMMA_SEP +
                    ExerciseDbContract.ExerciseEntry.COLUMN_LIFT_TYPE + TEXT_TYPE + COMMA_SEP +
                    ExerciseDbContract.ExerciseEntry.COLUMN_DATE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ExerciseDbContract.ExerciseEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ExercisesDB.db";

    public ExerciseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}