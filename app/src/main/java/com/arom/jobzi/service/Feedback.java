package com.arom.jobzi.service;

import java.io.Serializable;

public class Feedback implements Serializable {
    
    private static final float serialVersionUID = 1F;
    
    private String userGivingFeedback;
    
    private String comment;
    
    private double rating;
    
    public String getUserGivingFeedback() {
        return userGivingFeedback;
    }
    
    public void setUserGivingFeedback(String userGivingFeedback) {
        this.userGivingFeedback = userGivingFeedback;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
    }
}
