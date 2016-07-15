package com.lifttracker.common;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by cameronridgewell on 7/15/16.
 */
public class SuperSetExerciseList extends ArrayList<ArrayList<Exercise>> {
    public boolean add(Exercise exercise)
    {
        ArrayList<Exercise> tmp = new ArrayList<>();
        tmp.add(exercise);
        add(tmp);
        return true;
    }

    public boolean addTo(int index, Exercise exercise)
    {
        return get(index).add(exercise);
    }

    public boolean isSuperset(int index)
    {
        return get(index).size() > 1;
    }
}
