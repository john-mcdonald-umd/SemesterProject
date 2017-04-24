package edu.umd.cs.semesterproject;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.umd.cs.semesterproject.adapter.ViewPagerAdapter;
import edu.umd.cs.semesterproject.fragment.ActionsFragment;
import edu.umd.cs.semesterproject.fragment.ConditionsFragment;
import edu.umd.cs.semesterproject.fragment.RulesFragment;

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
        viewPagerAdapter.addFragment(RulesFragment.newInstance(), RulesFragment.getTitle());
        viewPagerAdapter.addFragment(ConditionsFragment.newInstance(), ConditionsFragment.getTitle());
        viewPagerAdapter.addFragment(ActionsFragment.newInstance(), ActionsFragment.getTitle());
        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {

    }
}
