package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arom.jobzi.fragment.TimePickerFragment;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.util.TimeFormatterUtil;

import java.util.Calendar;
import java.util.Date;

public class AvailableTimeSlotEditorActivity extends AppCompatActivity {
    
    public static final String DAY_TO_EDIT_BUNDLE_ARG = "day";
    public static final String AVAILABILITY_BUNDLE_ARG = "availability";
    public static final String AVAILABILITY_INDEX_BUNDLE_ARG = "availability_index";
    public static final String OTHER_AVAILABILITIES_BUNDLE_ARG = "other_availabilities";
    
    public static final int AVAILABILITY_ADDED_RESULT = 0;
    public static final int AVAILABILITY_SAVED_RESULT = 1;
    public static final int CANCEL_RESULT = 2;
    
    private TextView startTimeTextView;
    private TextView endTimeTextView;
    private Button saveButton;
    private Button cancelButton;
    
    private Availability availability;
    // Use this to implement time slot collision detection but that seems complex.
    // ALso, Date stores more than just time which might cause serious issues.
    private Availability[] otherAvailabilities;
    
    private String day;
    private int availabilityIndex;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_time_slot_editor);
        
        startTimeTextView = findViewById(R.id.startTimeTextView);
        startTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartTimePickerDialog();
            }
        });
        
        endTimeTextView = findViewById(R.id.endTimeTextView);
        endTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEndTimePickerDialog();
            }
        });
        
        Bundle bundle = getIntent().getExtras();
        
        day = bundle.getString(DAY_TO_EDIT_BUNDLE_ARG);
        availabilityIndex = bundle.getInt(AVAILABILITY_INDEX_BUNDLE_ARG);
        availability = (Availability) bundle.getSerializable(AVAILABILITY_BUNDLE_ARG);
        otherAvailabilities = (Availability[]) bundle.getSerializable(OTHER_AVAILABILITIES_BUNDLE_ARG);
        
        TextView dayOfWeekTextView = findViewById(R.id.dayOfWeekTextView);
        dayOfWeekTextView.setText(getString(R.string.day_of_week_placeholder, day));
        
        Calendar calendar = Calendar.getInstance();
        
        if (availability == null) {
            
            availability = new Availability();
            
            availability.setStartTime(calendar.getTime());
            
            // Increment by 30 minutes.
            calendar.add(Calendar.MINUTE, 30);
            
            availability.setEndTime(calendar.getTime());
            
        }
        
        updateTime();
        
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                saveButton.setEnabled(false);
                
                Intent result = new Intent();
                Bundle bundle = new Bundle();
                
                bundle.putString(DAY_TO_EDIT_BUNDLE_ARG, day);
                bundle.putInt(AVAILABILITY_INDEX_BUNDLE_ARG, availabilityIndex);
                bundle.putSerializable(AVAILABILITY_BUNDLE_ARG, availability);
                
                result.putExtras(bundle);
                
                AvailableTimeSlotEditorActivity.this.setResult(AVAILABILITY_SAVED_RESULT, result);
                AvailableTimeSlotEditorActivity.this.finish();
                
            }
        });
        
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvailableTimeSlotEditorActivity.this.setResult(CANCEL_RESULT);
                AvailableTimeSlotEditorActivity.this.finish();
            }
        });
        
    }
    
    private void updateTime() {
        
        startTimeTextView.setText(TimeFormatterUtil.formatTime(this, availability.getStartTime()));
        endTimeTextView.setText(TimeFormatterUtil.formatTime(this, availability.getEndTime()));
        
    }
    
    private void showStartTimePickerDialog() {
        showTimePickerDialog(availability.getStartTime(), new TimePickerFragment.CustomTimeSetListener() {
            @Override
            public void onTimeSet(Date time) {
                
                Calendar newStartTime = Calendar.getInstance();
                newStartTime.setTime(time);
                
                Calendar oldStartTime = Calendar.getInstance();
                oldStartTime.setTime(availability.getStartTime());
                
                Calendar endTime = Calendar.getInstance();
                endTime.setTime(availability.getEndTime());
                
                Availability conflicting;
                
                if (compareTo(newStartTime, endTime) >= 0) {
                    Toast.makeText(AvailableTimeSlotEditorActivity.this, "Please select a time before " + TimeFormatterUtil.formatTime(AvailableTimeSlotEditorActivity.this, availability.getEndTime()), Toast.LENGTH_LONG).show();
                    saveButton.setEnabled(false);
                } else if ((conflicting = getConflicting(newStartTime, endTime)) != null) {
                    Toast.makeText(AvailableTimeSlotEditorActivity.this, "This time conflicts with: " + TimeFormatterUtil.formatAvailability(AvailableTimeSlotEditorActivity.this, conflicting), Toast.LENGTH_SHORT).show();
                    saveButton.setEnabled(false);
                } else {
                    saveButton.setEnabled(true);
                }
                availability.setStartTime(time);
                updateTime();
                
            }
        });
    }
    
    private void showEndTimePickerDialog() {
        showTimePickerDialog(availability.getEndTime(), new TimePickerFragment.CustomTimeSetListener() {
            @Override
            public void onTimeSet(Date time) {
                
                Calendar newEndTime = Calendar.getInstance();
                newEndTime.setTime(time);
                
                Calendar oldEndTime = Calendar.getInstance();
                oldEndTime.setTime(availability.getEndTime());
                
                Calendar startTime = Calendar.getInstance();
                startTime.setTime(availability.getStartTime());
                
                Availability conflicting;
                
                if (compareTo(startTime, newEndTime) >= 0) {
                    Toast.makeText(AvailableTimeSlotEditorActivity.this, "Please select a time after " + TimeFormatterUtil.formatTime(AvailableTimeSlotEditorActivity.this, availability.getStartTime()), Toast.LENGTH_LONG).show();
                    saveButton.setEnabled(false);
                } else if ((conflicting = getConflicting(startTime, newEndTime)) != null) {
                    Toast.makeText(AvailableTimeSlotEditorActivity.this, "This time conflicts with: " + TimeFormatterUtil.formatAvailability(AvailableTimeSlotEditorActivity.this, conflicting), Toast.LENGTH_SHORT).show();
                    saveButton.setEnabled(false);
                } else {
                    saveButton.setEnabled(true);
                }
                
                availability.setEndTime(time);
                updateTime();
                
            }
        });
    }
    
    private Availability getConflicting(Calendar startTime, Calendar endTime) {
        
        Calendar availabilityStartTime = Calendar.getInstance();
        Calendar availabilityEndTime = Calendar.getInstance();
        
        for (Availability availability : otherAvailabilities) {
            
            availabilityStartTime.setTime(availability.getStartTime());
            availabilityEndTime.setTime(availability.getEndTime());
            
            if ((compareTo(startTime, availabilityEndTime) <= 0 && compareTo(startTime, availabilityStartTime) >= 0) ||
                    (compareTo(endTime, availabilityStartTime) >= 0 && compareTo(endTime, availabilityEndTime) <= 0) ||
                    (compareTo(startTime, availabilityStartTime) <= 0 && compareTo(endTime, availabilityEndTime) >= 0)) {
                return availability;
            }
            
        }
        
        return null;
        
    }
    
    /**
     * @param first
     * @param second
     * @return 1 if first > second, -1 if first < second, and 0 if first == second. Basically, it is a normalized first - second.
     */
    private int compareTo(Calendar first, Calendar second) {
        
        if (first.get(Calendar.HOUR_OF_DAY) > second.get(Calendar.HOUR_OF_DAY)) {
            return 1;
        } else if (first.get(Calendar.HOUR_OF_DAY) < second.get(Calendar.HOUR_OF_DAY)) {
            return -1;
        } else {
            
            if (first.get(Calendar.MINUTE) > second.get(Calendar.MINUTE)) {
                return 1;
            } else if (first.get(Calendar.MINUTE) < second.get(Calendar.MINUTE)) {
                return -1;
            }
            
        }
        
        return 0;
        
    }
    
    private void showTimePickerDialog(Date time, TimePickerFragment.CustomTimeSetListener timeSetListener) {
        
        DialogFragment timePickerFrag = new TimePickerFragment();
        
        Bundle bundle = new Bundle();
        bundle.putSerializable(TimePickerFragment.TIME_TO_EDIT_BUNDLE_ARG, time);
        bundle.putSerializable(TimePickerFragment.TIME_SET_LISTENER_BUNDLE_ARG, timeSetListener);
        
        timePickerFrag.setArguments(bundle);
        
        timePickerFrag.show(getSupportFragmentManager(), "");
        
    }
    
}
