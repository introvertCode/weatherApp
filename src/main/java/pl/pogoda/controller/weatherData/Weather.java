package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;

import java.util.List;

public abstract class Weather {
    protected final List<WeatherForecast> weatherList;
    protected  int hour;
    protected  int day;

    public Weather(List<WeatherForecast> weatherList) {
        this.weatherList = weatherList;
    }

    protected Boolean checkHour(int hour){
        return hour == 8 || hour == 14 || hour == 20 || hour == 7 || hour == 13 || hour == 19;
    }
}
