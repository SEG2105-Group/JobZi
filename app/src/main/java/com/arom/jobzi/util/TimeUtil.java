package com.arom.jobzi.util;

import android.content.Context;
import android.text.format.DateFormat;

import com.arom.jobzi.service.Availability;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class TimeUtil {
    
    private TimeUtil() {}
    
    /**
     * Returns the availability used by certain activities to have an initial availability
     * set (i.e. a <i>default</i> availability.
     * <p>
     * Can be modified in the future to return an availability with start/end times that are,
     * for example, locked to 15-minute intervals and are, by default, an hour apart.
     * </p>
     *
     * @return
     */
    public static Availability createDefaultAvailability() {
        
        Availability availability = new Availability();
        
        Calendar currentCalendar = Calendar.getInstance();
        availability.setStartTime(currentCalendar.getTime());
        
        currentCalendar.add(Calendar.MINUTE, 30);
        availability.setEndTime(currentCalendar.getTime());
        
        return availability;
        
    }
    
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
    
    public static List<String> getDaysOfWeekNames() {
    
        List<String> daysOfWeekList = new ArrayList<String>();
    
        daysOfWeekList.add(Weekday.MONDAY.getName());
        daysOfWeekList.add(Weekday.TUESDAY.getName());
        daysOfWeekList.add(Weekday.WEDNESDAY.getName());
        daysOfWeekList.add(Weekday.THURSDAY.getName());
        daysOfWeekList.add(Weekday.FRIDAY.getName());
        daysOfWeekList.add(Weekday.SATURDAY.getName());
        daysOfWeekList.add(Weekday.SUNDAY.getName());
    
        return daysOfWeekList;
        
    }
    
    public enum Weekday {
        
        MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"), THURSDAY("Thursday"), FRIDAY("Friday"), SATURDAY("Saturday"), SUNDAY("Sunday");
        
        private final String name;
        
        Weekday(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
    }
    
}
