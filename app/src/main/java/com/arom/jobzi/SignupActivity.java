package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arom.jobzi.account.AccountType;
import com.arom.jobzi.user.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView passwordTextView;
    private TextView emailTextView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private Spinner accountTypesSpinner;
    private Button signupButton;
    private Button backButton;

    private DatabaseReference db;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = FirebaseDatabase.getInstance().getReference();
        
        emailTextView = findViewById(R.id.emailTextView);
        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        accountTypesSpinner = findViewById(R.id.accountTypesSpinner);
        signupButton = findViewById(R.id.signupButton);
        backButton = findViewById(R.id.backButton);

        ArrayAdapter<AccountType> spinnerArrayAdapter = new ArrayAdapter<AccountType>(this, android.R.layout.simple_spinner_item, AccountType.values());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypesSpinner.setAdapter(spinnerArrayAdapter);
        accountTypesSpinner.setSelection(AccountType.HOME_OWNER.ordinal());

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = SignupActivity.this.processSignup();
                
                if(user != null) {
                    Intent toWelcomeIntent = new Intent(SignupActivity.this, WelcomeActivity.class);
                    toWelcomeIntent.putExtra(WelcomeActivity.USER, user);
                    startActivity(toWelcomeIntent);
                } else {
                    Toast.makeText(SignupActivity.this, "You are missing some info.", Toast.LENGTH_LONG).show();
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLoginIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(toLoginIntent);
            }
        });

    }

    private User processSignup() {
    
        String username = usernameTextView.getText().toString();
        String email = emailTextView.getText().toString();
        String firstName = firstNameTextView.getText().toString();
        String lastName = lastNameTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        AccountType accountType = (AccountType) accountTypesSpinner.getSelectedItem();

        if(email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
            return null;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAccountType(accountType);

        String id = db.push().getKey();
        
        user.setId(id);
        
        db.child(id).setValue(user);
        
        return user;
        
    }

}