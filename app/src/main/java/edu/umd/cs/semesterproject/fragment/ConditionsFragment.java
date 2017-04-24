package edu.umd.cs.semesterproject.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.umd.cs.semesterproject.R;

public class ConditionsFragment extends Fragment {

    private static final String TITLE = "CONDITIONS";
    private static final String FRAGMENT_CONDITIONS = "fragment_conditions";

    public ConditionsFragment() {}

    public static ConditionsFragment newInstance() {

        ConditionsFragment conditionsFragment = new ConditionsFragment();

        return conditionsFragment;
    }

    public static String getTitle() {
        return TITLE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conditions, container, false);
    }
}
