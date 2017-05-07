package edu.umd.cs.semesterproject.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.umd.cs.semesterproject.BluetoothLocationActivity;
import edu.umd.cs.semesterproject.BluetoothTimeActivity;
import edu.umd.cs.semesterproject.DependencyFactory;
import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.VolumeTimeActivity;
import edu.umd.cs.semesterproject.dialog.RuleTypeDialogFragment;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.util.Codes;

public class BluetoothFragment extends Fragment implements View.OnClickListener {

    private static final String TITLE = "Bluetooth";

    // Recycler view for the list of rules.
    private RecyclerView mRuleRecyclerView;
    // Floating action button is the (+) button for adding a rule.
    private FloatingActionButton mFloatingActionButton;
    // Services and Adapter for using the recycler view
    private RuleService ruleService;
    private RuleAdapter ruleAdapter;

    public BluetoothFragment() {}

    public static BluetoothFragment newInstance() {

        BluetoothFragment bluetoothFragment = new BluetoothFragment();

        return bluetoothFragment;
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

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);

        ruleService = DependencyFactory.getRuleService(getActivity());

        ruleAdapter = new RuleAdapter(ruleService.getBluetoothRules());
        mRuleRecyclerView.setAdapter(ruleAdapter);

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        updateUI();
    }

    // For clicking the (+) button. Will open up a dialogue box to choose what type of rule to open.
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fab:
                RuleTypeDialogFragment.newInstance().show(getActivity().getSupportFragmentManager(), TITLE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Codes.REQUEST_CODE_CREATE_RULE) {
            if(resultCode == Activity.RESULT_OK){
                Rule rule = Codes.getRuleCreated(data);
                ruleService.addRule(rule);
            }
        }
        updateUI();
    }

    private class RuleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Rule rule;
        private TextView name, conditions;

        public RuleHolder(View v){
            super(v);
            v.setOnClickListener(this);
            name = (TextView) v.findViewById(R.id.list_item_rule_name);
            conditions = (TextView) v.findViewById(R.id.list_item_rule_conditions);
        }

        public void bindRule(Rule r) {
            rule = r;
            name.setText(r.getName());
            conditions.setText(r.getConditions());
        }

        @Override
        public void onClick(View v) {
            // Get intent for the correct acivity based on the type of rule, then start that activity for result
            Intent intent = null;
            String type = rule.getType();
            if (type.equals(Rule.TYPE_TIME)){
                intent = BluetoothTimeActivity.newIntent(getContext(), rule.getId());
                startActivityForResult(intent, Codes.REQUEST_CODE_CREATE_RULE);
            }
            if (type.equals(Rule.TYPE_LOCATION)){
                intent = BluetoothLocationActivity.newIntent(getContext(), rule.getId());
                startActivityForResult(intent, Codes.REQUEST_CODE_CREATE_RULE);
            }
        }
    }

    private class RuleAdapter extends RecyclerView.Adapter<RuleHolder>{

        private List<Rule> rule_list;

        public RuleAdapter(List<Rule> list){
            rule_list = list;
        }

        public void setRules(List<Rule> list){
            rule_list = list;
        }

        @Override
        public RuleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new RuleHolder(layoutInflater.inflate(R.layout.list_item_rule, parent, false));
        }

        @Override
        public void onBindViewHolder(RuleHolder holder, int position) {
            holder.bindRule(rule_list.get(position));
        }

        @Override
        public int getItemCount() {
            return rule_list.size();
        }
    }

    private void updateUI(){
        List<Rule> list = ruleService.getBluetoothRules();
        if (list.size() > 0) {
            Log.d("BluetoothFragment", "UpdateUI(): The first element of getBluetoothRules() is " + list.get(0).getName());
        }
        else{
            Log.d("BluetoothFragment", "UpdateUI(): getBluetoothRules() returned an empty list");
        }
        if (ruleAdapter == null){
            ruleAdapter = new RuleAdapter(list);
            mRuleRecyclerView.setAdapter(ruleAdapter);
        }
        else{
            ruleAdapter.setRules(list);
            ruleAdapter.notifyDataSetChanged();
        }
    }
}
