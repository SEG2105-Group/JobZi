package com.arom.jobzi.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arom.jobzi.R;

public class AdminFragment extends Fragment {
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_admin, null, false);
		
		ViewPager viewPager = view.findViewById(R.id.viewPager);
		
		CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager());
		
		UserListFragment userListFragment = new UserListFragment();
		ServiceListFragment serviceListFragment = new ServiceListFragment();
		
		customPagerAdapter.addFragment(userListFragment, getText(R.string.users_label));
		customPagerAdapter.addFragment(serviceListFragment, getText(R.string.services_label));
		
		viewPager.setAdapter(customPagerAdapter);
		
		return view;
		
	}
}
