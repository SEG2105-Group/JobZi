package com.arom.jobzi.service;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.io.Serializable;

public class Service implements Serializable, Parcelable {

    private static final float serialVersionUID = 2F;

    private String id;

    private String name;
    private double rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if(obj instanceof Service) {

            Service service = (Service) obj;

            return this.getId().equals(service.getId());

        }

        return false;


    }
    
    public static final Parcelable.Creator<Service> CREATOR = new Parcelable.Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel parcel) {
            
            Service service = new Service();
            
            service.setId(parcel.readString());
            service.setName(parcel.readString());
            service.setRate(parcel.readDouble());
            
            return service;
            
        }
    
        @Override
        public Service[] newArray(int i) {
            return new Service[i];
        }
    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeDouble(rate);
    }
    
}
