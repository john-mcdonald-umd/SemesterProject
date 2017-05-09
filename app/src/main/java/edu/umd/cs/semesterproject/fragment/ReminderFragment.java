package edu.umd.cs.semesterproject.fragment;

import android.view.View;
import android.widget.Switch;

import java.util.List;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.dialog.RuleTypeDialogFragment;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.BluetoothAction;
import edu.umd.cs.semesterproject.model.Rule;

// Fragment for Reminder Tab of the TabLayout
public class ReminderFragment extends BaseFragment{

    private static final String TITLE = "Reminder";

    public static ReminderFragment newInstance() {

        ReminderFragment actionsFragment = new ReminderFragment();

        return actionsFragment;
    }

    public static String getTitle() {
        return TITLE;
    }

    @Override
    protected List<Rule> getRules() {
        return ruleService.getReminderRules();
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

    // Checks the start and end switches for the Bluetooth rule to see what they should be switched to when it starts and ends.
    public static Action getAction(View view){
        Switch startSwitch = (Switch) view.findViewById(R.id.start_bluetooth_switch);
        Switch endSwitch = (Switch) view.findViewById(R.id.end_bluetooth_switch);

        return new BluetoothAction(startSwitch.isChecked(), endSwitch.isChecked());
    }

    public static void setupSpecificLayout(View view, Rule rule){
        Switch startSwitch = (Switch) view.findViewById(R.id.start_bluetooth_switch);
        Switch endSwitch = (Switch) view.findViewById(R.id.end_bluetooth_switch);

        BluetoothAction bluetoothAction = (BluetoothAction) rule.getAction();
        startSwitch.setChecked(bluetoothAction.getStartAction());
        endSwitch.setChecked(bluetoothAction.getEndAction());
    }
}
