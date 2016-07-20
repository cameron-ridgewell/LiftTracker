package com.lifttracker.common;

import android.util.Log;
import android.view.View;

/**
 * Created by cameronridgewell on 7/19/16.
 */
public class ExerciseWithViewId extends Exercise{
    private int id;
    public ExerciseWithViewId(String name) {
        super(name);
        this.id = View.generateViewId();
    }

    public ExerciseWithViewId(Exercise exercise) {
        super(exercise);
        Log.e("Tag", exercise.getName());
        this.id = View.generateViewId();
    }

    public int getViewId() {
        return id;
    }

    public void setViewId(int id) {
        this.id = id;
    }
}
