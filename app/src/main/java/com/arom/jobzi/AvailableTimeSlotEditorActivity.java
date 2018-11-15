package com.arom.jobzi;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.arom.jobzi.service.AvailableTimeSlot;

public class AvailableTimeSlotEditorActivity extends AppCompatActivity {

    public static final String TIMESLOT_BUNDLE_ARG = "timeslot";

	private TextView selectStartTime, selectEndTime;
	private TextView startTimeViewer, endTimeViewer;
	private Button saveButton, cancelButton;
	private TimePickerDialog timePickerDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_available_time_slot_editor);

		final AvailableTimeSlot availableTimeSlot = (AvailableTimeSlot) getIntent().getSerializableExtra(TIMESLOT_BUNDLE_ARG);

		selectStartTime = findViewById(R.id.selectStartTime);
		selectStartTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(AvailableTimeSlotEditorActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    }
                }, 0, 0, true);
                timePickerDialog.show();
			}
		});

		selectEndTime = findViewById(R.id.selectEndTime);
		selectEndTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Intent intent = new Intent(AvailableTimeSlotEditorActivity.this, TimePickerActivity.class);
				//startActivity(intent);
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
				//AvailableTimeSlotsListFragment is fragment not activity, so cannot start an activity next line
				AvailableTimeSlotEditorActivity.this.finish();
			}
		});

		//startTimeViewer = findViewById(R.id.startTimeViewer);
		//startTimeViewer.setText(TimePickerActivity.onTimeSet());

		endTimeViewer = findViewById(R.id.endTimeViewer);
	}

	public TimePickerDialog getTime(){
	    return timePickerDialog;
    }
}
