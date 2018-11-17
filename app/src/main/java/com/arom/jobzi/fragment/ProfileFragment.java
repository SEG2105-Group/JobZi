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
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.Util;

public class ProfileFragment extends Fragment {

    private static final String ACCOUNT_TYPE_BUNDLE_ARG = "account_type";

    private User user;
    private AccountType accountType;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();

        Bundle args = new Bundle();
        args.putSerializable(Util.USER_BUNDLE_ARG, user);
        args.putSerializable(ACCOUNT_TYPE_BUNDLE_ARG, user.getAccountType());
        fragment.setArguments(args);

        return fragment;
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
            user = (User) args.getSerializable(Util.USER_BUNDLE_ARG);
            accountType = (AccountType) args.getSerializable(ACCOUNT_TYPE_BUNDLE_ARG);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        switch (accountType) {
            case SERVICE_PROVIDER:
                View view = inflater.inflate(R.layout.fragment_service_provider_profile, container, false);

                if(user != null) {
                    fillInServiceProviderFields(view);
                }

                return view;
            default:
                return inflater.inflate(0, container, false);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void fillInServiceProviderFields(View view) {

        EditText addressEditText = view.findViewById(R.id.addressEditText);
        EditText phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
        EditText descriptionEditText = view.findViewById(R.id.descriptionEditText);
        Switch licensedSwitch = view.findViewById(R.id.licensedSwitch);

        ServiceProviderProfile serviceProviderProfile = (ServiceProviderProfile) user.getUserProfile();

        addressEditText.setText(serviceProviderProfile.getAddress());
        phoneNumberEditText.setText(serviceProviderProfile.getPhoneNumber());
        descriptionEditText.setText(serviceProviderProfile.getDescription());
        licensedSwitch.setChecked(serviceProviderProfile.isLicensed());

    }

}
