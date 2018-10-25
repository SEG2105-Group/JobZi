package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arom.jobzi.user.User;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class LoginActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView passwordTextView;

    private Button loginButton;
    private Button signupButton;

    private DatabaseReference accountsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountsDatabase = FirebaseDatabase.getInstance().getReference().child(SignupActivity.ACCOUNTS);
        
        usernameTextView = findViewById(R.id.loginPsernameTextView);
        passwordTextView = findViewById(R.id.loginPasswordTextView);

        signupButton = findViewById(R.id.gotoSignupButton);

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogin();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    public void processLogin() {

        final String username = usernameTextView.getText().toString();
        final String password = passwordTextView.getText().toString();

        accountsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                    User user = userSnapshot.getValue(User.class);

                    if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        Intent toWelcomeIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
                        toWelcomeIntent.putExtra(WelcomeActivity.USER, user);
                        startActivity(toWelcomeIntent);
                        return;
                    }
                }

                Toast.makeText(LoginActivity.this, "Invalid username or password or user does not exist.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
