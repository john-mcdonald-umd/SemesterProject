package edu.umd.cs.semesterproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.SeekBar;
import android.widget.Switch;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.VolumeAction;
import edu.umd.cs.semesterproject.util.Codes;

public class VolumeTimeFragment extends BaseTimeFragment {

    private final String TAG = "VolumeTimeFragment";

    public static Fragment newInstance(String userID){
        Bundle bundle = new Bundle();
        bundle.putString(Codes.RULE_ID, userID);
        VolumeTimeFragment ruleFragment = new VolumeTimeFragment();
        ruleFragment.setArguments(bundle);
        return ruleFragment;
    }

    @Override
    protected Rule.ActionType getActionType() {
        return Rule.ActionType.VOLUME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_volume_time;
    }

    @Override
    protected Action getAction() {
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
