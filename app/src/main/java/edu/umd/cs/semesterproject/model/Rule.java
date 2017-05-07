package edu.umd.cs.semesterproject.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class Rule implements Serializable {

    private String mId;
    private String mName;
    private RuleType mRuleType;
    private ActionType mActionType;
    private Action mAction;
    private boolean mIsEnabled;

    public Rule() {
        mId = UUID.randomUUID().toString();
    }

    public Rule(String name, boolean isEnabled) {
        mId = UUID.randomUUID().toString();
        mName = name;
        mIsEnabled = isEnabled;
    }

    public Rule(String name, boolean isEnabled, ActionType actionType, RuleType ruleType, Action action) {
        mId = UUID.randomUUID().toString();
        mName = name;
        mRuleType = ruleType;
        mActionType = actionType;
        mAction = action;
        mIsEnabled = isEnabled;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public abstract String getConditions();

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public RuleType getRuleType() {
        return mRuleType;
    }

    public void setRuleType(RuleType ruleType) {
        mRuleType = ruleType;
    }

    public ActionType getActionType() {
        return mActionType;
    }

    public void setActionType(ActionType actionType) {
        mActionType = actionType;
    }

    public Action getAction() {
        return mAction;
    }

    public void setAction(Action action) {
        mAction = action;
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public void setEnabled(boolean enabled) {
        mIsEnabled = enabled;
    }

    public enum RuleType {
        TIME, LOCATION
    }

    public enum ActionType {
        VOLUME, WIFI, BLUETOOTH, HEADPHONE
    }
}