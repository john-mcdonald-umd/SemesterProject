package edu.umd.cs.semesterproject.service.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.service.RuleService;

// An in memory implementation of RuleService, primarily used for testing.
public class InMemoryRuleService implements RuleService {

    private List<Rule> rules;

    public InMemoryRuleService() {
        this.rules = new ArrayList<>();
    }

    // Add a rule. If a rule already exists with the rule's id, then update the current rule.
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

    @Override
    public List<Rule> getAllRules() {
        return rules;
    }

    @Override
    public List<Rule> getAllRulesByActionType(Rule.ActionType actionType) {
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
        return getAllRulesByActionType(Rule.ActionType.VOLUME);
    }

    @Override
    public List<Rule> getBluetoothRules() {
        return getAllRulesByActionType(Rule.ActionType.BLUETOOTH);
    }

    @Override
    public List<Rule> getWifiRules() {
        return getAllRulesByActionType(Rule.ActionType.WIFI);
    }

    @Override
    public List<Rule> getReminderRules() {
        return null;
    }

    @Override
    public List<Rule> getLocationRules() {
        return getRulesByType(Rule.RuleType.LOCATION);
    }

    @Override
    public List<Rule> getTimeRules() {
        return getRulesByType(Rule.RuleType.TIME);
    }


    public void deleteRule(String id){
        for (int i = 0; i < rules.size(); i++){
            if (rules.get(i).getId().equals(id)){
                rules.remove(i);
                return;
            }
        }
        Log.d("InMemoryRuleService", "Tried to delete rule with id " + id + " but there was no rule with that id.");
    }

}
