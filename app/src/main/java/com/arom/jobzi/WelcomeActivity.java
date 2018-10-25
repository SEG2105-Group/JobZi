package com.arom.jobzi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.arom.jobzi.user.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WelcomeActivity extends AppCompatActivity {
	
	public static final String USER = "user";
	
	private User user;
	
	private DatabaseReference db;
    
    private TextView welcomeBannerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
	
		user = (User) getIntent().getSerializableExtra(USER);

        db = FirebaseDatabase.getInstance().getReference();
        
        welcomeBannerTextView = findViewById(R.id.welcomeBannerTextView);
        welcomeBannerTextView.setText(getString(R.string.user_welcome_banner, user.getUsername(), user.getAccountType().toString()));
        
    }

}
