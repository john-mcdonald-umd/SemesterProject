package edu.umd.cs.semesterproject.util;

/* The codes for creating intents is going to get a little messy since we have a lot of different intents using similar rule names.
   I just put some of the rules in here to keep it organized.
 */

import android.content.Intent;

import java.io.Serializable;

import edu.umd.cs.semesterproject.model.Rule;

public class Codes {

    public static final int REQUEST_CODE_CREATE_RULE = 0;
    public static final String RULE_ID = "RULEID";
    public static final String RULE_CREATED = "RULE_CREATED";
    public static final int PLACE_PICKER_REQUEST = 199;

    public static Rule getRuleCreated(Intent data){
        Serializable result = data.getSerializableExtra(RULE_CREATED);
        Rule rule = (Rule) result;
        return rule;
    }

}
