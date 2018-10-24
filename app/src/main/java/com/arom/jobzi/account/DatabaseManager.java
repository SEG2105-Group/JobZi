package com.arom.jobzi.account;

import android.support.annotation.NonNull;

import com.arom.jobzi.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseManager {


    private static final String ADMIN_ACCOUNTS = "admin_accounts";
    private static final String HOME_OWNERS_ACCOUNTS = "home_owners_accounts";
    private static final String SERVICE_PROVIDERS_ACCOUNTS = "service_providers_accounts";

    private DatabaseReference database;

    public DatabaseManager() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void addAdmin() {
        database.child(ADMIN_ACCOUNTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User adminUser = (User) dataSnapshot.getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addUser(User user, String uid) {
//        database.child(ACCOUNTS_CHILD).child(uid).setValue(user);
    }

    public void getUser(String uid, ValueEventListener valueEventListener) {
//        database.child(ACCOUNTS_CHILD).child(uid).addListenerForSingleValueEvent(valueEventListener);
    }

}
