package com.arom.jobzi.dapater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.arom.jobzi.R;
import com.arom.jobzi.service.Availability;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AvailabilitiesExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<String> daysOfWeekList;
    private HashMap<String, List<Availability>> dayToAvailabilityMap;

    public AvailabilitiesExpandableListAdapter(Context context){
        this.context = context;

        daysOfWeekList = new ArrayList<String>();

        daysOfWeekList.add(Calendar.getInstance().getDisplayName(Calendar.MONDAY, Calendar.LONG, Locale.getDefault()));
        daysOfWeekList.add(Calendar.getInstance().getDisplayName(Calendar.TUESDAY, Calendar.LONG, Locale.getDefault()));
        daysOfWeekList.add(Calendar.getInstance().getDisplayName(Calendar.WEDNESDAY, Calendar.LONG, Locale.getDefault()));
        daysOfWeekList.add(Calendar.getInstance().getDisplayName(Calendar.THURSDAY, Calendar.LONG, Locale.getDefault()));
        daysOfWeekList.add(Calendar.getInstance().getDisplayName(Calendar.FRIDAY, Calendar.LONG, Locale.getDefault()));
        daysOfWeekList.add(Calendar.getInstance().getDisplayName(Calendar.SATURDAY, Calendar.LONG, Locale.getDefault()));
        daysOfWeekList.add(Calendar.getInstance().getDisplayName(Calendar.SUNDAY, Calendar.LONG, Locale.getDefault()));

        for(String dayOfWeek: daysOfWeekList) {
            dayToAvailabilityMap.put(dayOfWeek, new ArrayList<Availability>());
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
    public View getGroupView(int weekDayPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(weekDayPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.id.AvailibilitiesListView, null);
        }

        //TextView listHeader = convertView.findViewById(R.id.)

        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int weekDayPosition, int availibilitiesPosition) {
        return true;
    }
}

