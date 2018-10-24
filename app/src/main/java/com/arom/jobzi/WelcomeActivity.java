package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.arom.jobzi.account.DatabaseManager;
import com.arom.jobzi.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;

    private FirebaseAuth authentication;
    private FirebaseUser firebaseUser;

    private TextView accountTypeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        databaseManager = new DatabaseManager();

        authentication = FirebaseAuth.getInstance();
        firebaseUser = authentication.getCurrentUser();

        accountTypeTextView = findViewById(R.id.accountTypeTextView);

        if(firebaseUser != null) {
            databaseManager.getUser(firebaseUser.getUid(), new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = (User) dataSnapshot.getValue();
                    accountTypeTextView.setText(user.getAccountType().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            Intent toLoginIntent = new Intent(this, LoginActivity.class);
            startActivity(toLoginIntent);
        }

    }

}
