package edu.umd.cs.semesterproject.model;

import java.util.List;

public class TimeRule extends Rule {

    private long mStartTime;
    private long mEndTime;
    private List<Day> mDays;

    public TimeRule() {
        super();
    }

    public TimeRule(String name, boolean isEnabled, long startTime, long endTime, List<Day> days) {
        super(name, isEnabled);

        mStartTime = startTime;
        mEndTime = endTime;
        mDays = days;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long startTime) {
        mStartTime = startTime;
    }

    public long getEndTime() {
        return mEndTime;
    }

    public void setEndTime(long endTime) {
        mEndTime = endTime;
    }

    public List<Day> getDays() {
        return mDays;
    }

    public void setDays(List<Day> days) {
        mDays = days;
    }

    public enum Day {
        MON, TUE, WED, THUR, FRI, SAT, SUN
    }
}
