package com.arom.jobzi;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.arom.jobzi.fragment.AdminPagerAdapter;
import com.arom.jobzi.service.ServicesFragment;
import com.arom.jobzi.user.UsersFragment;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ViewPager viewPager = findViewById(R.id.viewPager);
        
        AdminPagerAdapter adminPagerAdapter = new AdminPagerAdapter(getSupportFragmentManager());
        adminPagerAdapter.addFragment(new UsersFragment(), getText(R.string.users_label));
        adminPagerAdapter.addFragment(new ServicesFragment(), getText(R.string.services_label));
        
        viewPager.setAdapter(adminPagerAdapter);
        
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        
    }
}
