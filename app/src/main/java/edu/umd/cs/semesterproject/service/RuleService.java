package edu.umd.cs.semesterproject.service;

import java.util.List;

import edu.umd.cs.semesterproject.model.Rule;

public interface RuleService {

    void addRule(Rule rule);
    Rule getRuleById(String id);
    List<Rule> getVolumeRules();
    List<Rule> getBluetoothRules();
    List<Rule> getWifiRules();
    List<Rule> getHeadphoneRules();
    void deleteRule(String id);
}
