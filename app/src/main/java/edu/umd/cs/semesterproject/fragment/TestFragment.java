package edu.umd.cs.semesterproject.fragment;

/**
 * Created by James on 4/25/2017.
 */

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

import edu.umd.cs.semesterproject.R;

/**
 * Test fragment for testing a fragment in the tabs without having to go through dialogue boxes to get through
 * Mainly for testing UI and basic functionality.
 */

public class TestFragment extends Fragment {

    private static final String TITLE = "TEST";

    public TestFragment() {
    }

    public static TestFragment newInstance() {

        TestFragment testFragment = new TestFragment();

        return testFragment;
    }

    public static String getTitle() {
        return TITLE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_volume_time, container, false);
        Button setTimeButton = (Button) view.findViewById(R.id.set_time_button);

        // Link UI elements
        setTimeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                try{
                    showTimePickerDialog(v);
                } catch (Exception e){

                }
            }
        });


        return view;
    }



    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new VolumeTimeFragment.TimePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }
}
