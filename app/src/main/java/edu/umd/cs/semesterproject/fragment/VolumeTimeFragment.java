package edu.umd.cs.semesterproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.TimeRule;
import edu.umd.cs.semesterproject.model.VolumeAction;
import edu.umd.cs.semesterproject.util.Codes;

// The fragment used to create Volume Time rules.
public class VolumeTimeFragment extends BaseTimeFragment {

    private final String TAG = "VolumeTimeFragment";

    public static Fragment newInstance(String userID){
        Bundle bundle = new Bundle();
        bundle.putString(Codes.RULE_ID, userID);
        VolumeTimeFragment ruleFragment = new VolumeTimeFragment();
        ruleFragment.setArguments(bundle);
        return ruleFragment;
    }

    @Override
    protected Rule.ActionType getActionType() {
        return Rule.ActionType.VOLUME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_volume_time;
    }

    @Override
    protected void setupSpecificLayout(View view, TimeRule rule) {
        VolumeFragment.setupSpecificLayout(view, rule);
    }

    @Override
    protected Action getAction() {
        return VolumeFragment.getAction(view);
    }
}
