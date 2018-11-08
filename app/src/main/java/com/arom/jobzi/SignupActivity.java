package com.arom.jobzi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    public static final String ACCOUNTS = "accounts";

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$");
    private static final Pattern VALID_PATTERN = Pattern.compile("^[a-zA-Z]+");

    private TextView usernameTextView;
    private TextView passwordTextView;
    private TextView emailTextView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private Spinner accountTypesSpinner;
    private Button signupButton;
    private Button backButton;

    private FirebaseAuth auth;
    private DatabaseReference accountsDatabase;

    private boolean adminExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        accountsDatabase = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS);

        accountsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if(user.getAccountType().equals(AccountType.ADMIN)) {
                        adminExists = true;
                        break;
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

        ArrayAdapter<AccountType> spinnerArrayAdapter = new ArrayAdapter<AccountType>(this, android.R.layout.simple_spinner_dropdown_item, AccountType.values()) {
            @Override
            public boolean isEnabled(int position) {

                if(getItem(position).equals(AccountType.ADMIN) && adminExists) {
                    return false;
                }

                return super.isEnabled(position);

            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);

                TextView textView = (TextView) view;

                if(getItem(position).equals(AccountType.ADMIN) && adminExists) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }

                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypesSpinner.setAdapter(spinnerArrayAdapter);
        accountTypesSpinner.setSelection(AccountType.HOME_OWNER.ordinal());

        signupButton.setEnabled(true);
        backButton.setEnabled(true);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signupButton.setEnabled(false);
                backButton.setEnabled(false);

                accountsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final AccountType accountType = (AccountType) accountTypesSpinner.getSelectedItem();

                        final String username = usernameTextView.getText().toString();
                        final String email = emailTextView.getText().toString();
                        final String firstName = firstNameTextView.getText().toString();
                        final String lastName = lastNameTextView.getText().toString();
                        final String password = passwordTextView.getText().toString();

                        if(email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || username.isEmpty()) {
                            Toast.makeText(SignupActivity.this, "You are missing some info.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (!EMAIL_PATTERN.matcher(email).matches()) {
                            Toast.makeText(SignupActivity.this, "Email is not valid.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if(!VALID_PATTERN.matcher(firstName).matches()){
                            Toast.makeText(SignupActivity.this, "First name is not valid.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if(!VALID_PATTERN.matcher(lastName).matches()){
                            Toast.makeText(SignupActivity.this, "Last name is not valid.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {

                                    FirebaseUser newUser = task.getResult().getUser();

                                    String id = newUser.getUid();

                                    User user = new User();

                                    user.setId(id);
                                    user.setUsername(username);
                                    user.setEmail(email);
                                    user.setFirstName(firstName);
                                    user.setLastName(lastName);
                                    user.setAccountType(accountType);

                                    DatabaseReference newUserDb = accountsDatabase.child(id);

                                    newUserDb.setValue(user);

                                    Intent toWelcomeIntent = new Intent(SignupActivity.this, WelcomeActivity.class);
                                    toWelcomeIntent.putExtra(WelcomeActivity.USER, user);
                                    startActivity(toWelcomeIntent);

                                } else {

                                    Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    Log.w("firebaseDebug", "createUserWithEmail: Failed", task.getException());
    
                                    signupButton.setEnabled(true);
                                    backButton.setEnabled(true);

                                }

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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

}
