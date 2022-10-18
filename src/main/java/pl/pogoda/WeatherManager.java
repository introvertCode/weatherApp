package pl.pogoda;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import javafx.scene.image.Image;
import pl.pogoda.controller.weatherData.WeatherDate;
import pl.pogoda.controller.weatherData.WeatherState;
import pl.pogoda.controller.weatherData.WeatherTemperature;
import pl.pogoda.model.City;
import java.util.List;

public class WeatherManager {

    private final OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(Config.API_KEY);
    private final WeatherTemperature weatherTemperature;
    private final WeatherDate weatherDate;
    private final WeatherState weatherState;
    private final City city;

    public WeatherManager(City city) {
        this.city = city;
        List<WeatherForecast> weatherList = prepareWeatherForecastAsList();
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
            .count(45)
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

