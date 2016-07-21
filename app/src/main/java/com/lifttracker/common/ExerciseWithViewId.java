package com.lifttracker.common;

import android.util.Log;
import android.view.View;

/**
 * Created by cameronridgewell on 7/19/16.
 */
public class ExerciseWithViewId extends Exercise{
    private int id;
    private boolean selected;
    public ExerciseWithViewId(String name) {
        super(name);
        this.id = View.generateViewId();
        this.selected = false;
    }

    public ExerciseWithViewId(Exercise exercise) {
        super(exercise);
        this.id = View.generateViewId();
        this.selected = false;
    }

    public ExerciseWithViewId(ExerciseWithViewId exercise)
    {
        super(exercise);
        this.id = exercise.id;
        this.selected = exercise.selected;

    }

    public int getViewId() {
        return id;
    }

    public void setViewId(int id) {
        this.id = id;
    }

    public void select()
    {
        selected = true;
    }

    public void deselect()
    {
        selected = false;
    }

    public boolean isSelected()
    {
        return selected;
    }
}
