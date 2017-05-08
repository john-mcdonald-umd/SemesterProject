package edu.umd.cs.semesterproject.service.impl;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import edu.umd.cs.semesterproject.DependencyFactory;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.Time;
import edu.umd.cs.semesterproject.model.TimeRule;
import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.util.DateUtil;

public class TimeBackgroundService extends IntentService {

    private static final int CHECK_CONDITIONS_REQUEST = 1;

    public TimeBackgroundService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Check the time rules to see if any are should be triggered.
        RuleService ruleService = DependencyFactory.getRuleService(getApplicationContext());
        List<Rule> rules = ruleService.getTimeRules();
        Calendar currTime = Calendar.getInstance();
        currTime.set(Calendar.HOUR_OF_DAY, currTime.get(Calendar.HOUR_OF_DAY));
        currTime.set(Calendar.MINUTE, currTime.get(Calendar.MINUTE));
        currTime.set(Calendar.DAY_OF_WEEK, currTime.get(Calendar.DAY_OF_WEEK));

        for (Rule r : rules){
            if (r.isEnabled()){
                TimeRule timeRule = (TimeRule) r;
                Time startTime = timeRule.getEndTime();
                Time endTime = timeRule.getStartTime();
                for (TimeRule.Day day : timeRule.getDays()){
                    int startDay = day.ordinal() + 1;
                    if (startDay > 7)
                        startDay = 1;
                    // check today
                    if (startDay == (currTime.get(Calendar.DAY_OF_WEEK))){
                        // The start and end time are in the same day
                        if (startTime.getHour() <= endTime.getHour() || ((startTime.getHour() == endTime.getHour()) && (startTime.getMinute() <= endTime.getHour()))) {
                            if (startTime.getHour() <= currTime.get(Calendar.HOUR_OF_DAY) || ((startTime.getHour() == (currTime.get(Calendar.HOUR_OF_DAY))) && (startTime.getMinute() <= currTime.get(Calendar.MINUTE)))) {
                                if (endTime.getHour() >= currTime.get(Calendar.HOUR_OF_DAY) || ((endTime.getHour() == (currTime.get(Calendar.HOUR_OF_DAY)) && endTime.getMinute() >= currTime.get(Calendar.MINUTE)))) {
                                    executeAction(timeRule.getAction());
                                }
                            }
                        }
                        // The start and end time are in different days and the end time is tomorrow.
                        if (startTime.getHour() >= endTime.getHour() || ((startTime.getHour() == endTime.getHour()) && (startTime.getMinute() >= endTime.getHour()))) {
                            if (startTime.getHour() <= currTime.get(Calendar.HOUR_OF_DAY) || ((startTime.getHour() == (currTime.get(Calendar.HOUR_OF_DAY))) && (startTime.getMinute() <= (currTime.get(Calendar.HOUR_OF_DAY))))) {
                                    executeAction(timeRule.getAction());
                            }
                        }
                    }
                    // check yesterday overlap
                    int yesterday = currTime.get(Calendar.DAY_OF_WEEK) - 1;
                    if (yesterday < 1)
                        yesterday = 7;
                    if (startDay == yesterday){
                        // the start and end time are in different days, and the start time was yesterday
                        if (endTime.getHour() >= currTime.get(Calendar.HOUR_OF_DAY) || ((endTime.getHour() == (currTime.get(Calendar.HOUR_OF_DAY))) && endTime.getMinute() >= currTime.get(Calendar.MINUTE))){
                            executeAction(timeRule.getAction());
                        }
                    }
                }

            }
        }
    }

    public void executeAction(Action a){
        Log.d("TimeBackgroundService", "executeAction()");
    }

    public static void setTimeAlarm(Context context, int intervalInMilliSeconds) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent timeIntent = TimeBackgroundService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, CHECK_CONDITIONS_REQUEST, timeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + intervalInMilliSeconds,
                intervalInMilliSeconds, pendingIntent);
    }

    public static void cancelTimeAlarm(Context context, TimeRule timeRule) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent reminderIntent = TimeBackgroundService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, CHECK_CONDITIONS_REQUEST, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }

    public TimeBackgroundService(){
        super("time");
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, TimeBackgroundService.class);
    }
}
