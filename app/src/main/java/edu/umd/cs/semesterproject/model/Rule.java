package edu.umd.cs.semesterproject.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class Rule implements Serializable{

    public static final String TYPE_TIME = "TIME";
    public static final String TYPE_LOCATION = "LOCATION";

    public static final String RULE_TYPE_VOLUME = "VOLUME";
    public static final String RULE_TYPE_WIFI = "WIFI";
    public static final String RULE_TYPE_BLUETOOTH = "BLUETOOTH";

    private String mId;
    private String mName;
    private String mConditions;
    private boolean mIsEnabled;
    // Time, Location, Hardware, ...
    private String mType;
    // Volume, Wifi, Bluetooth, ...
    private String mRuleType;

    public Rule() {
        mId = UUID.randomUUID().toString();
    }

    public Rule(String name, String type, boolean isEnabled) {
        mId = UUID.randomUUID().toString();
        mName = name;
        mType = type;
        mIsEnabled = isEnabled;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getConditions() { return "NEED TO IMPLEMENT CONDITIONS"; }

    public void setConditions(String conditions) { mConditions = conditions; };

    public String getType(){ return mType; }

    public void setType(String type){ mType = type; }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setRuleType(String rule_type){ mRuleType = rule_type; }

    public String getRuleType() { return mRuleType; }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public void setEnabled(boolean enabled) {
        mIsEnabled = enabled;
    }
}