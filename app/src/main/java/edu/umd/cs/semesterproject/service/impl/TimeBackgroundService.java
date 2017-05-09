package edu.umd.cs.semesterproject.service.impl;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

import edu.umd.cs.semesterproject.DependencyFactory;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.BluetoothAction;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.Time;
import edu.umd.cs.semesterproject.model.TimeRule;
import edu.umd.cs.semesterproject.model.VolumeAction;
import edu.umd.cs.semesterproject.model.WifiAction;
import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.util.DateUtil;

public class TimeBackgroundService extends IntentService {

    private static final int CHECK_CONDITIONS_REQUEST = 1;
    private static final String TAG = TimeBackgroundService.class.getSimpleName();

    public TimeBackgroundService(String name) {
        super(name);
    }

    @Override
    // Handles the intent when the service is called.
    // Checks all enabled rules.
    // For each enabled rule, if the current time is in the time frame for the rule then it will execute the start action
    // if the start action of the rule hasn't already been executed.
    // If it is out of the time frame it will try to execute the end action of the rule if it hasn't
    // already executed the end action for that rule.
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
                Time startTime = timeRule.getStartTime();
                Time endTime = timeRule.getEndTime();
                // for each day the rule is set
                for (TimeRule.Day day : timeRule.getDays()){
                    boolean actionExecuted = false;
                    // convert the TimeRule.Day to Calendar day.
                    int startDay = day.ordinal() + 1;

                    //Log.d("TimeBackgroundService", "Start hour: " + startTime.getHour() + ", start miunte: " + startTime.getMinute());
                    //Log.d("TimeBackgroundService", "End hour: " + endTime.getHour() + ", end miunte: " + endTime.getMinute());
                    //Log.d("TimeBackgroundService", "Current hour: " + currTime.get(Calendar.HOUR_OF_DAY) + ", current miunte: " + currTime.get(Calendar.MINUTE));

                    // if the day is equal to today.
                    if (startDay == (currTime.get(Calendar.DAY_OF_WEEK))){
                        // The start and end time are in the same day
                        if (startTime.getHour() < endTime.getHour() || ((startTime.getHour() == endTime.getHour()) && (startTime.getMinute() <= endTime.getMinute()))) {
                            if (startTime.getHour() < currTime.get(Calendar.HOUR_OF_DAY) || ((startTime.getHour() == (currTime.get(Calendar.HOUR_OF_DAY))) && (startTime.getMinute() <= currTime.get(Calendar.MINUTE)))) {
                                if (endTime.getHour() > currTime.get(Calendar.HOUR_OF_DAY) || ((endTime.getHour() == (currTime.get(Calendar.HOUR_OF_DAY)) && endTime.getMinute() >= currTime.get(Calendar.MINUTE)))) {
                                    executeAction(timeRule.getAction(), true);
                                    actionExecuted = true;
                                }
                            }
                        }
                        // The start and end time are in different days and the end time is tomorrow.
                        if (startTime.getHour() > endTime.getHour() || ((startTime.getHour() == endTime.getHour()) && (startTime.getMinute() >= endTime.getMinute()))) {
                            if (startTime.getHour() < currTime.get(Calendar.HOUR_OF_DAY) || ((startTime.getHour() == (currTime.get(Calendar.HOUR_OF_DAY))) && (startTime.getMinute() <= (currTime.get(Calendar.MINUTE))))) {
                                    executeAction(timeRule.getAction(), true);
                                    actionExecuted = true;
                            }
                        }
                    }
                    // check if the rule started yesterday
                    int yesterday = currTime.get(Calendar.DAY_OF_WEEK) - 1;
                    if (yesterday < 1)
                        yesterday = 7;
                    if (startDay == yesterday){
                        // the start and end time are in different days, and the start time was yesterday
                        if (endTime.getHour() > currTime.get(Calendar.HOUR_OF_DAY) || ((endTime.getHour() == (currTime.get(Calendar.HOUR_OF_DAY))) && endTime.getMinute() >= currTime.get(Calendar.MINUTE))){
                            executeAction(timeRule.getAction(), true);
                            actionExecuted = true;
                        }
                    }
                    // if the current time is not in the correct time frame and the end time action hasn't been exected yet
                    // then execute that action.
                    if (!actionExecuted){
                        executeAction(timeRule.getAction(), false);
                    }
                }

            }
        }
    }

    // execute the given action
    // start_end signifies if its the start of the action or the end of the action
    // true == start
    // false == end
    public void executeAction(Action a, boolean start_end){
        // if it is in the time frame and it hasn't already executed the start action.
        if (start_end && a.getStartEnd()){
            a.setStartEnd(false);
        }
        // if it is in the time frame and it has already executed the start action.
        else if (start_end && !a.getStartEnd()){
            return;
        }
        // if it is out of the time frame and it hasn't already executed the end action.
        else if (!start_end && !a.getStartEnd()){
            a.setStartEnd(true);
        }
        // if it is out of the time frame and it has already executed the end action.
        else{
            return;
        }

        if (a.getType() == Action.Type.VOLUME){
            VolumeAction volumeAction = (VolumeAction) a;
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            if (start_end) {
                // execute start action
                Log.d(TAG, "execute start volume");
                if (volumeAction.getStartMode().equals(VolumeAction.VolumeMode.SILENT)) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                } else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, (audioManager.getStreamMaxVolume(AudioManager.STREAM_RING) / volumeAction.getStartVolume()), 0);
                }
            }
            else{
                // execute end action
                if (volumeAction.getEndMode().equals(VolumeAction.VolumeMode.SILENT)) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                } else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, (audioManager.getStreamMaxVolume(AudioManager.STREAM_RING) / volumeAction.getEndVolume()), 0);
                }
                }
        }
        else if (a.getType() == Action.Type.BLUETOOTH){
            BluetoothAction bluetoothAction = (BluetoothAction) a;
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (start_end){
                if (bluetoothAction.getStartAction())
                    mBluetoothAdapter.enable();
                else
                    mBluetoothAdapter.disable();
            }
            else{
                if (bluetoothAction.getEndAction())
                    mBluetoothAdapter.enable();
                else
                    mBluetoothAdapter.disable();
            }
        }
        else if (a.getType() == Action.Type.WIFI){
            WifiAction wifiAction = (WifiAction) a;
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (start_end){
                Log.d(TAG, "execute start wifi");
                wifiManager.setWifiEnabled(wifiAction.getStartAction());
            }
            else{
                Log.d(TAG, "execute end wifi");
                wifiManager.setWifiEnabled(wifiAction.getEndAction());
            }
        }
        else{
            Log.e("TimeBackgroundService", "executeAction() called without a defined type, no action taken.");
        }

    }

    // Start an alarm with a given interval in milliseconds.
    // The alarm will send an intent to this class to check the time interval rules.
    public static void setTimeAlarm(Context context, int intervalInMilliSeconds) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent timeIntent = TimeBackgroundService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, CHECK_CONDITIONS_REQUEST, timeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + intervalInMilliSeconds,
                intervalInMilliSeconds, pendingIntent);
    }

    // cancel the alarm started by setTimeAlarm()
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
