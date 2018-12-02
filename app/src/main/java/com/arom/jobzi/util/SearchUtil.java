package com.arom.jobzi.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.arom.jobzi.ServiceProviderSelectorActivity;
import com.arom.jobzi.account.AccountType;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.search.SearchQuery;
import com.arom.jobzi.search.SearchResult;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public final class SearchUtil {
    
    private SearchUtil() {
    }
    
    public static void displaySearchResult(final Activity activity, final SearchQuery searchQuery) {
        
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                DataSnapshot accountsSnapshot = dataSnapshot.child(Util.ACCOUNTS_NODE);
                DataSnapshot profilesSnapshot = dataSnapshot.child(Util.PROFILES_NODE);
                
                SearchResult searchResult = new SearchResult();
                
                List<User> matchingUsers = new ArrayList<User>();
                HashMap<User, List<Availability>> thisDayMatchingActivities = new HashMap<User, List<Availability>>();
                
                searchResult.setServiceProviders(matchingUsers);
                searchResult.setAvailabilities(thisDayMatchingActivities);
                
                for (DataSnapshot userSnapshot : accountsSnapshot.getChildren()) {
                    
                    User user = userSnapshot.getValue(User.class);
                    
                    if (!user.getAccountType().equals(AccountType.SERVICE_PROVIDER)) {
                        continue;
                    }
                    
                    ServiceProviderProfile profile = profilesSnapshot.child(user.getId()).getValue(ServiceProviderProfile.class);
                    
                    if (profile.getAvailabilities() == null) {
                        continue;
                    }
                    
                    if (!profile.getServices().contains(searchQuery.getService())) {
                        continue;
                    }
                    
                    if (searchQuery.getRating() != SearchQuery.IGNORE_RATING && profile.getRating() < searchQuery.getRating()) {
                        continue;
                    }
                    
                    user.setUserProfile(profile);
                    
                    Calendar searchedStartTime = Calendar.getInstance();
                    searchedStartTime.setTime(searchQuery.getAvailability().getStartTime());
                    
                    Calendar searchedEndTime = Calendar.getInstance();
                    searchedEndTime.setTime(searchQuery.getAvailability().getEndTime());
                    
                    List<Availability> userAvailabilities = profile.getAvailabilities().get(searchQuery.getWeekday().getName());
                    
                    if(userAvailabilities == null) {
                        continue;
                    }
                    
                    List<Availability> matchingAvailabilities = new ArrayList<Availability>();
                    
                    for (Availability userAvailability : userAvailabilities) {
                        
                        Calendar startTime = Calendar.getInstance();
                        startTime.setTime(userAvailability.getStartTime());
                        
                        Calendar endTime = Calendar.getInstance();
                        endTime.setTime(userAvailability.getEndTime());
                        
                        if (TimeUtil.compareTo(startTime, searchedStartTime) >= 0 &&
                                TimeUtil.compareTo(endTime, searchedEndTime) <= 0) {
                            
                            if(thisDayMatchingActivities.get(user) == null) {
    
                                thisDayMatchingActivities.put(user, matchingAvailabilities);
                                matchingUsers.add(user);
    
                            }
                            
                            matchingAvailabilities.add(userAvailability);
                            
                        }
                        
                    }
                    
                }
                
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                
                if(matchingUsers.isEmpty()) {
    
                    Toast.makeText(activity, "No service providers were found given the search criteria.", Toast.LENGTH_LONG).show();
                    return;
                    
                }
    
                Log.d("search-debug", "Matched these service providers:\n" + matchingUsers.toString() + "\nWith these availabilities:\n" + thisDayMatchingActivities.toString());
    
                Intent toServiceProviderSelectorIntent = new Intent(activity, ServiceProviderSelectorActivity.class);
                
                Bundle bundle = new Bundle();
                bundle.putSerializable(ServiceProviderSelectorActivity.SEARCH_RESULT_BUNDLE_ARG, searchResult);
                
                toServiceProviderSelectorIntent.putExtras(bundle);
                
                activity.startActivity(toServiceProviderSelectorIntent);
                
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        });
        
    }
    
}
