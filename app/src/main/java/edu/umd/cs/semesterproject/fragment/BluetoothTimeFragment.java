package edu.umd.cs.semesterproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.util.Codes;

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
    protected Action getAction() {
        return BluetoothFragment.getAction(view);
    }
}
