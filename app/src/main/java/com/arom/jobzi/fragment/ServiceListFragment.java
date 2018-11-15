package com.arom.jobzi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arom.jobzi.R;
import com.arom.jobzi.ServiceEditorActivity;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.service.ServiceArrayAdapter;
import com.arom.jobzi.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ServiceListFragment extends Fragment {
	
	private List<Service> serviceList;
	
	private FloatingActionButton addServiceFloatingButton;

	private ServiceItemListener listener;
	
	public ServiceListFragment() {
	
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		serviceList = new ArrayList<Service>();
		
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_service_list, container, false);
		
		ListView serviceListView = view.findViewById(R.id.serviceListView);
		
		ServiceArrayAdapter serviceArrayAdapter = new ServiceArrayAdapter(getActivity(), serviceList);
		
		serviceListView.setAdapter(serviceArrayAdapter);
		
		serviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				Service service = serviceList.get(position);
				
				Intent toServiceEditorIntent = new Intent(ServiceListFragment.this.getActivity(), ServiceEditorActivity.class);
				toServiceEditorIntent.putExtra(ServiceEditorActivity.SERVICE_BUNDLE_ARG, service);
				toServiceEditorIntent.putExtra(ServiceEditorActivity.NEW_SERVICE_MODE_BUNDLE_ARG, false);
				
				ServiceListFragment.this.startActivity(toServiceEditorIntent);
				
			}
		});
		
		serviceListView.setLongClickable(true);
		serviceListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
				
				DeleteServiceDialogFragment deleteServiceDialogFragment = new DeleteServiceDialogFragment();
				
				Bundle bundle = new Bundle();
				bundle.putSerializable(DeleteServiceDialogFragment.SERVICE_BUNDLE_ARG, serviceList.get(i));
				
				deleteServiceDialogFragment.setArguments(bundle);
				
				deleteServiceDialogFragment.show(ServiceListFragment.this.getActivity().getSupportFragmentManager(), "");
				
				return true;
				
			}
		});
		
		Util.getInstance().addServiceListListener(serviceArrayAdapter, serviceList);
		
		addServiceFloatingButton = view.findViewById(R.id.addServiceFloatingButton);
		
		addServiceFloatingButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ServiceListFragment.this.addService();
			}
		});
		
		return view;
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		addServiceFloatingButton.setEnabled(true);
		
	}
	
	private void addService() {
		
		addServiceFloatingButton.setEnabled(false);
		
		Service service = new Service();
		
		service.setName("");
		service.setRate(0);
		
		Intent toServiceEditorIntent = new Intent(ServiceListFragment.this.getActivity(), ServiceEditorActivity.class);
		toServiceEditorIntent.putExtra(ServiceEditorActivity.SERVICE_BUNDLE_ARG, service);
		toServiceEditorIntent.putExtra(ServiceEditorActivity.NEW_SERVICE_MODE_BUNDLE_ARG, true);
		ServiceListFragment.this.startActivity(toServiceEditorIntent);
		
	}

	public interface ServiceItemListener {

	    void onClick(Service service);

    }

}
