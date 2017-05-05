package edu.umd.cs.semesterproject.service.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "semester_project";
    public static final int DB_VERSION = 1;

    public static final String ID = "_ID";

    /*
     *  CREATE TABLE STATEMENTS
     */
    public static final String CREATE_TABLE_RULES = "CREATE TABLE " + DbSchema.RuleTable.NAME + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DbSchema.RuleTable.Columns.ID + " TEXT,"
            + DbSchema.RuleTable.Columns.NAME + " TEXT,"
            + DbSchema.RuleTable.Columns.IS_ENABLED + " INTEGER"
            + " )";

    public static final String CREATE_TABLE_TIME_RULES = "CREATE TABLE " + DbSchema.TimeRuleTable.NAME + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DbSchema.TimeRuleTable.Columns.ID + " TEXT,"
            + DbSchema.TimeRuleTable.Columns.RULE_ID + " INTEGER,"
            + DbSchema.TimeRuleTable.Columns.START_TIME + " INTEGER,"
            + DbSchema.TimeRuleTable.Columns.END_TIME + " INTEGER,"
            + "FOREIGN KEY(" + DbSchema.TimeRuleTable.Columns.RULE_ID + ") REFERENCES " + DbSchema.RuleTable.NAME + "(" + ID + ")"
            + " )";

    public static final String CREATE_TABLE_VOLUMES = "CREATE TABLE " + DbSchema.VolumeTable.NAME + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DbSchema.VolumeTable.Columns.ID + " TEXT,"
            + DbSchema.VolumeTable.Columns.RULE_ID + " INTEGER,"
            + DbSchema.VolumeTable.Columns.START_VOLUME + " INTEGER,"
            + DbSchema.VolumeTable.Columns.END_VOLUME + " INTEGER,"
            + DbSchema.VolumeTable.Columns.START_MODE + " INTEGER,"
            + DbSchema.VolumeTable.Columns.END_MODE + " INTEGER,"
            + "FOREIGN KEY(" + DbSchema.VolumeTable.Columns.RULE_ID + ") REFERENCES " + DbSchema.RuleTable.NAME + "(" + ID + ")"
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


}
