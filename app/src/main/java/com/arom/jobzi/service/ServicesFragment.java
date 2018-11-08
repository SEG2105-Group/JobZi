package com.arom.jobzi.service;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arom.jobzi.R;
import com.arom.jobzi.user.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.LinkedList;
import java.util.List;

public class ServicesFragment extends Fragment {
    private View view;
    private DatabaseReference accountsDatabase;
    private Service service;
    private List<Service> services;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_item, container, false);
        services = new LinkedList<Service>();

        ListView listView = view.findViewById(R.id.fragmentuServiceList);

        ServiceArrayAdapter listViewServiceAdapter = new ServiceArrayAdapter(getActivity(), services);
        listView.setAdapter(listViewServiceAdapter);

        return view;
    }
}