package edu.umd.cs.semesterproject.fragment;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import edu.umd.cs.semesterproject.DependencyFactory;
import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.TimeRule;
import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.util.Codes;

public class BluetoothTimeFragment extends Fragment {

    private final String TAG = "BluetoothTimeFragment";

    private Rule rule;
    private TimeRule timeRule;
    /* Some parameters used for setting the start and end times */
    private boolean startTimeSet = false;
    private boolean endTimeSet = false;

    EditText ruleName;

    public static Fragment newInstance(String userID){
        Bundle bundle = new Bundle();
        bundle.putString(Codes.RULE_ID, userID);
        BluetoothTimeFragment ruleFragment = new BluetoothTimeFragment();
        ruleFragment.setArguments(bundle);
        return ruleFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Call to super
        super.onCreate(savedInstanceState);

        // Get Arguments
        Bundle args = getArguments();
        String ruleID = args.getString(Codes.RULE_ID);
        RuleService ruleService = DependencyFactory.getRuleService(getActivity());
        rule = ruleService.getRuleById(ruleID);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Call to super
        super.onCreateView(inflater, container, savedInstanceState);

        // Set content view
        View view = inflater.inflate(R.layout.fragment_bluetooth_time, container, false);
        ruleName = (EditText) view.findViewById(R.id.rule_name);
        Button startTimeButton = (Button) view.findViewById(R.id.set_start_time_button);
        Button endTimeButton = (Button) view.findViewById(R.id.set_end_time_button);
        Button saveButton = (Button) view.findViewById(R.id.save_button);
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);

        if (rule == null) {
            // Set up time rule
            timeRule = new TimeRule(ruleName.getText().toString(), true, 0, 0, 0, 0, null);
        }
        else{
            startTimeSet = (endTimeSet = true);
            ruleName.setText(rule.getName());
            timeRule = (TimeRule) rule;
        }

        // Link UI elements
        startTimeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                try{
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            timeRule.setStartTime(selectedHour, selectedMinute);
                        }
                    }, hour, minute, false);
                    mTimePicker.setTitle("Select Start Time");
                    mTimePicker.show();

                    startTimeSet = true;
                } catch (Exception e){
                    Log.e(TAG, e.toString());
                }
            }
        });
        endTimeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                try{
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            timeRule.setEndTime(selectedHour, selectedMinute);
                        }
                    }, hour, minute, false);
                    mTimePicker.setTitle("Select End Time");
                    mTimePicker.show();

                    endTimeSet = true;
                } catch (Exception e){
                    Log.e(TAG, e.toString());
                }
            }
        });
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                try{
                    /* If they've set both times */
                    if (startTimeSet && endTimeSet) {
                        Intent intent = new Intent();
                        timeRule.setActionType(Rule.ActionType.BLUETOOTH);
                        timeRule.setName(ruleName.getText().toString());
                        intent.putExtra(Codes.RULE_CREATED, timeRule);
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                    }
                    else{
                        Toast toast = Toast.makeText(getActivity(), "Please set both the start and end times before saving.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (Exception e){
                    Log.e(TAG, e.toString());
                }
            }
        });
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                try{
                    Intent returnIntent = new Intent();
                    getActivity().setResult(Activity.RESULT_CANCELED, returnIntent);
                    getActivity().finish();
                } catch (Exception e){
                    Log.e(TAG, e.toString());
                }
            }
        });

        return view;
    }

}
