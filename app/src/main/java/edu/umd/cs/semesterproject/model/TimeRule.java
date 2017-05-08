package edu.umd.cs.semesterproject.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// A rule that is triggered on changes in time.
// Has a start Time and end Time and a list of days that it executes on.
public class TimeRule extends Rule {

    private Time mStartTime;
    private Time mEndTime;
    private List<Day> mDays;

    public TimeRule() {
        super();
    }

    @Override
    public String getConditions() {
        return mStartTime.getHour() + ":" + String.format("%02d", mStartTime.getMinute()) + " - " + mEndTime.getHour() + ":" + String.format("%02d", mEndTime.getMinute());
    }

    public TimeRule(String name, boolean isEnabled, Time startTime, Time endTime, List<Day> days) {
        super(name, isEnabled);

        this.setRuleType(RuleType.TIME);
        mStartTime = startTime;
        mEndTime = endTime;
        mDays = days;
    }

    public TimeRule(String name, boolean isEnabled, int startHour, int startMinute, int endHour, int endMinute, List<Day> days) {
        super(name, isEnabled);

        this.setRuleType(RuleType.TIME);
        mStartTime = new Time(startHour, startMinute);
        mEndTime = new Time(endHour, endMinute);
        mDays = days;
    }

    public TimeRule(String name, boolean isEnabled, ActionType actionType, Action action, Time startTime, Time endTime, List<Day> days) {
        super(name, isEnabled, actionType, RuleType.TIME, action);

        mStartTime = startTime;
        mEndTime = endTime;
        mDays = days;
    }

    public Time getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Time startTime) {
        mStartTime = startTime;
    }

    public void setStartTime(int hour, int minute) {
        mStartTime = new Time(hour, minute);
    }

    public Time getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Time endTime) {
        mEndTime = endTime;
    }

    public void setEndTime(int hour, int minute) {
        mEndTime = new Time(hour, minute);
    }

    public List<Day> getDays() {
        return mDays;
    }

    public void setDays(List<Day> days) {
        mDays = days;
    }

    public String getRuleId() {
        return super.getId();
    }

    public enum Day {
        SUN, MON, TUE, WED, THUR, FRI, SAT
    }

}
