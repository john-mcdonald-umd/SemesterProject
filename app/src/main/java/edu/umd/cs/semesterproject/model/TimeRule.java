package edu.umd.cs.semesterproject.model;

import java.util.List;

public class TimeRule extends Rule {

    private Time mStartTime;
    private Time mEndTime;
    private List<Day> mDays;

    public TimeRule() {
        super();
    }

    public TimeRule(String id, String name, RuleType ruleType, ActionType actionType, Action action, boolean isEnabled, Time startTime, Time endTime, List<Day> days) {
        super(id, name, ruleType, actionType, isEnabled);
    }

    @Override
    public String getConditions() {
        return mStartTime.getHour() + " - " + mEndTime.getHour();
    }

    public TimeRule(String name, boolean isEnabled, long startTime, long endTime, List<Day> days) {
        super(name, Rule.TYPE_TIME, isEnabled);

        mStartTime = startTime;
        mEndTime = endTime;
        mDays = days;
    }

    public TimeRule(String name, RuleType ruleType, ActionType actionType, Action action, boolean isEnabled, Time startTime, Time endTime, List<Day> days) {
        super(name, ruleType, actionType, action, isEnabled);

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

    public Time getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Time endTime) {
        mEndTime = endTime;
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
