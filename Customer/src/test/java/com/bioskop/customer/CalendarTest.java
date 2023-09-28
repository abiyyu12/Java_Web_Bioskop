package com.bioskop.customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class CalendarTest {
    @Test
    void testCalendar() {
        Calendar calendar = Calendar.getInstance();
        LocalDate localDate = LocalDate.now();
        DayOfWeek dayOfWeek1 = localDate.getDayOfWeek();
        System.out.println(dayOfWeek1.toString());
        if(dayOfWeek1 == DayOfWeek.SATURDAY){
            int dayOfMonth = localDate.getDayOfMonth()+2;
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        }else if(dayOfWeek1 == DayOfWeek.SUNDAY){
            int dayOfWeek = localDate.getDayOfMonth()+1;
            calendar.set(Calendar.DAY_OF_MONTH,dayOfWeek);
        }
        Date date = calendar.getTime();
        LocalDate localDateStart = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(localDateStart.getDayOfMonth());
    }

    @Test
    void testLocalDate() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());
    }

    @Test
    void testSeat() {
        int totalSeat = 98;
        List<String> seat = new ArrayList<>(totalSeat);
        for (int i = 1; i <= totalSeat; i++) {
            seat.add(String.valueOf(i));
        }
        String s = seat.get(0);
        System.out.println(s);
        System.out.println(seat.size());
    }


    @Test
    void testBrcptEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String ab = passwordEncoder.encode("A").toString();
        String abEncode = "A";
        boolean matches = passwordEncoder.matches(abEncode, ab);
        Assertions.assertTrue(matches);
    }

    @Test
    void testTime() {
        LocalTime now = LocalTime.now();
        LocalTime  lastHour = now.minusHours(4);
        long hoursBetween = ChronoUnit.HOURS.between(lastHour,now);
        int totalBiaya = (int) (2000 * hoursBetween);
        System.out.println(totalBiaya);
        Assertions.assertEquals(8000,totalBiaya);
    }

    @Test
    void testToString(){
        List<String> a = List.of("A", "V", "C");

        System.out.println(a);
    }

    @Test
    void testStringTokenizer(){
        List<String> arrayAsList = List.of("2023-","2024","2021-","2021");
        List<String> newList = new ArrayList<>();
        for (String list : arrayAsList) {
            String[] split = list.split("-");
            newList.add(split[0]);
        }
        System.out.println(newList);
    }

    @Test
    void testNanoLocalTimes() {
        LocalTime localTimeEnd = LocalTime.parse("20:00");
        LocalTime localTimeStart = LocalTime.now();
        System.out.println("Start : " + localTimeStart.getNano());
        System.out.println("End   : " + localTimeEnd.getHour());
    }
}
