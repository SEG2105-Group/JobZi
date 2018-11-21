package com.arom.jobzi.fragment.serviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.arom.jobzi.AvailableTimeSlotEditorActivity;
import com.arom.jobzi.R;
import com.arom.jobzi.adapater.AvailabilitiesExpandableListAdapter;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ServiceProviderAvailabilitiesFragment extends Fragment implements AvailabilitiesExpandableListAdapter.OnAddClickListener {
    
    private ExpandableListView availabilitiesListView;
    
    public static final int ADD_AVAILABILITY_REQUEST = 0;
    public static final int EDIT_AVAILABILITY_REQUEST = 1;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_available_time_slots_list, container, false);
        
        availabilitiesListView = view.findViewById(R.id.availabilitiesListView);
        
        final AvailabilitiesExpandableListAdapter adapter = new AvailabilitiesExpandableListAdapter(getContext(), this);
        availabilitiesListView.setAdapter(adapter);
        
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference profileDatabase = Util.getInstance().getProfilesDatabase().child(user.getUid());
        
        profileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                ServiceProviderProfile profile = dataSnapshot.getValue(ServiceProviderProfile.class);
                adapter.updateAvailabilities(profile);
                adapter.notifyDataSetChanged();
                
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        });
        
        profileDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                ServiceProviderProfile profile = dataSnapshot.getValue(ServiceProviderProfile.class);
                adapter.updateAvailabilities(profile);
                adapter.notifyDataSetChanged();
                
            }
    
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
        
            }
        });
        
        return view;
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onAdd(int day) {
        
        Intent toTimeSlotEditorIntent = new Intent(this.getActivity(), AvailableTimeSlotEditorActivity.class);
        
        Bundle bundle = new Bundle();
        bundle.putInt(AvailableTimeSlotEditorActivity.DAY_BUNDLE_ARG, day);
        toTimeSlotEditorIntent.putExtras(bundle);
        
        startActivityForResult(toTimeSlotEditorIntent, ADD_AVAILABILITY_REQUEST);
        
    }
    
    @Override
    public void onEdit(int day, Availability availability) {
        
        Intent toTimeSlotEditorIntent = new Intent(this.getActivity(), AvailableTimeSlotEditorActivity.class);
        
        Bundle bundle = new Bundle();
        bundle.putInt(AvailableTimeSlotEditorActivity.DAY_BUNDLE_ARG, day);
        bundle.putSerializable(AvailableTimeSlotEditorActivity.AVAILABILITY_BUNDLE_ARG, availability);
        toTimeSlotEditorIntent.putExtras(bundle);
        
        startActivityForResult(toTimeSlotEditorIntent, EDIT_AVAILABILITY_REQUEST);
        
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        
        switch (resultCode) {
            case AvailableTimeSlotEditorActivity.AVAILABALITY_ADDED_RESULT:
                
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final DatabaseReference profileDatabase = Util.getInstance().getProfilesDatabase().child(user.getUid());
                profileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Bundle bundle = data.getExtras();
                        
                        ServiceProviderProfile profile = dataSnapshot.getValue(ServiceProviderProfile.class);
                        
                        Availability availability = (Availability) bundle.getSerializable(AvailableTimeSlotEditorActivity.AVAILABILITY_BUNDLE_ARG);
                        int day = bundle.getInt(AvailableTimeSlotEditorActivity.DAY_BUNDLE_ARG);
                        
                        profile.getAvailabilities()[day].add(availability);
                        profileDatabase.setValue(profile);
                        
                    }
                    
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    
                    }
                });
                
                
                break;
        }
        
    }
}
