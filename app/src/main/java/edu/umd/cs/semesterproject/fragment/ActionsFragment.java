package edu.umd.cs.semesterproject.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.umd.cs.semesterproject.R;

public class ActionsFragment extends Fragment {

    private static final String TITLE = "ACTIONS";
    private static final String FRAGMENT_ACTIONS = "fragment_actions";

    public ActionsFragment() {}

    public static ActionsFragment newInstance() {

        ActionsFragment actionsFragment = new ActionsFragment();

        return actionsFragment;
    }

    public static String getTitle() {
        return TITLE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_actions, container, false);
    }
}
