/*package edu.umd.cs.semesterproject.service.impl;

import android.content.Context;

import java.util.List;

import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.service.RuleService;

public class SQLiteRuleService implements RuleService {

    private DbHelper mDatabase;

    public SQLiteRuleService(Context context) {
        mDatabase = new DbHelper(context);
    }

    @Override
    public void addRule(Rule rule) {
        mDatabase.addRule(rule);
    }

    @Override
    public Rule getRuleById(String id) {
        return mDatabase.getRuleById(id);
    }

    @Override
    public List<Rule> getAllRules() {
        return mDatabase.getAllRules();
    }

    @Override
    public List<Rule> getAllRulesByActionType(Rule.ActionType actionType) {
        return mDatabase.getAllRulesByActionType(actionType);
    }

    @Override
    public void updateRule(Rule rule) {
        mDatabase.updateRule(rule);
    }

    @Override
    public void deleteRule(String id) {
        mDatabase.deleteRule(id);
    }

    @Override
    public List<Rule> getVolumeRules() {
        return getAllRulesByActionType(Rule.ActionType.VOLUME);
    }

    @Override
    public List<Rule> getBluetoothRules() {
        return null;
    }

    @Override
    public List<Rule> getWifiRules() {
        return null;
    }

    @Override
    public List<Rule> getHeadphoneRules() {
        return null;
    }
}*/
