package com.arom.jobzi.fragment.serviceprovider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arom.jobzi.R;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.service.ServiceArrayAdapter;
import com.arom.jobzi.user.SessionManager;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.Util;

import java.io.Serializable;

public class ServiceProviderServicesFragment extends Fragment {
    
    public ServiceProviderServicesFragment() {
    }
    
    private static final String LISTENER_BUNDLE_ARG = "listener";
    
    private User user;
    private ServiceProviderProfile profile;
    
    private FloatingActionButton addServiceFloatingActionButton;
    
    private ServiceItemListener listener;
    
    public static ServiceProviderServicesFragment newInstance(ServiceItemListener listener) {
        
        ServiceProviderServicesFragment fragment = new ServiceProviderServicesFragment();
        
        Bundle bundle = new Bundle();
        bundle.putSerializable(LISTENER_BUNDLE_ARG, listener);
        
        fragment.setArguments(bundle);
        
        return fragment;
        
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_service_provider_services, container, false);
        
        final ListView serviceListView = view.findViewById(R.id.serviceListView);
        
        ServiceArrayAdapter serviceArrayAdapter = new ServiceArrayAdapter(getActivity(), profile.getServices());
        serviceListView.setAdapter(serviceArrayAdapter);
        
        Util.getInstance().addProfileServiceListListener(serviceArrayAdapter, user);
        
        serviceListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                
                listener.requestRemoveService(profile.getServices().get(i));
                
                return true;
                
            }
        });
        
        addServiceFloatingActionButton = view.findViewById(R.id.addServiceFloatingButton);
        addServiceFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addServiceFloatingActionButton.setEnabled(false);
                listener.requestAddService();
            }
        });
        
        return view;
        
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        user = SessionManager.getInstance().getUser();
        profile = (ServiceProviderProfile) user.getUserProfile();
        
        listener = (ServiceItemListener) getArguments().getSerializable(LISTENER_BUNDLE_ARG);
        
    }
    
    @Override
    public void onResume() {
        super.onResume();
        addServiceFloatingActionButton.setEnabled(true);
    }
    
    public interface ServiceItemListener extends Serializable {
        
        void requestRemoveService(Service service);
        
        void requestAddService();
        
    }
    
}
