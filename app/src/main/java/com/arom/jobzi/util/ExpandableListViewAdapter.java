package com.arom.jobzi.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.arom.jobzi.R;
import com.arom.jobzi.service.Availabilities;

import java.util.List;

public class ExpandableListViewAdapter extends ArrayAdapter<Availabilities> {

    private List<Availabilities> availabilities;

    public ExpandableListViewAdapter(@NonNull Context context, @NonNull List<Availabilities> availabilities) {
        super(context,R.layout.availabilities_item, availabilities);

    }
}
