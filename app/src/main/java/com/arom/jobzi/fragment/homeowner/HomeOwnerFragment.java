package com.arom.jobzi.fragment.homeowner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arom.jobzi.HomeOwnerSearchActivity;
import com.arom.jobzi.R;

public class HomeOwnerFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home_owner, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		FloatingActionButton searchForProviderActionButton = view.findViewById(R.id.searchForServiceProviderButton);
		
		searchForProviderActionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent toHomeOwnerSearchIntent = new Intent(HomeOwnerFragment.this.getContext(), HomeOwnerSearchActivity.class);
				HomeOwnerFragment.this.getActivity().startActivity(toHomeOwnerSearchIntent);
			}
		});
		
	}
}
