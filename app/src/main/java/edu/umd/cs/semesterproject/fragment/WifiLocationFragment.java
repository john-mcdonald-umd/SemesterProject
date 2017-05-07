package edu.umd.cs.semesterproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.util.Codes;

public class WifiLocationFragment extends BaseLocationFragment {

    private final String TAG = "WifiTimeFragment";

    public static Fragment newInstance(String userID){
        Bundle bundle = new Bundle();
        bundle.putString(Codes.RULE_ID, userID);
        WifiLocationFragment ruleFragment = new WifiLocationFragment();
        ruleFragment.setArguments(bundle);
        return ruleFragment;
    }

    @Override
    protected Rule.ActionType getActionType() {
        return Rule.ActionType.WIFI;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wifi_location;
    }

    @Override
    protected Action getAction() {
        return null;
    }

}
