package com.lifttracker.common;

/**
 * Created by cameronridgewell on 6/24/16.
 */

import com.lifttracker.common.Workout;

import java.util.ArrayList;
import java.util.List;

public class Exercise {
    public enum LiftType {
        Chest, Back, Arms, Abs, Shoulders, Legs, Other, None
    }

    public enum ExerciseType {
        Weightlifting, Cardiovascular, Stretching, Other, None
    }

    private String id_;
    private String name_;
    private LiftType liftType_;
    private ExerciseType exerciseType_;

    public Exercise(String name)
    {
        this.name_ = name;
        this.liftType_ = LiftType.None;
        this.exerciseType_ = ExerciseType.None;
    }

    public String getName() {
        return name_;
    }

    public void setName(String name_) {
        this.name_ = name_;
    }

    public LiftType getLiftType() {
        return liftType_;
    }

    public void setLiftType(LiftType LiftType_) {
        this.liftType_ = LiftType_;
    }

    public ExerciseType getExerciseType() {
        return exerciseType_;
    }

    public void setExerciseType(ExerciseType exerciseType_) {
        this.exerciseType_ = exerciseType_;
    }

    public String getId() {
        return id_;
    }

    public void setId(String id_) {
        this.id_ = id_;
    }
}
