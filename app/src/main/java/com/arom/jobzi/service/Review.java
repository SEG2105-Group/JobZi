package com.arom.jobzi.service;

import java.io.Serializable;

public class Review implements Serializable {
    
    private static final float serialVersionUID = 1F;
    
    private String userGivingReview;
    
    private String comment;
    
    private double rating;
    
    public String getUserGivingReview() {
        return userGivingReview;
    }
    
    public void setUserGivingReview(String userGivingReview) {
        this.userGivingReview = userGivingReview;
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
