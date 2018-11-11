package com.arom.jobzi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.arom.jobzi.fragment.AdminPagerAdapter;
import com.arom.jobzi.fragment.ServiceListFragment;
import com.arom.jobzi.fragment.UserListFragment;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.Util;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ViewPager viewPager = findViewById(R.id.viewPager);
        
        AdminPagerAdapter adminPagerAdapter = new AdminPagerAdapter(getSupportFragmentManager());

        UserListFragment userListFragment = new UserListFragment();
        ServiceListFragment serviceListFragment = new ServiceListFragment();

        adminPagerAdapter.addFragment(userListFragment, getText(R.string.users_label));
        adminPagerAdapter.addFragment(serviceListFragment, getText(R.string.services_label));

        viewPager.setAdapter(adminPagerAdapter);
        
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



    }

    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.logout_drawer:
                Util.getInstance().logout(this);
                Toast.makeText(this, "Loging out", Toast.LENGTH_SHORT);

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
