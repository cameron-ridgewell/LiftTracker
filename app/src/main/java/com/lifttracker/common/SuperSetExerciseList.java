package com.lifttracker.common;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by cameronridgewell on 7/15/16.
 */
public class SuperSetExerciseList extends ArrayList<ArrayList<ExerciseWithViewId>> {
    public boolean add(ExerciseWithViewId exercise)
    {
        ArrayList<ExerciseWithViewId> tmp = new ArrayList<>();
        tmp.add(exercise);
        add(tmp);
        return true;
    }

    public boolean add(Exercise exercise)
    {
        ArrayList<ExerciseWithViewId> tmp = new ArrayList<>();
        tmp.add(new ExerciseWithViewId(exercise));
        add(tmp);
        return true;
    }

    public boolean addTo(int index, Exercise exercise)
    {
        return get(index).add(new ExerciseWithViewId(exercise));
    }

    public boolean addTo(int index, ExerciseWithViewId exercise)
    {
        return get(index).add(exercise);
    }

    public boolean isSuperset(int index)
    {
        return get(index).size() > 1;
    }
}
