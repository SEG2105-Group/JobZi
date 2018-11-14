package com.arom.jobzi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arom.jobzi.R;

public class ServiceProviderFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_service_provider, container, false);

        ViewPager viewPager = view.findViewById(R.id.viewPager);

        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager());

        AvailableTimeSlotsListFragment availableTimeSlotsListFragment = new AvailableTimeSlotsListFragment();
        ServiceListFragment serviceListFragment = new ServiceListFragment();

        customPagerAdapter.addFragment(availableTimeSlotsListFragment, getText(R.string.availabilities_label));
        customPagerAdapter.addFragment(serviceListFragment, getText(R.string.services_label));

        viewPager.setAdapter(customPagerAdapter);

        return view;

    }
	
}
