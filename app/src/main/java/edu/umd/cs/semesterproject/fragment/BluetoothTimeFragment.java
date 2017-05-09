package edu.umd.cs.semesterproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.TimeRule;
import edu.umd.cs.semesterproject.util.Codes;

// The fragment that is used for creating Bluetooth Time Rules.
public class BluetoothTimeFragment extends BaseTimeFragment {

    private final String TAG = "BluetoothTimeFragment";

    public static Fragment newInstance(String userID) {
        Bundle bundle = new Bundle();
        bundle.putString(Codes.RULE_ID, userID);
        BluetoothTimeFragment ruleFragment = new BluetoothTimeFragment();
        ruleFragment.setArguments(bundle);
        return ruleFragment;
    }

    @Override
    protected Rule.ActionType getActionType() {
        return Rule.ActionType.BLUETOOTH;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bluetooth_time;
    }

    @Override
    protected void setupSpecificLayout(View view, TimeRule rule) {
        BluetoothFragment.setupSpecificLayout(view, rule);
    }

    @Override
    protected Action getAction() {
        return BluetoothFragment.getAction(view);
    }
}
