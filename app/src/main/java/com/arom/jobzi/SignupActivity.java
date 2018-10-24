package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.arom.jobzi.account.AccountType;
import com.arom.jobzi.account.DatabaseManager;
import com.arom.jobzi.user.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

                SignupActivity.this.processSignup();

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

    private void processSignup() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        String email = emailTextView.getText().toString();
        final String firstName = firstNameTextView.getText().toString();
        String lastName = lastNameTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        AccountType accountType = (AccountType) accountTypesSpinner.getSelectedItem();

        if(email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
            return;
        }

        final User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAccountType(accountType);
        db.child(user.getId()).setValue(user);

    }

}
