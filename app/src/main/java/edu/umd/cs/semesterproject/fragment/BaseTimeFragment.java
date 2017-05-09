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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.umd.cs.semesterproject.DependencyFactory;
import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.TimeRule;
import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.util.Codes;

// Base fragment for creating Time Rules.
public abstract class BaseTimeFragment extends Fragment {

    private final String TAG = BaseTimeFragment.class.getSimpleName();

    private Rule rule;
    private TimeRule timeRule;
    /* Some parameters used for setting the start and end times */
    private boolean startTimeSet = false;
    private boolean endTimeSet = false;

    EditText ruleName;
    CheckBox S, M, T, W, Th, F, Sa;
    protected View view;

    // Gets the action type of the Location Rule. Volume Rules would return Rule.ActionType.VOLUME
    protected abstract Rule.ActionType getActionType();
    // gets the layout id. VolumeLocationRules and WifiLocationRules have different layouts, for example.
    protected abstract int getLayoutId();
    // set up specific layout for the ActionType
    protected abstract void setupSpecificLayout(View view, TimeRule rule);
    // gets the action of the Rule
    protected abstract Action getAction();

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
        view = inflater.inflate(getLayoutId(), container, false);
        ruleName = (EditText) view.findViewById(R.id.rule_name);
        Button startTimeButton = (Button) view.findViewById(R.id.set_start_time_button);
        Button endTimeButton = (Button) view.findViewById(R.id.set_end_time_button);
        Button saveButton = (Button) view.findViewById(R.id.save_button);
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        // Checkboxes, eventually put these in an array.
        S = (CheckBox) view.findViewById(R.id.checkbox_sun);
        M = (CheckBox) view.findViewById(R.id.checkbox_mon);
        T = (CheckBox) view.findViewById(R.id.checkbox_tues);
        W = (CheckBox) view.findViewById(R.id.checkbox_wed);
        Th = (CheckBox) view.findViewById(R.id.checkbox_thurs);
        F = (CheckBox) view.findViewById(R.id.checkbox_fri);
        Sa = (CheckBox) view.findViewById(R.id.checkbox_sat);

        // if creating a new rule.
        if (rule == null) {
            // Set up time rule
            timeRule = new TimeRule(ruleName.getText().toString(), true, 0, 0, 0, 0, null);
        }
        // if editing a pre-existing rule.
        else{
            startTimeSet = (endTimeSet = true);
            ruleName.setText(rule.getName());
            timeRule = (TimeRule) rule;
            setCheckBoxes(timeRule);
            setupSpecificLayout(view, timeRule);
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
                        Log.d(TAG, "saveButtonStarted");
                        Intent intent = new Intent();
                        timeRule.setActionType(getActionType());
                        Log.d(TAG, "got action type");
                        timeRule.setAction(getAction());
                        Log.d(TAG, "got action");
                        timeRule.setDays(parseCheckBoxes());
                        timeRule.setName(ruleName.getText().toString());
                        intent.putExtra(Codes.RULE_CREATED, timeRule);
                        Log.d(TAG, "about to set result");
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        Log.d(TAG, "about to finish");
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

    // returns a List<TimeRule.Day> based on the state of the checkboxes.
    private List<TimeRule.Day> parseCheckBoxes(){
        ArrayList<TimeRule.Day> al = new ArrayList<>();
        if (S.isChecked()){
            al.add(TimeRule.Day.SUN);
        }
        if (M.isChecked()){
            al.add(TimeRule.Day.MON);
        }
        if (T.isChecked()){
            al.add(TimeRule.Day.TUE);
        }
        if (W.isChecked()){
            al.add(TimeRule.Day.WED);
        }
        if (Th.isChecked()){
            al.add(TimeRule.Day.THUR);
        }
        if (F.isChecked()){
            al.add(TimeRule.Day.FRI);
        }
        if (Sa.isChecked()){
            al.add(TimeRule.Day.SAT);
        }

        return al;
    }

    private void setCheckBoxes(TimeRule r){
        for (TimeRule.Day day : r.getDays()){
            if (day.equals(TimeRule.Day.SUN))
                S.setChecked(true);
            if (day.equals(TimeRule.Day.MON))
                M.setChecked(true);
            if (day.equals(TimeRule.Day.TUE))
                T.setChecked(true);
            if (day.equals(TimeRule.Day.WED))
                W.setChecked(true);
            if (day.equals(TimeRule.Day.THUR))
                Th.setChecked(true);
            if (day.equals(TimeRule.Day.FRI))
                F.setChecked(true);
            if (day.equals(TimeRule.Day.SAT))
                Sa.setChecked(true);
        }
    }
}
