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

import com.arom.jobzi.util.TimePickerFragment;

import java.util.Date;

public class AvailableTimeSlotEditorActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final String TIMESLOT_BUNDLE_ARG = "timeslot";

	private TextView selectStartTime, selectEndTime;
	private TextView startTimeViewer, endTimeViewer;
	private Button saveButton, cancelButton;
	private TimePickerDialog timePickerDialog;
	private int hour, minute;
	private boolean startFlag, endFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_available_time_slot_editor);
        startTimeViewer = findViewById(R.id.startTimeViewer);
        endTimeViewer = findViewById(R.id.endTimeViewer);


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
		DateFormat.format("hh:mm", new Date(0,0,0, hour, minute));
        if (startFlag){
            startTimeViewer.setText(DateFormat.format("hh:mm", new Date(0,0,0, hour, minute)));
            startFlag = false;
        }
        if (endFlag){
            endTimeViewer.setText(hour + ":" + minute);
            endFlag = false;
        }
    }

    public void showTimePickerDialog(){
        DialogFragment timePickerFrag = new TimePickerFragment();
        timePickerFrag.show(getSupportFragmentManager(), TIMESLOT_BUNDLE_ARG);
    }
}