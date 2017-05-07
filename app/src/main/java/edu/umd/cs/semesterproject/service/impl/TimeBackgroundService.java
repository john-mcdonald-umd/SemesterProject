package edu.umd.cs.semesterproject.service.impl;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import android.support.annotation.Nullable;

import edu.umd.cs.semesterproject.model.Day;
import edu.umd.cs.semesterproject.model.TimeRule;
import edu.umd.cs.semesterproject.util.DateUtil;

public class TimeBackgroundService extends IntentService {

    private static final int REQUEST_CODE = 1;

    public TimeBackgroundService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // TODO: Get all TimeRules.
    }

    public void setTimeAlarm(Context context, TimeRule timeRule) {
        PendingIntent pendingIntent = PendingIntent.getService(context, REQUEST_CODE, newIntent(context), PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        Calendar calendar;
        for (Day.DAY_STRING day : timeRule.getDays()) {
            calendar = DateUtil.setCalendar(
                    Day.getCalendarDay(day),
                    timeRule.getStartTime().getHour(),
                    timeRule.getStartTime().getMinute());

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        }
    }

    public void cancelTimeAlarm(Context context, TimeRule timeRule) {
        PendingIntent pendingIntent = PendingIntent.getService(context, REQUEST_CODE, newIntent(context), PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        for (Day.DAY_STRING day : timeRule.getDays()) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, TimeBackgroundService.class);
    }
}
