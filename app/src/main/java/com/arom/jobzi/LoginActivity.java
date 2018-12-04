package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arom.jobzi.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextView emailTextView;
    private TextView passwordTextView;

    private Button loginButton;
    private Button signupButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.loginEmailTextView);
        passwordTextView = findViewById(R.id.loginPasswordTextView);

        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.gotoSignupButton);
	
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {processLogin();
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

        loginButton.setEnabled(false);
        signupButton.setEnabled(false);

        final String email = emailTextView.getText().toString();
        final String password = passwordTextView.getText().toString();
        
        // This code is sadly repeated in the UserProfileUtil class but this use case is incompatible
        // with the methods in that class.
        if(email.isEmpty()) {
            
            Toast.makeText(this, "Please provide an email address.", Toast.LENGTH_SHORT).show();
    
            loginButton.setEnabled(true);
            signupButton.setEnabled(true);
            
            return;
            
        }
        
        if(password.isEmpty()) {
    
            Toast.makeText(this, "Please provide a password.", Toast.LENGTH_SHORT).show();
    
            loginButton.setEnabled(true);
            signupButton.setEnabled(true);
    
            return;
    
        }
        
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                
                if(task.isSuccessful()) {
    
                    DatabaseReference database = Util.getInstance().getAccountsDatabase();
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        
                        }
    
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
        
                        }
                    });
                    
                    Intent toLandingIntent = new Intent(LoginActivity.this, LandingActivity.class);
                    LoginActivity.this.startActivity(toLandingIntent);
                    LoginActivity.this.finish();
                    
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password or user does not exist.", Toast.LENGTH_LONG).show();
	
					loginButton.setEnabled(true);
					signupButton.setEnabled(true);
	
				}

            }
        });

    }

}
