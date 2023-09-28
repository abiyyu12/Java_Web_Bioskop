package com.abiyyu.projects.admin;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.ZoneId;

public class TestTime {



    @Test
    void convertTest() {
        // Get Time Now
//        LocalTime localTime = LocalTime.now();
        LocalTime localTime = LocalTime.parse("22:10");
        System.out.println(localTime);
        // Start Time From Database
        LocalTime localTime1 = LocalTime.parse("22:30");
        int hour = localTime.getHour();
        int hourTime = localTime1.getHour();
        int minutes = localTime.getMinute();
        int minutesTime = localTime1.getMinute();
        if(hour < hourTime || (hour == hourTime && minutes < minutesTime)){
            System.out.println("Accepted Order");
        }
        else {
            System.out.println("Error Accepted Order");
        }
    }
}
