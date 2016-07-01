package com.lifttracker.common;

/**
 * Created by cameronridgewell on 6/24/16.
 */

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

public class Exercise {
    public enum LiftType {
        Chest, Back, Arms, Abs, Shoulders, Legs, Other, None
    }

    public enum ExerciseType {
        Weightlifting, Cardiovascular, Stretching, Other, None
    }

    @SerializedName("_id")
    private String id_;
    private String name_;
    private LiftType liftType_;
    private ExerciseType exerciseType_;
    private String lastPerformed_;

    public Exercise(String name)
    {
        this.name_ = name;
        this.liftType_ = LiftType.None;
        this.exerciseType_ = ExerciseType.None;
        this.lastPerformed_ = new DateTime(0).toString();
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

    public DateTime getLastPerformedDate() {
        return new DateTime(lastPerformed_);
    }

    public void setLastPerformedDate(DateTime lastPerformed_) {
        this.lastPerformed_ = lastPerformed_.toString();
    }

    public void setLastPerformedDate(String lastPerformed_) {
        this.lastPerformed_ = lastPerformed_;
    }
}
