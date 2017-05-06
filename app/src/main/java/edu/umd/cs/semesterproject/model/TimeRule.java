package edu.umd.cs.semesterproject.model;

import java.util.List;

public class TimeRule extends Rule {

    private long mStartHour;
    private long mEndHour;
    private List<Day> mDays;

    public TimeRule() {
        super();
    }

    @Override
    public String getConditions() {
        return mStartHour + " - " + mEndHour;
    }

    public TimeRule(String name, boolean isEnabled, long startTime, long endTime, List<Day> days) {
        super(name, Rule.TYPE_TIME, isEnabled);

        mStartHour = startTime;
        mEndHour = endTime;
        mDays = days;
    }

    public long getStartTime() {
        return mStartHour;
    }

    public void setStartTime(long startTime) {
        mStartHour = startTime;
    }

    public long getEndTime() {
        return mEndHour;
    }

    public void setEndTime(long endTime) {
        mEndHour = endTime;
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
