package com.arom.jobzi.adapater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.arom.jobzi.R;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.service.Availability;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AvailabilitiesExpandableListAdapter extends BaseExpandableListAdapter {
    
    private enum Weekday {
        
        MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"), THURSDAY("Thursday"), FRIDAY("Friday"), SATURDAY("Saturday"), SUNDAY("Sunday");
        
        private final String name;
        
        Weekday(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
    }
    
    private Context context;
    
    private List<String> daysOfWeekList;
    private HashMap<String, List<Availability>> dayToAvailabilityMap;
    
    private OnAddClickListener addAvailabilityListener;
    
    private List<Availability>[] availabilities;
    
    @SuppressLint("WrongConstant")
    public AvailabilitiesExpandableListAdapter(Context context, OnAddClickListener addAvailabilityListener) {
        this.context = context;
        
        daysOfWeekList = new ArrayList<String>();

        daysOfWeekList.add(Weekday.MONDAY.getName());
        daysOfWeekList.add(Weekday.TUESDAY.getName());
        daysOfWeekList.add(Weekday.WEDNESDAY.getName());
        daysOfWeekList.add(Weekday.THURSDAY.getName());
        daysOfWeekList.add(Weekday.FRIDAY.getName());
        daysOfWeekList.add(Weekday.SATURDAY.getName());
        daysOfWeekList.add(Weekday.SUNDAY.getName());
        
        DateFormatSymbols.getInstance().getWeekdays();
        
        dayToAvailabilityMap = new HashMap<String, List<Availability>>();
    
        this.addAvailabilityListener = addAvailabilityListener;
        
    }
    
    public void updateAvailabilities(ServiceProviderProfile profile) {
    
        availabilities = profile.getAvailabilities();
        
        if(availabilities == null) {
            availabilities = new ArrayList<ArrayList<Availability>>(daysOfWeekList.size()).toArray();
        }
    
        for (int i = 0; i < daysOfWeekList.size(); i++) {
            
            String dayOfWeek = daysOfWeekList.get(i);
            dayToAvailabilityMap.put(dayOfWeek, availabilities[i]);
            
        }
    
    }
    
    @Override
    public int getGroupCount() {
        return daysOfWeekList.size();
    }
    
    @Override
    public int getChildrenCount(int weekDayPosition) {
        return dayToAvailabilityMap.get(daysOfWeekList.get(weekDayPosition)).size();
    }
    
    @Override
    public Object getGroup(int weekDayPosition) {
        return daysOfWeekList.get(weekDayPosition);
    }
    
    @Override
    public Object getChild(int weekDayPosition, int availibilitiesPosition) {
        return dayToAvailabilityMap.get(daysOfWeekList.get(weekDayPosition)).get(availibilitiesPosition);
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
    
    @SuppressLint("ResourceType")
    @Override
    public View getGroupView(final int weekDayPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.week_days_list_group, parent, false);
        }
        
        TextView weekdayTextView = convertView.findViewById(R.id.weekdayTextView);
        weekdayTextView.setText(daysOfWeekList.get(weekDayPosition));
        
        Button addAvailabilityButton = convertView.findViewById(R.id.addAvailabilityButton);
        addAvailabilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAvailabilityListener.onAdd(weekDayPosition);
            }
        });
        
        return convertView;
    }
    
    @Override
    public View getChildView(final int weekdayPosition, final int availabilitiesPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.availability_list_item, parent, false);
        }
        
        TextView availabilityTextView = convertView.findViewById(R.id.availibilityTextView);
        
        final Availability availability = dayToAvailabilityMap.get(daysOfWeekList.get(weekdayPosition)).get(availabilitiesPosition);
        
        availabilityTextView.setText(DateFormat.getTimeFormat(context).format(availability.getStartTime()) + " - " + DateFormat.getTimeFormat(context).format(availability.getEndTime()));
        
        availabilityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAvailabilityListener.onEdit(weekdayPosition, dayToAvailabilityMap.get(daysOfWeekList.get(weekdayPosition)).get(availabilitiesPosition));
            }
        });
        
        return convertView;
        
    }
    
    @Override
    public boolean isChildSelectable(int weekDayPosition, int availabilitiesPosition) {
        return true;
    }
    
    public interface OnAddClickListener {
        void onAdd(int day);
        void onEdit(int day, Availability availability);
    }
    
}

