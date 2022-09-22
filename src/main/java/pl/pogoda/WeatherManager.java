package pl.pogoda;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.Temperature;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import pl.pogoda.controller.weatherData.WeatherDate;
import pl.pogoda.controller.weatherData.WeatherState;
import pl.pogoda.controller.weatherData.WeatherTemperature;
import pl.pogoda.model.City;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeatherManager {

    private OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(Config.API_KEY);

    private WeatherTemperature weatherTemperature;
    private WeatherDate weatherDate;
    private WeatherState weatherState;

    City city;
    List<String> temperatures = new ArrayList<String>();
    List<WeatherForecast> weatherList;



    public WeatherManager(City city) {
        this.city = city;
        weatherList = prepareWeatherForecastAsList();
        weatherTemperature = new WeatherTemperature(weatherList);
        weatherDate = new WeatherDate(weatherList);
        weatherState = new WeatherState(weatherList);
    }

    private List<WeatherForecast> prepareWeatherForecastAsList(){
        final Forecast weather = openWeatherClient
            .forecast5Day3HourStep()
            .byCityName(city.getCity())
            .language(Language.POLISH)
            .unitSystem(UnitSystem.METRIC)
            .count(10)
            .retrieve()
            .asJava();

        return weather.getWeatherForecasts();
    }

    public List<String> getTemperatures(){
        temperatures = weatherTemperature.getTemperatures();
        System.out.println(temperatures);
        return temperatures;
    }

    public void getWeatherDates(){
        System.out.println(weatherDate.getDays());
        System.out.println(weatherDate.getHours());
    }

    public void getWeatherStates(){
        System.out.println(weatherState.getWeatherStatesInPolish());
    }
}

