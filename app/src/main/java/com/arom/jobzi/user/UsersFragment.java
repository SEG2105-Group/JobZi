package com.arom.jobzi.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arom.jobzi.LoginActivity;
import com.arom.jobzi.R;
import com.arom.jobzi.SignupActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

public class UsersFragment extends Fragment{
    private View view;
    private DatabaseReference accountsDatabase;
    private User user;
    private List<User> users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        
        // TODO: Move this to a helper method in the AuthUtil class.
        accountsDatabase = FirebaseDatabase.getInstance().getReference().child(SignupActivity.ACCOUNTS);
        addUsersListener();
        
        view = inflater.inflate(R.layout.fragment_user_list, container, false);
        
        users = new LinkedList<User>();

        ListView listView = view.findViewById(R.id.userListView);

        ArrayAdapter<User> listViewUsersAdapter = new ArrayAdapter<User>(getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                users);
        listView.setAdapter(listViewUsersAdapter);

        return view;
    }

    private void addUsersListener() {

        accountsDatabase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                User user = dataSnapshot.getValue(User.class);
                users.add(user);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                User userChanged = dataSnapshot.getValue(User.class);

                int i = 0;

                for(; i < users.size(); i++) {
                    if(users.get(i).getId().equals(userChanged.getId())) {
                        break;
                    }
                }

                users.set(i, userChanged);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                User userChanged = dataSnapshot.getValue(User.class);

                int i = 0;

                for(; i < users.size(); i++) {
                    if(users.get(i).getId().equals(userChanged.getId())) {
                        break;
                    }
                }

                users.remove(i);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
