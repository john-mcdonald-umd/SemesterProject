package edu.umd.cs.semesterproject.model;

import java.util.UUID;

public abstract class Action {

    private String mId;

    public Action() {
        mId = UUID.randomUUID().toString();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
