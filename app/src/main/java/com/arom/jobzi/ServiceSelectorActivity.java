package com.arom.jobzi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.service.ServiceArrayAdapter;
import com.arom.jobzi.user.SessionManager;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceSelectorActivity extends AppCompatActivity {
    
    private ServiceProviderProfile profile;
    private List<Service> serviceList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ContentLoadingProgressBar(this));
    
        User user = SessionManager.getInstance().getUser();
        
        profile = (ServiceProviderProfile) user.getUserProfile();
        
        serviceList = new ArrayList<Service>();
        
        Util.getInstance().addSingleValueServicesListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                for(DataSnapshot serviceData: dataSnapshot.getChildren()) {
                    
                    Service service = serviceData.getValue(Service.class);
                    
                    if(!profile.getServices().contains(service)) {
                        serviceList.add(service);
                    }
                    
                }
                
                if(serviceList.isEmpty()) {
                    Toast.makeText(ServiceSelectorActivity.this, "No services that you can add were found.", Toast.LENGTH_LONG).show();
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
                profile.getServices().add(selectedService);
            
                Util.getInstance().updateUser(SessionManager.getInstance().getUser());
                
                ServiceSelectorActivity.this.finish();
                
                /*
                Bundle bundle = new Bundle();
                bundle.putSerializable(SERVICE_BUNDLE_ARG, selectedService);
                
                Intent resultIntent = new Intent();
                resultIntent.putExtras(bundle);
                
                ServiceSelectorActivity.this.setResult(SERVICE_SELECTED_RESULT, resultIntent);
                ServiceSelectorActivity.this.finish();
                */
            
            }
        });
        
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                ServiceSelectorActivity.this.finish();
                
            }
        });
    
    }
    
}
