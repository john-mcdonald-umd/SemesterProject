package edu.umd.cs.semesterproject.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import edu.umd.cs.semesterproject.BluetoothLocationActivity;
import edu.umd.cs.semesterproject.BluetoothTimeActivity;
import edu.umd.cs.semesterproject.DependencyFactory;
import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.VolumeLocationActivity;
import edu.umd.cs.semesterproject.VolumeTimeActivity;
import edu.umd.cs.semesterproject.WifiLocationActivity;
import edu.umd.cs.semesterproject.WifiTimeActivity;
import edu.umd.cs.semesterproject.fragment.BluetoothFragment;
import edu.umd.cs.semesterproject.fragment.VolumeFragment;
import edu.umd.cs.semesterproject.fragment.WifiFragment;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.util.Codes;

public class RuleTypeDialogFragment extends DialogFragment implements View.OnClickListener {

    private Button mTimeRuleTextView;
    private Button mLocationRuleTextView;

    // Sources for deciding where the dialog came from
    private String source;

    private static final int PLACE_PICKER_REQUEST = 199;
    public RuleTypeDialogFragment() {
    }

    public static RuleTypeDialogFragment newInstance() {
        RuleTypeDialogFragment ruleTypeDialogFragment = new RuleTypeDialogFragment();

        return ruleTypeDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_rule_type, null, false);

        mTimeRuleTextView = (Button) view.findViewById(R.id.text_view_time_rule);
        mTimeRuleTextView.setOnClickListener(this);
        mLocationRuleTextView = (Button) view.findViewById(R.id.text_view_location_rule);
        mLocationRuleTextView.setOnClickListener(this);

        builder.setView(view)
                .setTitle("Rule Type")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        return dialog;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        Log.d("RuleTypeDialogFragment", "Tag = " + this.getTag());
        switch (id) {
            // if creating a time rule.
            case R.id.text_view_time_rule:
                // get the correct intent based on the RuleA.ActionType (simply checks the Title of the tab it came from.)
                if (this.getTag().equals(VolumeFragment.getTitle()))
                    intent = VolumeTimeActivity.newIntent(getActivity(), null);
                if (this.getTag().equals(BluetoothFragment.getTitle()))
                    intent = BluetoothTimeActivity.newIntent(getActivity(), null);
                if (this.getTag().equals(WifiFragment.getTitle()))
                    intent = WifiTimeActivity.newIntent(getActivity(), null);
                startActivityForResult(intent, Codes.REQUEST_CODE_CREATE_RULE);
                break;
            // if creating a location rule
            case R.id.text_view_location_rule:
                if (this.getTag().equals(VolumeFragment.getTitle()))
                    intent = VolumeLocationActivity.newIntent(getActivity(), null);
                if (this.getTag().equals(BluetoothFragment.getTitle()))
                    intent = BluetoothLocationActivity.newIntent(getActivity(), null);
                if (this.getTag().equals(WifiFragment.getTitle()))
                    intent = WifiLocationActivity.newIntent(getActivity(), null);
                startActivityForResult(intent, Codes.REQUEST_CODE_CREATE_RULE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Rule rule;
        RuleService ruleService = DependencyFactory.getRuleService(getActivity());

        if (requestCode == Codes.REQUEST_CODE_CREATE_RULE && resultCode == Activity.RESULT_OK) {
            rule = Codes.getRuleCreated(data);
            ruleService.addRule(rule);
        }

        else{
            Log.d("RuleTypeDialogFragment", "onActivityResult() called but no rule created");
        }

        dismiss();
    }
}
