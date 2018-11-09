package com.arom.jobzi.service;

import java.io.Serializable;

public class Service implements Serializable {

    private static final float serialVersionUID = 1L;

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

}
