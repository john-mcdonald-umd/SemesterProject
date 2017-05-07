package edu.umd.cs.semesterproject.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.TimeRule;
import edu.umd.cs.semesterproject.model.VolumeAction;
import edu.umd.cs.semesterproject.util.DateUtil;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "semester_project";
    public static final int DB_VERSION = 1;

    public static final String _ID = "_ID";

    /*
     *  CREATE TABLE STATEMENTS
     */
    public static final String CREATE_TABLE_RULES = "CREATE TABLE " + DbSchema.RuleTable.NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DbSchema.RuleTable.Columns.ID + " TEXT,"
            + DbSchema.RuleTable.Columns.NAME + " TEXT,"
            + DbSchema.RuleTable.Columns.RULE_TYPE + " TEXT,"
            + DbSchema.RuleTable.Columns.ACTION_TYPE + " TEXT,"
            + DbSchema.RuleTable.Columns.IS_ENABLED + " INTEGER"
            + " )";

    public static final String CREATE_TABLE_TIME_RULES = "CREATE TABLE " + DbSchema.TimeRuleTable.NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DbSchema.TimeRuleTable.Columns.RULE_ID + " INTEGER,"
            + DbSchema.TimeRuleTable.Columns.START_TIME + " TEXT,"
            + DbSchema.TimeRuleTable.Columns.END_TIME + " TEXT,"
            + DbSchema.TimeRuleTable.Columns.DAYS + " TEXT"
            + "FOREIGN KEY(" + DbSchema.TimeRuleTable.Columns.RULE_ID + ") REFERENCES " + DbSchema.RuleTable.NAME + "(" + _ID + ")"
            + " )";

    public static final String CREATE_TABLE_VOLUMES = "CREATE TABLE " + DbSchema.VolumeTable.NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DbSchema.VolumeTable.Columns.ID + " TEXT,"
            + DbSchema.VolumeTable.Columns.RULE_ID + " INTEGER,"
            + DbSchema.VolumeTable.Columns.START_VOLUME + " INTEGER,"
            + DbSchema.VolumeTable.Columns.END_VOLUME + " INTEGER,"
            + DbSchema.VolumeTable.Columns.START_MODE + " TEXT,"
            + DbSchema.VolumeTable.Columns.END_MODE + " TEXT,"
            + "FOREIGN KEY(" + DbSchema.VolumeTable.Columns.RULE_ID + ") REFERENCES " + DbSchema.RuleTable.NAME + "(" + _ID + ")"
            + " )";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RULES);
        db.execSQL(CREATE_TABLE_TIME_RULES);
        db.execSQL(CREATE_TABLE_VOLUMES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    /*
     * DATABASE CRUD METHODS
     */
    private void insert(String table, ContentValues contentValues) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(table, null, contentValues);
        db.close();
    }

    private Cursor select(String table, String[] columns, String whereClause, String[] whereArgs) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(table, columns, whereClause, whereArgs, null, null, null);
        db.close();

        return cursor;
    }

    /**
     * A select method that uses rawQuery.
     *
     * @param tables Array of tables to query.
     * @param tableNames Array of variable names for the tables to query. Must be in same order as tables array.
     * @param columns Array of columns to retrieve. Must be in the format <TableName>.<ColumnName>.
     * @param whereClause Any references to a column must be in the format <TableVarName>.<ColumnName>.
     * @param whereArgs Arguments to substitute into the whereClause. Any arg referencing a column must be in the format <TableVarName>.<ColumnName>.
     * @return Cursor with the query results.
     */
    private Cursor select(String[] tables, String[] tableNames, String[] columns, String whereClause, String[] whereArgs) {

        SQLiteDatabase db = this.getReadableDatabase();

        String tableString = "";
        for (int i = 0; i < tables.length - 1; i++) {
            tableString += tables[i] + " " + tableNames[i] + ", ";
        }
        tableString += tables[tables.length - 1] + " " + tableNames[tableNames.length - 1];

        String columnString = "";
        if (columns == null) {
            columnString = "*";
        } else {
            for (int i = 0; i < columns.length - 1; i++) {
                columnString += columns[i] + ", ";
            }
            columnString += columns[columns.length - 1];
        }

        String query = "SELECT " + columnString + " FROM " + tableString + " WHERE " + whereClause;

        Cursor cursor = db.rawQuery(query, whereArgs);
        db.close();

        return cursor;
    }

    private void update(String table, ContentValues contentValues, String whereClause, String[] whereArgs) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.update(table, contentValues, whereClause, whereArgs);
        db.close();
    }

    private void delete(String table, String whereClause, String[] whereArgs) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(table, whereClause, whereArgs);
        db.close();
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
        // TODO: Double check that the boolean is being entered into the DB as 0 / 1.
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

    /**
     * A method to get a rule's database id from its Java id.
     *
     * @param id The rule's Java id.
     * @return The rule's database id or if the rule doesn't exist -1.
     */
    public int getRuleDatabaseIdFromId(String id) {

        int ruleDatabaseId = -1;

        Cursor cursor = select(
                DbSchema.RuleTable.NAME,
                new String[]{_ID},
                DbSchema.RuleTable.Columns.ID + "=?",
                new String[]{id}
        );
        RuleCursorWrapper ruleCursorWrapper = new RuleCursorWrapper(cursor);
        try {
            if (ruleCursorWrapper.moveToFirst()) {
                ruleDatabaseId = ruleCursorWrapper.getRuleDatabaseId();
            }
        } finally {
            ruleCursorWrapper.close();
        }

        return ruleDatabaseId;
    }

    public boolean ruleExists(String id) {

        Cursor cursor = select(DbSchema.RuleTable.NAME, null, DbSchema.RuleTable.Columns.ID + "=?", new String[] {id});

        return cursor.moveToFirst();
    }

    /*
     * RULESERVICE METHODS
     */
    public void addRule(Rule rule) {

        if (ruleExists(rule.getId())) {
            updateRule(rule);
        } else {
            ContentValues contentValues = getRuleContentValues(rule);
            insert(DbSchema.RuleTable.NAME, contentValues);

            ContentValues ruleContentValues;
            switch (rule.getRuleType()) {
                case TIME:
                    TimeRule timeRule = (TimeRule) rule;
                    ruleContentValues = getTimeRuleContentValues(timeRule);
                    insert(DbSchema.TimeRuleTable.NAME, ruleContentValues);
                    break;
                case LOCATION:
                    break;
            }

            ContentValues actionContentValues;
            switch (rule.getActionType()) {
                case VOLUME:
                    VolumeAction volumeAction = (VolumeAction) rule.getAction();
                    actionContentValues = getVolumeContentValues(volumeAction, rule);
                    insert(DbSchema.VolumeTable.NAME, actionContentValues);
                    break;
                case WIFI:
                    break;
                case BLUETOOTH:
                    break;
            }
        }
    }

    public Rule getRuleById(String id) {

        Rule rule = null;
        Action action = null;

        if (ruleExists(id)) {

            Rule.RuleType ruleType = getRuleTypeById(id);
            Rule.ActionType actionType = getActionTypeById(id);

            switch (ruleType) {
                case TIME:
                    rule = getTimeRuleById(id);
                    break;
                case LOCATION:
                    break;
            }

            switch (actionType) {
                case VOLUME:
                    action = getVolumeActionById(id);
                    break;
                case WIFI:
                    break;
                case BLUETOOTH:
                    break;
            }

            rule.setAction(action);
        }

        return rule;
    }

    public List<Rule> getAllRules() {

        List<Rule> rules = new ArrayList<>();

        Rule rule = null;
        Action action = null;

        Cursor ruleCursor = select(DbSchema.RuleTable.NAME, null, null, null);
        RuleCursorWrapper ruleCursorWrapper = new RuleCursorWrapper(ruleCursor);
        try {
            ruleCursorWrapper.moveToFirst();
            while (!ruleCursorWrapper.isAfterLast()) {
                Rule.RuleType ruleType = ruleCursorWrapper.getRuleType();
                switch (ruleType) {
                    case TIME:
                        rule = ruleCursorWrapper.getTimeRule();
                        break;
                    case LOCATION:
                        break;
                }

                Rule.ActionType actionType = ruleCursorWrapper.getActionType();
                switch (actionType) {
                    case VOLUME:
                        action = getVolumeActionById(ruleCursorWrapper.getRuleId());
                        break;
                    case WIFI:
                        break;
                    case BLUETOOTH:
                        break;
                }

                rule.setAction(action);
                rules.add(rule);
            }
        } finally {
            ruleCursorWrapper.close();
        }

        return rules;
    }

    public List<Rule> getAllRulesByActionType(Rule.ActionType actionType) {

        List<Rule> rules = new ArrayList<>();

        Rule rule = null;
        Action action = null;

        Cursor ruleCursor = select(
                DbSchema.RuleTable.NAME,
                null,
                DbSchema.RuleTable.Columns.ACTION_TYPE + "=?",
                new String[]{actionType.toString()}
        );
        RuleCursorWrapper ruleCursorWrapper = new RuleCursorWrapper(ruleCursor);
        try {
            ruleCursorWrapper.moveToFirst();
            while (!ruleCursorWrapper.isAfterLast()) {
                Rule.RuleType ruleType = ruleCursorWrapper.getRuleType();
                switch (ruleType) {
                    case TIME:
                        rule = ruleCursorWrapper.getTimeRule();
                        break;
                    case LOCATION:
                        break;
                }

                switch (actionType) {
                    case VOLUME:
                        action = getVolumeActionById(ruleCursorWrapper.getRuleId());
                        break;
                    case WIFI:
                        break;
                    case BLUETOOTH:
                        break;
                }

                rule.setAction(action);
                rules.add(rule);
            }
        } finally {
            ruleCursorWrapper.close();
        }

        return rules;
    }

    public void updateRule(Rule rule) {

        ContentValues contentValues = getRuleContentValues(rule);
        update(DbSchema.RuleTable.NAME, contentValues, DbSchema.RuleTable.Columns.ID + "=?", new String[]{rule.getId()});

        ContentValues ruleContentValues;
        switch (rule.getRuleType()) {
            case TIME:
                TimeRule timeRule = (TimeRule) rule;
                ruleContentValues = getTimeRuleContentValues(timeRule);
                update(DbSchema.TimeRuleTable.NAME, ruleContentValues, DbSchema.TimeRuleTable.Columns.RULE_ID + "=?", new String[]{rule.getId()});
                break;
            case LOCATION:
                break;
        }

        ContentValues actionContentValues;
        switch (rule.getActionType()) {
            case VOLUME:
                VolumeAction volumeAction = (VolumeAction) rule.getAction();
                actionContentValues = getVolumeContentValues(volumeAction, rule);
                update(DbSchema.VolumeTable.NAME, actionContentValues, DbSchema.VolumeTable.Columns.RULE_ID + "=?", new String[]{rule.getId()});
                break;
            case WIFI:
                break;
            case BLUETOOTH:
                break;
        }
    }

    public void deleteRule(String id) {

        Rule.RuleType ruleType = getRuleTypeById(id);
        Rule.ActionType actionType = getActionTypeById(id);

        delete(DbSchema.RuleTable.NAME, DbSchema.RuleTable.Columns.ID + "=?", new String[] {id});

        switch (ruleType) {
            case TIME:
                delete(DbSchema.VolumeTable.NAME, DbSchema.TimeRuleTable.Columns.RULE_ID + "=?", new String[] {id});
                break;
            case LOCATION:
                break;
        }

        switch (actionType) {
            case VOLUME:
                delete(DbSchema.VolumeTable.NAME, DbSchema.VolumeTable.Columns.RULE_ID + "=?", new String[] {id});
                break;
            case WIFI:
                break;
            case BLUETOOTH:
                break;
            case HEADPHONE:
                break;
        }
    }

    /*
     * HELPER METHODS
     */
    public Rule.RuleType getRuleTypeById(String id) {

        Rule.RuleType ruleType = null;

        Cursor ruleTypeCursor = select(
                DbSchema.RuleTable.NAME,
                new String[]{DbSchema.RuleTable.Columns.RULE_TYPE},
                DbSchema.RuleTable.Columns.ID + "=?",
                new String[]{id}
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

        Cursor actionTypeCursor = select(
                DbSchema.RuleTable.NAME,
                new String[]{DbSchema.RuleTable.Columns.ACTION_TYPE},
                DbSchema.RuleTable.Columns.ID + "=?",
                new String[]{id}
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

        Cursor ruleCursor = select(
                new String[]{DbSchema.RuleTable.NAME, DbSchema.TimeRuleTable.NAME},
                new String[]{"rt", "trt"},
                null,
                DbSchema.RuleTable.Columns.ID + "=?", new String[] {id});
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

    public VolumeAction getVolumeActionById(String id) {

        VolumeAction volumeAction = null;

        Cursor actionCursor = select(DbSchema.VolumeTable.NAME, null, DbSchema.VolumeTable.Columns.RULE_ID + "=?", new String[]{id});
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

    /*
     * CURSORWRAPPERS
     */
    private class RuleCursorWrapper extends CursorWrapper {

        public RuleCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public int getRuleDatabaseId() {
            return getInt(getColumnIndex(_ID));
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
    }
}
