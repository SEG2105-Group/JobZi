package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arom.jobzi.user.User;
import com.arom.jobzi.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Util.getInstance().onUserLogin(LoginActivity.this, task.getResult().getUser());
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password or user does not exist.", Toast.LENGTH_LONG).show();
	
					loginButton.setEnabled(true);
					signupButton.setEnabled(true);
	
				}

            }
        });

    }

}
