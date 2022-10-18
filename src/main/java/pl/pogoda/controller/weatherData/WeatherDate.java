package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;

import java.util.ArrayList;
import java.util.List;

public class WeatherDate extends Weather{

    List<Integer> hours = new ArrayList<>();
    List<Integer> days = new ArrayList<>();
    private final static int TYPE_DAYS = 1;
    private final static int TYPE_HOURS = 2;

    public WeatherDate(List<WeatherForecast> weatherList) {
        super(weatherList);
        setWeatherDate();
    }

    public List<Integer> getHours() {
        return hours;
    }

    public List<Integer> getDays() {
        return days;
    }


    private void setWeatherDate(){
        days = setDataInList(TYPE_DAYS);
        hours = setDataInList(TYPE_HOURS);
    }
}
