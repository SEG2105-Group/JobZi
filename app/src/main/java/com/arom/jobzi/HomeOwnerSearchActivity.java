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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arom.jobzi.search.SearchQuery;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.SearchUtil;
import com.arom.jobzi.util.TimeUtil;

public class HomeOwnerSearchActivity extends AppCompatActivity {
    
    private static final int EDIT_AVAILABILITY_REQUEST = 0;
    private static final int SELECT_SERVICE_REQUEST = 1;
    private static final int SELECT_SERVICE_PROVIDER_REQUEST = 2;
    
    private Availability availability;
    private Service selectedService;
    
    private TextView serviceDisplayTextView;
    
    private Spinner dayOfWeekSpinner;
    private TextView availabilityTextView;
    
    private RatingBar ratingBar;
    
    private Button searchForServiceProviderButton;
    
    private boolean useRating;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_search);
        
        ActionBar actionBar = getSupportActionBar();
        
        if (actionBar != null) {
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
        
        availability = TimeUtil.createDefaultAvailability();
        availabilityTextView.setText(TimeUtil.formatAvailability(this, availability));
        
        availabilityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Intent toAvailableTimeSlotEditorIntent = new Intent(HomeOwnerSearchActivity.this, AvailableTimeSlotEditorActivity.class);
                
                Bundle bundle = new Bundle();
                bundle.putSerializable(AvailableTimeSlotEditorActivity.AVAILABILITY_BUNDLE_ARG, availability);
                bundle.putString(AvailableTimeSlotEditorActivity.DAY_TO_EDIT_BUNDLE_ARG, ((TimeUtil.Weekday) dayOfWeekSpinner.getSelectedItem()).getName());
                
                toAvailableTimeSlotEditorIntent.putExtras(bundle);
                
                HomeOwnerSearchActivity.this.startActivityForResult(toAvailableTimeSlotEditorIntent, EDIT_AVAILABILITY_REQUEST);
                
            }
        });
        
        ratingBar = findViewById(R.id.ratingBar);
        
        CheckBox useRatingCheckBox = findViewById(R.id.useRatingCheckBox);
        
        useRatingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setUserRating(b);
            }
        });
        
        setUserRating(false);
        
        searchForServiceProviderButton = findViewById(R.id.searchForServiceProviderButton);
        searchForServiceProviderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                
                SearchQuery searchQuery = new SearchQuery();
                
                searchQuery.setService(selectedService);
                
                searchQuery.setAvailability(availability);
                searchQuery.setWeekday((TimeUtil.Weekday) dayOfWeekSpinner.getSelectedItem());
                
                if (useRating) {
                    searchQuery.setRating(ratingBar.getRating());
                } else {
                    searchQuery.setRating(SearchQuery.IGNORE_RATING);
                }
                
                SearchUtil.displaySearchResult(HomeOwnerSearchActivity.this, searchQuery);
                
            }
        });
        
        searchForServiceProviderButton.setEnabled(false);
        
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeOwnerSearchActivity.this.finish();
            }
        });
        
        selectedService = null;
        
        serviceDisplayTextView = findViewById(R.id.serviceDisplayTextView);
        
        Button setServiceButton = findViewById(R.id.setServiceButton);
        setServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeOwnerSearchActivity.this, ServiceSelectorActivity.class);
                startActivityForResult(intent, SELECT_SERVICE_REQUEST);
            }
        });
        
    }
    
    private void updateSelectedService(Service selectedService) {
        
        this.selectedService = selectedService;
        
        serviceDisplayTextView.setText(selectedService.getName());
        
        updateSearchButton();
        
    }
    
    private void updateAvailability(Availability availability) {
        
        this.availability = availability;
        
        availabilityTextView.setText(TimeUtil.formatAvailability(this, availability));
    
        updateSearchButton();
    
    }
    
    private void updateSearchButton() {
        
        searchForServiceProviderButton.setEnabled(selectedService != null && availability != null);
        
    }
    
    private void setUserRating(boolean useRating) {
        
        this.useRating = useRating;
        
        ratingBar.setEnabled(useRating);
        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        
        return super.onOptionsItemSelected(item);
        
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        switch (requestCode) {
            case SELECT_SERVICE_REQUEST:
                switch (resultCode) {
                    case ServiceSelectorActivity.SERVICE_SELECTED_RESULT:
                        
                        Bundle bundle = data.getExtras();
                        
                        Service selectedService = (Service) bundle.getSerializable(ServiceSelectorActivity.SERVICE_SELECTED_BUNDLE_ARG);
                        
                        updateSelectedService(selectedService);
                        
                        break;
                    case ServiceSelectorActivity.NO_SERVICES_FOUND_RESULT:
                        Toast.makeText(this, "No services were found at this time.", Toast.LENGTH_LONG).show();
                        break;
                    case ServiceSelectorActivity.CANCEL_RESULT:
                        // No need to do anything.
                        break;
                }
                break;
            case EDIT_AVAILABILITY_REQUEST:
                
                switch (resultCode) {
                    case AvailableTimeSlotEditorActivity.AVAILABILITY_SAVED_RESULT:
                        
                        Availability availability = (Availability) data.getSerializableExtra(AvailableTimeSlotEditorActivity.AVAILABILITY_BUNDLE_ARG);
                        
                        updateAvailability(availability);
                        
                        break;
                }
                
                break;
            case SELECT_SERVICE_PROVIDER_REQUEST:
                
                switch (resultCode) {
                    case ServiceProviderSelectorActivity.SERVICE_PROVIDER_SELECTED_RESULT:
                        
                        Bundle bundle = data.getExtras();
                        
                        User user = (User) bundle.getSerializable(ServiceProviderSelectorActivity.SELECTED_SERVICE_PROVIDER_BUNDLE_ARG);
                        
                        finish();
                        
                        break;
                    case ServiceProviderSelectorActivity.CANCEL_RESULT:
                        // No need to do anything.
                        break;
                }
                
                break;
            
        }
    }
    
}
