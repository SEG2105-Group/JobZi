package com.arom.jobzi;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.arom.jobzi.service.ServicesFragment;
import com.arom.jobzi.admin.UsersFragment;

public class AdminActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminviewlist);

        ServicesFragment servicesFragment = new ServicesFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, servicesFragment).commit();

        UsersFragment usersFragment = new UsersFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, usersFragment).commit();

    }
}
