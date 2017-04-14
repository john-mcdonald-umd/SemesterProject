package edu.umd.cs.semesterproject.model;

import java.util.List;

public class Rule {

    private String mId;
    private String mName;
    // TODO: Add geofence field.
    private List<TimeRange> mTimeRanges;
    private List<Day> mDays;
    private Boolean mIsEnabled;

    public Rule() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<TimeRange> getTimeRanges() {
        return mTimeRanges;
    }

    public void setTimeRanges(List<TimeRange> timeRanges) {
        mTimeRanges = timeRanges;
    }

    public List<Day> getDays() {
        return mDays;
    }

    public void setDays(List<Day> days) {
        mDays = days;
    }

    public Boolean getEnabled() {
        return mIsEnabled;
    }

    public void setEnabled(Boolean enabled) {
        mIsEnabled = enabled;
    }

    public enum Day {
        MON, TUES, WED, THURS, FRI, SAT, SUN
    }
}
