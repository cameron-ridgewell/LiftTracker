package com.lifttracker;

import android.provider.BaseColumns;

public final class ExerciseDbContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ExerciseDbContract() {}

    /* Inner class that defines the table contents */
    public static abstract class ExerciseEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercises_table";
        public static final String COLUMN_EXERCISE_ID = "exercise_id";
        public static final String COLUMN_EXERCISE_NAME = "exercise_name";
        public static final String COLUMN_EXERCISE_TYPE = "exercise_type";
        public static final String COLUMN_LIFT_TYPE = "lift_type";
        public static final String COLUMN_DATE = "most_recent_date";
    }
}
