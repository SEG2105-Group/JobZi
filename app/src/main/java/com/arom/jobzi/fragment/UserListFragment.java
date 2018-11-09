package com.arom.jobzi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arom.jobzi.R;
import com.arom.jobzi.user.User;
import com.arom.jobzi.user.UserArrayAdapter;
import com.arom.jobzi.util.Util;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        ListView userListView = view.findViewById(R.id.userListView);

        userList = new ArrayList<User>();

        Util.getInstance().addUserListListener(userList);

        UserArrayAdapter userArrayAdapter = new UserArrayAdapter(getActivity(), userList);

        userListView.setAdapter(userArrayAdapter);

        return view;

    }

}
