package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import pl.pogoda.controller.services.DateService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeatherDate extends Weather{

    List<Integer> hours = new ArrayList<Integer>();
    List<Integer> days = new ArrayList<Integer>();
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

        days = setDataInArrays(TYPE_DAYS);
        hours = setDataInArrays(TYPE_HOURS);

        return;
    }
}
