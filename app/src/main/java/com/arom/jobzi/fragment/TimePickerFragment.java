package com.arom.jobzi.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {
    
    public static final String TIME_TO_EDIT_BUNDLE_ARG = "timeslot";
    public static final String TIME_SET_LISTENER_BUNDLE_ARG = "time_set_listener";
    
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        Date timeToEdit = (Date) bundle.getSerializable(TIME_TO_EDIT_BUNDLE_ARG);
        final CustomTimeSetListener timeSetListener = (CustomTimeSetListener) bundle.getSerializable(TIME_SET_LISTENER_BUNDLE_ARG);

        Calendar calender = Calendar.getInstance();
        calender.setTime(timeToEdit);

        int hour = calender.get(Calendar.HOUR_OF_DAY);
        int minute = calender.get(Calendar.MINUTE);
        
        Date timeSlot = (Date) getArguments().getSerializable(TIME_TO_EDIT_BUNDLE_ARG);
        calender.setTime(timeSlot);
        
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                
                Date date = calendar.getTime();
                
                timeSetListener.onTimeSet(date);
                
            }
        }, hour, minute, DateFormat.is24HourFormat(getActivity()));
        
        return timePickerDialog;
        
    }

    public interface CustomTimeSetListener extends Serializable {
        void onTimeSet(Date time);
    }
    
}
