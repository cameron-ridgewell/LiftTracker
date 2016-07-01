package com.lifttracker.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.joda.time.DateTime;
import com.lifttracker.ExerciseDbContract;
import com.lifttracker.ExerciseDbHelper;
import com.lifttracker.common.Exercise;

import java.util.ArrayList;

/**
 * Created by cameronridgewell on 6/29/16.
 */

public class MemoryRequisition {

    private String FILENAME = "lifttracker_saved_items";
    private static Context mContext;
    private static MemoryRequisition instance = null;
    private ExerciseDbHelper mDbHelper;
    private SQLiteDatabase db;

    protected MemoryRequisition(Context context){
        mDbHelper = new ExerciseDbHelper(mContext);
        db = mDbHelper.getWritableDatabase();
        mContext = context;
    };

    public static MemoryRequisition getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            return new MemoryRequisition(context);
        } else {
            return instance;
        }
    }

    public void inputExerciseDb(ArrayList<Exercise> exerciseArrayList)
    {
        for (Exercise e : exerciseArrayList)
        {
            addExerciseDbItem(e, new DateTime());
        }
    }

    private void emptyTable()
    {
        db.execSQL("DELETE FROM " + ExerciseDbContract.ExerciseEntry.TABLE_NAME);
    }

    public void addExerciseDbItem(Exercise exercise, DateTime dateTime)
    {
        ContentValues values = new ContentValues();
        values.put(ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_NAME,
                exercise.getName());
        values.put(ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_ID,
                exercise.getId());
        values.put(ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_TYPE,
                exercise.getExerciseType().toString());
        values.put(ExerciseDbContract.ExerciseEntry.COLUMN_LIFT_TYPE,
                exercise.getLiftType().toString());
        values.put(ExerciseDbContract.ExerciseEntry.COLUMN_DATE,
                dateTime.toString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                ExerciseDbContract.ExerciseEntry.TABLE_NAME,
                null,
                values);
    }

    public Exercise getExerciseDbItem(String name_)
    {
        String[] projection = {
                ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_ID,
                ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_NAME,
                ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_TYPE,
                ExerciseDbContract.ExerciseEntry.COLUMN_LIFT_TYPE,
                ExerciseDbContract.ExerciseEntry.COLUMN_DATE,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_NAME + " ASC";

        String selection = ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_NAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { name_ };

        Cursor c = db.query(
                ExerciseDbContract.ExerciseEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        c.moveToFirst();
        try {
            return cursorToExercise(c);
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage().toString());
            return null;
        }
    }

    public ArrayList<Exercise> getAllExercises()
    {
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();

        String[] projection = {
                ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_ID,
                ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_NAME,
                ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_TYPE,
                ExerciseDbContract.ExerciseEntry.COLUMN_LIFT_TYPE,
                ExerciseDbContract.ExerciseEntry.COLUMN_DATE,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_NAME + " ASC";

        try (Cursor c = db.query(
                ExerciseDbContract.ExerciseEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );)
        {
            while (c.moveToNext()) {
                Exercise exercise = cursorToExercise(c);
                if (exercise != null)
                {
                    exercises.add(exercise);
                }
            }
        }

        return exercises;
    }

    private Exercise cursorToExercise(Cursor c)
    {
        try {
            Exercise exercise = new Exercise(c.getString(c.getColumnIndex(
                    ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_NAME)));
            exercise.setId(c.getString(c.getColumnIndex(
                    ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_ID)));
            exercise.setExerciseType(Exercise.ExerciseType.valueOf(c.getString(c.getColumnIndex(
                    ExerciseDbContract.ExerciseEntry.COLUMN_EXERCISE_TYPE))));
            exercise.setLiftType(Exercise.LiftType.valueOf(c.getString(c.getColumnIndex(
                    ExerciseDbContract.ExerciseEntry.COLUMN_LIFT_TYPE))));
            exercise.setLastPerformedDate(new DateTime(c.getColumnIndex(
                    ExerciseDbContract.ExerciseEntry.COLUMN_DATE)));
            return exercise;
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage().toString());
            return null;
        }
    }

    public ArrayList<Exercise> getAllExercisesByLiftType(Exercise.LiftType liftType)
    {
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        return exercises;
    }
}
