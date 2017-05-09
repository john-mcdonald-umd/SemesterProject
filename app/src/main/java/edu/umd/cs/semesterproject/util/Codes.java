package edu.umd.cs.semesterproject.util;

/*
   This class holds codes and a few functions that are used across classes.
 */

import android.content.Intent;

import java.io.Serializable;

import edu.umd.cs.semesterproject.model.Rule;

public class Codes {

    public static final int REQUEST_CODE_CREATE_RULE = 0;
    public static final String RULE_ID = "RULEID";
    public static final String RULE_CREATED = "RULE_CREATED";
    public static final int PLACE_PICKER_REQUEST = 199;
    public static final int MY_NOTIFICATION_ID = 10;

    public static Rule getRuleCreated(Intent data){
        Serializable result = data.getSerializableExtra(RULE_CREATED);
        Rule rule = (Rule) result;
        return rule;
    }

}
