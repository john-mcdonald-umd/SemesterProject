package edu.umd.cs.semesterproject.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.umd.cs.semesterproject.R;

public class RulesFragment extends Fragment {

    private static final String TITLE = "RULES";
    private static final String FRAGMENT_RULES = "fragment_rule";

    private RecyclerView mRuleRecyclerView;

    public RulesFragment() {}

    public static RulesFragment newInstance() {

        RulesFragment rulesFragment = new RulesFragment();

        return rulesFragment;
    }

    public static String getTitle() {
        return TITLE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_rule, container, false);

        mRuleRecyclerView = (RecyclerView) view.findViewById(R.id.ruleRecyclerView);
        mRuleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
