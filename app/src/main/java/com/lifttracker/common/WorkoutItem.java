package com.lifttracker.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cameronridgewell on 6/26/16.
 */
public class WorkoutItem {

    private Exercise exercise_;
    private List<Set> sets_;

    public WorkoutItem(Exercise exercise)
    {
        this.exercise_ = exercise;
        sets_ = new ArrayList<>();
    }

    public Exercise getExercise() {
        return exercise_;
    }

    public void setExercise(Exercise exercise_) {
        this.exercise_ = exercise_;
    }

    public Set getSet(int index) {
        return sets_.get(index);
    }

    public List<Set> getSetList() {
        return sets_;
    }

    public void addSet(Set set) {
        this.sets_.add(set);
    }

    public void removeSet(int index) {
        this.sets_.remove(index);
    }

    public void setSetList(List<Set> sets_) {
        this.sets_ = sets_;
    }
}
