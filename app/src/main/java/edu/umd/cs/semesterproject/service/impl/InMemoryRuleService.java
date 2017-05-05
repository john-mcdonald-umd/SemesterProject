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
            currRule.setConditions(rule.getConditions());
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

    public List<Rule> getVolumeRules() {

        List<Rule> volumeRules = new ArrayList<Rule>();

        for (Rule rule : rules){
            if (rule.getRuleType().equals(Rule.RULE_TYPE_VOLUME)){
                volumeRules.add(rule);
            }
        }

        return volumeRules;
    }

    public void deleteRule(String id){
        // TODO: implement this later
    }

}
