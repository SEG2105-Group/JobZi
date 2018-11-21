package com.arom.jobzi;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.arom.jobzi.adapater.AvailabilitiesExpandableListAdapter;
import com.arom.jobzi.adapater.ServiceArrayAdapter;
import com.arom.jobzi.fragment.TimePickerFragment;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.service.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AvailableTimeSlotEditorActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final String TIMESLOT_BUNDLE_ARG = "timeslot";

	private TextView selectStartTime, selectEndTime;
	private TextView startTimeViewer, endTimeViewer;
	private Button saveButton, cancelButton;
	private Calendar calendar;
	private int hour, minute;
	private int startHour, startMinute;
	private int endHour, endMinute;
	private boolean startFlag, endFlag;
	private String amPm;
	private TimePickerDialog timePickerDialog;
	private List<Availability> availabilityList;

    public static final String AVAILIBILITY_SELECTED_BUNDLE_ARG = "availibility_selected";
    public static final int AVAILIBILITY_SELECTED_RESULT = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_available_time_slot_editor);

		availabilityList = new ArrayList<>();

        calendar = Calendar.getInstance();

        if (DateFormat.is24HourFormat(this)){
            hour = calendar.get(Calendar.HOUR_OF_DAY);
        } else {
            hour = calendar.get(Calendar.HOUR);
        }
		minute = calendar.get(Calendar.MINUTE);

        startTimeViewer = findViewById(R.id.startTimeViewer);
        endTimeViewer = findViewById(R.id.endTimeViewer);

        startTimeViewer.setText(hour + ":" + minute + " " + getAMPM());
        endTimeViewer.setText(hour + ":" + minute + " " + getAMPM());

		selectStartTime = findViewById(R.id.selectStartTime);
		selectStartTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                showTimePickerDialog();
                startFlag = true;
			}
		});

		selectEndTime = findViewById(R.id.selectEndTime);
		selectEndTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                showTimePickerDialog();
                endFlag = true;
			}
		});

		saveButton = findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AvailableTimeSlotEditorActivity.this.finish();
			}
		});

		cancelButton = findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AvailableTimeSlotEditorActivity.this.finish();
			}
		});

		endTimeViewer = findViewById(R.id.endTimeViewer);
	}

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        if (!DateFormat.is24HourFormat(this)){
            if (hour == 0)
                hour = 12;
        }

	    this.hour = endHour;
	    this.minute = endMinute;

	    if (startFlag){
	        startHour = hour;
	        startMinute = minute;
            startTimeViewer.setText(hour + ":" + minute + " " + getAMPM());
            startFlag = false;
        }
        if (endFlag){
	        endHour = hour;
	        endMinute = minute;
            endTimeViewer.setText(hour + ":" + minute + " " + getAMPM());
			endFlag = false;
        }
    }

    public void showTimePickerDialog(){
        DialogFragment timePickerFrag = new TimePickerFragment();
        timePickerFrag.show(getSupportFragmentManager(), TIMESLOT_BUNDLE_ARG);
    }

	public String getAMPM(){
		amPm = "";
		if (DateFormat.is24HourFormat(this)) {
            return amPm;
        }

        if (calendar.get(Calendar.AM_PM) == Calendar.AM)
            amPm = "AM";
        else if (calendar.get(Calendar.AM_PM) == Calendar.PM)
            amPm = "PM";

		return amPm;
	}


    private void setupAvailibilities() {

        AvailableTimeSlotEditorActivity.this.setContentView(R.layout.activity_available_time_slot_editor);

        ListView availibilityTextView = findViewById(R.id.availibilityItemTextView);
        final AvailabilitiesExpandableListAdapter adapter = new AvailabilitiesExpandableListAdapter(AvailableTimeSlotEditorActivity.this);
        availibilityTextView.setAdapter((ListAdapter) adapter);

        validateTime(startHour, endHour, startMinute, endMinute);

        availibilityTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Availability selectedAvailibility = availabilityList.get(i);

                Intent resultIntent = new Intent();
                Bundle resultBundle = new Bundle();

                resultBundle.putSerializable(AVAILIBILITY_SELECTED_BUNDLE_ARG, selectedAvailibility);
                resultIntent.putExtras(resultBundle);

                AvailableTimeSlotEditorActivity.this.setResult(AVAILIBILITY_SELECTED_RESULT, resultIntent);
                AvailableTimeSlotEditorActivity.this.finish();

            }
        });
    }

    public void validateTime(int startHour, int endHour, int startMinute, int endMinute) {
        if (startHour > endHour){
            int tempHour = startHour;
            startHour = endHour;
            endHour = tempHour;

            int tempMinute = startMinute;
            startMinute = endMinute;
            endMinute = tempMinute;
        }
    }
}