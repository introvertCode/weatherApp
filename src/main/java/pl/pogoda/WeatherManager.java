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

    OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(Config.API_KEY);
    City city;
    String[] parts;
    List<String> temperatures = new ArrayList<String>();
    List<WeatherForecast> weatherList;
    LocalDateTime date;
//    LocalDate today = LocalDate.now();
    LocalDateTime today = LocalDateTime.now();
    int hour;
    int day;
    int currentDay;


    public WeatherManager(City city) {
        this.city = city;
        weatherList = prepareWeatherForecastAsList();
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
        WeatherTemperature temperature = new WeatherTemperature();
        temperatures = temperature.setWeatherTemperature(weatherList);
        return temperatures;
    }

    public void getWeatherDates(){

        WeatherDate weatherDate = new WeatherDate();
        weatherDate.setWeatherDate(weatherList);
        System.out.println(weatherDate.getDays());
        System.out.println(weatherDate.getHours());
//        for (WeatherForecast w :prepareWeatherForecastAsList()) {
//            System.out.println(w.getTemperature());
//            System.out.println(w.getWeatherState().getIconId());
//            parts = w.getTemperature().toString().split(" ");
//
//            System.out.println(parts[1]);
//            System.out.println(w.getForecastTime().getClass().getName());
//            System.out.println(w.getForecastTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));//string
        }
//        return "http://openweathermap.org/img/wn/10d@2x.png";

    public void getWeatherStates(){
        WeatherState weatherState = new WeatherState();
        weatherState.setWeatherState(weatherList);
        System.out.println(weatherState.getWeatherStates());
    }
}

