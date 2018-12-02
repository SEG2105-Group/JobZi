package com.arom.jobzi;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.service.Feedback;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.RatingsUtil;
import com.arom.jobzi.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {
    
    public static final String SERVICE_PROVIDER_BUNDLE_ARG = "service_provider";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    
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

                Feedback feedback = new Feedback();
                
                double currentRating = ratingBar.getRating();
                
                feedback.setUserGivingFeedback(FirebaseAuth.getInstance().getCurrentUser().getUid());
                feedback.setRating(currentRating);
                feedback.setComment(commentEditText.getText().toString());
    
                ServiceProviderProfile profile = (ServiceProviderProfile) serviceProvider.getUserProfile();
                
                List<Feedback> feedbacks = profile.getFeedbacks();
                
                if(feedbacks == null) {
                    feedbacks = new ArrayList<Feedback>();
                    profile.setFeedbacks(feedbacks);
                }
                
                feedbacks.add(feedback);
    
                if(profile.getRating() == RatingsUtil.DEFAULT_RATING) {
                    profile.setRating(currentRating);
                } else {
                    profile.setRating((profile.getRating() + currentRating) / 2);
                }
                
                DatabaseReference profilesDatabase = Util.getInstance().getProfilesDatabase();
                
                profilesDatabase.child(serviceProvider.getId()).setValue(profile);
                
                FeedbackActivity.this.finish();
                
            }
        });
        
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedbackActivity.this.finish();
            }
        });
        
    }
}
