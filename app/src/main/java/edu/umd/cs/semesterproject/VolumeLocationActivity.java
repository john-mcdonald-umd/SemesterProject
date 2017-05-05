package edu.umd.cs.semesterproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import edu.umd.cs.semesterproject.fragment.VolumeLocationFragment;



public class VolumeLocationActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call to super
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return VolumeLocationFragment.newInstance();
    }
}
