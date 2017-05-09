package edu.umd.cs.semesterproject.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "semester_project";
    private static final int DB_VERSION = 3;

    public static final String _ID = "_id";

    /*
     *  CREATE TABLE STATEMENTS
     */
    public static final String CREATE_TABLE_RULES = "CREATE TABLE " + DbSchema.RuleTable.NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DbSchema.RuleTable.Columns.ID + " TEXT, "
            + DbSchema.RuleTable.Columns.NAME + " TEXT, "
            + DbSchema.RuleTable.Columns.RULE_TYPE + " TEXT, "
            + DbSchema.RuleTable.Columns.ACTION_TYPE + " TEXT, "
            + DbSchema.RuleTable.Columns.IS_ENABLED + " INTEGER "
            + " )";

    public static final String CREATE_TABLE_TIME_RULES = "CREATE TABLE " + DbSchema.TimeRuleTable.NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DbSchema.TimeRuleTable.Columns.RULE_ID + " TEXT, "
            + DbSchema.TimeRuleTable.Columns.START_TIME + " TEXT, "
            + DbSchema.TimeRuleTable.Columns.END_TIME + " TEXT, "
            + DbSchema.TimeRuleTable.Columns.DAYS + " TEXT, "
            + "FOREIGN KEY(" + DbSchema.TimeRuleTable.Columns.RULE_ID + ") REFERENCES " + DbSchema.RuleTable.NAME + "(" + DbSchema.RuleTable.Columns.ID + ")"
            + " )";

    public static final String CREATE_TABLE_LOCATION_RULES = "CREATE TABLE " + DbSchema.LocationRuleTable.NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DbSchema.LocationRuleTable.Columns.RULE_ID + " TEXT, "
            + DbSchema.LocationRuleTable.Columns.LATITUDE + " INTEGER, "
            + DbSchema.LocationRuleTable.Columns.LONGITUDE + " INTEGER, "
            + DbSchema.LocationRuleTable.Columns.RADIUS + " INTEGER, "
            + "FOREIGN KEY(" + DbSchema.LocationRuleTable.Columns.RULE_ID + ") REFERENCES " + DbSchema.RuleTable.NAME + "(" + DbSchema.RuleTable.Columns.ID + ")"
            + " )";

    public static final String CREATE_TABLE_VOLUMES = "CREATE TABLE " + DbSchema.VolumeTable.NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DbSchema.VolumeTable.Columns.ID + " TEXT, "
            + DbSchema.VolumeTable.Columns.RULE_ID + " TEXT, "
            + DbSchema.VolumeTable.Columns.START_VOLUME + " INTEGER, "
            + DbSchema.VolumeTable.Columns.END_VOLUME + " INTEGER, "
            + DbSchema.VolumeTable.Columns.START_MODE + " TEXT, "
            + DbSchema.VolumeTable.Columns.END_MODE + " TEXT, "
            + "FOREIGN KEY(" + DbSchema.VolumeTable.Columns.RULE_ID + ") REFERENCES " + DbSchema.RuleTable.NAME + "(" + DbSchema.RuleTable.Columns.ID + ")"
            + " )";

    public static final String CREATE_TABLE_WIFIS = "CREATE TABLE " + DbSchema.WifiTable.NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DbSchema.WifiTable.Columns.ID + " TEXT, "
            + DbSchema.WifiTable.Columns.RULE_ID + " TEXT, "
            + DbSchema.WifiTable.Columns.START_ENABLED + " INTEGER, "
            + DbSchema.WifiTable.Columns.END_ENABLED + " INTEGER, "
            + "FOREIGN KEY(" + DbSchema.WifiTable.Columns.RULE_ID + ") REFERENCES " + DbSchema.RuleTable.NAME + "(" + DbSchema.RuleTable.Columns.ID + ")"
            + " )";

    public static final String CREATE_TABLE_BLUETOOTHS = "CREATE TABLE " + DbSchema.BluetoothTable.NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DbSchema.BluetoothTable.Columns.ID + " TEXT, "
            + DbSchema.BluetoothTable.Columns.RULE_ID + " TEXT, "
            + DbSchema.BluetoothTable.Columns.START_ENABLED + " INTEGER, "
            + DbSchema.BluetoothTable.Columns.END_ENABLED + " INTEGER, "
            + "FOREIGN KEY(" + DbSchema.BluetoothTable.Columns.RULE_ID + ") REFERENCES " + DbSchema.RuleTable.NAME + "(" + DbSchema.RuleTable.Columns.ID + ")"
            + " )";

    public static final String CREATE_TABLE_REMINDERS = "CREATE TABLE " + DbSchema.ReminderTable.NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DbSchema.ReminderTable.Columns.ID + " TEXT, "
            + DbSchema.ReminderTable.Columns.RULE_ID + " TEXT, "
            + DbSchema.ReminderTable.Columns.START_REMINDER + " TEXT, "
            + DbSchema.ReminderTable.Columns.END_REMINDER + " TEXT, "
            + "FOREIGN KEY(" + DbSchema.ReminderTable.Columns.RULE_ID + ") REFERENCES " + DbSchema.RuleTable.NAME + "(" + DbSchema.RuleTable.Columns.ID + ")"
            + " )";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RULES);
        db.execSQL(CREATE_TABLE_TIME_RULES);
        db.execSQL(CREATE_TABLE_VOLUMES);
        db.execSQL(CREATE_TABLE_WIFIS);
        db.execSQL(CREATE_TABLE_BLUETOOTHS);
        db.execSQL(CREATE_TABLE_REMINDERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    /*
     * DATABASE CRUD METHODS
     */
    public void insert(String table, ContentValues contentValues) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(table, null, contentValues);
    }

    public Cursor select(String table, String[] columns, String whereClause, String[] whereArgs) {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(table, columns, whereClause, whereArgs, null, null, null);
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
    public Cursor select(String[] tables, String[] tableNames, String[] columns, String whereClause, String[] whereArgs) {

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
        System.out.println(query);

        return db.rawQuery(query, whereArgs);
    }

    public void update(String table, ContentValues contentValues, String whereClause, String[] whereArgs) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.update(table, contentValues, whereClause, whereArgs);
        db.close();
    }

    public void delete(String table, String whereClause, String[] whereArgs) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(table, whereClause, whereArgs);
    }
}
