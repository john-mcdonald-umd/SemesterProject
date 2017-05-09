package edu.umd.cs.semesterproject.model;

import java.io.Serializable;
import java.util.UUID;

// An action to be executed when the conditions of a rule is set.
public abstract class Action implements Serializable {

    // this variables keep track whether the start and end actions need to be executed.
    // true = start needs to be executed
    // false = end needs to be executed
    private boolean execute_start_end;

    private String mId;
    private Type mType;

    public Action(Type type) {
        mId = UUID.randomUUID().toString();
        mType = type;
        execute_start_end = true;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setType(Type type){
        mType = type;
    }

    public void toggleStartEnd(){
        execute_start_end = !execute_start_end;
    }

    public void setStartEnd(boolean set){
        execute_start_end = set;
    }

    public boolean getStartEnd(){
        return execute_start_end;
    }

    public Type getType(){
        return mType;
    }

    public enum Type{
        VOLUME, WIFI, BLUETOOTH, REMINDER;
    }
}
