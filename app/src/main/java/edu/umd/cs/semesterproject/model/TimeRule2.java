package edu.umd.cs.semesterproject.model;

import java.util.List;

public class TimeRule2 extends Rule {

    private int mStartHour;
    private int mStartMinute;
    private int mEndHour;
    private int mEndMinute;
    private List<Day> mDays;
    private Action startAction;
    private Action endAction;

    public TimeRule2() {
        super();
    }

    public TimeRule2(String name, boolean isEnabled, int startHour, int startMinute, int endHour, int endMinute, List<Day> days) {
        super(name, Rule.TYPE_TIME, isEnabled);

        mStartHour = startHour;
        mStartMinute = startMinute;
        mEndHour = endHour;
        mEndMinute = endMinute;
        mDays = days;
    }

    public int getStartHour() {
        return mStartHour;
    }

    public int getStartMinute(){ return mStartMinute; }

    public void setStartTime(int startHour, int startMinute) {
        mStartMinute = startMinute;
        mStartHour = startHour;
    }

    public int getEndHour() { return mEndHour; }

    public int getEndMinute() { return mEndMinute; }

    public void setEndTime(int endHour, int endMinute) {
        mEndMinute = endMinute;
        mEndHour = endHour;
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
