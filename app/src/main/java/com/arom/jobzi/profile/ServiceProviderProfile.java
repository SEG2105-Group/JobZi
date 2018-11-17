package com.arom.jobzi.profile;

import com.arom.jobzi.service.AvailableTimeSlot;
import com.arom.jobzi.service.Service;

import java.util.List;

public class ServiceProviderProfile extends UserProfile {
	
	private List<AvailableTimeSlot> availabilities;
	private List<Service> services;
	
	private String address;
	private String phoneNumber;
	private String description;
	
	private boolean licensed;
	
	public List<AvailableTimeSlot> getAvailabilities() {
		return availabilities;
	}
	
	public void setAvailabilities(List<AvailableTimeSlot> availabilities) {
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isLicensed() {
		return licensed;
	}
	
	public void setLicensed(boolean licensed) {
		this.licensed = licensed;
	}
	
	@Override
	public boolean copyFrom(UserProfile userProfile) {
		
		if(userProfile instanceof ServiceProviderProfile) {
			
			ServiceProviderProfile serviceProviderProfile = (ServiceProviderProfile) userProfile;
			setAddress(serviceProviderProfile.getAddress());
			setPhoneNumber(serviceProviderProfile.getPhoneNumber());
			setDescription(serviceProviderProfile.getDescription());
			setLicensed(serviceProviderProfile.isLicensed());
			
			return true;
			
		}
		
		
		return false;
		
	}
}
