package edu.umd.cs.semesterproject.service.impl;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.semesterproject.DependencyFactory;
import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.BluetoothAction;
import edu.umd.cs.semesterproject.model.ReminderAction;
import edu.umd.cs.semesterproject.model.VolumeAction;
import edu.umd.cs.semesterproject.model.WifiAction;
import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.util.Codes;


public class GeofenceTransitionIntentService extends IntentService {


    private static final String TAG = GeofenceTransitionIntentService.class.getSimpleName();

    public GeofenceTransitionIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "geofence transition");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        // Handling errors
        if ( geofencingEvent.hasError() ) {
            String errorMsg = getErrorString(geofencingEvent.getErrorCode() );
            Log.e( TAG, errorMsg );
            return;
        }

        int geoFenceTransition = geofencingEvent.getGeofenceTransition();

        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

        String geofenceID = triggeringGeofences.get(0).getRequestId();
        Log.d(TAG, "ID: " + geofenceID);
        ArrayList<String> listOfIDs = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences){
            listOfIDs.add(geofence.getRequestId());
        }

        RuleService ruleService = DependencyFactory.getRuleService(getApplicationContext());

        // Check if the transition type is of interest
        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            Log.d(TAG, "enter");

            executeAction(ruleService.getRuleById(geofenceID).getAction(), true);
            for (String geofenceID : listOfIDs){
                if (geofenceID != null)
                    executeAction(ruleService.getRuleById(geofenceID).getAction(), true);
            }
        }
        else if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT ) {

            Log.d(TAG, "exit");
            // Get the geofence that were triggered

            for (String geofenceID : listOfIDs){
                if (geofenceID != null)
                    executeAction(ruleService.getRuleById(geofenceID).getAction(), false);
            }

            String geofenceTransitionDetails = getGeofenceTrasitionDetails(geoFenceTransition, triggeringGeofences );

            // Send notification details as a String
            // sendNotification( geofenceTransitionDetails );
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
        else if (a.getType() == Action.Type.REMINDER){
            ReminderAction reminderAction = (ReminderAction) a;
            if (start_end){
                if (!reminderAction.getStartReminder().equals("")){
                    Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext())
                            .setTicker(getResources().getString(R.string.reminder_notification))
                            .setSmallIcon(android.R.drawable.ic_menu_compass)
                            .setContentTitle(getResources().getString(R.string.reminder_notification))
                            .setContentText(reminderAction.getStartReminder())
                            .setAutoCancel(true);

                    Notification notification = notificationBuilder.build();

                    NotificationManager notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(Codes.MY_NOTIFICATION_ID, notification);
                }
            }
            else{
                if (!reminderAction.getEndReminder().equals("")){
                    Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext())
                            .setTicker(getResources().getString(R.string.reminder_notification))
                            .setSmallIcon(android.R.drawable.ic_menu_compass)
                            .setContentTitle(getResources().getString(R.string.reminder_notification))
                            .setContentText(reminderAction.getEndReminder())
                            .setAutoCancel(true);

                    Notification notification = notificationBuilder.build();

                    NotificationManager notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(Codes.MY_NOTIFICATION_ID, notification);
                }
            }
        }
        else{
            Log.e("TimeBackgroundService", "executeAction() called without a defined type, no action taken.");
        }

    }


    private String getGeofenceTrasitionDetails(int geoFenceTransition, List<Geofence> triggeringGeofences) {
        // get the ID of each geofence triggered
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for ( Geofence geofence : triggeringGeofences ) {
            triggeringGeofencesList.add( geofence.getRequestId() );
        }

        String status = null;
        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER )
            status = "Entering ";
        else if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT )
            status = "Exiting ";
        return status + TextUtils.join( ", ", triggeringGeofencesList);
    }


    private static String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";
        }
    }

}