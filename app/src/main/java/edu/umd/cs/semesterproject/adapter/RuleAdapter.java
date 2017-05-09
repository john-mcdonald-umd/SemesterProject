package edu.umd.cs.semesterproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.VolumeLocationActivity;
import edu.umd.cs.semesterproject.VolumeTimeActivity;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.util.Codes;

public class RuleAdapter extends RecyclerView.Adapter<RuleAdapter.RuleHolder> {

    private Context mContext;
    private List<Rule> mRules;

    public RuleAdapter(Context context, List<Rule> rules) {
        mContext = context;
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

    class RuleHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        private Rule mRule;
        private TextView mRuleName;
        private TextView mRuleConditions;
        private Switch mRuleEnabled;

        public RuleHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mRuleName = (TextView) itemView.findViewById(R.id.list_item_rule_name);
            mRuleConditions = (TextView) itemView.findViewById(R.id.list_item_rule_conditions);
            mRuleEnabled = (Switch) itemView.findViewById(R.id.list_item_rule_enabled);
        }

        public void bindRule(Rule rule) {
            mRule = rule;
            mRuleName.setText(rule.getName());
            mRuleConditions.setText(rule.getConditions());
            mRuleEnabled.setChecked(rule.isEnabled());
        }

        /**
         * Get intent for the correct activity based on the type of rule, then start that activity for result.
         */
        @Override
        public void onClick(View v) {
            Rule rule = mRules.get(getAdapterPosition());

            Intent intent;
            Rule.RuleType type = rule.getRuleType();
            switch (Rule.RuleType.TIME) {
                case TIME:
                    intent = VolumeTimeActivity.newIntent(mContext, rule.getId());
                    ((Activity) mContext).startActivityForResult(intent, Codes.REQUEST_CODE_CREATE_RULE);
                    break;
                case LOCATION:
                    intent = VolumeLocationActivity.newIntent(mContext, rule.getId());
                    ((Activity) mContext).startActivityForResult(intent, Codes.REQUEST_CODE_CREATE_RULE);
                    break;
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mRule.setEnabled(isChecked);
        }
    }
}
