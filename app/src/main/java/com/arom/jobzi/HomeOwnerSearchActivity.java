package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.profile.UserProfile;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.util.TimeUtil;
import com.arom.jobzi.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeOwnerSearchActivity extends AppCompatActivity {

    private Availability availability;
    
    private Spinner dayOfWeekSpinner;
    private TextView availabilityTextView;
    
    private RatingBar ratingBar;
    
    private boolean useAvailability;
    private boolean useRating;

    private TextView serviceDisplayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_search);
    
        ActionBar actionBar = getSupportActionBar();
        
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        dayOfWeekSpinner = findViewById(R.id.dayOfWeekSpinner);
        ArrayAdapter<TimeUtil.Weekday> spinnerArrayAdapter = new ArrayAdapter<TimeUtil.Weekday>(this, android.R.layout.simple_spinner_dropdown_item, TimeUtil.Weekday.values()) {
            
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                
                View view = super.getDropDownView(position, convertView, parent);
    
                TextView textView = (TextView) view;
                
                textView.setText(TimeUtil.Weekday.values()[position].getName());
                
                return view;
                
            }
            
        };

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOfWeekSpinner.setAdapter(spinnerArrayAdapter);
        dayOfWeekSpinner.setSelection(TimeUtil.Weekday.MONDAY.ordinal());
        
        availabilityTextView = findViewById(R.id.availabilityTextView);
    
        availability = new Availability();
        availability.setStartTime(Calendar.getInstance().getTime());
        availability.setEndTime(Calendar.getInstance().getTime());
        
        availabilityTextView.setText(TimeUtil.formatAvailability(this, availability));
        
        availabilityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Intent toAvailableTimeSlotEditorIntent = new Intent(HomeOwnerSearchActivity.this, AvailableTimeSlotEditorActivity.class);
                
                Bundle bundle = new Bundle();
                bundle.putSerializable(AvailableTimeSlotEditorActivity.AVAILABILITY_BUNDLE_ARG, availability);
                bundle.putString(AvailableTimeSlotEditorActivity.DAY_TO_EDIT_BUNDLE_ARG, ((TimeUtil.Weekday) dayOfWeekSpinner.getSelectedItem()).getName());
                
                toAvailableTimeSlotEditorIntent.putExtras(bundle);
                
                HomeOwnerSearchActivity.this.startActivity(toAvailableTimeSlotEditorIntent);
                
            }
        });
        
        ratingBar = findViewById(R.id.ratingBar);
        
        CheckBox useAvailabilityCheckBox = findViewById(R.id.useAvailabilityCheckBox);
        
        useAvailabilityCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setUseAvailability(b);
            }
        });
        
        setUseAvailability(false);
        
        CheckBox useRatingCheckBox = findViewById(R.id.useRatingCheckBox);
        
        useRatingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setUserRating(b);
            }
        });
        
        setUserRating(false);
    
        Button searchForServiceProviderButton = findViewById(R.id.searchForServiceProviderButton);
        searchForServiceProviderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        
            
            
            }
        });
        
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeOwnerSearchActivity.this.finish();
            }
        });

        serviceDisplayer = findViewById(R.id.serviceDisplayer);
        Button setService = findViewById(R.id.setServiceButton);
        setService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeOwnerSearchActivity.this, ServiceSelectorActivity.class);
                startActivityForResult(intent,ServiceSelectorActivity.SERVICE_SELECTED_RESULT);
            }
        });

    }
    
    private void setUseAvailability(boolean useAvailability) {
        
        this.useAvailability = useAvailability;
    
        dayOfWeekSpinner.setEnabled(useAvailability);
        availabilityTextView.setEnabled(useAvailability);
        
    }
    
    private void setUserRating(boolean useRating) {
        
        this.useRating = useRating;
        
        ratingBar.setEnabled(useRating);
        
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case ServiceSelectorActivity.SERVICE_SELECTED_RESULT:

                final Service selectedService = (Service) data.getSerializableExtra(ServiceSelectorActivity.SERVICE_SELECTED_BUNDLE_ARG);
                serviceDisplayer.setText(selectedService.getName());
                break;
            case ServiceSelectorActivity.NO_SERVICES_FOUND_RESULT:
                Toast.makeText(this, "No services that you can add were found.", Toast.LENGTH_LONG).show();
                break;
            case ServiceSelectorActivity.CANCEL_RESULT:
                // No need to do anything.
                break;
        }
    }

}
