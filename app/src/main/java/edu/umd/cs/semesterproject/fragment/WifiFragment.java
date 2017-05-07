package edu.umd.cs.semesterproject.fragment;

import android.view.View;
import android.widget.Switch;

import java.util.List;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.dialog.RuleTypeDialogFragment;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.BluetoothAction;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.WifiAction;

public class WifiFragment extends BaseFragment {

    private static final String TITLE = "Wifi";

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

    public static Action getAction(View view){
        Switch startSwitch = (Switch) view.findViewById(R.id.start_wifi_switch);
        Switch endSwitch = (Switch) view.findViewById(R.id.end_wifi_switch);

        return new WifiAction(startSwitch.isChecked(), endSwitch.isChecked());
    }
}
