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
import com.arom.jobzi.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServiceProviderServicesFragment extends Fragment {
    
    private static final String LISTENER_BUNDLE_ARG = "listener";
    
    private List<Service> services;
    
    private ServiceArrayAdapter serviceArrayAdapter;
    
    private FloatingActionButton addServiceFloatingActionButton;
    
    private ServiceItemListener listener;
    
    private ChildEventListener serviceDeletionListener;
    
    public ServiceProviderServicesFragment() {
    }
    
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
        
        return inflater.inflate(R.layout.fragment_service_provider_services, container, false);
        
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    
        ListView serviceListView = view.findViewById(R.id.serviceListView);
    
        serviceArrayAdapter = new ServiceArrayAdapter(getActivity(), services);
        serviceListView.setAdapter(serviceArrayAdapter);
    
        setupServiceList();
    
        serviceListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            
                listener.requestRemoveService(services.get(i));
            
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
    
    
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        services = new ArrayList<Service>();
    
        listener = (ServiceItemListener) getArguments().getSerializable(LISTENER_BUNDLE_ARG);
        
    }
    
    private void setupServiceList() {
        
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        
        DatabaseReference userProfileDatabase = Util.getInstance().getProfilesDatabase().child(user.getUid());
        userProfileDatabase.addValueEventListener(new ValueEventListener() {
            
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                services.clear();
                
                List<Service> updatedServices = dataSnapshot.getValue(ServiceProviderProfile.class).getServices();
                
                if (updatedServices != null) {
                    services.addAll(updatedServices);
                }
                
                serviceArrayAdapter.notifyDataSetChanged();
                
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
            
        });
    
        DatabaseReference servicesDatabase = Util.getInstance().getServicesDatabase();
        serviceDeletionListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            
            }
        
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            
            }
        
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            
                listener.onServiceDeleted(dataSnapshot.getValue(Service.class));
            
            }
        
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            
            }
        
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        };
        
        servicesDatabase.addChildEventListener(serviceDeletionListener);
        
    }
    
    @Override
    public void onResume() {
        super.onResume();
        addServiceFloatingActionButton.setEnabled(true);
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    
        DatabaseReference servicesDatabase = Util.getInstance().getServicesDatabase();
        servicesDatabase.removeEventListener(serviceDeletionListener);
    
    }
    
    public interface ServiceItemListener extends Serializable {
        
        void requestRemoveService(Service service);
        
        void requestAddService();
    
        /**
         * This is called when a change in the database containing all the services has been detected and the change is the deletion of a service.
         * @param service The <code>Service</code> object that was deleted.
         */
        void onServiceDeleted(Service service);
        
    }
    
}
