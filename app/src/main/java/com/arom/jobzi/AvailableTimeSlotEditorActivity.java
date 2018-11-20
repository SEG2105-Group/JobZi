package com.arom.jobzi;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.arom.jobzi.fragment.TimePickerFragment;

import java.util.Calendar;

public class AvailableTimeSlotEditorActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final String TIMESLOT_BUNDLE_ARG = "timeslot";

	private TextView selectStartTime, selectEndTime;
	private TextView startTimeViewer, endTimeViewer;
	private Button saveButton, cancelButton;
	private Calendar calendar;
	private TimePickerDialog timePickerDialog;
	private int hour, minute;
	private boolean startFlag, endFlag;
	private String amPm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_available_time_slot_editor);

		hour = calendar.get(Calendar.HOUR_OF_DAY);
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

        if (startFlag){
            startTimeViewer.setText(hour + ":" +minute + " " + getAMPM());
            startFlag = false;
        }
        if (endFlag){
            endTimeViewer.setText(hour + ":" +minute + " " + getAMPM());
            endFlag = false;
        }
    }

    public void showTimePickerDialog(){
        DialogFragment timePickerFrag = new TimePickerFragment();
        timePickerFrag.show(getSupportFragmentManager(), TIMESLOT_BUNDLE_ARG);
    }

	public String getAMPM(){
		amPm = "";
		if (! DateFormat.is24HourFormat(this)){
			if (hour >= 12){
				amPm = "PM";
			} else {
				amPm = "AM";
			}
		}
		return amPm;
	}
}