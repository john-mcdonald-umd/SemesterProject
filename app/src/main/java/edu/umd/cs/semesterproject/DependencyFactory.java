package edu.umd.cs.semesterproject;

import android.app.Service;
import android.content.Context;

import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.service.impl.InMemoryRuleService;
import edu.umd.cs.semesterproject.service.impl.SQLiteRuleService;

public class DependencyFactory {

    private static RuleService mRuleService;

    public static RuleService getRuleService(Context context) {
        if (mRuleService == null) {
            mRuleService = new SQLiteRuleService(context);
        }
        return mRuleService;
    }
}
