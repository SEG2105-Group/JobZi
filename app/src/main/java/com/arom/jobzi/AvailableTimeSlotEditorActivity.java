package com.arom.jobzi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arom.jobzi.util.TimePickerActivity;

public class AvailableTimeSlotEditorActivity extends AppCompatActivity {
	private TextView selectStartTime, selectEndTime;
	private Button saveButton, cancelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_available_time_slot_editor);

		selectStartTime = findViewById(R.id.selectStartTime);
		selectStartTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AvailableTimeSlotEditorActivity.this, TimePickerActivity.class);
				startActivity(intent);
			}
		});

		selectEndTime = findViewById(R.id.selectEndTime);
		selectEndTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(AvailableTimeSlotEditorActivity.this, TimePickerActivity.class);
				startActivity(intent);
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
	}
	
}
