package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import java.util.ArrayList;
import java.util.List;

public class WeatherTemperature extends Weather{

    List<String> temperatures = new ArrayList<>();
    private final static int TYPE_TEMPERATURE = 3;

    public WeatherTemperature(List<WeatherForecast> weatherList) {
        super(weatherList);
        setWeatherTemperature();
    }

    public List<String> getTemperatures() {
        return temperatures;
    }

    private void setWeatherTemperature(){
        temperatures = setDataInList(TYPE_TEMPERATURE);
    }
}
