package edu.umd.cs.semesterproject;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.umd.cs.semesterproject.adapter.ViewPagerAdapter;
import edu.umd.cs.semesterproject.fragment.BluetoothFragment;
import edu.umd.cs.semesterproject.fragment.HeadphoneFragment;
import edu.umd.cs.semesterproject.fragment.TestFragment;
import edu.umd.cs.semesterproject.fragment.VolumeFragment;
import edu.umd.cs.semesterproject.fragment.WifiFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    //test push
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(VolumeFragment.newInstance(), VolumeFragment.getTitle());
        viewPagerAdapter.addFragment(WifiFragment.newInstance(), WifiFragment.getTitle());
        viewPagerAdapter.addFragment(BluetoothFragment.newInstance(), BluetoothFragment.getTitle());
        viewPagerAdapter.addFragment(HeadphoneFragment.newInstance(), HeadphoneFragment.getTitle());
        viewPagerAdapter.addFragment(TestFragment.newInstance(), TestFragment.getTitle());

        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {

    }
}
