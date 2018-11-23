package com.arom.jobzi.util;

import android.content.Context;
import android.text.format.DateFormat;

import com.arom.jobzi.service.Availability;

import java.util.Date;

public final class TimeFormatterUtil {
    
    public static String formatAvailability(Context context, Availability availability) {
        
        return formatTime(context, availability.getStartTime()) + " - " + formatTime(context, availability.getEndTime());
        
    }
    
    public static String formatTime(Context context, Date time) {
        return DateFormat.getTimeFormat(context).format(time);
    }
    
}
