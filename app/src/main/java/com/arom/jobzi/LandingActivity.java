package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arom.jobzi.fragment.AdminFragment;
import com.arom.jobzi.fragment.HomeOwnerFragment;
import com.arom.jobzi.fragment.ServiceProviderFragment;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.Util;

public class LandingActivity extends AppCompatActivity {
	
	protected DrawerLayout drawerLayout;
	
	protected ActionBarDrawerToggle drawerToggle;
	
	protected Toolbar toolbar;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		
		drawerLayout = findViewById(R.id.drawer_layout);
		
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		
		drawerLayout.addDrawerListener(drawerToggle);
		drawerToggle.syncState();

        Bundle bundle = getIntent().getExtras();

        final User user = (User) bundle.getSerializable(Util.USER_BUNDLE_ARG);

        NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
				switch (menuItem.getItemId()){
					
					case R.id.logoutMenuItem:
						Util.getInstance().logout(LandingActivity.this);
						break;
						
					case R.id.profileMenuItem:

					    Intent toProfileActivity = new Intent(LandingActivity.this, ProfileActivity.class);
					    toProfileActivity.putExtra(Util.USER_BUNDLE_ARG, user);
						startActivity(toProfileActivity);

						break;
						
				}
				
				drawerLayout.closeDrawer(GravityCompat.START);
				return true;
			}
		});

		View headerView = navigationView.getHeaderView(0);
		
		TextView usernameNavHeader = headerView.findViewById(R.id.usernameNavHeader);
		TextView accountTypeNavHeader = headerView.findViewById(R.id.accountTypeNavHeader);
		TextView emailNavHeader = headerView.findViewById(R.id.emailNavHeader);
		
		usernameNavHeader.setText(user.getUsername());
		accountTypeNavHeader.setText(user.getAccountType().toString());
		emailNavHeader.setText(user.getEmail());

		setupFragment(savedInstanceState, user);

	}

	private void setupFragment(Bundle savedInstanceBundle, User user) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment;

	    if(savedInstanceBundle == null) {

            switch(user.getAccountType()) {
                case ADMIN:

                    fragment = new AdminFragment();
                    break;

                case HOME_OWNER:

                    fragment = new HomeOwnerFragment();
                    break;

                case SERVICE_PROVIDER:

                    fragment = new ServiceProviderFragment();
                    break;

                default:
                    throw new IllegalStateException(String.format("The account type (%s) does not have a defined fragment.", user.getAccountType()));

            }

            fragment.setArguments(getIntent().getExtras());

            fragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();

        }

    }

}
