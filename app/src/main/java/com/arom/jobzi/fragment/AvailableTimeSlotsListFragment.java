package com.arom.jobzi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.arom.jobzi.AvailableTimeSlotEditorActivity;
import com.arom.jobzi.R;
import com.arom.jobzi.service.AvailableTimeSlot;
import com.arom.jobzi.util.TimePickerActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AvailableTimeSlotsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AvailableTimeSlotsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailableTimeSlotsListFragment extends Fragment {
    private TextView mondayTextView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_available_time_slots_list, container, false);

        mondayTextView = view.findViewById(R.id.mondayTextView);
        mondayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AvailableTimeSlotsListFragment.this.getActivity(), AvailableTimeSlotEditorActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
