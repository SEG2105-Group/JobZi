package com.arom.jobzi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arom.jobzi.R;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.service.ServiceArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class ServiceListFragment extends Fragment {

    private List<Service> serviceList;

	public ServiceListFragment() {
	
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		serviceList = new ArrayList<Service>();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_service_list, container, false);

        ListView serviceListView = view.findViewById(R.id.serviceListView);

        ServiceArrayAdapter serviceArrayAdapter = new ServiceArrayAdapter(getActivity(), serviceList);

        serviceListView.setAdapter(serviceArrayAdapter);

		return view;

	}
	
}
