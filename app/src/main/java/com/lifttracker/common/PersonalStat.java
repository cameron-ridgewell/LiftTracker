package com.lifttracker.common;

import org.joda.time.DateTime;

/**
 * Created by cameronridgewell on 7/6/16.
 */
public class PersonalStat {
    public enum StatType {
        Weight, BodyFatPercentage, ChestMeasurement, WaistMeasurement 
    }

    public enum Unit {
        lb, kg, in, cm
    }
    
    private StatType statType_;
    private double measurement_;
    private Unit measurementUnit_;
    private long timeLong_;
    
    public PersonalStat(StatType statType, double measurement, Unit measurementUnit)
    {
        this.statType_ = statType;
        this.measurement_ = measurement;
        this.measurementUnit_ = measurementUnit;
    }

    public StatType getStatType() {
        return statType_;
    }

    public void setStatType(StatType statType) {
        this.statType_ = statType;
    }

    public double getMeasurement() {
        return measurement_;
    }

    public void setMeasurement(double measurement) {
        this.measurement_ = measurement;
    }

    public Unit getMeasurementUnit() {
        return measurementUnit_;
    }

    public void setMeasurementUnit(Unit measurementUnit) {
        this.measurementUnit_ = measurementUnit;
    }

    public DateTime getTime()
    {
        return new DateTime(timeLong_);
    }

    public void setTime(DateTime dateTime)
    {
        this.timeLong_ = dateTime.getMillis();
    }

    public void setTime(long timeLong)
    {
        this.timeLong_ = timeLong;
    }
}
