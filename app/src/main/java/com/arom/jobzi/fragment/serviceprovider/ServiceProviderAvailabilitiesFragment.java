package com.arom.jobzi.fragment.serviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.arom.jobzi.AvailableTimeSlotEditorActivity;
import com.arom.jobzi.R;

public class ServiceProviderAvailabilitiesFragment extends Fragment {

    private ExpandableListView expandableList;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_available_time_slots_list, container, false);

        expandableList = view.findViewById(R.id.expandableListView);

        button = view.findViewById(R.id.testingButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceProviderAvailabilitiesFragment.this.getActivity(), AvailableTimeSlotEditorActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
