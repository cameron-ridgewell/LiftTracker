package com.lifttracker.common;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Created by cameronridgewell on 6/26/16.
 */
public class WorkoutItem {
    private Exercise exercise_;
    private List<Set> sets_;
    private long timeLong_;

    public WorkoutItem(Exercise exercise)
    {
        this.exercise_ = exercise;
        this.sets_ = new ArrayList<>();
        this.timeLong_ = DateTime.now().getMillis();
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

    public DateTime getStartTime()
    {
        return new DateTime(timeLong_);
    }

    public void setStartTime(DateTime time)
    {
        this.timeLong_ = time.getMillis();
    }


    public void setStartTime(long time)
    {
        this.timeLong_ = time;
    }
}
