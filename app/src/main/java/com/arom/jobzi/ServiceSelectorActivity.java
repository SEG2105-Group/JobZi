package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.arom.jobzi.adapater.ServiceArrayAdapter;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.util.Util;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceSelectorActivity extends AppCompatActivity {
    
    public static final String SERVICES_TO_EXCLUDE_BUNDLE_ARG = "services_to_match";
    
    public static final String SERVICE_SELECTED_BUNDLE_ARG = "service_selected";
    
    public static final int SERVICE_SELECTED_RESULT = 0;
    public static final int CANCEL_RESULT = 1;
    public static final int NO_SERVICES_FOUND_RESULT = 2;
    
    private List<Service> serviceList;
    
    /**
     * These services are to be excluded when retrieving the list of all services that can be selected from. This is optional.
     */
    private Service[] servicesToExclude;
    
    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ContentLoadingProgressBar(this));
        
        if (getIntent().getExtras() != null) {
            servicesToExclude = (Service[]) getIntent().getExtras().getSerializable(SERVICES_TO_EXCLUDE_BUNDLE_ARG);
        }
        
        serviceList = new ArrayList<Service>();
        
        DatabaseReference servicesDatabase = Util.getInstance().getServicesDatabase();
        
        servicesDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                for (DataSnapshot serviceData : dataSnapshot.getChildren()) {
                    
                    Service service = serviceData.getValue(Service.class);
                    
                    if (servicesToExclude == null) {
                        serviceList.add(service);
                    } else if (!ArrayUtils.contains(servicesToExclude, service)) {
                        serviceList.add(service);
                    }
                    
                }
                
                if (serviceList.isEmpty()) {
                    ServiceSelectorActivity.this.setResult(NO_SERVICES_FOUND_RESULT);
                    ServiceSelectorActivity.this.finish();
                } else {
                    
                    setupServiceList();
                    
                }
                
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        });
    }
    
    private void setupServiceList() {
        
        ServiceSelectorActivity.this.setContentView(R.layout.activity_service_selector);
        
        ListView serviceListView = findViewById(R.id.serviceListView);
        final ServiceArrayAdapter adapter = new ServiceArrayAdapter(ServiceSelectorActivity.this, serviceList);
        serviceListView.setAdapter(adapter);
        
        serviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                
                Service selectedService = serviceList.get(i);
                
                Intent resultIntent = new Intent();
                
                Bundle resultBundle = new Bundle();
                resultBundle.putSerializable(SERVICE_SELECTED_BUNDLE_ARG, selectedService);
                resultIntent.putExtras(resultBundle);
                
                ServiceSelectorActivity.this.setResult(SERVICE_SELECTED_RESULT, resultIntent);
                ServiceSelectorActivity.this.finish();
                
            }
        });
        
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                ServiceSelectorActivity.this.setResult(CANCEL_RESULT);
                ServiceSelectorActivity.this.finish();
                
            }
        });
        
    }
    
}
