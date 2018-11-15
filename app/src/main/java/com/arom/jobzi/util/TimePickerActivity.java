package com.arom.jobzi.util;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.arom.jobzi.AvailableTimeSlotEditorActivity;
import com.arom.jobzi.R;
import com.arom.jobzi.fragment.AvailableTimeSlotsListFragment;

import java.sql.Time;

public class TimePickerActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.activity_time_picker);

        timePicker = findViewById(R.id.timeSelector);

        final int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, AvailableTimeSlotEditorActivity.class);
                //startActivity(intent);
                TimePickerActivity.this.finish();
            }
        });

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerActivity.this.finish();
            }
        });


    }
}
