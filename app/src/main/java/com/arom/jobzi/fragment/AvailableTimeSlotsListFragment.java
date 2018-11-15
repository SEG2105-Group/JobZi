package com.arom.jobzi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.arom.jobzi.AvailableTimeSlotEditorActivity;
import com.arom.jobzi.AvailibilitiesPopupActivity;
import com.arom.jobzi.R;
import com.arom.jobzi.service.AvailableTimeSlot;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AvailableTimeSlotsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AvailableTimeSlotsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailableTimeSlotsListFragment extends Fragment {
    private ExpandableListView mondayListView;
    private Button testButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_available_time_slots_list, container, false);

        mondayListView = view.findViewById(R.id.mondayListView);
        testButton = view.findViewById(R.id.testbutton);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AvailableTimeSlotsListFragment.this.getActivity(), AvailableTimeSlotEditorActivity.class);
                startActivity(intent);
            }
        });

        /*
        mondayListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AvailableTimeSlotsListFragment.this.getActivity(), AvailibilitiesPopupActivity.class);
                intent.putExtra(AvailableTimeSlotEditorActivity.TIMESLOT_BUNDLE_ARG, new AvailableTimeSlot());
                startActivity(intent);
            }
        });*/

        return view;
    }


}
