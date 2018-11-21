package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
		
		Intent redirectIntent;
		
		if(currentUser == null) {
			
			redirectIntent = new Intent(this, LoginActivity.class);
			
		} else {
			
			redirectIntent = new Intent(this, LandingActivity.class);
			
		}
		
		startActivity(redirectIntent);
		finish();
		
	}
}
