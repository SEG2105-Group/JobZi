package com.arom.jobzi.adapater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.arom.jobzi.R;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AvailabilitiesExpandableListAdapter extends BaseExpandableListAdapter {
    
    private Context context;
    
    private List<String> daysOfWeekList;
    
    private OnAvailabilityListener availabilityListener;
    
    private HashMap<String, List<Availability>> dailyAvailabilities;
    
    @SuppressLint("WrongConstant")
    public AvailabilitiesExpandableListAdapter(Context context, OnAvailabilityListener availabilityListener) {
        this.context = context;
        
        daysOfWeekList = TimeUtil.getDaysOfWeekNames();
        
        dailyAvailabilities = new HashMap<String, List<Availability>>();
        
        for (String dayOfWeek : daysOfWeekList) {
            dailyAvailabilities.put(dayOfWeek, new ArrayList<Availability>());
        }
        
        this.availabilityListener = availabilityListener;
        
    }
    
    public void updateAvailabilities(ServiceProviderProfile profile) {
        
        HashMap<String, List<Availability>> newDailyAvailabilities = profile.getAvailabilities();
        
        if (newDailyAvailabilities == null) {
            
            for (List<Availability> availabilityList : dailyAvailabilities.values()) {
                availabilityList.clear();
            }
            
            notifyDataSetChanged();
            
            return;
            
        }
        
        for (String dayOfWeek : daysOfWeekList) {
            
            List<Availability> newAvailabilities = newDailyAvailabilities.get(dayOfWeek);
            
            List<Availability> availabilities = dailyAvailabilities.get(dayOfWeek);
            
            if (newAvailabilities == null) {
                
                if (!availabilities.isEmpty()) {
                    availabilities.clear();
                }
                
            } else {
                availabilities.clear();
                availabilities.addAll(newAvailabilities);
                
            }
            
        }
        
        notifyDataSetChanged();
        
    }
    
    @Override
    public int getGroupCount() {
        return daysOfWeekList.size();
    }
    
    @Override
    public int getChildrenCount(int weekDayPosition) {
        return dailyAvailabilities.get(daysOfWeekList.get(weekDayPosition)).size();
    }
    
    @Override
    public Object getGroup(int weekDayPosition) {
        return daysOfWeekList.get(weekDayPosition);
    }
    
    @Override
    public Object getChild(int weekDayPosition, int availabilityPosition) {
        return dailyAvailabilities.get(daysOfWeekList.get(weekDayPosition)).get(availabilityPosition);
    }
    
    @Override
    public long getGroupId(int weekDayPosition) {
        return weekDayPosition;
    }
    
    @Override
    public long getChildId(int weekDayPosition, int availibilitiesPosition) {
        return availibilitiesPosition;
    }
    
    @Override
    public boolean hasStableIds() {
        return false;
    }
    
    @Override
    public View getGroupView(final int weekDayPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.week_days_list_group, parent, false);
        }
        
        TextView weekdayTextView = convertView.findViewById(R.id.weekdayTextView);
        weekdayTextView.setText(daysOfWeekList.get(weekDayPosition));
        
        Button addAvailabilityButton = convertView.findViewById(R.id.addAvailabilityButton);
        addAvailabilityButton.setFocusable(false);
        addAvailabilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                availabilityListener.onAdd(daysOfWeekList.get(weekDayPosition));
            }
        });
        
        return convertView;
    }
    
    @Override
    public View getChildView(final int weekdayPosition, final int availabilityPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.availability_list_item, parent, false);
        }
        
        TextView availabilityTextView = convertView.findViewById(R.id.availibilityTextView);
        
        final Availability availability = dailyAvailabilities.get(daysOfWeekList.get(weekdayPosition)).get(availabilityPosition);
        
        availabilityTextView.setText(TimeUtil.formatAvailability(context, availability));
        
        availabilityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                availabilityListener.onEdit(daysOfWeekList.get(weekdayPosition), availability, availabilityPosition);
            }
        });
        
        availabilityTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                
                availabilityListener.onDeleteAvailability(daysOfWeekList.get(weekdayPosition), availabilityPosition);
                
                return true;
                
            }
        });
        
        return convertView;
        
    }
    
    @Override
    public boolean isChildSelectable(int weekDayPosition, int availabilitiesPosition) {
        return true;
    }
    
    public List<Availability> getAvailabilities(String day) {
        return dailyAvailabilities.get(day);
    }
    
    public interface OnAvailabilityListener {
        
        void onAdd(String day);
        
        void onEdit(String day, Availability availability, int index);
        
        void onDeleteAvailability(String day, int index);
        
    }
    
}

