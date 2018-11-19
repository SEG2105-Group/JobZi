package com.arom.jobzi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.arom.jobzi.account.AccountType;
import com.arom.jobzi.fragment.ProfileFragment;
import com.arom.jobzi.profile.UserProfile;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.UserProfileUtil;
import com.arom.jobzi.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {
    
    public static final String EXTRA_PROFILE_INFO_TAG = "ExtraProfileInfo";
    
    private DatabaseReference accountsDatabase;
    
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Spinner accountTypesSpinner;
    private Button signupButton;
    private Button backButton;
    
    private boolean adminExists;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    
        accountsDatabase = Util.getInstance().getAccountsDatabase();
        
        accountsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    
                    User user = userSnapshot.getValue(User.class);
                    
                    if (user.getAccountType().equals(AccountType.ADMIN)) {
                        adminExists = true;
                        break;
                    }
                    
                }
                
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        });
        
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        accountTypesSpinner = findViewById(R.id.accountTypesSpinner);
        signupButton = findViewById(R.id.signupButton);
        backButton = findViewById(R.id.backButton);
        
        ArrayAdapter<AccountType> spinnerArrayAdapter = new ArrayAdapter<AccountType>(this, android.R.layout.simple_spinner_dropdown_item, AccountType.values()) {
            @Override
            public boolean isEnabled(int position) {
                
                if (getItem(position).equals(AccountType.ADMIN) && adminExists) {
                    return false;
                }
                
                return super.isEnabled(position);
                
            }
            
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                
                View view = super.getDropDownView(position, convertView, parent);
                
                TextView textView = (TextView) view;
                
                if (getItem(position).equals(AccountType.ADMIN) && adminExists) {
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
        
        accountTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                
                FragmentManager fragmentManager = SignupActivity.this.getSupportFragmentManager();
                
                Fragment fragment = fragmentManager.findFragmentByTag(EXTRA_PROFILE_INFO_TAG);
                
                switch ((AccountType) parent.getItemAtPosition(position)) {
                    case SERVICE_PROVIDER:
                        fragment = ProfileFragment.newInstance(AccountType.SERVICE_PROVIDER);
                        break;
                    default:
                        
                        if (fragment != null) {
                            fragmentManager.beginTransaction().remove(fragment).commit();
                        }
                        
                        return;
                    
                }
                
                fragmentManager.beginTransaction().add(R.id.extraProfileInformationFragment, fragment, EXTRA_PROFILE_INFO_TAG).commit();
                
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
        
        signupButton.setEnabled(true);
        backButton.setEnabled(true);
        
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                signupButton.setEnabled(false);
                backButton.setEnabled(false);
                
                onSignupRequest();
                
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
    
    private void onSignupRequest() {
        
        accountsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                AccountType accountType = (AccountType) accountTypesSpinner.getSelectedItem();
                
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                
                final User user = UserProfileUtil.getInstance().createUser(username, email, firstName, lastName, accountType);
                
                Fragment profileFragment = SignupActivity.this.getSupportFragmentManager().findFragmentByTag(EXTRA_PROFILE_INFO_TAG);
                
                UserProfile userProfile = null;
                
                if (profileFragment != null) {
                    
                    View profileFragmentView = profileFragment.getView();
                    
                    switch (accountType) {
                        case SERVICE_PROVIDER:
                            
                            EditText addressEditText = profileFragmentView.findViewById(R.id.addressEditText);
                            EditText phoneNumberEditText = profileFragmentView.findViewById(R.id.phoneNumberEditText);
                            EditText descriptionEditText = profileFragmentView.findViewById(R.id.descriptionEditText);
                            Switch licensedSwitch = profileFragmentView.findViewById(R.id.licensedSwitch);
                            
                            String address = addressEditText.getText().toString();
                            String phoneNumber = phoneNumberEditText.getText().toString();
                            String description = descriptionEditText.getText().toString();
                            boolean licensed = licensedSwitch.isChecked();
                            
                            userProfile = UserProfileUtil.getInstance().createServiceProviderProfile(address, phoneNumber, description, licensed);
                            break;
                        
                    }
                }
                
                user.setUserProfile(userProfile);
                
                // Validates everything about the user profile.
                if (!UserProfileUtil.getInstance().validateUserInfoWithError(SignupActivity.this, user)) {
                    
                    signupButton.setEnabled(true);
                    backButton.setEnabled(true);
                    
                    return;
                }
    
                FirebaseAuth auth = FirebaseAuth.getInstance();
                
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        
                        if (task.isSuccessful()) {
                            
                            FirebaseUser newUser = task.getResult().getUser();
                            
                            String id = newUser.getUid();
                            
                            user.setId(id);
                            
                            accountsDatabase.child(id).setValue(user);
                            
                            Intent toLandingIntent = new Intent(SignupActivity.this, LandingActivity.class);
                            SignupActivity.this.startActivity(toLandingIntent);
                            SignupActivity.this.finish();
                            
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
    
}
