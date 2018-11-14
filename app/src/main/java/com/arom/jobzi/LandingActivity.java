package com.arom.jobzi;

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
import android.view.Menu;
import android.view.MenuItem;

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
		
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
				switch (menuItem.getItemId()){
					
					case R.id.logoutMenuItem:
						Util.getInstance().logout(LandingActivity.this);
						break;
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
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		
		Fragment fragment;
		
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
		
		fragment.setArguments(bundle);
		
		fragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		
	}
	
}
