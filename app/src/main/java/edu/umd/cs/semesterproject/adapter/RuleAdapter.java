package edu.umd.cs.semesterproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Rule;

public class RuleAdapter extends RecyclerView.Adapter<RuleAdapter.RuleHolder> {

    private List<Rule> mRules;

    public RuleAdapter(List<Rule> rules) {
        mRules = rules;
    }

    @Override
    public RuleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rule, parent, false);

        return new RuleHolder(view);
    }

    @Override
    public void onBindViewHolder(RuleHolder holder, int position) {
        holder.bindRule(mRules.get(position));
    }

    @Override
    public int getItemCount() {
        return mRules.size();
    }

    public void setRules(List<Rule> rules) {
        mRules = rules;
    }

    class RuleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mRuleName;
        private TextView mRuleConditions;
        private Switch mRuleEnabled;

        public RuleHolder(View itemView) {
            super(itemView);

            mRuleName = (TextView) itemView.findViewById(R.id.list_item_rule_name);
            mRuleConditions = (TextView) itemView.findViewById(R.id.list_item_rule_conditions);
            mRuleEnabled = (Switch) itemView.findViewById(R.id.list_item_rule_enabled);
        }

        public void bindRule(Rule rule) {
            mRuleName.setText(rule.getName());
            mRuleConditions.setText("Conditions...");
            mRuleEnabled.setChecked(rule.isEnabled());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
