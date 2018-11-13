package com.arom.jobzi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arom.jobzi.fragment.ServiceListFragment;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.Util;

public class ServiceProviderActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private FloatingActionButton addServiceFloatingAction;
    private Context context;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);
        context = this;
    
        Toolbar toolbar = findViewById(R.id.toolbar_ServiceProvider);
        setSupportActionBar(toolbar);
    
        drawerLayout = findViewById(R.id.drawer_layout);
    
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.logoutMenuItem:
                        Util.getInstance().logout(ServiceProviderActivity.this);
                        Toast.makeText(ServiceProviderActivity.this, "Logging out", Toast.LENGTH_SHORT);
                
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

        addServiceFloatingAction = findViewById(R.id.FAB_addAvailability);
        addServiceFloatingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceProviderActivity.this.addAvailibility();
            }
        });
    
    }

    public void addAvailibility(){
        ServiceAvailibility serviceAvailibility = new ServiceAvailibility();
        Intent intent = new Intent(ServiceProviderActivity.this, ServiceAvailibility.class);
        startActivity(intent);
    }

    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
