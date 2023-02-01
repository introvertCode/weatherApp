package pl.pogoda.controller.services;

import java.time.LocalDateTime;

public class DateService {

    LocalDateTime today = LocalDateTime.now();
    public LocalDateTime getTodayDate(){
        return today;
    }
}
