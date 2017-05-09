package edu.umd.cs.semesterproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.LocationRule;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.util.Codes;

public class ReminderLocationFragment extends BaseLocationFragment {

    private final String TAG = "ReminderLocationFragment";

    public static Fragment newInstance(String userID){
        Bundle bundle = new Bundle();
        bundle.putString(Codes.RULE_ID, userID);
        ReminderLocationFragment ruleFragment = new ReminderLocationFragment();
        ruleFragment.setArguments(bundle);
        return ruleFragment;
    }

    @Override
    protected Rule.ActionType getActionType() {
        return Rule.ActionType.REMINDER;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reminder_location;
    }

    @Override
    protected void setupSpecificLayout(View view, LocationRule rule) {
        ReminderFragment.setupSpecificLayout(view, rule);
    }

    @Override
    protected Action getAction() {
        return ReminderFragment.getAction(view);
    }
}
