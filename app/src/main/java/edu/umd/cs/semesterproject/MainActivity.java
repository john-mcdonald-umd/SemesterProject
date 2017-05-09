package edu.umd.cs.semesterproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import edu.umd.cs.semesterproject.adapter.ViewPagerAdapter;
import edu.umd.cs.semesterproject.fragment.BluetoothFragment;
import edu.umd.cs.semesterproject.fragment.ReminderFragment;
import edu.umd.cs.semesterproject.fragment.VolumeFragment;
import edu.umd.cs.semesterproject.fragment.WifiFragment;
import edu.umd.cs.semesterproject.service.impl.ServiceLocation;
import edu.umd.cs.semesterproject.service.impl.TimeBackgroundService;

// Main activity of the Project. Has the TabLayout which is the main UI
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // basic fields for increments for the alarm to check time intervals
    private final int ONE_MINUTE = 60000;
    private final int TWO_MINUTES = 120000;
    private final int FIVE_MINUTES = 300000;
    private final int TEN_MINUTES = 600000;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Start alarm to start checking time rules
        TimeBackgroundService.setTimeAlarm(this, ONE_MINUTE);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(VolumeFragment.newInstance(), VolumeFragment.getTitle());
        viewPagerAdapter.addFragment(WifiFragment.newInstance(), WifiFragment.getTitle());
        viewPagerAdapter.addFragment(BluetoothFragment.newInstance(), BluetoothFragment.getTitle());
        viewPagerAdapter.addFragment(ReminderFragment.newInstance(), ReminderFragment.getTitle());

        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);




        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            try {
                askPermission();
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        startService(new Intent(MainActivity.this, ServiceLocation.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
// Make sure it's our original READ_CONTACTS request
        if (requestCode == 999) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // Asks for permission
    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                this,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                999
        );
    }

    @Override
    public void onClick(View v) {

    }
}
