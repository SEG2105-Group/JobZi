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
import com.arom.jobzi.ServiceEditorActivity;
import com.arom.jobzi.adapater.CustomPagerAdapter;
import com.arom.jobzi.fragment.admin.AdminServicesFragment;
import com.arom.jobzi.fragment.admin.AdminUsersFragment;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;

public class AdminFragment extends Fragment implements AdminServicesFragment.ServiceItemListener, DeleteServiceDialogFragment.DeleteServiceListener {
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment_admin, null, false);
		
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		ViewPager viewPager = view.findViewById(R.id.viewPager);
		
		CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager());
		
		AdminUsersFragment adminUsersFragment = new AdminUsersFragment();
		AdminServicesFragment adminServicesFragment = AdminServicesFragment.newInstance(this);
		
		customPagerAdapter.addFragment(adminUsersFragment, getText(R.string.users_label));
		customPagerAdapter.addFragment(adminServicesFragment, getText(R.string.services_label));
		
		viewPager.setAdapter(customPagerAdapter);
		
	}
	
	@Override
	public void onDelete(final Service service) {
  
		DatabaseReference servicesDatabase = Util.getInstance().getServicesDatabase();
		servicesDatabase.child(service.getId()).removeValue();
		
		final DatabaseReference profilesDatabase = Util.getInstance().getProfilesDatabase();
		profilesDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                for(DataSnapshot profileSnapshot: dataSnapshot.getChildren()) {
    
                    try {
                        
                        ServiceProviderProfile serviceProviderProfile = profileSnapshot.getValue(ServiceProviderProfile.class);
    
                        List<Service> services = serviceProviderProfile.getServices();
                        
                        services.remove(service);
                        
                        profilesDatabase.child(profileSnapshot.getKey()).setValue(serviceProviderProfile);
                        
                    } catch(Exception ex) {
                        // Do nothing.
                    }
                    
                }
                
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        });
		
	}

	@Override
	public void onClick(Service service) {

        Intent toServiceEditorIntent = new Intent(this.getActivity(), ServiceEditorActivity.class);
        toServiceEditorIntent.putExtra(ServiceEditorActivity.SERVICE_BUNDLE_ARG, (Serializable) service);
        toServiceEditorIntent.putExtra(ServiceEditorActivity.NEW_SERVICE_MODE_BUNDLE_ARG, false);

        startActivity(toServiceEditorIntent);

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
    public void addService() {

        Service service = new Service();

        service.setName("");
        service.setRate(0);

        Intent toServiceEditorIntent = new Intent(getActivity(), ServiceEditorActivity.class);
        toServiceEditorIntent.putExtra(ServiceEditorActivity.SERVICE_BUNDLE_ARG, (Serializable) service);
        toServiceEditorIntent.putExtra(ServiceEditorActivity.NEW_SERVICE_MODE_BUNDLE_ARG, true);
        startActivity(toServiceEditorIntent);

    }

}
