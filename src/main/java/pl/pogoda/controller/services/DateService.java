package pl.pogoda.controller.services;

import java.time.LocalDateTime;

public class DateService {

    static LocalDateTime today = LocalDateTime.now();
    public static LocalDateTime getTodayDate(){
        return today;
    }
}
