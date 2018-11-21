package com.arom.jobzi.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {
    
    public static final String TIME_SLOT_BUNDLE_ARG = "timeslot";

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calender = Calendar.getInstance();
        int hour = calender.get(Calendar.HOUR_OF_DAY);
        int minute = calender.get(Calendar.MINUTE);
        
        Date timeSlot = (Date) getArguments().getSerializable(TIME_SLOT_BUNDLE_ARG);
        calender.setTime(timeSlot);
        
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));

    }

}
