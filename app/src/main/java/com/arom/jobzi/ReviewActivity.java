package com.arom.jobzi;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.service.Review;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.RatingsUtil;
import com.arom.jobzi.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    
    public static final String SERVICE_PROVIDER_BUNDLE_ARG = "service_provider";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
    
        Bundle bundle = getIntent().getExtras();
        
        final User serviceProvider = (User) bundle.getSerializable(SERVICE_PROVIDER_BUNDLE_ARG);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.rate_service_provider, serviceProvider.getUsername()));
        
        final RatingBar ratingBar = findViewById(R.id.ratingBar);
        final EditText commentEditText = findViewById(R.id.commentEditText);
        
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Review review = new Review();
                
                double currentRating = ratingBar.getRating();
                
                review.setUserGivingReview(FirebaseAuth.getInstance().getCurrentUser().getUid());
                review.setRating(currentRating);
                review.setComment(commentEditText.getText().toString());
    
                ServiceProviderProfile profile = (ServiceProviderProfile) serviceProvider.getUserProfile();
                
                List<Review> reviews = profile.getReviews();
                
                if(reviews == null) {
                    reviews = new ArrayList<Review>();
                    profile.setReviews(reviews);
                }
                
                reviews.add(review);
    
                if(profile.getRating() == RatingsUtil.DEFAULT_RATING) {
                    profile.setRating(currentRating);
                } else {
                    profile.setRating((profile.getRating() + currentRating) / 2);
                }
                
                DatabaseReference profilesDatabase = Util.getInstance().getProfilesDatabase();
                
                profilesDatabase.child(serviceProvider.getId()).setValue(profile);
                
                ReviewActivity.this.finish();
                
            }
        });
        
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewActivity.this.finish();
            }
        });
        
    }
}
