package edu.umd.cs.semesterproject.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.umd.cs.semesterproject.R;

public class HeadphoneFragment extends Fragment {

    private static final String TITLE = "Headphones";

    private RecyclerView mRuleRecyclerView;

    public HeadphoneFragment() {}

    public static HeadphoneFragment newInstance() {

        HeadphoneFragment headphoneFragment = new HeadphoneFragment();

        return headphoneFragment;
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
