package com.arom.jobzi.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arom.jobzi.R;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.user.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class FragmentServices extends Fragment {

    View view;
    //private List<Services> services;
    private DatabaseReference accountsDatabase;

    public FragmentServices(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.service_item, container, false);

        return view;
    }
}
