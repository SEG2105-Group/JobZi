package com.arom.jobzi.adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.arom.jobzi.R;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.TimeUtil;

import java.util.HashMap;
import java.util.List;

public class ServiceProviderExpandableArrayAdapter extends BaseExpandableListAdapter {
    
    private Context context;
    
    private OnAvailabilitySelectedListener listener;
    
    private HashMap<User, List<Availability>> availabilitiesMap;
    private List<User> serviceProviders;
    
    public ServiceProviderExpandableArrayAdapter(Context context, OnAvailabilitySelectedListener listener, HashMap<User, List<Availability>> availabilitiesMap, List<User> serviceProviders) {
        super();
        
        this.context = context;
        
        this.listener = listener;
        
        this.availabilitiesMap = availabilitiesMap;
        this.serviceProviders = serviceProviders;
        
    }
    
    @Override
    public int getGroupCount() {
        return serviceProviders.size();
    }
    
    @Override
    public int getChildrenCount(int i) {
        return availabilitiesMap.get(serviceProviders.get(i)).size();
    }
    
    @Override
    public Object getGroup(int i) {
        return serviceProviders.get(i);
    }
    
    @Override
    public Object getChild(int i, int i1) {
        return availabilitiesMap.get(serviceProviders.get(i)).get(i1);
    }
    
    @Override
    public long getGroupId(int i) {
        return i;
    }
    
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }
    
    @Override
    public boolean hasStableIds() {
        return false;
    }
    
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.service_provider_list_group, viewGroup, false);
        }
        
        User serviceProvider = serviceProviders.get(i);
        
        TextView serviceProviderTextView = view.findViewById(R.id.serviceProviderTextView);
        
        serviceProviderTextView.setText(serviceProvider.getUsername());
        
        return view;
        
    }
    
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.availability_list_item, viewGroup, false);
        }
        
        final User serviceProvider = serviceProviders.get(i);
        final Availability availability = availabilitiesMap.get(serviceProvider).get(i1);
        
        TextView availabilityTextView = view.findViewById(R.id.availibilityTextView);
        availabilityTextView.setText(TimeUtil.formatAvailability(context, availability));
        
        availabilityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelect(serviceProvider, availability);
            }
        });
        
        return view;
        
    }
    
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    
    public interface OnAvailabilitySelectedListener {
        
        void onSelect(User serviceProvider, Availability availability);
        
    }
    
}
