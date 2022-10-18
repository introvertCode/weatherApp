package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import javafx.scene.image.Image;
import pl.pogoda.controller.services.WeatherServices;

import java.util.ArrayList;
import java.util.List;

public class WeatherState extends Weather{
    private List<String> weatherStatesInEnglish = new ArrayList<>();
    private final static int TYPE_ENGLISH = 4;
    private final static int TYPE_POLISH = 5;
    List<String> weatherStatesInPolish = new ArrayList<>();
    List<Image> weatherStatesImages = new ArrayList<>();

    private final WeatherServices weatherStateAsImage = new WeatherServices();


    public WeatherState(List<WeatherForecast> weatherList) {
        super(weatherList);
        setWeatherState();
    }

    public List<String> getWeatherStatesInEnglish() {
        return weatherStatesInEnglish;
    }
    public List<String> getWeatherStatesInPolish() {
        return weatherStatesInPolish;
    }
    public List<Image> getWeatherStatesImages() {
        return weatherStatesImages;
    }


    private void setWeatherState(){
        weatherStatesInEnglish = setDataInList(TYPE_ENGLISH);
        weatherStatesInPolish = setDataInList(TYPE_POLISH);
        createImageList();
    }

    private void createImageList(){
        for(String stateString : weatherStatesInEnglish) {
            weatherStatesImages.add(weatherStateAsImage.getImage(stateString));
        }
    }
}
