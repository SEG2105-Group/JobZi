package com.arom.jobzi.account;

import com.arom.jobzi.user.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseManager {

    private static final String ACCOUNTS_CHILD = "accounts";
    private static final String NUMBER_OF_ACCOUNTS_CHILD = "number_of_accounts";

    private DatabaseReference database;

    public DatabaseManager() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void addUser(User user, String uid) {
        database.child(ACCOUNTS_CHILD).child(uid).setValue(user);
    }

    public void getUser(String uid, ValueEventListener valueEventListener) {
        database.child(ACCOUNTS_CHILD).addListenerForSingleValueEvent(valueEventListener);
    }

}
