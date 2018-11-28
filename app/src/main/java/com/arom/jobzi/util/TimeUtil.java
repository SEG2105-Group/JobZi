package com.arom.jobzi.util;

import android.content.Context;
import android.text.format.DateFormat;

import com.arom.jobzi.service.Availability;

import java.util.Calendar;
import java.util.Date;

public final class TimeUtil {
    
    public static String formatAvailability(Context context, Availability availability) {
        
        return formatTime(context, availability.getStartTime()) + " - " + formatTime(context, availability.getEndTime());
        
    }
    
    public static String formatTime(Context context, Date time) {
        return DateFormat.getTimeFormat(context).format(time);
    }
    
    /**
     * @param first
     * @param second
     * @return 1 if first > second, -1 if first < second, and 0 if first == second. Basically, it is a normalized first - second.
     */
    public static int compareTo(Calendar first, Calendar second) {
        
        if (first.get(Calendar.HOUR_OF_DAY) > second.get(Calendar.HOUR_OF_DAY)) {
            return 1;
        } else if (first.get(Calendar.HOUR_OF_DAY) < second.get(Calendar.HOUR_OF_DAY)) {
            return -1;
        } else {
            
            if (first.get(Calendar.MINUTE) > second.get(Calendar.MINUTE)) {
                return 1;
            } else if (first.get(Calendar.MINUTE) < second.get(Calendar.MINUTE)) {
                return -1;
            }
            
        }
        
        return 0;
        
    }
    
    public static Availability getConflicting(Calendar startTime, Calendar endTime, Availability[] otherAvailabilities) {
        
        Calendar availabilityStartTime = Calendar.getInstance();
        Calendar availabilityEndTime = Calendar.getInstance();
        
        for (Availability availability : otherAvailabilities) {
            
            availabilityStartTime.setTime(availability.getStartTime());
            availabilityEndTime.setTime(availability.getEndTime());
            
            if ((TimeUtil.compareTo(startTime, availabilityEndTime) <= 0 && TimeUtil.compareTo(startTime, availabilityStartTime) >= 0) ||
                    (TimeUtil.compareTo(endTime, availabilityStartTime) >= 0 && TimeUtil.compareTo(endTime, availabilityEndTime) <= 0) ||
                    (TimeUtil.compareTo(startTime, availabilityStartTime) <= 0 && TimeUtil.compareTo(endTime, availabilityEndTime) >= 0)) {
                return availability;
            }
            
        }
        
        return null;
        
    }
    
}
