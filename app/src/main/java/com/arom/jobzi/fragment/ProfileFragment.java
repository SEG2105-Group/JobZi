package com.arom.jobzi.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.arom.jobzi.R;
import com.arom.jobzi.account.AccountType;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.profile.UserProfile;
import com.arom.jobzi.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    
    private static final String ACCOUNT_TYPE_BUNDLE_ARG = "account_type";
    
    private AccountType accountType;
    
    private UserProfile userProfile;
    
    private EditText addressEditText;
    private EditText companyNameEditText;
    private EditText phoneNumberEditText;
    private EditText descriptionEditText;
    private Switch licensedSwitch;
    
    public ProfileFragment() {
    
    }
    
    public static ProfileFragment newInstance(AccountType accountType) {
        ProfileFragment fragment = new ProfileFragment();
        
        Bundle args = new Bundle();
        args.putSerializable(ACCOUNT_TYPE_BUNDLE_ARG, accountType);
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle args = getArguments();
        
        if (args != null) {
            accountType = (AccountType) args.getSerializable(ACCOUNT_TYPE_BUNDLE_ARG);
        }
        
    }
    
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        
        switch (accountType) {
            case ADMIN:
                return inflater.inflate(R.layout.admin_profile, container, false);
            case SERVICE_PROVIDER:
                
                View view = inflater.inflate(R.layout.service_provider_profile, container, false);
    
                addressEditText = view.findViewById(R.id.addressEditText);
                companyNameEditText = view.findViewById(R.id.companyNameEditText);
                phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
                descriptionEditText = view.findViewById(R.id.descriptionEditText);
                licensedSwitch = view.findViewById(R.id.licensedSwitch);
    
                if(firebaseUser != null) {
                    fillInServiceProviderFields(view);
                }
                
                return view;
            
            case HOME_OWNER:
                return inflater.inflate(R.layout.home_owner_profile, container, false);
            default:
                return null;
        }
    }
    
    private void fillInServiceProviderFields(View view) {
        
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference serviceProviderProfileDatabase = Util.getInstance().getProfilesDatabase().child(firebaseUser.getUid());
        
        serviceProviderProfileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
    
                ServiceProviderProfile serviceProviderProfile = dataSnapshot.getValue(ServiceProviderProfile.class);
                
                addressEditText.setText(serviceProviderProfile.getAddress());
                companyNameEditText.setText(serviceProviderProfile.getCompanyName());
                phoneNumberEditText.setText(serviceProviderProfile.getPhoneNumber());
                descriptionEditText.setText(serviceProviderProfile.getDescription());
                licensedSwitch.setChecked(serviceProviderProfile.isLicensed());
    
                userProfile = serviceProviderProfile;
                
            }
    
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
        
            }
        });
        
    }
    
    public UserProfile getUserProfile() {
        
        switch (accountType) {
            case SERVICE_PROVIDER:
                updateServiceProviderProfile((ServiceProviderProfile) userProfile);
                break;
            default:
                return null;
        }
        
        return userProfile;
        
    }
    
    private void updateServiceProviderProfile(ServiceProviderProfile profile) {
        
        profile.setAddress(addressEditText.getText().toString());
        profile.setCompanyName(companyNameEditText.getText().toString());
        profile.setPhoneNumber(phoneNumberEditText.getText().toString());
        profile.setDescription(descriptionEditText.getText().toString());
        profile.setLicensed(licensedSwitch.isChecked());
        
    }
    
}
