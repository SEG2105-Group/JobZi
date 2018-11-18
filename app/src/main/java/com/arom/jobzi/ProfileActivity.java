package com.arom.jobzi;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arom.jobzi.fragment.ProfileFragment;
import com.arom.jobzi.user.SessionManager;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.UserProfileUtil;
import com.arom.jobzi.util.Util;

public class ProfileActivity extends AppCompatActivity {
    
    private ProfileFragment profileFragment;
    
    private EditText usernameEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private TextView emailTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        getSupportActionBar().setTitle(R.string.title_profile_editor);
        
        usernameEditText = findViewById(R.id.usernameEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailTextView = findViewById(R.id.emailDisplayTextView);
        
        final User user = SessionManager.getInstance().getUser();
        
        fillInUserFields(user);
        
        profileFragment = ProfileFragment.newInstance(user.getAccountType());
        
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.extraProfileInformationFragment, profileFragment).commit();
        
        final Button saveButton = findViewById(R.id.saveButton);
        final Button cancelButton = findViewById(R.id.cancelButton);
        
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                saveButton.setEnabled(false);
                cancelButton.setEnabled(false);
                
                if (updateUserInformation()) {
                    Util.getInstance().updateUser(user);
                    ProfileActivity.this.finish();
                } else {
                    saveButton.setEnabled(true);
                    cancelButton.setEnabled(true);
                }
                
            }
        });
        
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.finish();
            }
        });
        
    }
    
    private void fillInUserFields(User user) {
        
        usernameEditText.setText(user.getUsername());
        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        emailTextView.setText(user.getEmail());
        
    }
    
    /**
     * @return <code>true</code> if updating the user's information was successful and <code>false</code> otherwise.
     */
    private boolean updateUserInformation() {
        
        User user = SessionManager.getInstance().getUser();
        
        User unvalidatedUser = new User();
        unvalidatedUser.copyFrom(user);
        
        unvalidatedUser.setUsername(usernameEditText.getText().toString());
        unvalidatedUser.setFirstName(firstNameEditText.getText().toString());
        unvalidatedUser.setLastName(lastNameEditText.getText().toString());
        
        unvalidatedUser.setUserProfile(profileFragment.getUserProfile());
        
        if (UserProfileUtil.getInstance().validateUserInfoWithError(this, unvalidatedUser)) {
            
            user.copyFrom(unvalidatedUser);

//            if(user.getUserProfile() != null) {
//                user.getUserProfile().copyFrom(unvalidatedUser.getUserProfile());
//            }
            
            user.setUserProfile(unvalidatedUser.getUserProfile());
            
            return true;
            
        }
        
        return false;
        
    }
    
}
