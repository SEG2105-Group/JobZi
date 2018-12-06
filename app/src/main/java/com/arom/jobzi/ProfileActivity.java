package com.arom.jobzi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arom.jobzi.fragment.ProfileFragment;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.UserProfileUtil;
import com.arom.jobzi.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    
    private ProfileFragment profileFragment;
    
    private EditText usernameEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private TextView emailTextView;
    
    private Button saveButton;
    private Button cancelButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        getSupportActionBar().setTitle(R.string.title_profile_editor);
        
        usernameEditText = findViewById(R.id.usernameEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailTextView = findViewById(R.id.emailDisplayTextView);
    
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
    
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userDatabase = Util.getInstance().getAccountsDatabase().child(firebaseUser.getUid());
        
        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setupUserInterface(dataSnapshot.getValue(User.class));
            }
    
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
        
            }
        });
        
    }
    
    private void setupUserInterface(User user) {
        
        usernameEditText.setText(user.getUsername());
        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        emailTextView.setText(user.getEmail());
    
        profileFragment = ProfileFragment.newInstance(user.getAccountType());
    
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.extraProfileInformationFragment, profileFragment).commit();
    
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            
                saveButton.setEnabled(false);
                cancelButton.setEnabled(false);
            
                updateUserInformation();
                
            }
        });
    
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.finish();
            }
        });
    
    }
    
    /**
     * @return <code>true</code> if updating the user's information was successful and <code>false</code> otherwise.
     */
    private void updateUserInformation() {
        
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference userDatabase = Util.getInstance().getAccountsDatabase().child(firebaseUser.getUid());
        
        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
    
                User user = dataSnapshot.getValue(User.class);
    
                user.setUsername(usernameEditText.getText().toString());
                user.setFirstName(firstNameEditText.getText().toString());
                user.setLastName(lastNameEditText.getText().toString());
                
                user.setUserProfile(profileFragment.getUserProfile());
                
                if (UserProfileUtil.getInstance().validateUserInfoWithError(ProfileActivity.this, user, null)) {
                    
                    userDatabase.setValue(user);

                    DatabaseReference profilesDatabase = Util.getInstance().getProfilesDatabase();
                    profilesDatabase.child(user.getId()).setValue(user.getUserProfile());

                    ProfileActivity.this.finish();
                    
                } else {
                    
                    saveButton.setEnabled(true);
                    cancelButton.setEnabled(true);
                    
                }
                
            }
    
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
        
            }
        });
        
    }
    
}
