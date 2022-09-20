package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import pl.pogoda.controller.services.DateService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeatherTemperature {

    String[] parts;
    List<String> temperatures = new ArrayList<String>();
    List<WeatherForecast> weatherList;
    LocalDateTime date;
    //    LocalDate today = LocalDate.now();
    LocalDateTime today;
    int hour;
    int day;
    int currentDay;

    public WeatherTemperature() {
        today = DateService.getTodayDate();
    }

    public List<String> setWeatherTemperature(List<WeatherForecast> weatherList){
        for (WeatherForecast w :weatherList) {
            date = w.getForecastTime();
//            w.getForecastTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            hour = date.getHour();
            day = date.getDayOfMonth();
            currentDay = today.getDayOfMonth();

            if (currentDay == day){
                if (hour == 6 || hour == 12 || hour == 20 || hour == 23){
                    parts = w.getTemperature().toString().split(" ");
                    temperatures.add(parts[1] + " °C");
                }

            } else {
                if (hour == 6 || hour == 12 || hour == 20){
                    parts = w.getTemperature().toString().split(" ");
                    temperatures.add(parts[1] + " °C");
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
        System.out.println(temperatures);
        return temperatures;
    }
}
