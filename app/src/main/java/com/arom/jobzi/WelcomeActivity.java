package com.arom.jobzi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.arom.jobzi.account.AccountType;
import com.arom.jobzi.user.User;
import com.arom.jobzi.user.UserList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
	
	public static final String USER = "user";
	
	private User user;

	private DatabaseReference accountsDatabase;
    
    private TextView welcomeBannerTextView;

    private List<User> users;

    private ListView userList;
    private UserList userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
	
		user = (User) getIntent().getSerializableExtra(USER);

        accountsDatabase = FirebaseDatabase.getInstance().getReference().child(SignupActivity.ACCOUNTS);

        userList = findViewById(R.id.userList);

        users = new LinkedList<User>();

        if (user.getAccountType().equals(AccountType.ADMIN)) {
            userList.setVisibility(TextView.VISIBLE);
            userListAdapter = new UserList(this, users);
            userList.setAdapter(userListAdapter);
            addUsersListener();
        }

        welcomeBannerTextView = findViewById(R.id.welcomeBannerTextView);
        welcomeBannerTextView.setText(getString(R.string.user_welcome_banner, user.getUsername(), user.getAccountType().toString()));
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
