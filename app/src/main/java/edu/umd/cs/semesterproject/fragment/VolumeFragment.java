package edu.umd.cs.semesterproject.fragment;

// Fragment for the volume tab of the main activity

import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

import java.util.List;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.dialog.RuleTypeDialogFragment;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.VolumeAction;

// The fragment used for the Volume tab of the TabLayout.
public class VolumeFragment extends BaseFragment {

    private static final String TITLE = "Volume";
    private static final String TAG = VolumeFragment.class.getSimpleName();

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

    // Returns an action based on the View view.
    // It will get the start and end volumes from the seekbars.
    // And will get whether or not to set it to vibrate at the start and end.
    // The volumes are ratios of SeekBar.Max() / SeekBar.getProgress()
    public static Action getAction(View view){
        SeekBar startVolume = (SeekBar) view.findViewById(R.id.seek_bar_start_volume);
        SeekBar endVolume = (SeekBar) view.findViewById(R.id.seek_bar_end_volume);
        Switch startVibrate = (Switch) view.findViewById(R.id.start_volume_vibrate_switch);
        Switch endVibrate = (Switch) view.findViewById(R.id.end_volume_vibrate_switch);

        VolumeAction volumeAction = new VolumeAction();
        Log.d(TAG, "created VolumeAction");
        if (startVolume.getProgress() == 0) {
            volumeAction.setStartVolume(startVolume.getMax());
        }
        else {
            volumeAction.setStartVolume(startVolume.getMax() / startVolume.getProgress());
        }
        if (endVolume.getProgress() == 0) {
            volumeAction.setEndVolume(endVolume.getMax());
        }
        else {
            volumeAction.setEndVolume(endVolume.getMax() / endVolume.getProgress());
        }

        // set modes
        volumeAction.setStartVibrate(startVibrate.isChecked());
        volumeAction.setEndVibrate(endVibrate.isChecked());

        return volumeAction;
    }

    public static void setupSpecificLayout(View view, Rule rule){
        SeekBar startVolume = (SeekBar) view.findViewById(R.id.seek_bar_start_volume);
        SeekBar endVolume = (SeekBar) view.findViewById(R.id.seek_bar_end_volume);
        Switch startVibrate = (Switch) view.findViewById(R.id.start_volume_vibrate_switch);
        Switch endVibrate = (Switch) view.findViewById(R.id.end_volume_vibrate_switch);

        VolumeAction volumeAction = (VolumeAction) rule.getAction();

        startVolume.setProgress((int) (startVolume.getMax() / volumeAction.getStartVolume()));
        endVolume.setProgress((int) (endVolume.getMax() / volumeAction.getEndVolume()));
        startVibrate.setChecked(volumeAction.getStartVibrate());
        endVibrate.setChecked(volumeAction.getEndVibrate());
    }
}
