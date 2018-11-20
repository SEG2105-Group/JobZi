package com.arom.jobzi.service;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.sql.Time;

public class Availability implements Serializable {

    private static float serialVersionUID = 1F;

    private Time startTime;
    private Time endTime;

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Exclude
    public String getDisplayName() {
        // TODO: implement this.
        return "";
    }

}
