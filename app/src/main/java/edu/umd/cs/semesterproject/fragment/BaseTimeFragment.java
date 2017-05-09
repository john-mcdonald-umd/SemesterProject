package edu.umd.cs.semesterproject.fragment;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.umd.cs.semesterproject.DependencyFactory;
import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.Time;
import edu.umd.cs.semesterproject.model.TimeRule;
import edu.umd.cs.semesterproject.model.VolumeAction;
import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.util.Codes;

// Base fragment for creating Time Rules.
public abstract class BaseTimeFragment extends Fragment implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private final String TAG = BaseTimeFragment.class.getSimpleName();

    protected TimeRule mTimeRule;
    protected RuleService mRuleService;

    protected Time mStartTime;
    protected Time mEndTime;

    protected TextView mIsEnabledTextView;
    protected Switch mIsEnabledSwitch;
    protected TextInputEditText mNameEditText;
    protected EditText mStartTimeEditText;
    protected EditText mEndTimeEditText;
    protected ToggleButton mMon, mTues, mWed, mThurs, mFri, mSat, mSun;
    protected Button mSaveButton;
    protected Button mCancelButton;

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

//        mIsEnabledTextView = (TextView) view.findViewById(R.id.text_view_enabled);
//        mIsEnabledTextView.setText("Off");
//        mIsEnabledSwitch = (Switch) view.findViewById(R.id.switch_enabled);
//        mIsEnabledSwitch.setOnCheckedChangeListener(this);
        mNameEditText = (TextInputEditText) view.findViewById(R.id.edit_text_name);
        mStartTimeEditText = (EditText) view.findViewById(R.id.edit_text_start_time);
        mStartTimeEditText.setOnClickListener(this);
        mEndTimeEditText = (EditText) view.findViewById(R.id.edit_text_end_time);
        mEndTimeEditText.setOnClickListener(this);
        mMon = (ToggleButton) view.findViewById(R.id.toggle_button_mon);
        mTues = (ToggleButton) view.findViewById(R.id.toggle_button_tues);
        mWed = (ToggleButton) view.findViewById(R.id.toggle_button_wed);
        mThurs = (ToggleButton) view.findViewById(R.id.toggle_button_thurs);
        mFri = (ToggleButton) view.findViewById(R.id.toggle_button_fri);
        mSat = (ToggleButton) view.findViewById(R.id.toggle_button_sat);
        mSun = (ToggleButton) view.findViewById(R.id.toggle_button_sun);
        mSaveButton = (Button) view.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(this);
        mSaveButton.setEnabled(false);
        mCancelButton = (Button) view.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(this);

        if (mTimeRule != null) {

            VolumeAction volumeAction = (VolumeAction) mTimeRule.getAction();

            mIsEnabledTextView.setText(mTimeRule.isEnabled() ? "On" : "Off");
            mIsEnabledSwitch.setChecked(mTimeRule.isEnabled());

            mNameEditText.setText(mTimeRule.getName());

            mStartTime = mTimeRule.getStartTime();
            mStartTimeEditText.setText(mTimeRule.getStartTime().toString());
            mEndTime = mTimeRule.getEndTime();
            mEndTimeEditText.setText(mTimeRule.getStartTime().toString());
            mMon.setChecked(mTimeRule.getDays().contains(TimeRule.Day.MON));
            mTues.setChecked(mTimeRule.getDays().contains(TimeRule.Day.TUES));
            mWed.setChecked(mTimeRule.getDays().contains(TimeRule.Day.WED));
            mThurs.setChecked(mTimeRule.getDays().contains(TimeRule.Day.THURS));
            mFri.setChecked(mTimeRule.getDays().contains(TimeRule.Day.FRI));
            mSat.setChecked(mTimeRule.getDays().contains(TimeRule.Day.SAT));
            mSun.setChecked(mTimeRule.getDays().contains(TimeRule.Day.SUN));

            mSaveButton.setEnabled(true);
        }

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
        mStartTimeEditText.setOnClickListener(new Button.OnClickListener() {
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
        mEndTimeEditText.setOnClickListener(new Button.OnClickListener() {
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
        mSaveButton.setOnClickListener(new Button.OnClickListener() {
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
        mCancelButton.setOnClickListener(new Button.OnClickListener() {
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
            al.add(TimeRule.Day.TUES);
        }
        if (W.isChecked()){
            al.add(TimeRule.Day.WED);
        }
        if (Th.isChecked()){
            al.add(TimeRule.Day.THURS);
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
            if (day.equals(TimeRule.Day.TUES))
                T.setChecked(true);
            if (day.equals(TimeRule.Day.WED))
                W.setChecked(true);
            if (day.equals(TimeRule.Day.THURS))
                Th.setChecked(true);
            if (day.equals(TimeRule.Day.FRI))
                F.setChecked(true);
            if (day.equals(TimeRule.Day.SAT))
                Sa.setChecked(true);
        }
    }
}
