package com.arom.jobzi.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arom.jobzi.R;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.service.ServiceArrayAdapter;
import com.arom.jobzi.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServiceListFragment extends Fragment {

    private static final String LISTENER_BUNDLE_ARG = "listener";

    private List<Service> serviceList;

    private FloatingActionButton addServiceFloatingButton;

    private ServiceItemListener listener;

    public ServiceListFragment() {

    }

    public static ServiceListFragment newInstance(ServiceItemListener listener) {
        
        ServiceListFragment fragment = new ServiceListFragment();
        
        Bundle bundle = new Bundle();
        bundle.putSerializable(LISTENER_BUNDLE_ARG, listener);
        
        fragment.setArguments(bundle);
        
        return fragment;
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        serviceList = new ArrayList<Service>();

        Bundle arguments = getArguments();

        listener = (ServiceItemListener) arguments.get(LISTENER_BUNDLE_ARG);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView serviceListView = view.findViewById(R.id.serviceListView);

        ServiceArrayAdapter serviceArrayAdapter = new ServiceArrayAdapter(getActivity(), serviceList);

        serviceListView.setAdapter(serviceArrayAdapter);

        serviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (listener != null) {
                    listener.onClick(serviceList.get(position));
                }

            }
        });

        serviceListView.setLongClickable(true);
        serviceListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (listener != null) {
                    listener.onLongClick(serviceList.get(i));
                }

                return true;

            }
        });

        Util.getInstance().addServiceListListener(serviceArrayAdapter, serviceList);

        addServiceFloatingButton = view.findViewById(R.id.addServiceFloatingButton);

        addServiceFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addServiceFloatingButton.setEnabled(false);

                if(listener != null) {
                    listener.addService();
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        addServiceFloatingButton.setEnabled(true);

    }

    public interface ServiceItemListener extends Serializable {

        void onClick(Service service);

        void onLongClick(Service service);

        void addService();

    }

}
