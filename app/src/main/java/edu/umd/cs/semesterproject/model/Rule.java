package edu.umd.cs.semesterproject.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class Rule implements Serializable{

    private String mId;
    private String mName;
    private boolean mIsEnabled;

    public Rule() {
        mId = UUID.randomUUID().toString();
    }

    public Rule(String name, boolean isEnabled) {
        mId = UUID.randomUUID().toString();
        mName = name;
        mIsEnabled = isEnabled;
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

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public void setEnabled(boolean enabled) {
        mIsEnabled = enabled;
    }
}