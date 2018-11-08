package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

	public static final String USER = "user";

	private FirebaseUser firebaseUser;
	private User user;

	private FirebaseAuth auth;
	private DatabaseReference accountsDatabase;
    
    private TextView welcomeBannerTextView;

    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

		auth = FirebaseAuth.getInstance();
        accountsDatabase = FirebaseDatabase.getInstance().getReference().child(SignupActivity.ACCOUNTS);

		user = (User) getIntent().getSerializableExtra(USER);
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
                auth.signOut();
                Intent toLoginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(toLoginIntent);
            }
        });
        
    }

    private void addUsersListener() {

        accountsDatabase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                User user = dataSnapshot.getValue(User.class);
                users.add(user);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                User userChanged = dataSnapshot.getValue(User.class);

                int i = 0;

                for(; i < users.size(); i++) {
                    if(users.get(i).getId().equals(userChanged.getId())) {
                        break;
                    }
                }

                users.set(i, userChanged);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                User userChanged = dataSnapshot.getValue(User.class);

                int i = 0;

                for(; i < users.size(); i++) {
                    if(users.get(i).getId().equals(userChanged.getId())) {
                        break;
                    }
                }

                users.remove(i);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
