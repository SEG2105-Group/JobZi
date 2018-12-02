package com.arom.jobzi;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.arom.jobzi.user.User;

public class RatingActivity extends AppCompatActivity {
    
    private User serviceProvider;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
    
        Bundle bundle = getIntent().getExtras();
        
        serviceProvider = (User) bundle.getSerializable(ServiceProviderSelectorActivity.SELECTED_SERVICE_PROVIDER_BUNDLE_ARG);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.rate_service_provider, serviceProvider.getUsername()));
        
        Button saveButton = findViewById(R.id.saveButton);
        
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingActivity.this.finish();
            }
        });
        
    }
}
