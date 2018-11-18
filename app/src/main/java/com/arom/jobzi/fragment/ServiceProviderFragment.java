package com.arom.jobzi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arom.jobzi.R;
import com.arom.jobzi.ServiceSelectorActivity;
import com.arom.jobzi.fragment.serviceprovider.ServiceProviderServicesFragment;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.service.AvailableTimeSlot;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.user.SessionManager;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderFragment extends Fragment implements ServiceProviderServicesFragment.ServiceItemListener, DeleteServiceDialogFragment.DeleteServiceListener {
    
    private ServiceProviderProfile profile;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_service_provider, container, false);
        
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager());
        
        AvailableTimeSlotsListFragment availableTimeSlotsListFragment = new AvailableTimeSlotsListFragment();
        ServiceProviderServicesFragment serviceListFragment = ServiceProviderServicesFragment.newInstance(this);
        
        customPagerAdapter.addFragment(availableTimeSlotsListFragment, getText(R.string.availabilities_label));
        customPagerAdapter.addFragment(serviceListFragment, getText(R.string.services_label));
        
        viewPager.setAdapter(customPagerAdapter);
        
        return view;
        
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        profile = (ServiceProviderProfile) SessionManager.getInstance().getUser().getUserProfile();
        
        if (profile.getServices() == null) {
            profile.setServices(new ArrayList<Service>());
        }
        
        if (profile.getAvailabilities() == null) {
            profile.setAvailabilities(new ArrayList<AvailableTimeSlot>());
        }
        
    }
    
    @Override
    public void onDelete(Service service) {
        
        User user = SessionManager.getInstance().getUser();
        
        ServiceProviderProfile profile = (ServiceProviderProfile) user.getUserProfile();
        
        List<Service> services = profile.getServices();
        
        if (services == null) {
            return;
        }
        
        services.remove(service);
    
        Util.getInstance().updateUser(user);
        
    }
    
    @Override
    public void requestRemoveService(Service service) {
    
        DeleteServiceDialogFragment deleteServiceDialogFragment = new DeleteServiceDialogFragment();
    
        Bundle bundle = new Bundle();
        bundle.putSerializable(DeleteServiceDialogFragment.LISTENER_BUNDLE_ARG, this);
        bundle.putSerializable(DeleteServiceDialogFragment.SERVICE_BUNDLE_ARG, service);
    
        deleteServiceDialogFragment.setArguments(bundle);
    
        deleteServiceDialogFragment.show(this.getActivity().getSupportFragmentManager(), "");
    
    }
    
    @Override
    public void requestAddService() {
        
        Intent toServiceSelectorIntent = new Intent(getActivity(), ServiceSelectorActivity.class);
        
        startActivity(toServiceSelectorIntent);
        
    }
    
}
