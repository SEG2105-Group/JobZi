package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class SignupActivity extends AppCompatActivity {

    private TextView emailTextView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView passwordTextView;
    private Spinner accountTypesSpinner;
    private Button signupButton;
    private Button backButton;

    private FirebaseAuth authentication;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        authentication = FirebaseAuth.getInstance();
        databaseManager = new DatabaseManager();

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

        String email = emailTextView.getText().toString();
        String firstName = firstNameTextView.getText().toString();
        String lastName = lastNameTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        AccountType accountType = (AccountType) accountTypesSpinner.getSelectedItem();

        final User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAccountType(accountType);

        authentication.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String uid = authResult.getUser().getUid();
                databaseManager.addUser(user, uid);
            }
        });


    }

}
