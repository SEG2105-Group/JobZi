package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.TextView;

import com.arom.jobzi.service.Availability;
import com.arom.jobzi.util.TimeUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Deliverable3Test {
    
    @Rule
    public ActivityTestRule<AvailableTimeSlotEditorActivity> availabilityEditorRule = new ActivityTestRule<AvailableTimeSlotEditorActivity>(AvailableTimeSlotEditorActivity.class, true, false);
    
    private AvailableTimeSlotEditorActivity availableTimeSlotEditorActivity;
    
    @Before
    public void setup() {
        
        Intent startEditorIntent = new Intent();
        Bundle bundle = new Bundle();
        startEditorIntent.putExtras(bundle);
        
        availableTimeSlotEditorActivity = availabilityEditorRule.launchActivity(startEditorIntent);
        
    }
    
    @Test
    public void testTimeComparison() {
    
        Calendar startTime = Calendar.getInstance();
        
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.MINUTE, 10);
        
        Assert.assertTrue(TimeUtil.compareTo(endTime, startTime) >= 0);
        
    }
    
    @Test
    @UiThreadTest
    public void testTimeTextView() {
    
        Availability availability = TimeUtil.createDefaultAvailability();
        
        TextView startTimeTextView = availableTimeSlotEditorActivity.findViewById(R.id.startTimeTextView);
        
        startTimeTextView.setText(TimeUtil.formatAvailability(availableTimeSlotEditorActivity, availability));
        
        Assert.assertEquals(TimeUtil.formatAvailability(availableTimeSlotEditorActivity, availability), startTimeTextView.getText().toString());
        
    }
    
    @Test
    @UiThreadTest
    public void testSaveButton() {
        
        Button saveButton = availableTimeSlotEditorActivity.findViewById(R.id.saveButton);
        Assert.assertTrue(saveButton.isEnabled());
        
    }
    
}
