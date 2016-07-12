package com.lifttracker.common;

/**
 * Created by cameronridgewell on 6/24/16.
 */
import org.joda.time.DateTime;
import com.lifttracker.common.Exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class Workout {
    private long timeLong_;
    private Exercise.LiftType LiftType_;
    private List<WorkoutItem> exercise_list_;

    public Workout(DateTime dateTime)
    {
        this.timeLong_ = dateTime.getMillis();
        this.LiftType_ = Exercise.LiftType.None;
        this.exercise_list_ = new ArrayList<>();
    }

    public DateTime getStartTime() {
        return new DateTime(timeLong_);
    }

    public void setStartTime(DateTime dateTime_) {
        this.timeLong_ = dateTime_.getMillis();
    }

    public void setStartTime(long dateTime_) {
        this.timeLong_ = dateTime_;
    }

    public Exercise.LiftType getLiftType() {
        return LiftType_;
    }

    public void setLiftType(Exercise.LiftType LiftType_) {
        this.LiftType_ = LiftType_;
    }

    public void addExercise(Exercise exercise)
    {
        WorkoutItem newItem = new WorkoutItem(exercise);
        this.exercise_list_.add(newItem);
    }

    public void swapExercise(int index1, int index2)
    {
        Collections.swap(this.exercise_list_, index1, index2);
    }

    public List<WorkoutItem> getExerciseList() {
        return this.exercise_list_;
    }
}
