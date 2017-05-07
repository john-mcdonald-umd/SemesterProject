package edu.umd.cs.semesterproject.fragment;

// Fragment for the volume tab of the main activity

import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

import java.util.List;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.dialog.RuleTypeDialogFragment;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.VolumeAction;

public class VolumeFragment extends BaseFragment {

    private static final String TITLE = "Volume";

    public static VolumeFragment newInstance() {

        VolumeFragment actionsFragment = new VolumeFragment();

        return actionsFragment;
    }

    public static String getTitle() {
        return TITLE;
    }

    @Override
    protected List<Rule> getRules() {
        return ruleService.getVolumeRules();
    }

    // For clicking the (+) button. Will open up a dialogue box to choose what type of rule to open.
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fab:
                RuleTypeDialogFragment.newInstance().show(getActivity().getSupportFragmentManager(), getTitle());
                break;
        }
    }

    public static Action getAction(View view){
        SeekBar startVolume = (SeekBar) view.findViewById(R.id.seek_bar_start_volume);
        SeekBar endVolume = (SeekBar) view.findViewById(R.id.seek_bar_end_volume);
        Switch startVibrate = (Switch) view.findViewById(R.id.start_volume_vibrate_switch);
        Switch endVibrate = (Switch) view.findViewById(R.id.end_volume_vibrate_switch);

        VolumeAction volumeAction = new VolumeAction();
        volumeAction.setStartVolume(startVolume.getProgress());
        volumeAction.setEndVolume(endVolume.getProgress());
        /* Set modes too, gotta ask john what the modes mean */

        return volumeAction;
    }
}
