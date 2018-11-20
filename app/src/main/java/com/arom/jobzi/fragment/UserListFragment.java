package com.arom.jobzi.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arom.jobzi.R;
import com.arom.jobzi.dapater.UserArrayAdapter;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.Util;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    private List<User> userList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userList = new ArrayList<User>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        ListView userListView = view.findViewById(R.id.userListView);

        final UserArrayAdapter userArrayAdapter = new UserArrayAdapter(getActivity(), userList);

        userListView.setAdapter(userArrayAdapter);
    
        DatabaseReference accountsDatabase = Util.getInstance().getAccountsDatabase();
        accountsDatabase.addChildEventListener(new ChildEventListener() {
        
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            
                User user = dataSnapshot.getValue(User.class);
                userList.add(user);
            
                userArrayAdapter.notifyDataSetChanged();
            
            }
        
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            
                User userChanged = dataSnapshot.getValue(User.class);
            
                userList.set(userList.indexOf(userChanged), userChanged);
    
                userArrayAdapter.notifyDataSetChanged();
            
            }
        
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            
                User userRemoved = dataSnapshot.getValue(User.class);
            
                userList.remove(userRemoved);
    
                userArrayAdapter.notifyDataSetChanged();
            
            }
        
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            
            }
        
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        });
        
        return view;

    }

}
