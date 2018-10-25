package com.arom.jobzi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.arom.jobzi.user.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
	
	public static final String USER = "user";
	
	private User user;

	private DatabaseReference db;
    
    private TextView welcomeBannerTextView;

    private DatabaseReference usersDatabase;

    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
	
		user = (User) getIntent().getSerializableExtra(USER);

        db = FirebaseDatabase.getInstance().getReference().child(SignupActivity.ACCOUNTS);

        if (user.getAccountType().equals("Admin")) {
            addUsersListener();
        }

        welcomeBannerTextView = findViewById(R.id.welcomeBannerTextView);
        welcomeBannerTextView.setText(getString(R.string.user_welcome_banner, user.getUsername(), user.getAccountType().toString()));
    }

    private void addUsersListener(){

        usersDatabase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = (User) ds.getValue();
                    users.add(user);
                    Log.d("firebaseDebug", "Adding user");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

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
