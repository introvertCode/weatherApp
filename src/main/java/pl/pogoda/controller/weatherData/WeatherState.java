package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeatherState {

    String[] parts;
    List<String> weatherStates = new ArrayList<String>();


    List<WeatherForecast> weatherList;
    LocalDateTime date;
    //    LocalDate today = LocalDate.now();
    LocalDateTime today = LocalDateTime.now();
    int hour;
    int day;
    int currentDay;

    public WeatherState() {

    }

    public List<String> getWeatherStates() {
        return weatherStates;
    }

    public void setWeatherState(List<WeatherForecast> weatherList){
        for (WeatherForecast w :weatherList) {
            date = w.getForecastTime();
//            w.getForecastTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            hour = date.getHour();
            System.out.println(hour);
            day = date.getDayOfMonth();
            currentDay = today.getDayOfMonth();

            if (currentDay == day){
                if (hour == 8 || hour == 14 || hour == 20 || hour == 23){
                    weatherStates.add(w.getWeatherState().toString());

                }

            } else {
                if (hour == 8 || hour == 14 || hour == 20){
                    weatherStates.add(w.getWeatherState().toString());
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
