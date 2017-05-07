package edu.umd.cs.semesterproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.util.Codes;

public class VolumeLocationFragment extends BaseLocationFragment {

    private final String TAG = "VolumeLocationFragment";

    public static Fragment newInstance(String userID){
        Bundle bundle = new Bundle();
        bundle.putString(Codes.RULE_ID, userID);
        VolumeLocationFragment ruleFragment = new VolumeLocationFragment();
        ruleFragment.setArguments(bundle);
        return ruleFragment;
    }

    @Override
    protected Rule.ActionType getActionType() {
        return Rule.ActionType.VOLUME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_volume_location;
    }

    @Override
    protected Action getAction() {
        return VolumeFragment.getAction(view);
    }

}