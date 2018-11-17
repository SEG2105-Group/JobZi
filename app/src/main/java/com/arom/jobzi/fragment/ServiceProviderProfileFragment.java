package com.arom.jobzi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arom.jobzi.R;

public class ServiceProviderProfileFragment extends Fragment {

    public ServiceProviderProfileFragment() {

    }

    public static ServiceProviderProfileFragment newInstance() {
        ServiceProviderProfileFragment fragment = new ServiceProviderProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service_provider_profile, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
