package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import javafx.scene.image.Image;
import pl.pogoda.controller.services.DateService;
import pl.pogoda.controller.services.WeatherStateAsImage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeatherState extends Weather{

    private List<String> weatherStatesInEnglish = new ArrayList<String>();

    final static int TYPE_ENGLISH = 4;
    final static int TYPE_POLISH = 5;

    List<String> weatherStatesInPolish = new ArrayList<String>();
    List<Image> weatherStatesImages = new ArrayList<Image>();

    private WeatherStateAsImage weatherStateAsImage = new WeatherStateAsImage();


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

        weatherStatesInEnglish = setDataInArrays(TYPE_ENGLISH);
        weatherStatesInPolish = setDataInArrays(TYPE_POLISH);
        createImageList();
        return;
    }

    private void createImageList(){
        for(String stateString : weatherStatesInEnglish) {
//            System.out.println(stateString);
            weatherStatesImages.add(weatherStateAsImage.getImage(stateString));
        }
    }
}
