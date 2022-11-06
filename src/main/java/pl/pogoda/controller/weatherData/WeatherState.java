package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import javafx.scene.image.Image;
import pl.pogoda.controller.services.WeatherServices;

import java.util.ArrayList;
import java.util.List;

public class WeatherState extends Weather{
    private final List<String> weatherStatesInEnglish = new ArrayList<>();
    List<String> weatherStatesInPolish = new ArrayList<>();
    List<Image> weatherStatesImages = new ArrayList<>();

    private final WeatherServices weatherStateAsImage = new WeatherServices();


    public WeatherState(List<WeatherForecast> weatherList) {
        super(weatherList);
        setWeatherState();
    }

    public List<String> getWeatherStatesInPolish() {
        return weatherStatesInPolish;
    }
    public List<Image> getWeatherStatesImages() {
        return weatherStatesImages;
    }


    private void setWeatherState(){
        setStates();
        createImageList();
    }

    private void createImageList(){
        for(String stateString : weatherStatesInEnglish) {
            weatherStatesImages.add(weatherStateAsImage.getImage(stateString));
        }
    }

    private String getWeatherStateInPolish(String weatherState){
        String polishWeather;
        ArrayList<Integer> indexesOfParenthesis = findParenthesis(weatherState);
        polishWeather = weatherState.substring(indexesOfParenthesis.get(0) + 1, indexesOfParenthesis.get(1));
        return polishWeather;
    }

    private String getWeatherStateInEnglish(String weatherState){
        String englishWeather;
        int startOfTitle = 15;
        ArrayList<Integer> indexesOfParenthesis = findParenthesis(weatherState);
        englishWeather = weatherState.substring(startOfTitle, indexesOfParenthesis.get(0));
        return englishWeather;
    }

    private void setStates(){
        String statePolish;
        String stateEnglish;
        for (WeatherForecast weatherForecast :weatherList) {
            hour = weatherForecast.getForecastTime().getHour();
            statePolish = getWeatherStateInPolish(weatherForecast.getWeatherState().toString());
            stateEnglish = getWeatherStateInEnglish(weatherForecast.getWeatherState().toString());
            if (checkHour(hour)){
                weatherStatesInPolish.add(statePolish);
                weatherStatesInEnglish.add(stateEnglish);
            }
        }
    }

    private ArrayList<Integer> findParenthesis(String stringToSearch){
        ArrayList<Integer> indexesOfParenthesis = new ArrayList<>();
        for (int i =0; i < stringToSearch.length(); i++) {
            if (stringToSearch.charAt(i) == '(' || stringToSearch.charAt(i) == ')'){
                indexesOfParenthesis.add(i);
            }
        }
        return indexesOfParenthesis;
    }

}
