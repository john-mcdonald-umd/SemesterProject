package edu.umd.cs.semesterproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        Button setTimeButton = (Button) view.findViewById(R.id.set_start_time_button);

        // Link UI elements


        return view;
    }


}
