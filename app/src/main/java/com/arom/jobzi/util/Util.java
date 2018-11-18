package com.arom.jobzi.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;

import com.arom.jobzi.LandingActivity;
import com.arom.jobzi.LoginActivity;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.profile.UserProfile;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.user.SessionManager;
import com.arom.jobzi.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public final class Util {
    
    private static final String ACCOUNTS_NODE = "accounts";
    private static final String SERVICES_NODE = "services";
    private static final String PROFILES_NODE = "profiles";
    
    private static final String PROFILE_SERVICES_NODE = "services";
    
    private static Util instance;
    
    private FirebaseAuth firebaseAuth;
    
    private DatabaseReference database;
    
    private DatabaseReference accountsDatabase;
    private DatabaseReference servicesDatabase;
    private DatabaseReference profilesDatabase;
    
    private Util() {
    }
    
    public FirebaseUser getCurrentUser() {
        firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.getCurrentUser();
    }
    
    public Task<AuthResult> createUser(String email, String password) {
        
        firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
        
    }
    
    /**
     * This method is called when it has been detected that the user has logged in (either when starting the application with a user already logged in or when a user manually logs in).
     *
     * @param firebaseUser
     */
    public void onUserLogin(final Activity activity, final FirebaseUser firebaseUser) {
        
        database = FirebaseDatabase.getInstance().getReference();
        
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                User user = dataSnapshot.child(ACCOUNTS_NODE).child(firebaseUser.getUid()).getValue(User.class);
                
                // Must retrieve and set the user profile manually.
                Class<?> profileClass = UserProfileUtil.getInstance().getClassByAccountType(user.getAccountType());
                
                final UserProfile userProfile = (UserProfile) dataSnapshot.child(PROFILES_NODE).child(firebaseUser.getUid()).getValue(profileClass);
                
                user.setUserProfile(userProfile);
    
                SessionManager.getInstance().setUser(user);
                
                gotoLanding(activity);
                
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        });
        
        /*
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                User user = dataSnapshot.child(ACCOUNTS_NODE).child(firebaseUser.getUid()).getValue(User.class);
    
                Class<? extends UserProfile> userProfileClass = UserProfileUtil.getInstance().getClassByAccountType(user.getAccountType());
    
                if (userProfileClass != null) {
                    user.setUserProfile(dataSnapshot.child(PROFILES_NODE).child(firebaseUser.getUid()).getValue(userProfileClass));
                }
                
                SessionManager.getInstance().setUser(user);
                
            }
    
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
        
            }
        });*/
        
    }
    
    private void gotoLanding(Activity activity) {
        
        Intent toUserIntent = new Intent(activity, LandingActivity.class);
        activity.startActivity(toUserIntent);
        activity.finish();
        
    }
    
    public void logout(Activity activity) {
        
        SessionManager.getInstance().setUser(null);
        
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        
        Intent toLoginIntent = new Intent(activity, LoginActivity.class);
        activity.startActivity(toLoginIntent);
        activity.finish();
        
    }
    
    public void addUserListListener(final BaseAdapter adapterToUpdate, final List<User> userList) {
        
        accountsDatabase = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS_NODE);
        
        accountsDatabase.addChildEventListener(new ChildEventListener() {
            
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                
                User user = dataSnapshot.getValue(User.class);
                userList.add(user);
                
                adapterToUpdate.notifyDataSetChanged();
                
            }
            
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                
                User userChanged = dataSnapshot.getValue(User.class);
                
                userList.set(userList.indexOf(userChanged), userChanged);
                
                adapterToUpdate.notifyDataSetChanged();
                
            }
            
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                
                User userRemoved = dataSnapshot.getValue(User.class);
                
                userList.remove(userRemoved);
                
                adapterToUpdate.notifyDataSetChanged();
                
            }
            
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        });
        
    }
    
    public void addServiceListListener(final BaseAdapter adapterToUpdate, final List<Service> serviceList) {
        
        servicesDatabase = FirebaseDatabase.getInstance().getReference().child(SERVICES_NODE);
        
        servicesDatabase.addChildEventListener(new ChildEventListener() {
            
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                
                Service service = dataSnapshot.getValue(Service.class);
                serviceList.add(service);
                
                adapterToUpdate.notifyDataSetChanged();
                
            }
            
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                
                Service serviceChanged = dataSnapshot.getValue(Service.class);
                
                serviceList.set(serviceList.indexOf(serviceChanged), serviceChanged);
                
                adapterToUpdate.notifyDataSetChanged();
                
            }
            
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                
                Service serviceRemoved = dataSnapshot.getValue(Service.class);
                
                serviceList.remove(serviceRemoved);
                
                adapterToUpdate.notifyDataSetChanged();
                
            }
            
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        });
    
    }
    
    public void deleteService(Service service) {
        servicesDatabase = FirebaseDatabase.getInstance().getReference().child(SERVICES_NODE);
        servicesDatabase.child(service.getId()).removeValue();
    }
    
    public void addSingleValueAccountsListener(ValueEventListener accountsListener) {
        
        accountsDatabase = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS_NODE);
        accountsDatabase.addListenerForSingleValueEvent(accountsListener);
        
    }
    
    public void updateUser(User user) {
        
        accountsDatabase = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS_NODE);
        accountsDatabase.child(user.getId()).setValue(user);
        
        if (user.getUserProfile() != null) {
            profilesDatabase = FirebaseDatabase.getInstance().getReference().child(PROFILES_NODE);
            profilesDatabase.child(user.getId()).setValue(user.getUserProfile());
        }
        
    }
    
    public void updateService(Service service) {
        
        servicesDatabase = FirebaseDatabase.getInstance().getReference().child(SERVICES_NODE);
        
        if (service.getId() == null) {
            service.setId(servicesDatabase.push().getKey());
        }
        
        servicesDatabase.child(service.getId()).setValue(service);
        
    }
    
    public void addProfileServiceListListener(final BaseAdapter adapterToUpdate, final User user) {
        
        profilesDatabase = FirebaseDatabase.getInstance().getReference().child(PROFILES_NODE);
    
        ServiceProviderProfile profile = (ServiceProviderProfile) user.getUserProfile();
        final List<Service> services = profile.getServices();
        
        profilesDatabase.child(user.getId()).child(SERVICES_NODE).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                
                services.add(dataSnapshot.getValue(Service.class));
                adapterToUpdate.notifyDataSetChanged();
                
            }
    
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        
                services.set(Integer.parseInt(dataSnapshot.getKey()), dataSnapshot.getValue(Service.class));
                adapterToUpdate.notifyDataSetChanged();
    
            }
    
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                
                services.remove(dataSnapshot.getValue(Service.class));
                adapterToUpdate.notifyDataSetChanged();
    
            }
    
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            
            }
    
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
        
            }
        });
        
    }
    
    public void addSingleValueServicesListener(ValueEventListener listener) {
        
        servicesDatabase = FirebaseDatabase.getInstance().getReference().child(SERVICES_NODE);
        servicesDatabase.addListenerForSingleValueEvent(listener);
        
    }
    
    public static Util getInstance() {
        
        if (instance == null) {
            instance = new Util();
        }
        
        return instance;
    }
    
}
