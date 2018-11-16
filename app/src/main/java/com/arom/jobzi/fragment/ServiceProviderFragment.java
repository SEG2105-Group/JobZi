package com.arom.jobzi.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arom.jobzi.R;
import com.arom.jobzi.service.Service;

public class ServiceProviderFragment extends Fragment implements ServiceListFragment.ServiceItemListener, DeleteServiceDialogFragment.DeleteServiceListener {
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
	    View view = inflater.inflate(R.layout.fragment_service_provider, container, false);

        ViewPager viewPager = view.findViewById(R.id.viewPager);

        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager());

        AvailableTimeSlotsListFragment availableTimeSlotsListFragment = new AvailableTimeSlotsListFragment();
        ServiceListFragment serviceListFragment = ServiceListFragment.newInstance(this);

        customPagerAdapter.addFragment(availableTimeSlotsListFragment, getText(R.string.availabilities_label));
        customPagerAdapter.addFragment(serviceListFragment, getText(R.string.services_label));

        viewPager.setAdapter(customPagerAdapter);

        return view;

    }

    @Override
    public void onClick(Service service) {
        // Do nothing on click. Can't edit service.
    }

    @Override
    public void onLongClick(Service service) {

        DeleteServiceDialogFragment deleteServiceDialogFragment = new DeleteServiceDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(DeleteServiceDialogFragment.LISTENER_BUNDLE_ARG, this);
        bundle.putSerializable(DeleteServiceDialogFragment.SERVICE_BUNDLE_ARG, service);

        deleteServiceDialogFragment.setArguments(bundle);

        deleteServiceDialogFragment.show(this.getActivity().getSupportFragmentManager(), "");

    }

    @Override
    public void onDelete(Service service) {
    
    }

    @Override
    public void addService() {
        // TODO: Add service for service provider.
    }
}
