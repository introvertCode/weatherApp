package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Weather {
    private final List<WeatherForecast> weatherList;
    private  int hour;
    private  int day;

    public Weather(List<WeatherForecast> weatherList) {
        this.weatherList = weatherList;
    }

    private void setTimeOfForecast(WeatherForecast weatherForecast){
        LocalDateTime date = weatherForecast.getForecastTime();
        hour = date.getHour();
        day = date.getDayOfMonth();
    }

    protected <T> List<T> setDataInList(int type){
        List<T> results = new ArrayList<>();
        T data;
        for (WeatherForecast w :weatherList) {
            setTimeOfForecast(w);
            data = returnDataAccordingToType(type, w);
            if (hour == 8 || hour == 14 || hour == 20){
                results.add(data);
            }
        }
        return results;
    }

    private <T> T returnDataAccordingToType(int type, WeatherForecast w){
        T data = null;
        if (type == 1){
            data = (T) (Integer)day;
        } else if (type == 2){
            data = (T) (Integer)hour;
        } else if (type == 3) {
            String temperatureWithUnit;
            int temperatureAsInt = (int)Math.round(w.getTemperature().getValue());
            temperatureWithUnit = temperatureAsInt + "°C";
            data = (T) temperatureWithUnit;
        } else if (type == 4) {
            String unEditedWeatherState = w.getWeatherState().toString();
            data = (T) getWeatherStateInEnglish(unEditedWeatherState);
        } else if (type == 5) {
            String unEditedWeatherState = w.getWeatherState().toString();
            data = (T) getWeatherStateInPolish(unEditedWeatherState);
        }
        return data;
    }

    private String getWeatherStateInEnglish(String weatherState){
        String englishWeather;
        int startOfTitle = 15;
        ArrayList<Integer> indexesOfParenthesis = findParenthesis(weatherState);
        englishWeather = weatherState.substring(startOfTitle, indexesOfParenthesis.get(0));
        return englishWeather;
    }

    private ArrayList<Integer> findParenthesis(String stringToSearch){
        ArrayList<Integer> indexesOfParenthesis = new ArrayList<Integer>();
        for (int i =0; i < stringToSearch.length(); i++) {
            if (stringToSearch.charAt(i) == '(' || stringToSearch.charAt(i) == ')'){
                indexesOfParenthesis.add(i);
            }
        }
        return indexesOfParenthesis;
    }

    private String getWeatherStateInPolish(String weatherState){
        String polishWeather;
        ArrayList<Integer> indexesOfParenthesis = findParenthesis(weatherState);
        polishWeather = weatherState.substring(indexesOfParenthesis.get(0) + 1, indexesOfParenthesis.get(1));
        return polishWeather;
    }
}