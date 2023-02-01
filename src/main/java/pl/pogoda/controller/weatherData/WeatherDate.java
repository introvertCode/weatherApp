package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;

import java.util.ArrayList;
import java.util.List;

public class WeatherDate extends Weather {

    List<Integer> hours = new ArrayList<>();
    List<Integer> days = new ArrayList<>();

    public WeatherDate(List<WeatherForecast> weatherList) {
        super(weatherList);
        setWeatherDateList();
    }

    public List<Integer> getHours() {
        return hours;
    }

    public List<Integer> getDays() {
        return days;
    }

    private void setWeatherDateList(){

        for (WeatherForecast weatherForecast :weatherList) {
            hour = weatherForecast.getForecastTime().getHour();
            day = weatherForecast.getForecastTime().getDayOfMonth();
            if (checkHour(hour)){
                hours.add(hour);
                days.add(day);
            }
        }
    }
}