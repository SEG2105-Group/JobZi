package com.arom.jobzi.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends FragmentPagerAdapter {
	
	private final List<Fragment> fragmentList = new ArrayList<Fragment>();
	private final List<CharSequence> fragmentNamesList = new ArrayList<CharSequence>();
	
	public CustomPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public void addFragment(Fragment fragment, CharSequence name) {
		fragmentList.add(fragment);
		fragmentNamesList.add(name);
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return fragmentNamesList.get(position);
	}
	
	@Override
	public Fragment getItem(int i) {
		return fragmentList.get(i);
	}
	
	@Override
	public int getCount() {
		return fragmentList.size();
	}
}
