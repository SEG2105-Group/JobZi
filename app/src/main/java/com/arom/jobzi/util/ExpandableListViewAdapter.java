package com.arom.jobzi.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.arom.jobzi.R;
import com.arom.jobzi.service.Availabilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private List<String> weekDays; //group
    private List<Availabilities> availabilities; // children
    private Context context;

    public ExpandableListViewAdapter(Context context){
        this.context = context;
        weekDays =  new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));
    }

    @Override
    public int getGroupCount() {
        return weekDays.size();
    }

    @Override
    public int getChildrenCount(int weekDayPosition) {
        return availabilities.size();
    }

    @Override
    public Object getGroup(int weekDayPosition) {
        return weekDays.get(weekDayPosition);
    }

    @Override
    public Object getChild(int weekDayPosition, int availibilitiesPosition) {
        return availabilities.get(weekDayPosition);
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

