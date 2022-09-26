package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;

import java.util.ArrayList;
import java.util.List;

public class Hours extends WeatherDate{

    List<Integer> hours = new ArrayList<Integer>();

    public Hours(List<WeatherForecast> weatherList) {
        super(weatherList);
    }

    public List<Integer> getHours() {
        return hours;
    }

    public void setHours(){

//        hours = setDataInArrays(TYPE_HOURS);

        return;
    }

}
