package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeatherDate {

    String[] parts;
    List<Integer> hours = new ArrayList<Integer>();
    List<Integer> days = new ArrayList<Integer>();

    List<WeatherForecast> weatherList;
    LocalDateTime date;
    //    LocalDate today = LocalDate.now();
    LocalDateTime today = LocalDateTime.now();
    int hour;
    int day;
    int currentDay;

    public WeatherDate() {

    }

    public List<Integer> getHours() {
        return hours;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setWeatherDate(List<WeatherForecast> weatherList){
        for (WeatherForecast w :weatherList) {
            date = w.getForecastTime();
//            w.getForecastTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            hour = date.getHour();
            day = date.getDayOfMonth();
            currentDay = today.getDayOfMonth();

            if (currentDay == day){
                if (hour == 8 || hour == 14 || hour == 20 || hour == 23){
                    days.add(day);
                    hours.add(hour);

                }

            } else {
                if (hour == 8 || hour == 14 || hour == 20){
                    days.add(day);
                    hours.add(hour);
                }
            }
//            System.out.println(w.getTemperature());
//            System.out.println(w.getForecastTime());
//            System.out.println(w.getWeatherState().getIconId());
//            parts = w.getTemperature().toString().split(" ");

//            System.out.println(parts[1]);
//            System.out.println(w.getForecastTime().getClass().getName());
//            System.out.println(w.getForecastTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));//string
        }
//        System.out.println(days);
//        System.out.println(hours);
        return;
    }
}
