package edu.umd.cs.semesterproject.fragment;

import android.view.View;

import java.util.List;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.dialog.RuleTypeDialogFragment;
import edu.umd.cs.semesterproject.model.Rule;

public class BluetoothFragment extends BaseFragment{

    private static final String TITLE = "Bluetooth";

    public static BluetoothFragment newInstance() {

        BluetoothFragment actionsFragment = new BluetoothFragment();

        return actionsFragment;
    }

    public static String getTitle() {
        return TITLE;
    }

    @Override
    protected List<Rule> getRules() {
        return ruleService.getBluetoothRules();
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
}
