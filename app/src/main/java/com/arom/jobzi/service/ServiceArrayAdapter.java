package com.arom.jobzi.service;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.arom.jobzi.R;

import java.util.List;

public class ServiceArrayAdapter extends ArrayAdapter<Service> {

    private Activity context;
    private List<Service> services;

    public ServiceArrayAdapter(Activity context, List<Service> services) {
        super(context, R.layout.service_item, services);

        this.context = context;
        this.services = services;

    }

}
