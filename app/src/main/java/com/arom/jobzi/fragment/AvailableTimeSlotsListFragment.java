package com.arom.jobzi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arom.jobzi.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AvailableTimeSlotsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AvailableTimeSlotsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailableTimeSlotsListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_available_time_slots_list, container, false);
    }

}
