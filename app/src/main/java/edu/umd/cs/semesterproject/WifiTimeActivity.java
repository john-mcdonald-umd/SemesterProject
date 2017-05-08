package edu.umd.cs.semesterproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import edu.umd.cs.semesterproject.fragment.WifiTimeFragment;
import edu.umd.cs.semesterproject.util.Codes;

// Activity that holds the fragment for creating a Wifi Time Rule.
public class WifiTimeActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call to super
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String userID = bundle.getString(Codes.RULE_ID);
        return WifiTimeFragment.newInstance(userID);
    }


    public static Intent newIntent(Context context, String ruleID){
        Intent intent = new Intent(context, WifiTimeActivity.class);
        intent.putExtra(Codes.RULE_ID, ruleID);
        return intent;
    }
}
