package com.arom.jobzi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arom.jobzi.user.User;
import com.arom.jobzi.util.Util;

public class HomeOwnerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner);

        Toolbar toolbar = findViewById(R.id.toolbar_HomeOwner);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.logoutMenuItem:
                        Util.getInstance().logout(HomeOwnerActivity.this);
                        Toast.makeText(HomeOwnerActivity.this, "Logging out", Toast.LENGTH_SHORT);

                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        Bundle bundle = getIntent().getExtras();

        User user = (User) bundle.getSerializable(Util.ARG_USER);

        Menu menu = navigationView.getMenu();

        MenuItem usernameMenuItem = menu.findItem(R.id.usernameMenuItem);
        MenuItem accountTypeMenuItem = menu.findItem(R.id.accountTypeMenuItem);

        usernameMenuItem.setTitle(getString(R.string.username_menu_label, user.getUsername()));
        accountTypeMenuItem.setTitle(getString(R.string.account_type_menu_label, user.getAccountType().toString()));

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
}
