package com.arom.jobzi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.arom.jobzi.R;


public class AvailableTimeSlotsListFragment extends Fragment {
    private ExpandableListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_available_time_slots_list, container, false);

        listView = view.findViewById(R.id.expandableListView);

        return view;
    }


}
