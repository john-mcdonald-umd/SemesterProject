package edu.umd.cs.semesterproject.model;

public class ReminderAction extends Action {

    private String mStartReminder;
    private String mEndReminder;

    public ReminderAction(String startReminder, String endReminder) {
        super(Type.REMINDER);

        mStartReminder = startReminder;
        mEndReminder = endReminder;
    }

    public String getStartReminder() {
        return mStartReminder;
    }

    public void setStartReminder(String startReminder) {
        mStartReminder = startReminder;
    }

    public String getEndReminder() {
        return mEndReminder;
    }

    public void setEndReminder(String endReminder) {
        mEndReminder = endReminder;
    }
}
