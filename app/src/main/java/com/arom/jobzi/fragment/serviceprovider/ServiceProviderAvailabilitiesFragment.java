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
import com.arom.jobzi.fragment.DeleteAvailabilityDialogFragment;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceProviderAvailabilitiesFragment extends Fragment implements AvailabilitiesExpandableListAdapter.OnAvailabilityListener {
    
    public static final int ADD_AVAILABILITY_REQUEST = 0;
    public static final int EDIT_AVAILABILITY_REQUEST = 1;
    
    private ExpandableListView availabilitiesListView;
    private AvailabilitiesExpandableListAdapter availabilitiesAdapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_available_time_slots_list, container, false);
        
        availabilitiesListView = view.findViewById(R.id.availabilitiesListView);
        
        availabilitiesAdapter = new AvailabilitiesExpandableListAdapter(getContext(), this);
        availabilitiesListView.setAdapter(availabilitiesAdapter);
        
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference profileDatabase = Util.getInstance().getProfilesDatabase().child(user.getUid());
        
        profileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                ServiceProviderProfile profile = dataSnapshot.getValue(ServiceProviderProfile.class);
                availabilitiesAdapter.updateAvailabilities(profile);
                
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        });
        
        profileDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                ServiceProviderProfile profile = dataSnapshot.getValue(ServiceProviderProfile.class);
                availabilitiesAdapter.updateAvailabilities(profile);
                
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
    public void onAdd(String day) {
        
        List<Availability> availabilities = availabilitiesAdapter.getAvailabilities(day);
        
        Intent toTimeSlotEditorIntent = new Intent(this.getActivity(), AvailableTimeSlotEditorActivity.class);
        
        Bundle bundle = new Bundle();
        bundle.putString(AvailableTimeSlotEditorActivity.DAY_TO_EDIT_BUNDLE_ARG, day);
        bundle.putSerializable(AvailableTimeSlotEditorActivity.OTHER_AVAILABILITIES_BUNDLE_ARG, availabilities.toArray(new Availability[availabilities.size()]));
        toTimeSlotEditorIntent.putExtras(bundle);
        
        startActivityForResult(toTimeSlotEditorIntent, ADD_AVAILABILITY_REQUEST);
        
    }
    
    @Override
    public void onEdit(String day, Availability availability, int index) {
        
        List<Availability> availabilities = availabilitiesAdapter.getAvailabilities(day);
        List<Availability> otherAvailabilities = new ArrayList<Availability>(availabilities);
        
        otherAvailabilities.remove(index);
        
        Intent toTimeSlotEditorIntent = new Intent(this.getActivity(), AvailableTimeSlotEditorActivity.class);
        
        Bundle bundle = new Bundle();
        bundle.putString(AvailableTimeSlotEditorActivity.DAY_TO_EDIT_BUNDLE_ARG, day);
        bundle.putInt(AvailableTimeSlotEditorActivity.AVAILABILITY_INDEX_BUNDLE_ARG, index);
        bundle.putSerializable(AvailableTimeSlotEditorActivity.AVAILABILITY_BUNDLE_ARG, availability);
        bundle.putSerializable(AvailableTimeSlotEditorActivity.OTHER_AVAILABILITIES_BUNDLE_ARG, otherAvailabilities.toArray(new Availability[otherAvailabilities.size()]));
        
        toTimeSlotEditorIntent.putExtras(bundle);
        
        startActivityForResult(toTimeSlotEditorIntent, EDIT_AVAILABILITY_REQUEST);
        
    }
    
    @Override
    public void onDeleteAvailability(final String day, final int index) {
        
        final DeleteAvailabilityDialogFragment deleteAvailabilityDialogFragment = new DeleteAvailabilityDialogFragment();
        
        Bundle bundle = new Bundle();
        bundle.putSerializable(DeleteAvailabilityDialogFragment.LISTENER_BUNDLE_ARG, new DeleteAvailabilityDialogFragment.DeleteAvailabilityListener() {
            @Override
            public void onDelete(final String day, final int index) {
                
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final DatabaseReference userProfileDatabase = Util.getInstance().getProfilesDatabase().child(user.getUid());
                
                userProfileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        
                        ServiceProviderProfile profile = dataSnapshot.getValue(ServiceProviderProfile.class);
                        
                        profile.getAvailabilities().get(day).remove(index);
                        
                        userProfileDatabase.setValue(profile);
                        
                        deleteAvailabilityDialogFragment.dismiss();
                        
                    }
                    
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    
                    }
                });
                
            }
        });
        bundle.putString(DeleteAvailabilityDialogFragment.DAY_BUNDLE_ARG, day);
        bundle.putInt(DeleteAvailabilityDialogFragment.INDEX_BUNDLE_ARG, index);
        
        deleteAvailabilityDialogFragment.setArguments(bundle);
        
        deleteAvailabilityDialogFragment.show(getActivity().getSupportFragmentManager(), "");
        
    }
    
    @Override
    public void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        
        switch (resultCode) {
            case AvailableTimeSlotEditorActivity.AVAILABILITY_SAVED_RESULT:
                
                final Availability availability = (Availability) data.getSerializableExtra(AvailableTimeSlotEditorActivity.AVAILABILITY_BUNDLE_ARG);
                final String day = data.getStringExtra(AvailableTimeSlotEditorActivity.DAY_TO_EDIT_BUNDLE_ARG);
                
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final DatabaseReference profileDatabase = Util.getInstance().getProfilesDatabase().child(user.getUid());
                profileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        
                        ServiceProviderProfile profile = dataSnapshot.getValue(ServiceProviderProfile.class);
                        
                        HashMap<String, List<Availability>> availabilities = profile.getAvailabilities();
                        
                        if (availabilities == null) {
                            availabilities = new HashMap<String, List<Availability>>();
                            availabilities.put(day, new ArrayList<Availability>());
                            profile.setAvailabilities(availabilities);
                        }
                        
                        List<Availability> perDayAvailabilities = availabilities.get(day);
                        
                        if (perDayAvailabilities == null) {
                            perDayAvailabilities = new ArrayList<Availability>();
                            profile.getAvailabilities().put(day, perDayAvailabilities);
                        }
                        
                        if (requestCode == ADD_AVAILABILITY_REQUEST) {
                            
                            perDayAvailabilities.add(availability);
                            
                        } else if (requestCode == EDIT_AVAILABILITY_REQUEST) {
                            
                            int availabilityIndex = data.getIntExtra(AvailableTimeSlotEditorActivity.AVAILABILITY_INDEX_BUNDLE_ARG, -1);
                            perDayAvailabilities.set(availabilityIndex, availability);
                            
                        }
                        
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
