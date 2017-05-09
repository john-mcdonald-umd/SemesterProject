package edu.umd.cs.semesterproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.LocationRule;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.VolumeAction;
import edu.umd.cs.semesterproject.util.Codes;

// The Fragment used to create Volume Location Rules.
public class VolumeLocationFragment extends BaseLocationFragment implements AdapterView.OnItemSelectedListener {

    private final String TAG = "VolumeLocationFragment";
    private SeekBar startVolume, endVolume;
    private ArrayAdapter<CharSequence> startAdapter, endAdapter;
    private Spinner startSpinner, endSpinner;

    public static Fragment newInstance(String userID){
        Bundle bundle = new Bundle();
        bundle.putString(Codes.RULE_ID, userID);
        VolumeLocationFragment ruleFragment = new VolumeLocationFragment();
        ruleFragment.setArguments(bundle);
        return ruleFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        startVolume = (SeekBar) view.findViewById(R.id.seek_bar_start_volume);
        endVolume = (SeekBar) view.findViewById(R.id.seek_bar_end_volume);
        startSpinner = (Spinner) view.findViewById(R.id.spinner_start_volume_mode);
        endSpinner = (Spinner) view.findViewById(R.id.spinner_end_volume_mode);

        startAdapter =
                ArrayAdapter.createFromResource(
                        this.getActivity(),R.array.volume_array, android.R.layout.simple_spinner_item);
        startAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(startAdapter);
        startSpinner.setOnItemSelectedListener(this);

        endAdapter =
                ArrayAdapter.createFromResource(
                        this.getActivity(),R.array.volume_array, android.R.layout.simple_spinner_item);
        endAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        endSpinner.setAdapter(endAdapter);
        endSpinner.setOnItemSelectedListener(this);

        VolumeAction action = (VolumeAction) locationRule.getAction();
        if (action != null) {
            if (action.getStartMode().equals(VolumeAction.VolumeMode.NORMAL)) {
                startSpinner.setSelection(0);
            } else {
                startSpinner.setSelection(1);
            }
            if (action.getEndMode().equals(VolumeAction.VolumeMode.NORMAL)) {
                endSpinner.setSelection(0);
            } else {
                endSpinner.setSelection(1);
            }
        }

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        if (parentView.equals(startSpinner)){
            startVolume.setEnabled(position == 0);
        }
        if (parentView.equals(endSpinner)){
            endVolume.setEnabled(position == 0);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parentView) {

    }

    @Override
    protected Rule.ActionType getActionType() {
        return Rule.ActionType.VOLUME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_volume_location;
    }

    @Override
    protected void setupSpecificLayout(View view, LocationRule rule) {
        VolumeFragment.setupSpecificLayout(view, rule);
    }

    @Override
    protected Action getAction() {
        VolumeAction volumeAction = (VolumeAction) VolumeFragment.getAction(view);
        int startMode = startSpinner.getSelectedItemPosition();
        int endMode = endSpinner.getSelectedItemPosition();
        if (startMode == 0){
            volumeAction.setStartMode(VolumeAction.VolumeMode.NORMAL);
        }
        else{
            volumeAction.setStartMode(VolumeAction.VolumeMode.SILENT);
        }
        if (endMode == 0){
            volumeAction.setEndMode(VolumeAction.VolumeMode.NORMAL);
        }
        else{
            volumeAction.setEndMode(VolumeAction.VolumeMode.SILENT);
        }
        return volumeAction;
    }

}