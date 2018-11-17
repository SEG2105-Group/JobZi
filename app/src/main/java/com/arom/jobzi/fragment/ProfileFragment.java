package com.arom.jobzi.fragment;

import android.os.Bundle;
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
import com.arom.jobzi.user.SessionManager;
import com.arom.jobzi.user.User;

public class ProfileFragment extends Fragment {

    private static final String ACCOUNT_TYPE_BUNDLE_ARG = "account_type";

    private AccountType accountType;

    private EditText addressEditText;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        User user = SessionManager.getInstance().getUser();
        AccountType accountType = user.getAccountType();

        switch (accountType) {
            case ADMIN:
                return inflater.inflate(R.layout.admin_profile, container, false);
            case SERVICE_PROVIDER:
                View view = inflater.inflate(R.layout.service_provider_profile, container, false);

                if (user.getUserProfile() != null) {
                    fillInServiceProviderFields(view);
                }

                return view;
            case HOME_OWNER:
                return inflater.inflate(R.layout.home_owner_profile, container, false);
            default:
                return null;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void fillInServiceProviderFields(View view) {

        addressEditText = view.findViewById(R.id.addressEditText);
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        licensedSwitch = view.findViewById(R.id.licensedSwitch);

        User user = SessionManager.getInstance().getUser();

        ServiceProviderProfile serviceProviderProfile = (ServiceProviderProfile) user.getUserProfile();

        addressEditText.setText(serviceProviderProfile.getAddress());
        phoneNumberEditText.setText(serviceProviderProfile.getPhoneNumber());
        descriptionEditText.setText(serviceProviderProfile.getDescription());
        licensedSwitch.setChecked(serviceProviderProfile.isLicensed());

    }

    public UserProfile getUserProfile() {

        User user = SessionManager.getInstance().getUser();
        UserProfile profile;

        switch (accountType) {
            case SERVICE_PROVIDER:
                profile = new ServiceProviderProfile();
                profile.copyFrom(user.getUserProfile());
                updateServiceProviderProfile((ServiceProviderProfile) profile);
                break;
            default:
                profile = null;
                break;
        }

        return profile;

    }

    private void updateServiceProviderProfile(ServiceProviderProfile profile) {

        profile.setAddress(addressEditText.getText().toString());
        profile.setPhoneNumber(phoneNumberEditText.getText().toString());
        profile.setDescription(descriptionEditText.getText().toString());
        profile.setLicensed(licensedSwitch.isChecked());

    }

}
