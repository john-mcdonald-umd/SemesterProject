package edu.umd.cs.semesterproject;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.umd.cs.semesterproject.adapter.ViewPagerAdapter;
import edu.umd.cs.semesterproject.fragment.BluetoothFragment;
import edu.umd.cs.semesterproject.fragment.VolumeFragment;
import edu.umd.cs.semesterproject.fragment.WifiFragment;
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

        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {

    }
}
