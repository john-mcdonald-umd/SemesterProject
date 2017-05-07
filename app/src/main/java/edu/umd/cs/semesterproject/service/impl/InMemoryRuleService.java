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

    private List<Rule> getRulesByAction(Rule.ActionType actionType){
        List<Rule> newRules = new ArrayList<Rule>();

        for (Rule rule : rules){
            if (rule.getActionType().equals(actionType)){
                newRules.add(rule);
            }
        }

        return newRules;
    }

    private List<Rule> getRulesByType(Rule.RuleType ruleType){
        List<Rule> newRules = new ArrayList<Rule>();

        for (Rule rule : rules){
            if (rule.getRuleType().equals(ruleType)){
                newRules.add(rule);
            }
        }

        return newRules;
    }

    public List<Rule> getVolumeRules() {
        return getRulesByAction(Rule.ActionType.VOLUME);
    }

    @Override
    public List<Rule> getBluetoothRules() {
        return getRulesByAction(Rule.ActionType.BLUETOOTH);
    }

    @Override
    public List<Rule> getWifiRules() {
        return getRulesByAction(Rule.ActionType.WIFI);
    }

    @Override
    public List<Rule> getLocationRules() {
        return getRulesByType(Rule.RuleType.LOCATION);
    }

    @Override
    public List<Rule> getTimeRules() {
        return getRulesByType(Rule.RuleType.TIME);
    }

    @Override
    public List<Rule> getHeadphoneRules() {
        return getRulesByAction(Rule.ActionType.HEADPHONE);
    }

    public void deleteRule(String id){
        // TODO: implement this later
    }

}
