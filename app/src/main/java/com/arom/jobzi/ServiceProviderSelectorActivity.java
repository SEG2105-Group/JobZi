package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.arom.jobzi.adapater.ServiceProviderExpandableArrayAdapter;
import com.arom.jobzi.search.SearchResult;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.user.User;

public class ServiceProviderSelectorActivity extends AppCompatActivity implements ServiceProviderExpandableArrayAdapter.OnAvailabilitySelectedListener {
    
    public static final String SEARCH_RESULT_BUNDLE_ARG = "search_result";
    public static final String SELECTED_SERVICE_PROVIDER_BUNDLE_ARG = "service_provider";
    
    public static final int SERVICE_PROVIDER_SELECTED_RESULT = 0;
    public static final int CANCEL_RESULT = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_selector);
        
        Bundle bundle = getIntent().getExtras();
        
        SearchResult searchResult = (SearchResult) bundle.getSerializable(SEARCH_RESULT_BUNDLE_ARG);
    
        ExpandableListView serviceProvidersExpandableListView = findViewById(R.id.serviceProvidersExpandableListView);
        ServiceProviderExpandableArrayAdapter arrayAdapter = new ServiceProviderExpandableArrayAdapter(this, this, searchResult.getAvailabilities(), searchResult.getServiceProviders());
        
        serviceProvidersExpandableListView.setAdapter(arrayAdapter);
        
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    
                ServiceProviderSelectorActivity.this.setResult(CANCEL_RESULT);
                ServiceProviderSelectorActivity.this.finish();
                
            }
        });
        
    }
    
    @Override
    public void onSelect(User serviceProvider, Availability availability) {
    
        Intent data = new Intent();
        
        Bundle bundle = new Bundle();
        bundle.putSerializable(SELECTED_SERVICE_PROVIDER_BUNDLE_ARG, serviceProvider);
        
        data.putExtras(bundle);
        
        setResult(SERVICE_PROVIDER_SELECTED_RESULT, data);
        
        // Start new activity here.
        Toast.makeText(this, "You have selected: " + serviceProvider.getUsername(), Toast.LENGTH_LONG).show();
        finish();
        
    }
    
}
