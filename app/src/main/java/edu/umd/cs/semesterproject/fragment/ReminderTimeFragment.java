package edu.umd.cs.semesterproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.TimeRule;
import edu.umd.cs.semesterproject.util.Codes;

/**
 * Created by James on 5/9/2017.
 */

public class ReminderTimeFragment extends BaseTimeFragment {

    private final String TAG = "ReminderTimeFragment";

    public static Fragment newInstance(String userID){
        Bundle bundle = new Bundle();
        bundle.putString(Codes.RULE_ID, userID);
        ReminderTimeFragment ruleFragment = new ReminderTimeFragment();
        ruleFragment.setArguments(bundle);
        return ruleFragment;
    }

    @Override
    protected Rule.ActionType getActionType() {
        return Rule.ActionType.REMINDER;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reminder_time;
    }

    @Override
    protected void setupSpecificLayout(View view, TimeRule rule) {
        ReminderFragment.setupSpecificLayout(view, rule);
    }

    @Override
    protected Action getAction() {
        return ReminderFragment.getAction(view);
    }
}
