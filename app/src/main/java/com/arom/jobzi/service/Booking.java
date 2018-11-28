package com.arom.jobzi.service;

import com.arom.jobzi.user.User;

import java.util.Date;

public class Booking {
    
    private Date day;
    
    private Availability availability;
    
    private User homeOwner;
    private User serviceProvider;
    
    public Date getDay() {
        return day;
    }
    
    public void setDay(Date day) {
        this.day = day;
    }
    
    public Availability getAvailability() {
        return availability;
    }
    
    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
    
    public User getHomeOwner() {
        return homeOwner;
    }
    
    public void setHomeOwner(User homeOwner) {
        this.homeOwner = homeOwner;
    }
    
    public User getServiceProvider() {
        return serviceProvider;
    }
    
    public void setServiceProvider(User serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
