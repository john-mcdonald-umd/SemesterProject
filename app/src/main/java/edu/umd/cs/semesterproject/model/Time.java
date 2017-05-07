package edu.umd.cs.semesterproject.model;

import java.io.Serializable;

public class Time implements Serializable{

    private int mHour;
    private int mMinute;

    public Time() {
    }

    public Time(int hour, int minute) {
        mHour = hour;
        mMinute = minute;
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        mMinute = minute;
    }
}
