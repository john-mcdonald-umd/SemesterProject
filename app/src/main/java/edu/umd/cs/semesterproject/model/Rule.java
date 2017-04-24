package edu.umd.cs.semesterproject.model;

import java.util.List;
import java.util.UUID;

public class Rule {

    private String mId;
    private String mName;
    private List<Condition> mConditions;
    private List<Action> mActions;
    private boolean isEnabled;

    public Rule() {
        mId = UUID.randomUUID().toString();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Condition> getConditions() {
        return mConditions;
    }

    public void setConditions(List<Condition> conditions) {
        mConditions = conditions;
    }

    public List<Action> getActions() {
        return mActions;
    }

    public void setActions(List<Action> actions) {
        mActions = actions;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
