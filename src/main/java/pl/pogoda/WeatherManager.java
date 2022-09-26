package pl.pogoda;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.Temperature;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import javafx.scene.image.Image;
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

    private List<WeatherForecast> weatherList;
    private City city;

//    List<String> temperatures = new ArrayList<String>();
//    List<Integer> hours = new ArrayList<Integer>();
//    List<Integer> days = new ArrayList<Integer>();
//    List<String> states = new ArrayList<String>();




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
            .count(25)
            .retrieve()
            .asJava();

        return weather.getWeatherForecasts();
    }

    public List<String> getTemperatures(){
       return weatherTemperature.getTemperatures();
    }

    public List<Integer> getHours(){

        return weatherDate.getHours();

    }

    public List<Integer> getDays(){
        return weatherDate.getDays();

    }

    public List<String> getWeatherStates(){
        return weatherState.getWeatherStatesInPolish();
    }

    public List<Image> getWeatherStatesImg(){
        return weatherState.getWeatherStatesImages();
    }
}

