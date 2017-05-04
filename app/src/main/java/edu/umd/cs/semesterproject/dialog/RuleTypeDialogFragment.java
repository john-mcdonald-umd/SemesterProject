package edu.umd.cs.semesterproject.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.Serializable;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.VolumeTimeActivity;
import edu.umd.cs.semesterproject.fragment.VolumeTimeFragment;
import edu.umd.cs.semesterproject.model.TimeRule2;

public class RuleTypeDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextView mTimeRuleTextView;
    private TextView mLocationRuleTextView;

    public static final int REQUEST_CODE_CREATE_RULE = 0;

    private static final int PLACE_PICKER_REQUEST = 1;
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
                Intent intent = new Intent(getActivity(), VolumeTimeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE_RULE);
            case R.id.text_view_location_rule:

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

                break;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_CREATE_RULE && resultCode == Activity.RESULT_OK) {
            Serializable result = data.getSerializableExtra(VolumeTimeFragment.RULE_CREATED);
            TimeRule2 timeRule = (TimeRule2) result;
            /* TODO */
            /* Add timeRule to database. Maybe find a way to pipe this rule back to the fragment using parameters */

        }

        else if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
            }
        }

        dismiss();
    }
}
