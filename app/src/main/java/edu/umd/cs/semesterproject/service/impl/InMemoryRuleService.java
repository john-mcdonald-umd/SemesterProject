package edu.umd.cs.semesterproject.service.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.service.RuleService;

public class InMemoryRuleService implements RuleService {

    private Context context;
    private List<Rule> rules;

    public InMemoryRuleService(Context context) {
        this.context = context;
        this.rules = new ArrayList<Rule>();
    }

    public void addRule(Rule rule) {
        Rule currRule = getRuleById(rule.getId());
        if (currRule == null) {
            rules.add(rule);
        } else {
            currRule.setName(rule.getName());
            currRule.setEnabled(rule.isEnabled());
            currRule.setActionType(rule.getActionType());
            currRule.setRuleType(rule.getRuleType());
            currRule.setAction(rule.getAction());
        }
    }

    public Rule getRuleById(String id) {

        for (Rule rule : rules) {
            if (rule.getId().equals(id)) {
                return rule;
            }
        }

        return null;
    }

    private List<Rule> getRulesHelper(Rule.ActionType actionType){
        List<Rule> newRules = new ArrayList<Rule>();

        for (Rule rule : rules){
            if (rule.getRuleType().equals(actionType)){
                newRules.add(rule);
            }
        }

        return newRules;
    }

    public List<Rule> getVolumeRules() {
        return getRulesHelper(Rule.ActionType.VOLUME);
    }

    @Override
    public List<Rule> getBluetoothRules() {
        return getRulesHelper(Rule.ActionType.BLUETOOTH);
    }

    @Override
    public List<Rule> getWifiRules() {
        return getRulesHelper(Rule.ActionType.WIFI);
    }

    @Override
    public List<Rule> getHeadphoneRules() {
        return getRulesHelper(Rule.ActionType.HEADPHONE);
    }

    public void deleteRule(String id){
        // TODO: implement this later
    }

}
