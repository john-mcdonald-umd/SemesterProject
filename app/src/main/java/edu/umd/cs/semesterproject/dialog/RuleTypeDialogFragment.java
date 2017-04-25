package edu.umd.cs.semesterproject.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import edu.umd.cs.semesterproject.R;

public class RuleTypeDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextView mTimeRuleTextView;
    private TextView mLocationRuleTextView;

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

        mTimeRuleTextView = (TextView) view.findViewById(R.id.text_view_time_rule);
        mTimeRuleTextView.setOnClickListener(this);
        mLocationRuleTextView = (TextView) view.findViewById(R.id.text_view_location_rule);
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

        switch (id) {
            case R.id.text_view_time_rule:
                break;
            case R.id.text_view_location_rule:
                break;
        }
    }
}
