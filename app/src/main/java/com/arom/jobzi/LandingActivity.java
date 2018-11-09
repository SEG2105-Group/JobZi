package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.arom.jobzi.account.AccountType;
import com.arom.jobzi.user.User;
import com.arom.jobzi.user.UserArrayAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;
import java.util.List;

public class LandingActivity extends FragmentActivity {

	public static final String BUNDLE_ARG_USER = "user";

	private FirebaseUser firebaseUser;
	private User user;

	private FirebaseAuth auth;
    
    private TextView welcomeBannerTextView;

    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

		auth = FirebaseAuth.getInstance();

		user = (User) getIntent().getSerializableExtra(BUNDLE_ARG_USER);
        firebaseUser = auth.getCurrentUser();

        LinearLayout welcomeLinearLayout = findViewById(R.id.welcomeLinearLayout);

        users = new LinkedList<User>();

        if (user.getAccountType().equals(AccountType.ADMIN)) {

            ListView userList = new ListView(this);
            Button addServiceButton = new Button(this);
            addServiceButton.setText(R.string.add_service);

            welcomeLinearLayout.addView(userList);
            welcomeLinearLayout.addView(addServiceButton);

            UserArrayAdapter userArrayAdapter = new UserArrayAdapter(this, users);
            userList.setAdapter(userArrayAdapter);
            addUsersListener();

        }

        welcomeBannerTextView = findViewById(R.id.welcomeBannerTextView);
        welcomeBannerTextView.setText(getString(R.string.user_welcome_banner, user.getUsername(), user.getAccountType().toString()));
        
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LandingActivity.this.logout();
            }
        });
        
    }

    private void addUsersListener() {

    }

    private void logout() {
        auth.signOut();
        Intent toLoginIntent = new Intent(LandingActivity.this, LoginActivity.class);
        startActivity(toLoginIntent);
        finish();
    }

}
