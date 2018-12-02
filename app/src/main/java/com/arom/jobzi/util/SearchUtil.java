package com.arom.jobzi.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arom.jobzi.account.AccountType;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.service.Service;
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

    private SearchUtil() {}
    
    public static void displaySearchResult(Activity activity, final SearchQuery searchQuery) {
    
        DatabaseReference accountsDatabase = Util.getInstance().getAccountsDatabase();
        DatabaseReference profilesDatabase = Util.getInstance().getProfilesDatabase();
        
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        
                DataSnapshot accountsSnapshot = dataSnapshot.child(Util.ACCOUNTS_NODE);
                DataSnapshot profilesSnapshot = dataSnapshot.child(Util.PROFILES_NODE);
                
                SearchResult searchResult = new SearchResult();
                
                List<User> matchingUsers = new ArrayList<User>();
                
                searchResult.setServiceProviders(matchingUsers);

                HashMap<User, List<Availability>> thisDayMatchingActivities = new HashMap<User, List<Availability>>();

                for(DataSnapshot userSnapshot: accountsSnapshot.getChildren()) {

                    User user = userSnapshot.getValue(User.class);

                    if(!user.getAccountType().equals(AccountType.SERVICE_PROVIDER)) {
                        continue;
                    }

                    ServiceProviderProfile profile = profilesSnapshot.child(user.getId()).getValue(ServiceProviderProfile.class);

                    if(!profile.getServices().contains(searchQuery.getService())) {
                        continue;
                    }

                    if(searchQuery.getWeekday() == null || searchQuery.getAvailability() == null) {
                        matchingUsers.add(user);
                        continue;
                    }

                    Calendar searchedStartTime = Calendar.getInstance();
                    searchedStartTime.setTime(searchQuery.getAvailability().getStartTime());

                    Calendar searchedEndTime = Calendar.getInstance();
                    searchedEndTime.setTime(searchQuery.getAvailability().getEndTime());

                    List<Availability> userAvailabilities = profile.getAvailabilities().get(searchQuery.getWeekday().getName());
                    List<Availability> matchingAvailabilities = new ArrayList<Availability>();

                    thisDayMatchingActivities.put(user, matchingAvailabilities);

                    for(Availability userAvailability: userAvailabilities) {

                        Calendar startTime = Calendar.getInstance();
                        startTime.setTime(userAvailability.getStartTime());

                        Calendar endTime = Calendar.getInstance();
                        endTime.setTime(userAvailability.getEndTime());

                        if (TimeUtil.compareTo(startTime, searchedStartTime) >= 0 &&
                                TimeUtil.compareTo(endTime, searchedEndTime) <= 0) {
                            matchingAvailabilities.add(userAvailability);
                        }

                    }

                }

                Log.d("search-debug", "Matched these service providers:\n" + matchingUsers.toString() + "\nWith these availabilities:\n" + thisDayMatchingActivities.toString());
            
            }
    
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
        
            }
        });

    }
    
    public static class SearchResult {
    
        private List<User> serviceProviders;
        private HashMap<User, List<Availability>> availabilities;
    
        public List<User> getServiceProviders() {
            return serviceProviders;
        }
    
        public void setServiceProviders(List<User> serviceProviders) {
            this.serviceProviders = serviceProviders;
        }
    
        public HashMap<User, List<Availability>> getAvailabilities() {
            return availabilities;
        }
    
        public void setAvailabilities(HashMap<User, List<Availability>> availabilities) {
            this.availabilities = availabilities;
        }
        
    }

    public static class SearchQuery {
        
        private Service service;
        
        private TimeUtil.Weekday weekday;
        private Availability availability;
        
        private double rating;
    
        public Service getService() {
            return service;
        }
    
        public void setService(Service service) {
            this.service = service;
        }
    
        public TimeUtil.Weekday getWeekday() {
            return weekday;
        }
    
        public void setWeekday(TimeUtil.Weekday weekday) {
            this.weekday = weekday;
        }
    
        public Availability getAvailability() {
            return availability;
        }
    
        public void setAvailability(Availability availability) {
            this.availability = availability;
        }
    
        public double getRating() {
            return rating;
        }
    
        public void setRating(double rating) {
            this.rating = rating;
        }
    }
    
}
