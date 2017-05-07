package edu.umd.cs.semesterproject.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import edu.umd.cs.semesterproject.DependencyFactory;
import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.VolumeLocationActivity;
import edu.umd.cs.semesterproject.VolumeTimeActivity;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.util.Codes;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private static final String TITLE = "BASE";

    // Recycler view for the list of rules.
    private RecyclerView mRuleRecyclerView;
    // Floating action button is the (+) button for adding a rule.
    private FloatingActionButton mFloatingActionButton;
    // Services and Adapter for using the recycler view
    protected RuleService ruleService;
    private RuleAdapter ruleAdapter;

    public BaseFragment() {}

    public static String getTitle() {
        return TITLE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    protected abstract List<Rule> getRules();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_rule, container, false);

        mRuleRecyclerView = (RecyclerView) view.findViewById(R.id.ruleRecyclerView);
        mRuleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);

        ruleService = DependencyFactory.getRuleService(getActivity());

        ruleAdapter = new RuleAdapter(this.getRules());
        mRuleRecyclerView.setAdapter(ruleAdapter);

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Codes.REQUEST_CODE_CREATE_RULE) {
            if (resultCode == Activity.RESULT_OK) {
                Rule rule = Codes.getRuleCreated(data);
                ruleService.addRule(rule);

                updateUI();
            }
        }
    }

    private class RuleHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

        private Rule rule;
        private TextView name, conditions;
        private Switch mSwitch;

        public RuleHolder(View v){
            super(v);
            v.setOnClickListener(this);
            name = (TextView) v.findViewById(R.id.list_item_rule_name);
            conditions = (TextView) v.findViewById(R.id.list_item_rule_conditions);
            mSwitch = (Switch) v.findViewById(R.id.list_item_rule_enabled);
        }

        public void bindRule(Rule r) {
            rule = r;
            name.setText(r.getName());
            conditions.setText(r.getConditions());
            if (rule.isEnabled()){
                mSwitch.setChecked(true);
            }
            else{
                mSwitch.setChecked(false);
            }
            mSwitch.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get intent for the correct acivity based on the type of rule, then start that activity for result
            Intent intent = null;
            Rule.RuleType type = rule.getRuleType();
            if (type.equals(Rule.RuleType.TIME)){
                intent = VolumeTimeActivity.newIntent(getContext(), rule.getId());
                startActivityForResult(intent, Codes.REQUEST_CODE_CREATE_RULE);
            }
            else if (type.equals(Rule.RuleType.LOCATION)){
                intent = VolumeLocationActivity.newIntent(getContext(), rule.getId());
                startActivityForResult(intent, Codes.REQUEST_CODE_CREATE_RULE);
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                rule.setEnabled(true);
            } else {
                rule.setEnabled(false);
            }
        }
    }

    protected class RuleAdapter extends RecyclerView.Adapter<RuleHolder>{

        private List<Rule> rule_list;

        public RuleAdapter(List<Rule> list){
            rule_list = list;
        }

        public void setRules(List<Rule> list){
            rule_list = list;
        }

        @Override
        public RuleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
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

    protected void updateUI(){
        List<Rule> list = this.getRules();

        Log.d("BaseFragment", "UpdateUI(): getRules(): " + list.toString());

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
