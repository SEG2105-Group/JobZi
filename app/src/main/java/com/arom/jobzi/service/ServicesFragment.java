package com.arom.jobzi.service;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arom.jobzi.R;
import com.google.firebase.database.DatabaseReference;

public class ServicesFragment extends Fragment {

    View view;
    //private List<Services> services;
    private DatabaseReference accountsDatabase;

    public ServicesFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.service_item, container, false);

        return view;
    }
}
