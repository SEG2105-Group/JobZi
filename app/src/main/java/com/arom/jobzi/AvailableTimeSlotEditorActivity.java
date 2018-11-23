package com.arom.jobzi;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.arom.jobzi.fragment.TimePickerFragment;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.util.TimeFormatterUtil;

import java.util.Calendar;
import java.util.Date;

public class AvailableTimeSlotEditorActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final String DAY_TO_EDIT_BUNDLE_ARG = "day";
    public static final String AVAILABILITY_BUNDLE_ARG = "availability";
    public static final String AVAILABILITY_INDEX_BUNDLE_ARG = "availability_index";

    public static final int AVAILABILITY_ADDED_RESULT = 0;
    public static final int AVAILABILITY_SAVED_RESULT = 1;
    public static final int CANCEL_RESULT = 2;

    private TextView startTimeTextView;
    private TextView endTimeTextView;
    private Button saveButton;
    private Button cancelButton;

    private Calendar calendar;
    private Availability availability;

    private String day;
    private int availabilityIndex;

    private String amPm;

    private int hour, minute;
    private boolean startFlag, endFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_time_slot_editor);

        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        startTimeTextView = findViewById(R.id.startTimeTextView);
        startTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(availability.getStartTime());
                startFlag = true;
            }
        });

        endTimeTextView = findViewById(R.id.endTimeTextView);
        endTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(availability.getEndTime());
                endFlag = true;
            }
        });

        Bundle bundle = getIntent().getExtras();

        day = bundle.getString(DAY_TO_EDIT_BUNDLE_ARG);
        availabilityIndex = bundle.getInt(AVAILABILITY_INDEX_BUNDLE_ARG);
        availability = (Availability) bundle.getSerializable(AVAILABILITY_BUNDLE_ARG);

        if (availability == null) {

            availability = new Availability();

            availability.setStartTime(calendar.getTime());

            // Increment by 30 minutes.
            calendar.add(Calendar.MINUTE, 30);

            availability.setEndTime(calendar.getTime());

        }

        updateTimeViews();

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

    private void updateTimeViews() {

        startTimeTextView.setText(TimeFormatterUtil.formatTime(this, availability.getStartTime()));
        endTimeTextView.setText(TimeFormatterUtil.formatTime(this, availability.getEndTime()));

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

        if (startFlag) {

            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, minute);

            availability.getStartTime().setTime(calendar.getTimeInMillis());

            startFlag = false;

        } else if (endFlag) {

            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, minute);

            availability.getEndTime().setTime(calendar.getTimeInMillis());

            endFlag = false;
        }

        updateTimeViews();

    }

    public void showTimePickerDialog(Date time) {

        DialogFragment timePickerFrag = new TimePickerFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(TimePickerFragment.TIME_TO_EDIT_BUNDLE_ARG, time);

        timePickerFrag.setArguments(bundle);

        timePickerFrag.show(getSupportFragmentManager(), "");

    }

}