package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.arom.jobzi.adapater.ServiceArrayAdapter;
import com.arom.jobzi.fragment.serviceprovider.ServiceProviderServicesFragment;
import com.arom.jobzi.service.Service;

import java.util.List;

public class HomeOwnerSearchActivity extends AppCompatActivity {

    private ServiceArrayAdapter serviceArrayAdapter;
    private List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_search);
    
        ActionBar actionBar = getSupportActionBar();
        
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeOwnerSearchActivity.this.finish();
            }
        });


        Button setService = findViewById(R.id.setServiceButton);
        setService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeOwnerSearchActivity.this, ServiceSelectorActivity.class);
                startActivity(intent);
            }
        });
        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        
        return super.onOptionsItemSelected(item);
        
    }
}
