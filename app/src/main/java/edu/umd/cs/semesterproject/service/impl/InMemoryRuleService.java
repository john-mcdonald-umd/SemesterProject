package edu.umd.cs.semesterproject.service.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.service.RuleService;

/**
 * Created by James on 5/4/2017.
 */

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
            currRule.setType(rule.getType());
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

    private List<Rule> getRulesHelper(String ruleType){
        List<Rule> newRules = new ArrayList<Rule>();

        for (Rule rule : rules){
            if (rule.getRuleType().equals(ruleType)){
                newRules.add(rule);
            }
        }

        return newRules;
    }

    public List<Rule> getVolumeRules() {
        return getRulesHelper(Rule.RULE_TYPE_VOLUME);
    }

    @Override
    public List<Rule> getBluetoothRules() {
        return getRulesHelper(Rule.RULE_TYPE_BLUETOOTH);
    }

    @Override
    public List<Rule> getWifiRules() {
        return getRulesHelper(Rule.RULE_TYPE_WIFI);
    }

    @Override
    public List<Rule> getHeadphoneRules() {
        return getRulesHelper(Rule.RULE_TYPE_HEADPHONE);
    }

    public void deleteRule(String id){
        // TODO: implement this later
    }

}
