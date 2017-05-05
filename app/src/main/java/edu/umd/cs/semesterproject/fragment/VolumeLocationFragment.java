package edu.umd.cs.semesterproject.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import edu.umd.cs.semesterproject.R;

public class VolumeLocationFragment extends Fragment {

    private final String TAG = "VolumeLocationFragment";

    public static final String RULE_CREATED = "RULE_CREATED";
    private static final int PLACE_PICKER_REQUEST = 199;

    private Button addLocationButton;
    private Place place;
    private TextView locationLabel;

    public static Fragment newInstance() {
        return new VolumeLocationFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Call to super
        super.onCreateView(inflater, container, savedInstanceState);

        // Set content view
        View view = inflater.inflate(R.layout.fragment_volume_location, container, false);
        EditText ruleName = (EditText) view.findViewById(R.id.rule_name);
        Button saveButton = (Button) view.findViewById(R.id.save_button);
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        addLocationButton = (Button) view.findViewById(R.id.button_add_location);
        locationLabel = (TextView) view.findViewById(R.id.text_view_display_location);

        // Link UI elements
        addLocationButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                try{

                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e){
                    Log.e(TAG, e.toString());
                }
            }
        });

        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                try{


                    if (place != null) {
                        Intent intent = new Intent();
                        intent.putExtra(RULE_CREATED, "asdf");
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                    }
                    else{
                        Toast toast = Toast.makeText(getActivity(), "Please set both the start and end times before saving.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (Exception e){
                    Log.e(TAG, e.toString());
                }
            }
        });
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                try{
                    Intent returnIntent = new Intent();
                    getActivity().setResult(Activity.RESULT_CANCELED, returnIntent);
                    getActivity().finish();
                } catch (Exception e){
                    Log.e(TAG, e.toString());
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Retrieves the location selected by the user through Place Picker
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {

                addLocationButton.setVisibility(View.GONE);
                Place p = PlacePicker.getPlace(data, getActivity());
                String toastMsg = String.format("Place: %s", p.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
                place = p;
                locationLabel.setText("Selected Location: " + p.getName());
                locationLabel.setVisibility(View.VISIBLE);
            }
        }
    }
}