package com.arom.jobzi.user;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.arom.jobzi.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserList extends ArrayAdapter<User> {

    private Activity context;
    private List<User> users;
    private DatabaseReference db;

    public UserList(Activity context, List<User> users, DatabaseReference db) {
        super(context, R.layout.username_list);

        this.context = context;
        this.users = users;
        this.db = db;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View usernameListLayout = inflater.inflate(R.layout.username_list, null, true);

        User user = users.get(position);

        // TODO: Add user information here and have where it is to be set in the username_list layout file.

        return usernameListLayout;
    }

    public void addFromDatabase(){
        boolean flag = true;

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
