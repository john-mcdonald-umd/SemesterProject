package edu.umd.cs.semesterproject.Activities;

import android.support.v4.app.Fragment;

import edu.umd.cs.semesterproject.SingleFragmentActivity;
import edu.umd.cs.semesterproject.fragment.VolumeTimeFragment;

public class VolumeTimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return VolumeTimeFragment.newInstance();
    }
}
