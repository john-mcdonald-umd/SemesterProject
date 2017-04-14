package edu.umd.cs.semesterproject.model;

public class TimeRange {

    private long mStartTime;
    private long mEndTime;

    public TimeRange() {
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
}
