package com.lifttracker.common;

/**
 * Created by cameronridgewell on 6/24/16.
 */
public class Set{
    private int reps_;
    private double weight_;
    private String weight_units_;

    public Set(int reps_, double weight_, String weight_units) {
        this.reps_ = reps_;
        this.weight_ = weight_;
        this.weight_units_ = weight_units;
    }

    public Set(int reps_, double weight_) {
        this.reps_ = reps_;
        this.weight_ = weight_;
        this.weight_units_ = "lbs";
    }

    public int getReps() {
        return reps_;
    }

    public void setReps(int reps_) {
        this.reps_ = reps_;
    }

    public double getWeight() {
        return weight_;
    }

    public void setWeight(double weight_) {
        this.weight_ = weight_;
    }

    public String getWeightUnits() {
        return weight_units_;
    }

    public void setWeightUnits(String weight_units) {
        this.weight_units_ = weight_units;
    }
}
