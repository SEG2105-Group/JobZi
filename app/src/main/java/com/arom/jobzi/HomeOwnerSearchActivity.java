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
import android.widget.Spinner;
import android.widget.TextView;

import com.arom.jobzi.service.Availability;
import com.arom.jobzi.util.TimeUtil;

import java.util.Calendar;

public class HomeOwnerSearchActivity extends AppCompatActivity {

    private Availability availability;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_search);
    
        ActionBar actionBar = getSupportActionBar();
        
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        Spinner dayOfWeekSpinner = findViewById(R.id.dayOfWeekSpinner);
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
        
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeOwnerSearchActivity.this.finish();
            }
        });
        
        TextView availabilityTextView = findViewById(R.id.availabilityTextView);
    
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
        
//        CheckBox useDayCheckBox = findViewById(R.id.use)
        
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
