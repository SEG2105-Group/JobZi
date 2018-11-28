package com.arom.jobzi.fragment.serviceprovider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arom.jobzi.R;

public class ServiceProviderBookingsFragment extends Fragment {

    private static final String LISTENER_BUNDLE_ARG = "listener";

    public static ServiceProviderBookingsFragment newInstance(ServiceProviderServicesFragment.ServiceItemListener listener){
        ServiceProviderBookingsFragment fragment = new ServiceProviderBookingsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(LISTENER_BUNDLE_ARG, listener);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_service_provider_bookings, container, false);

    }

}
