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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import edu.umd.cs.semesterproject.DependencyFactory;
import edu.umd.cs.semesterproject.GeofenceActivity;
import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.LocationRule;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.util.Codes;

// Base fragment for creating a Location Rule.
public abstract class BaseLocationFragment extends Fragment {

    private final String TAG = "BaseLocationFragment";
    protected Rule rule;
    protected LocationRule locationRule;

    private boolean locationSet = false;

    private Button addLocationButton;
    private Place place;
    private TextView locationLabel;
    private EditText ruleName;
    private Switch enabled_switch;
    protected View view;

    // Gets the action type of the Location Rule. Volume Rules would return Rule.ActionType.VOLUME
    protected abstract Rule.ActionType getActionType();
    // gets the layout id. VolumeLocationRules and WifiLocationRules have different layouts, for example.
    protected abstract int getLayoutId();
    // set up specific layout for the ActionType
    protected abstract void setupSpecificLayout(View view, LocationRule rule);
    // gets the action of the Rule
    protected abstract Action getAction();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Call to super
        super.onCreate(savedInstanceState);

        // Get Arguments
        Bundle args = getArguments();
        String ruleID = args.getString(Codes.RULE_ID);
        RuleService ruleService = DependencyFactory.getRuleService(getActivity());
        rule = ruleService.getRuleById(ruleID);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Call to super
        super.onCreateView(inflater, container, savedInstanceState);

        // Set content view
        view = inflater.inflate(getLayoutId(), container, false);
        enabled_switch = (Switch) view.findViewById(R.id.switch_enabled);
        ruleName = (EditText) view.findViewById(R.id.rule_name);
        Button saveButton = (Button) view.findViewById(R.id.save_button);
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        addLocationButton = (Button) view.findViewById(R.id.button_add_location);
        locationLabel = (TextView) view.findViewById(R.id.text_view_display_location);

        // if creating new rule.
        if (rule == null) {
            // Set up location rule
            locationRule = new LocationRule(ruleName.getText().toString(), true, "", 0, 0, 0);
            enabled_switch.setChecked(true);
        }
        // if editing a pre-existing rule.
        else{
            locationSet = true;
            ruleName.setText(rule.getName());
            locationRule = (LocationRule) rule;
            enabled_switch.setChecked(locationRule.isEnabled());
            setupSpecificLayout(view, locationRule);
        }

        // Link UI elements
        addLocationButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                try{

                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(builder.build(getActivity()), Codes.PLACE_PICKER_REQUEST);
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
                    // if the location has been set, then save it and add it to the geofence.
                    if (locationSet) {
                        Intent intent = new Intent();
                        locationRule.setActionType(getActionType());
                        locationRule.setAction(getAction());
                        locationRule.setEnabled(enabled_switch.isChecked());
                        locationRule.setName(ruleName.getText().toString());
                        intent.putExtra(Codes.RULE_CREATED, locationRule);

                        Intent geofenceIntent = new Intent(getActivity(), GeofenceActivity.class);
                        geofenceIntent.putExtra("lat", place.getLatLng().latitude);
                        geofenceIntent.putExtra("lng", place.getLatLng().longitude);
                        geofenceIntent.putExtra("id", locationRule.getId());
                        geofenceIntent.putExtra("radius", 5000);
                        startActivity(geofenceIntent);

                        getActivity().setResult(Activity.RESULT_OK, geofenceIntent);

                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                    }
                    else{
                        Toast toast = Toast.makeText(getActivity(), "Please set the location before saving.", Toast.LENGTH_SHORT);
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
        if (requestCode == Codes.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                addLocationButton.setVisibility(View.GONE);
                Place p = PlacePicker.getPlace(data, getActivity());
                String toastMsg = String.format("Place: %s", p.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
                place = p;
                LatLng latLng = place.getLatLng();
                locationRule.setLatitude(latLng.latitude);
                locationRule.setLongitude(latLng.longitude);
                /* set radius */
                locationSet = true;
                locationLabel.setText("Selected Location: " + p.getName());
                locationLabel.setVisibility(View.VISIBLE);



            }
        }
    }

}
