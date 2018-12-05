package com.arom.jobzi;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.search.SearchQuery;
import com.arom.jobzi.service.Review;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Deliverable4Test {
    
    @Test
    public void testSetRating() {
    
        ServiceProviderProfile profile = new ServiceProviderProfile();
        
        profile.setRating(4.5);
    
        Assert.assertEquals(profile.getRating(), 4.5, 0.1);
        
    }
    
    @Test
    public void testReview() {
        
        Review review = new Review();
        
        review.setRating(3.0);
        
        Assert.assertEquals(review.getRating(), 3.0, 0.1);
        
    }
    
    @Test
    public void testSearchQuery() {
    
        SearchQuery query = new SearchQuery();
        
        query.setRating(1.5);
        
        Assert.assertEquals(query.getRating(), 1.5, 0.1);
        
    }
    
}
