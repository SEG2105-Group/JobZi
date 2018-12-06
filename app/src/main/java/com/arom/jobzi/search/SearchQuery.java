package com.arom.jobzi.search;

import com.arom.jobzi.service.Availability;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.util.TimeUtil;

public class SearchQuery {
    
    public static final double IGNORE_RATING = -1;
    
    private Service service;
    
    private TimeUtil.Weekday weekday;
    private Availability availability;
    
    private double rating;
    
    public Service getService() {
        return service;
    }
    
    public void setService(Service service) {
        this.service = service;
    }
    
    public TimeUtil.Weekday getWeekday() {
        return weekday;
    }
    
    public void setWeekday(TimeUtil.Weekday weekday) {
        this.weekday = weekday;
    }
    
    public Availability getAvailability() {
        return availability;
    }
    
    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
    }
    
}
