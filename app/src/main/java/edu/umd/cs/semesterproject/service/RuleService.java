package edu.umd.cs.semesterproject.service;

import java.util.List;

import edu.umd.cs.semesterproject.model.Rule;

public interface RuleService {

    void addRule(Rule rule);
    Rule getRuleById(String id);
    List<Rule> getAllRules();
    List<Rule> getAllRulesByActionType(Rule.ActionType actionType);
    List<Rule> getVolumeRules();
    List<Rule> getBluetoothRules();
    List<Rule> getWifiRules();
    List<Rule> getReminderRules();
    List<Rule> getLocationRules();
    List<Rule> getTimeRules();
    void deleteRule(String id);
}