package com.arom.jobzi.search;

import com.arom.jobzi.service.Availability;
import com.arom.jobzi.user.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class SearchResult implements Serializable {
    
    private static final float serialVersionUID = 1F;
    
    private List<User> serviceProviders;
    private HashMap<User, List<Availability>> availabilities;
    
    public List<User> getServiceProviders() {
        return serviceProviders;
    }
    
    public void setServiceProviders(List<User> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }
    
    public HashMap<User, List<Availability>> getAvailabilities() {
        return availabilities;
    }
    
    public void setAvailabilities(HashMap<User, List<Availability>> availabilities) {
        this.availabilities = availabilities;
    }
    
}
