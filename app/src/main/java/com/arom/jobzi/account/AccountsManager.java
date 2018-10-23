package com.arom.jobzi.account;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountsManager {

    private static final String ACCOUNTS_DATABASE_NAME = "accounts";

    private DatabaseReference database;
    private FirebaseAuth authentication;

    public void initializeDatabase() {
        database = FirebaseDatabase.getInstance().getReference(ACCOUNTS_DATABASE_NAME);
        authentication = FirebaseAuth.getInstance();
    }

    public boolean isSignedIn() {
        return authentication.getCurrentUser() != null;
    }



}
