package com.arom.jobzi;

import android.accounts.Account;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arom.jobzi.account.AccountType;
import com.arom.jobzi.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    public static final String ACCOUNTS = "accounts";

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    private TextView usernameTextView;
    private TextView passwordTextView;
    private TextView emailTextView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private Spinner accountTypesSpinner;
    private Button signupButton;
    private Button backButton;

    private DatabaseReference accountsDatabase;

    private boolean adminExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        accountsDatabase = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS);

        accountsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if(user.getAccountType().equals(AccountType.ADMIN)) {
                        adminExists = true;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        accountTypesSpinner = findViewById(R.id.accountTypesSpinner);
        signupButton = findViewById(R.id.signupButton);
        backButton = findViewById(R.id.backButton);

        ArrayAdapter<AccountType> spinnerArrayAdapter = new ArrayAdapter<AccountType>(this, android.R.layout.simple_spinner_item, AccountType.values()) {
            @Override
            public boolean isEnabled(int position) {

                if(getItem(position).equals(AccountType.ADMIN) && adminExists) {
                    return false;
                }

                return super.isEnabled(position);

            }

            @NonNull
            @Override
            public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view;

                if(getItem(position).equals(AccountType.ADMIN) && adminExists) {
                    textView.setTextColor(Color.GRAY);
                }

                return view;

            }
        };
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

        AccountType accountType = (AccountType) accountTypesSpinner.getSelectedItem();

        String username = usernameTextView.getText().toString();
        String email = emailTextView.getText().toString();
        String firstName = firstNameTextView.getText().toString();
        String lastName = lastNameTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        if(email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || username.isEmpty()) {
            return null;
        }

        Pattern pat = Pattern.compile(EMAIL_REGEX);
        if (!pat.matcher(email).matches())
            return null;

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAccountType(accountType);

        DatabaseReference newUserDb = accountsDatabase.push();

        String id = newUserDb.getKey();

        user.setId(id);
        
        newUserDb.setValue(user);

        return user;
        
    }

}
