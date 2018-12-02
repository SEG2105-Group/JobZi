package com.arom.jobzi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.arom.jobzi.search.SearchResult;

public class ServiceProviderSelectorActivity extends AppCompatActivity {
    
    public static final String SEARCH_RESULT_BUNDLE_ARG = "search_result";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_selector);
        
        Bundle bundle = getIntent().getExtras();
        
        SearchResult searchResult = (SearchResult) bundle.getSerializable(SEARCH_RESULT_BUNDLE_ARG);
        
    }
}
