package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import pl.pogoda.controller.services.DateService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Weather {
    protected List<WeatherForecast> weatherList;
    protected LocalDateTime date;
    //    LocalDate today = LocalDate.now();
    protected LocalDateTime today;


    protected  int hour;
    protected  int day;
    protected  int currentDay;

    public Weather(List<WeatherForecast> weatherList) {
        this.weatherList = weatherList;
        this.today = DateService.getTodayDate();;
    }

    protected void setTimeOfForecast(WeatherForecast weatherForecast){
        date = weatherForecast.getForecastTime();
        hour = date.getHour();
        day = date.getDayOfMonth();
        currentDay = today.getDayOfMonth();
    }

    protected <T> List<T> setDataInArrays(int type){
        List<T> results = new ArrayList<>();

        T data = null;

        for (WeatherForecast w :weatherList) {
                setTimeOfForecast(w);
                data = returnDataAccordingToType(type, w);

            if (currentDay == day){
                if (hour == 8 || hour == 14 || hour == 20 || hour == 23){
                    results.add((T)data);

                }

            } else {
                if (hour == 8 || hour == 14 || hour == 20){
                    results.add((T)data);

                }
            }
//            System.out.println(w.getTemperature());
//            System.out.println(w.getForecastTime());
//            System.out.println(w.getWeatherState().getIconId());
//            parts = w.getTemperature().toString().split(" ");

//            System.out.println(parts[1]);
//            System.out.println(w.getForecastTime().getClass().getName());
//            System.out.println(w.getForecastTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));//string
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
            String[] singleTemperature;
            String temperatureWithUnit;
            singleTemperature = w.getTemperature().toString().split(" ");
            temperatureWithUnit = singleTemperature[1] + "°C";
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
