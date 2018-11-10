package com.arom.jobzi.service;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

    @Override
    public int getCount() {
        return services.size();
    }

    @Nullable
    @Override
    public Service getItem(int position) {
        return services.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View view = inflater.inflate(R.layout.service_item, null, true);

        Service service = services.get(position);

        TextView serviceNameTextView = view.findViewById(R.id.serviceNameTextView);
        TextView serviceDescriptionTextView = view.findViewById(R.id.serviceDescriptionTextView);
        TextView hourlyRateTextView = view.findViewById(R.id.hourlyRateTextView);

        serviceNameTextView.setText(service.getName());
        serviceDescriptionTextView.setText(service.getName() + " is a service.");
        hourlyRateTextView.setText(String.format("%.2f $/hour", service.getRate()));

        return view;

    }
}
