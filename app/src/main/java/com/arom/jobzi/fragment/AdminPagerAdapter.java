package com.arom.jobzi.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminPagerAdapter extends FragmentPagerAdapter {
	
	private static final List<Fragment> FRAGMENT_LIST = new ArrayList<Fragment>();
	private static final List<CharSequence> FRAGMENT_NAME_LIST = new ArrayList<CharSequence>();
	
	public AdminPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public void addFragment(Fragment fragment, CharSequence name) {
		FRAGMENT_LIST.add(fragment);
		FRAGMENT_NAME_LIST.add(name);
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return FRAGMENT_NAME_LIST.get(position);
	}
	
	@Override
	public Fragment getItem(int i) {
		return FRAGMENT_LIST.get(i);
	}
	
	@Override
	public int getCount() {
		return FRAGMENT_LIST.size();
	}
}
