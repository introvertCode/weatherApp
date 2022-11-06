package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;

import java.util.ArrayList;
import java.util.List;

public class WeatherTemperature extends Weather{

    List<String> temperatures = new ArrayList<>();

    public WeatherTemperature(List<WeatherForecast> weatherList) {
        super(weatherList);
        setWeatherTemperature();
    }

    public List<String> getTemperatures() {
        return temperatures;
    }


    private String returnFormattedTemperature( WeatherForecast weatherForecast){
        String temperature;

        String temperatureWithUnit;
        int temperatureAsInt = (int)Math.round(weatherForecast.getTemperature().getValue());
        temperatureWithUnit = temperatureAsInt + "°C";
        temperature = temperatureWithUnit;

        return temperature;
    }

    private void setWeatherTemperature(){
        String temperature;
        for (WeatherForecast weatherForecast :weatherList) {
            hour = weatherForecast.getForecastTime().getHour();
            temperature = returnFormattedTemperature(weatherForecast);
            if (checkHour(hour)){
                temperatures.add(temperature);
            }
        }
    }
}
