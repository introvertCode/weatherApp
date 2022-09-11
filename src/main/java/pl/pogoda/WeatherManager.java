package pl.pogoda;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import pl.pogoda.model.City;

import java.util.List;

public class WeatherManager {

    OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(Config.API_KEY);
    City city;
    List<WeatherForecast> weatherList;

    public WeatherManager(City city) {
        this.city = city;
    }

    private List<WeatherForecast> prepareWeatherForecastAsList(){
        final Forecast weather = openWeatherClient
            .forecast5Day3HourStep()
            .byCityName(city.getCity())
            .language(Language.POLISH)
            .unitSystem(UnitSystem.METRIC)
            .count(1)
            .retrieve()
            .asJava();

        return weatherList = weather.getWeatherForecasts();
    }





}
