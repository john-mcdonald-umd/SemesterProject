package edu.umd.cs.semesterproject.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.BluetoothAction;
import edu.umd.cs.semesterproject.model.LocationRule;
import edu.umd.cs.semesterproject.model.ReminderAction;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.TimeRule;
import edu.umd.cs.semesterproject.model.VolumeAction;
import edu.umd.cs.semesterproject.model.WifiAction;
import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.util.DateUtil;

public class SQLiteRuleService implements RuleService {

    private DbHelper mDatabase;

    public SQLiteRuleService(Context context) {
        mDatabase = new DbHelper(context);
    }

    @Override
    public void addRule(Rule rule) {

        if (ruleExists(rule.getId())) {
            updateRule(rule);
        } else {

            ContentValues contentValues = getRuleContentValues(rule);
            System.out.println(contentValues);
            mDatabase.insert(DbSchema.RuleTable.NAME, contentValues);

            ContentValues ruleContentValues;
            switch (rule.getRuleType()) {
                case TIME:
                    TimeRule timeRule = (TimeRule) rule;
                    ruleContentValues = getTimeRuleContentValues(timeRule);
                    mDatabase.insert(DbSchema.TimeRuleTable.NAME, ruleContentValues);
                    break;
                case LOCATION:
                    LocationRule locationRule = (LocationRule) rule;
                    ruleContentValues = getLocationRuleContentValues(locationRule);
                    mDatabase.insert(DbSchema.LocationRuleTable.NAME, ruleContentValues);
                    break;
            }

            ContentValues actionContentValues;
            switch (rule.getActionType()) {
                case VOLUME:
                    VolumeAction volumeAction = (VolumeAction) rule.getAction();
                    actionContentValues = getVolumeContentValues(volumeAction, rule);
                    mDatabase.insert(DbSchema.VolumeTable.NAME, actionContentValues);
                    break;
                case WIFI:
                    WifiAction wiFiAction = (WifiAction) rule.getAction();
                    actionContentValues = getWiFiContentValues(wiFiAction, rule);
                    mDatabase.insert(DbSchema.WifiTable.NAME, actionContentValues);
                    break;
                case BLUETOOTH:
                    BluetoothAction bluetoothAction = (BluetoothAction) rule.getAction();
                    actionContentValues = getBluetoothContentValues(bluetoothAction, rule);
                    mDatabase.insert(DbSchema.BluetoothTable.NAME, actionContentValues);
                    break;
                case REMINDER:
                    ReminderAction reminderAction = (ReminderAction) rule.getAction();
                    actionContentValues = getReminderContentValues(reminderAction, rule);
                    mDatabase.insert(DbSchema.ReminderTable.NAME, actionContentValues);
                    break;
            }
        }
    }

    @Override
    public Rule getRuleById(String id) {

        Rule rule = null;

        if (id == null) {
            rule = null;
        } else {
            if (!ruleExists(id)) {
                rule = null;
            } else {
                Action action = null;
                Rule.RuleType ruleType = getRuleTypeById(id);
                Rule.ActionType actionType = getActionTypeById(id);

                switch (ruleType) {
                    case TIME:
                        rule = getTimeRuleById(id);
                        break;
                    case LOCATION:
                        rule = getLocationRuleById(id);
                        break;
                }

                switch (actionType) {
                    case VOLUME:
                        action = getVolumeActionById(id);
                        break;
                    case WIFI:
                        action = getWifiActionById(id);
                        break;
                    case BLUETOOTH:
                        action = getBluetoothActionById(id);
                        break;
                    case REMINDER:
                        action = getReminderActionById(id);
                        break;
                }

                rule.setAction(action);
            }
        }

        return rule;
    }

    @Override
    public List<Rule> getAllRules() {

        List<Rule> rules = new ArrayList<>();

        Rule rule = null;
        Action action = null;

        Cursor ruleCursor = mDatabase.select(DbSchema.RuleTable.NAME, null, null, null);
        RuleCursorWrapper ruleCursorWrapper = new RuleCursorWrapper(ruleCursor);
        try {
            ruleCursorWrapper.moveToFirst();
            while (!ruleCursorWrapper.isAfterLast()) {
                Rule.RuleType ruleType = ruleCursorWrapper.getRuleType();
                switch (ruleType) {
                    case TIME:
                        rule = getTimeRuleById(ruleCursorWrapper.getRuleId());
                        break;
                    case LOCATION:
                        rule = getLocationRuleById(ruleCursorWrapper.getRuleId());
                        break;
                }

                Rule.ActionType actionType = ruleCursorWrapper.getActionType();
                switch (actionType) {
                    case VOLUME:
                        action = getVolumeActionById(ruleCursorWrapper.getRuleId());
                        break;
                    case WIFI:
                        action = getWifiActionById(ruleCursorWrapper.getRuleId());
                        break;
                    case BLUETOOTH:
                        action = getBluetoothActionById(ruleCursorWrapper.getRuleId());
                        break;
                    case REMINDER:
                        action = getReminderActionById(ruleCursorWrapper.getRuleId());
                        break;
                }

                rule.setAction(action);
                rules.add(rule);

                ruleCursorWrapper.moveToNext();
            }
        } finally {
            ruleCursorWrapper.close();
        }

        return rules;
    }

    @Override
    public List<Rule> getAllRulesByRuleType(Rule.RuleType ruleType) {

        List<Rule> rules = new ArrayList<>();

        Rule rule = null;
        Action action = null;

        Cursor ruleCursor = mDatabase.select(DbSchema.RuleTable.NAME, null, DbSchema.RuleTable.Columns.RULE_TYPE + "=?", new String[] {ruleType.toString()});
        RuleCursorWrapper ruleCursorWrapper = new RuleCursorWrapper(ruleCursor);
        try {
            ruleCursorWrapper.moveToFirst();
            while (!ruleCursorWrapper.isAfterLast()) {
                switch (ruleType) {
                    case TIME:
                        rule = getTimeRuleById(ruleCursorWrapper.getRuleId());
                        break;
                    case LOCATION:
                        rule = getLocationRuleById(ruleCursorWrapper.getRuleId());
                        break;
                }

                Rule.ActionType actionType = ruleCursorWrapper.getActionType();
                switch (actionType) {
                    case VOLUME:
                        action = getVolumeActionById(ruleCursorWrapper.getRuleId());
                        break;
                    case WIFI:
                        action = getWifiActionById(ruleCursorWrapper.getRuleId());
                        break;
                    case BLUETOOTH:
                        action = getBluetoothActionById(ruleCursorWrapper.getRuleId());
                        break;
                    case REMINDER:
                        action = getReminderActionById(ruleCursorWrapper.getRuleId());
                        break;
                }

                rule.setAction(action);
                rules.add(rule);

                ruleCursorWrapper.moveToNext();
            }
        } finally {
            ruleCursorWrapper.close();
        }

        return rules;
    }

    @Override
    public List<Rule> getAllRulesByActionType(Rule.ActionType actionType) {

        List<Rule> rules = new ArrayList<>();

        Rule rule = null;
        Action action = null;

        Cursor ruleCursor = mDatabase.select(DbSchema.RuleTable.NAME, null, DbSchema.RuleTable.Columns.ACTION_TYPE + "=?", new String[] {actionType.toString()});
        RuleCursorWrapper ruleCursorWrapper = new RuleCursorWrapper(ruleCursor);
        try {
            ruleCursorWrapper.moveToFirst();
            while (!ruleCursorWrapper.isAfterLast()) {
                Rule.RuleType ruleType = ruleCursorWrapper.getRuleType();
                switch (ruleType) {
                    case TIME:
                        rule = getTimeRuleById(ruleCursorWrapper.getRuleId());
                        break;
                    case LOCATION:
                        rule = getLocationRuleById(ruleCursorWrapper.getRuleId());
                        break;
                }

                switch (actionType) {
                    case VOLUME:
                        action = getVolumeActionById(ruleCursorWrapper.getRuleId());
                        break;
                    case WIFI:
                        action = getWifiActionById(ruleCursorWrapper.getRuleId());
                        break;
                    case BLUETOOTH:
                        action = getBluetoothActionById(ruleCursorWrapper.getRuleId());
                        break;
                    case REMINDER:
                        action = getReminderActionById(ruleCursorWrapper.getRuleId());
                        break;
                }

                rule.setAction(action);
                rules.add(rule);

                ruleCursorWrapper.moveToNext();
            }
        } finally {
            ruleCursorWrapper.close();
        }

        return rules;
    }

    @Override
    public List<Rule> getVolumeRules() {
        return getAllRulesByActionType(Rule.ActionType.VOLUME);
    }

    @Override
    public List<Rule> getBluetoothRules() {
        return getAllRulesByActionType(Rule.ActionType.BLUETOOTH);
    }

    @Override
    public List<Rule> getWifiRules() {
        return getAllRulesByActionType(Rule.ActionType.WIFI);
    }

    @Override
    public List<Rule> getReminderRules() {
        return getAllRulesByActionType(Rule.ActionType.REMINDER);
    }

    @Override
    public List<Rule> getLocationRules() {
        return getAllRulesByRuleType(Rule.RuleType.LOCATION);
    }

    @Override
    public List<Rule> getTimeRules() {
        return getAllRulesByRuleType(Rule.RuleType.TIME);
    }

    public void updateRule(Rule rule) {

        ContentValues contentValues = getRuleContentValues(rule);
        mDatabase.update(DbSchema.RuleTable.NAME, contentValues, DbSchema.RuleTable.Columns.ID + "=?", new String[]{rule.getId()});

        ContentValues ruleContentValues;
        switch (rule.getRuleType()) {
            case TIME:
                TimeRule timeRule = (TimeRule) rule;
                ruleContentValues = getTimeRuleContentValues(timeRule);
                mDatabase.update(DbSchema.TimeRuleTable.NAME, ruleContentValues, DbSchema.TimeRuleTable.Columns.RULE_ID + "=?", new String[]{rule.getId()});
                break;
            case LOCATION:
                break;
        }

        ContentValues actionContentValues;
        switch (rule.getActionType()) {
            case VOLUME:
                VolumeAction volumeAction = (VolumeAction) rule.getAction();
                actionContentValues = getVolumeContentValues(volumeAction, rule);
                mDatabase.update(DbSchema.VolumeTable.NAME, actionContentValues, DbSchema.VolumeTable.Columns.RULE_ID + "=?", new String[]{rule.getId()});
                break;
            case WIFI:
                WifiAction wiFiAction = (WifiAction) rule.getAction();
                actionContentValues = getWiFiContentValues(wiFiAction, rule);
                mDatabase.update(DbSchema.WifiTable.NAME, actionContentValues, DbSchema.WifiTable.Columns.RULE_ID + "=?", new String[]{rule.getId()});
                break;
            case BLUETOOTH:
                BluetoothAction bluetoothAction = (BluetoothAction) rule.getAction();
                actionContentValues = getBluetoothContentValues(bluetoothAction, rule);
                mDatabase.update(DbSchema.BluetoothTable.NAME, actionContentValues, DbSchema.BluetoothTable.Columns.RULE_ID + "=?", new String[]{rule.getId()});
                break;
            case REMINDER:
                ReminderAction reminderAction = (ReminderAction) rule.getAction();
                actionContentValues = getReminderContentValues(reminderAction, rule);
                mDatabase.update(DbSchema.ReminderTable.NAME, actionContentValues, DbSchema.ReminderTable.Columns.RULE_ID + "=?", new String[]{rule.getId()});
                break;
        }
    }

    @Override
    public void deleteRule(String id) {

        Rule.RuleType ruleType = getRuleTypeById(id);
        Rule.ActionType actionType = getActionTypeById(id);

        mDatabase.delete(DbSchema.RuleTable.NAME, DbSchema.RuleTable.Columns.ID + "=?", new String[]{id});

        switch (ruleType) {
            case TIME:
                mDatabase.delete(DbSchema.VolumeTable.NAME, DbSchema.TimeRuleTable.Columns.RULE_ID + "=?", new String[]{id});
                break;
            case LOCATION:
                break;
        }

        switch (actionType) {
            case VOLUME:
                mDatabase.delete(DbSchema.VolumeTable.NAME, DbSchema.VolumeTable.Columns.RULE_ID + "=?", new String[]{id});
                break;
            case WIFI:
                mDatabase.delete(DbSchema.WifiTable.NAME, DbSchema.WifiTable.Columns.RULE_ID + "=?", new String[]{id});
                break;
            case BLUETOOTH:
                mDatabase.delete(DbSchema.BluetoothTable.NAME, DbSchema.BluetoothTable.Columns.RULE_ID + "=?", new String[]{id});
                break;
            case REMINDER:
                mDatabase.delete(DbSchema.ReminderTable.NAME, DbSchema.ReminderTable.Columns.RULE_ID + "=?", new String[]{id});
                break;
        }
    }

    /*
     * HELPER METHODS
     */
    public boolean ruleExists(String id) {

        Cursor cursor = mDatabase.select(DbSchema.RuleTable.NAME, null, DbSchema.RuleTable.Columns.ID + "=?", new String[] {id});

        return cursor.moveToFirst();
    }

    public Rule.RuleType getRuleTypeById(String id) {

        Rule.RuleType ruleType = null;

        Cursor ruleTypeCursor = mDatabase.select(
                DbSchema.RuleTable.NAME,
                new String[] {DbSchema.RuleTable.Columns.RULE_TYPE},
                DbSchema.RuleTable.Columns.ID + "=?",
                new String[] {id}
        );
        RuleCursorWrapper ruleCursorWrapper = new RuleCursorWrapper(ruleTypeCursor);
        try {
            if (ruleCursorWrapper.moveToFirst()) {
                ruleType = ruleCursorWrapper.getRuleType();
            }
        } finally {
            ruleCursorWrapper.close();
        }

        return ruleType;
    }

    public Rule.ActionType getActionTypeById(String id) {

        Rule.ActionType actionType = null;

        Cursor actionTypeCursor = mDatabase.select(
                DbSchema.RuleTable.NAME,
                new String[] {DbSchema.RuleTable.Columns.ACTION_TYPE},
                DbSchema.RuleTable.Columns.ID + "=?",
                new String[] {id}
        );
        RuleCursorWrapper ruleCursorWrapper = new RuleCursorWrapper(actionTypeCursor);
        try {
            if (actionTypeCursor.moveToFirst()) {
                actionType = ruleCursorWrapper.getActionType();
            }
        } finally {
            ruleCursorWrapper.close();
        }

        return actionType;
    }

    public TimeRule getTimeRuleById(String id) {

        TimeRule timeRule = null;

        Cursor ruleCursor = mDatabase.select(
                new String[] {DbSchema.RuleTable.NAME, DbSchema.TimeRuleTable.NAME},
                new String[] {"rt", "trt"},
                null,
                "rt." + DbSchema.RuleTable.Columns.ID + "=? AND trt." + DbSchema.TimeRuleTable.Columns.RULE_ID + "=?", new String[]{id, id});
        RuleCursorWrapper ruleCursorWrapper = new RuleCursorWrapper(ruleCursor);
        try {
            if (ruleCursorWrapper.moveToFirst()) {
                timeRule = ruleCursorWrapper.getTimeRule();
            }
        } finally {
            ruleCursor.close();
        }

        return timeRule;
    }

    public LocationRule getLocationRuleById(String id) {

        LocationRule locationRule = null;

        Cursor ruleCursor = mDatabase.select(
                new String[] {DbSchema.RuleTable.NAME, DbSchema.LocationRuleTable.NAME},
                new String[] {"rt", "lrt"},
                null,
                "rt." + DbSchema.RuleTable.Columns.ID + "=? AND lrt." + DbSchema.LocationRuleTable.Columns.RULE_ID + "=?", new String[]{id, id});
        RuleCursorWrapper ruleCursorWrapper = new RuleCursorWrapper(ruleCursor);
        try {
            if (ruleCursorWrapper.moveToFirst()) {
                locationRule = ruleCursorWrapper.getLocationRule();
            }
        } finally {
            ruleCursor.close();
        }

        return locationRule;
    }

    public VolumeAction getVolumeActionById(String id) {

        VolumeAction volumeAction = null;

        Cursor actionCursor = mDatabase.select(DbSchema.VolumeTable.NAME, null, DbSchema.VolumeTable.Columns.RULE_ID + "=?", new String[] {id});
        ActionCursorWrapper actionCursorWrapper = new ActionCursorWrapper(actionCursor);
        try {
            if (actionCursorWrapper.moveToFirst()) {
                volumeAction = actionCursorWrapper.getVolumeAction();
            }
        } finally {
            actionCursor.close();
        }

        return volumeAction;
    }

    public WifiAction getWifiActionById(String id) {

        WifiAction wifiAction = null;

        Cursor actionCursor = mDatabase.select(DbSchema.WifiTable.NAME, null, DbSchema.WifiTable.Columns.RULE_ID + "=?", new String[] {id});
        ActionCursorWrapper actionCursorWrapper = new ActionCursorWrapper(actionCursor);
        try {
            if (actionCursorWrapper.moveToFirst()) {
                wifiAction = actionCursorWrapper.getWifiAction();
            }
        } finally {
            actionCursor.close();
        }

        return wifiAction;
    }

    public BluetoothAction getBluetoothActionById(String id) {

        BluetoothAction bluetoothAction = null;

        Cursor actionCursor = mDatabase.select(DbSchema.BluetoothTable.NAME, null, DbSchema.BluetoothTable.Columns.RULE_ID + "=?", new String[]{id});
        ActionCursorWrapper actionCursorWrapper = new ActionCursorWrapper(actionCursor);
        try {
            if (actionCursorWrapper.moveToFirst()) {
                bluetoothAction = actionCursorWrapper.getBluetoothAction();
            }
        } finally {
            actionCursor.close();
        }

        return bluetoothAction;
    }

    public ReminderAction getReminderActionById(String id) {

        ReminderAction reminderAction = null;

        Cursor actionCursor = mDatabase.select(DbSchema.ReminderTable.NAME, null, DbSchema.ReminderTable.Columns.RULE_ID + "=?", new String[]{id});
        ActionCursorWrapper actionCursorWrapper = new ActionCursorWrapper(actionCursor);
        try {
            if (actionCursorWrapper.moveToFirst()) {
                reminderAction = actionCursorWrapper.getReminderAction();
            }
        } finally {
            actionCursor.close();
        }

        return reminderAction;
    }

    /*
     * CONTENT VALUES
     */
    public ContentValues getRuleContentValues(Rule rule) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.RuleTable.Columns.ID, rule.getId());
        contentValues.put(DbSchema.RuleTable.Columns.NAME, rule.getName());
        contentValues.put(DbSchema.RuleTable.Columns.RULE_TYPE, rule.getRuleType().toString());
        contentValues.put(DbSchema.RuleTable.Columns.ACTION_TYPE, rule.getActionType().toString());
        contentValues.put(DbSchema.RuleTable.Columns.IS_ENABLED, rule.isEnabled());
        return contentValues;
    }

    public ContentValues getTimeRuleContentValues(TimeRule timeRule) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.TimeRuleTable.Columns.RULE_ID, timeRule.getId());
        contentValues.put(DbSchema.TimeRuleTable.Columns.START_TIME, DateUtil.timeToCsv(timeRule.getStartTime()));
        contentValues.put(DbSchema.TimeRuleTable.Columns.END_TIME, DateUtil.timeToCsv(timeRule.getEndTime()));
        contentValues.put(DbSchema.TimeRuleTable.Columns.DAYS, DateUtil.dayListToCsv(timeRule.getDays()));

        return contentValues;
    }

    public ContentValues getLocationRuleContentValues(LocationRule locationRule) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.LocationRuleTable.Columns.RULE_ID, locationRule.getId());
        contentValues.put(DbSchema.LocationRuleTable.Columns.LOCATION_NAME, locationRule.getPlaceName());
        contentValues.put(DbSchema.LocationRuleTable.Columns.LATITUDE, locationRule.getLatitude());
        contentValues.put(DbSchema.LocationRuleTable.Columns.LONGITUDE, locationRule.getLongitude());
        contentValues.put(DbSchema.LocationRuleTable.Columns.RADIUS, locationRule.getRadius());

        return contentValues;
    }

    public ContentValues getVolumeContentValues(VolumeAction volumeAction, Rule rule) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.VolumeTable.Columns.ID, volumeAction.getId());
        contentValues.put(DbSchema.VolumeTable.Columns.RULE_ID, rule.getId());
        contentValues.put(DbSchema.VolumeTable.Columns.START_VOLUME, volumeAction.getStartVolume());
        contentValues.put(DbSchema.VolumeTable.Columns.END_VOLUME, volumeAction.getEndVolume());
        contentValues.put(DbSchema.VolumeTable.Columns.START_MODE, volumeAction.getStartMode().toString());
        contentValues.put(DbSchema.VolumeTable.Columns.END_MODE, volumeAction.getEndMode().toString());

        return contentValues;
    }

    public ContentValues getWiFiContentValues(WifiAction wifiAction, Rule rule) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.WifiTable.Columns.ID, wifiAction.getId());
        contentValues.put(DbSchema.WifiTable.Columns.RULE_ID, rule.getId());
        contentValues.put(DbSchema.WifiTable.Columns.START_ENABLED, wifiAction.getStartAction());
        contentValues.put(DbSchema.WifiTable.Columns.END_ENABLED, wifiAction.getEndAction());

        return contentValues;
    }

    public ContentValues getBluetoothContentValues(BluetoothAction bluetoothAction, Rule rule) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.BluetoothTable.Columns.ID, bluetoothAction.getId());
        contentValues.put(DbSchema.BluetoothTable.Columns.RULE_ID, rule.getId());
        contentValues.put(DbSchema.BluetoothTable.Columns.START_ENABLED, bluetoothAction.getStartAction());
        contentValues.put(DbSchema.BluetoothTable.Columns.END_ENABLED, bluetoothAction.getEndAction());

        return contentValues;
    }

    public ContentValues getReminderContentValues(ReminderAction reminderAction, Rule rule) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.ReminderTable.Columns.ID, reminderAction.getId());
        contentValues.put(DbSchema.ReminderTable.Columns.RULE_ID, rule.getId());
        contentValues.put(DbSchema.ReminderTable.Columns.START_REMINDER, reminderAction.getStartReminder());
        contentValues.put(DbSchema.ReminderTable.Columns.END_REMINDER, reminderAction.getEndReminder());

        return contentValues;
    }

    /*
     * CURSORWRAPPERS
     */
    private class RuleCursorWrapper extends CursorWrapper {

        public RuleCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        private String getRuleId() {
            return getString(getColumnIndex(DbSchema.RuleTable.Columns.ID));
        }

        public Rule.RuleType getRuleType() {
            return Rule.RuleType.valueOf(getString(getColumnIndex(DbSchema.RuleTable.Columns.RULE_TYPE)));
        }

        public Rule.ActionType getActionType() {
            return Rule.ActionType.valueOf(getString(getColumnIndex(DbSchema.RuleTable.Columns.ACTION_TYPE)));
        }

        public TimeRule getTimeRule() {

            String id = getString(getColumnIndex(DbSchema.RuleTable.Columns.ID));
            String name = getString(getColumnIndex(DbSchema.RuleTable.Columns.NAME));
            String ruleType = getString(getColumnIndex(DbSchema.RuleTable.Columns.RULE_TYPE));
            String actionType = getString(getColumnIndex(DbSchema.RuleTable.Columns.ACTION_TYPE));
            int isEnabled = getInt(getColumnIndex(DbSchema.RuleTable.Columns.IS_ENABLED));

            String startTime = getString(getColumnIndex(DbSchema.TimeRuleTable.Columns.START_TIME));
            String endTime = getString(getColumnIndex(DbSchema.TimeRuleTable.Columns.END_TIME));
            String days = getString(getColumnIndex(DbSchema.TimeRuleTable.Columns.DAYS));

            TimeRule timeRule = new TimeRule();
            timeRule.setId(id);
            timeRule.setName(name);
            timeRule.setRuleType(Rule.RuleType.valueOf(ruleType));
            timeRule.setActionType(Rule.ActionType.valueOf(actionType));
            timeRule.setEnabled(isEnabled == 1);
            timeRule.setStartTime(DateUtil.csvToTime(startTime));
            timeRule.setEndTime(DateUtil.csvToTime(endTime));
            timeRule.setDays(DateUtil.csvToDayList(days));

            return timeRule;
        }

        public LocationRule getLocationRule() {

            String id = getString(getColumnIndex(DbSchema.RuleTable.Columns.ID));
            String name = getString(getColumnIndex(DbSchema.RuleTable.Columns.NAME));
            String ruleType = getString(getColumnIndex(DbSchema.RuleTable.Columns.RULE_TYPE));
            String actionType = getString(getColumnIndex(DbSchema.RuleTable.Columns.ACTION_TYPE));
            int isEnabled = getInt(getColumnIndex(DbSchema.RuleTable.Columns.IS_ENABLED));

            String placeName = getString(getColumnIndex(DbSchema.LocationRuleTable.Columns.LOCATION_NAME));
            double latitude = getDouble(getColumnIndex(DbSchema.LocationRuleTable.Columns.LATITUDE));
            double longitude = getDouble(getColumnIndex(DbSchema.LocationRuleTable.Columns.LONGITUDE));
            double radius = getDouble(getColumnIndex(DbSchema.LocationRuleTable.Columns.RADIUS));

            LocationRule locationRule = new LocationRule();
            locationRule.setId(id);
            locationRule.setName(name);
            locationRule.setRuleType(Rule.RuleType.valueOf(ruleType));
            locationRule.setActionType(Rule.ActionType.valueOf(actionType));
            locationRule.setEnabled(isEnabled == 1);
            locationRule.setPlaceName(placeName);
            locationRule.setLatitude(latitude);
            locationRule.setLongitude(longitude);
            locationRule.setRadius(radius);

            return locationRule;
        }
    }

    private class ActionCursorWrapper extends CursorWrapper {

        private ActionCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        private VolumeAction getVolumeAction() {

            String id = getString(getColumnIndex(DbSchema.VolumeTable.Columns.ID));
            int startVolume = getInt(getColumnIndex(DbSchema.VolumeTable.Columns.START_VOLUME));
            int endVolume = getInt(getColumnIndex(DbSchema.VolumeTable.Columns.END_VOLUME));
            String startMode = getString(getColumnIndex(DbSchema.VolumeTable.Columns.START_MODE));
            String endMode = getString(getColumnIndex(DbSchema.VolumeTable.Columns.END_MODE));

            VolumeAction volumeAction = new VolumeAction();
            volumeAction.setId(id);
            volumeAction.setStartVolume(startVolume);
            volumeAction.setEndVolume(endVolume);
            volumeAction.setStartMode(VolumeAction.VolumeMode.valueOf(startMode));
            volumeAction.setEndMode(VolumeAction.VolumeMode.valueOf(endMode));

            return volumeAction;
        }

        private WifiAction getWifiAction() {

            String id = getString(getColumnIndex(DbSchema.WifiTable.Columns.ID));
            int startEnabled = getInt(getColumnIndex(DbSchema.WifiTable.Columns.START_ENABLED));
            int endEnabled = getInt(getColumnIndex(DbSchema.WifiTable.Columns.END_ENABLED));

            WifiAction wiFiAction = new WifiAction();
            wiFiAction.setId(id);
            wiFiAction.setStartAction(startEnabled == 1);
            wiFiAction.setEndAction(endEnabled == 1);

            return wiFiAction;
        }

        private BluetoothAction getBluetoothAction() {

            String id = getString(getColumnIndex(DbSchema.BluetoothTable.Columns.ID));
            int startEnabled = getInt(getColumnIndex(DbSchema.BluetoothTable.Columns.START_ENABLED));
            int endEnabled = getInt(getColumnIndex(DbSchema.BluetoothTable.Columns.END_ENABLED));

            BluetoothAction bluetoothAction = new BluetoothAction();
            bluetoothAction.setId(id);
            bluetoothAction.setStartAction(startEnabled == 1);
            bluetoothAction.setEndAction(endEnabled == 1);

            return bluetoothAction;
        }

        private ReminderAction getReminderAction() {

            String id = getString(getColumnIndex(DbSchema.ReminderTable.Columns.ID));
            String startReminder = getString(getColumnIndex(DbSchema.ReminderTable.Columns.START_REMINDER));
            String endReminder = getString(getColumnIndex(DbSchema.ReminderTable.Columns.END_REMINDER));

            ReminderAction reminderAction = new ReminderAction();
            reminderAction.setId(id);
            reminderAction.setStartReminder(startReminder);
            reminderAction.setEndReminder(endReminder);

            return reminderAction;
        }
    }
}
