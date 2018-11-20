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
import java.util.zip.Inflater;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private List<String> weekDays; //group
    private String[] group =  {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private List<List<Availabilities>> children = new ArrayList<List<Availabilities>>(7);
    private Context context;

    public ExpandableListViewAdapter(Context context){

        //LayoutInflater inflater = ;
        this.context = context;
        weekDays =  new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));
    }


    @Override
    public int getGroupCount() {
        return group.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int availibilitiesPosition) {
        return children.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int availibilitiesPosition) {
        return children.indexOf(groupPosition);//get(availibilitiesPosition));
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


