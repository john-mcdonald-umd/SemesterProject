package edu.umd.cs.semesterproject.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Switch;

import java.util.List;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.dialog.RuleTypeDialogFragment;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.BluetoothAction;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.WifiAction;

// The fragment used for the Wifi tab in the TabLayout
public class WifiFragment extends BaseFragment {

    private static final String TITLE = "Wifi";
    private static final String TAG = WifiFragment.class.getSimpleName();

    public static WifiFragment newInstance() {

        WifiFragment actionsFragment = new WifiFragment();

        return actionsFragment;
    }

    public static String getTitle() {
        return TITLE;
    }

    @Override
    protected List<Rule> getRules() {
        return ruleService.getWifiRules();
    }


    // For clicking the (+) button. Will open up a dialogue box to choose what type of rule to open.
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fab:
                RuleTypeDialogFragment.newInstance().show(getActivity().getSupportFragmentManager(), getTitle());
                break;
        }
    }

    // Checks the start and end switches for the Wifi rule to see what they should be switched to when it starts and ends.
    public static Action getAction(View view){
        Switch startSwitch = (Switch) view.findViewById(R.id.start_wifi_switch);
        Switch endSwitch = (Switch) view.findViewById(R.id.end_wifi_switch);

        Log.d(TAG, "startSwitch: " + startSwitch.isChecked() + ", endSwitch: " + endSwitch.isChecked());
        return new WifiAction(startSwitch.isChecked(), endSwitch.isChecked());
    }

    public static void setupSpecificLayout(View view, Rule rule){
        Switch startSwitch = (Switch) view.findViewById(R.id.start_wifi_switch);
        Switch endSwitch = (Switch) view.findViewById(R.id.end_wifi_switch);

        WifiAction wifiAction = (WifiAction) rule.getAction();
        startSwitch.setChecked(wifiAction.getStartAction());
        endSwitch.setChecked(wifiAction.getEndAction());
    }
}
