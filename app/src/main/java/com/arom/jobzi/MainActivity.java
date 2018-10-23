package com.arom.jobzi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arom.jobzi.account.AccountsManager;

public class MainActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView passwordTextView;

    private Button loginButton;
    private Button signupButton;
    
    private AccountsManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameTextView = findViewById(R.id.userName);
        passwordTextView = findViewById(R.id.userPassword);

        signupButton = findViewById(R.id.button_signUp);

        loginButton = findViewById(R.id.button_logIn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();



            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
