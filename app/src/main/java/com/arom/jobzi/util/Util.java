package com.arom.jobzi.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class Util {
    
    public static final String ACCOUNTS_NODE = "accounts";
    public static final String SERVICES_NODE = "services";
    public static final String PROFILES_NODE = "profiles";
    
    private static final String PROFILE_SERVICES_NODE = "services";
    
    private static Util instance;
    
    private Util() {
    }
    
    public DatabaseReference getAccountsDatabase() {
        return FirebaseDatabase.getInstance().getReference().child(ACCOUNTS_NODE);
    }
    
    public DatabaseReference getServicesDatabase() {
        return FirebaseDatabase.getInstance().getReference().child(SERVICES_NODE);
    }
    
    public DatabaseReference getProfilesDatabase() {
        return FirebaseDatabase.getInstance().getReference().child(PROFILES_NODE);
    }
    
    public static Util getInstance() {
        
        if (instance == null) {
            instance = new Util();
        }
        
        return instance;
    }
    
}
