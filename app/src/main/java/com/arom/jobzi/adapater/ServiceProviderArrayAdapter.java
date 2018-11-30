package com.arom.jobzi.adapater;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.arom.jobzi.user.User;

public class ServiceProviderArrayAdapter extends ArrayAdapter<User> {
    
    public ServiceProviderArrayAdapter(Context context, int resource, User[] objects) {
        super(context, resource, objects);
    }
    
}
