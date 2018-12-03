package com.arom.jobzi.profile;

import com.arom.jobzi.service.Availability;
import com.arom.jobzi.service.Feedback;
import com.arom.jobzi.service.Service;

import java.util.HashMap;
import java.util.List;

public class ServiceProviderProfile extends UserProfile {
    
    private HashMap<String, List<Availability>> availabilities;
    private List<Service> services;
    private List<Feedback> feedbacks;
    
    private String address;
    private String phoneNumber;
    private String companyName;
    private String description;
    
    private double rating;
    
    private boolean licensed;
    
    public HashMap<String, List<Availability>> getAvailabilities() {
        return availabilities;
    }
    
    public void setAvailabilities(HashMap<String, List<Availability>> availabilities) {
        this.availabilities = availabilities;
    }
    
    public List<Service> getServices() {
        return services;
    }
    
    public void setServices(List<Service> services) {
        this.services = services;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
    }
    
    public boolean isLicensed() {
        return licensed;
    }
    
    public void setLicensed(boolean licensed) {
        this.licensed = licensed;
    }
    
    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }
    
    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
    
    @Override
    public boolean copyFrom(UserProfile userProfile) {
        
        if (userProfile instanceof ServiceProviderProfile) {
            
            ServiceProviderProfile serviceProviderProfile = (ServiceProviderProfile) userProfile;
            setAddress(serviceProviderProfile.getAddress());
            setPhoneNumber(serviceProviderProfile.getPhoneNumber());
            setDescription(serviceProviderProfile.getDescription());
            setRating(serviceProviderProfile.getRating());
            setLicensed(serviceProviderProfile.isLicensed());
            
            setAvailabilities(serviceProviderProfile.getAvailabilities());
            setServices(serviceProviderProfile.getServices());
            setFeedbacks(serviceProviderProfile.getFeedbacks());
            
            return true;
            
        }
        
        
        return false;
        
    }
}
