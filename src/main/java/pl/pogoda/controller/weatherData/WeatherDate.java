package pl.pogoda.controller.weatherData;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import pl.pogoda.controller.services.DateService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeatherDate extends Weather{

    List<Integer> hours = new ArrayList<Integer>();
    List<Integer> days = new ArrayList<Integer>();
    final static int TYPE_DAYS = 1;
    final static int TYPE_HOURS = 2;

    public WeatherDate(List<WeatherForecast> weatherList) {
        super(weatherList);
        setWeatherDate();
    }

    public List<Integer> getHours() {
        return hours;
    }

    public List<Integer> getDays() {
        return days;
    }

//    public void setWeatherDate(){
//        for (WeatherForecast w :weatherList) {
//            setTime(w);
//
//
//
//            if (currentDay == day){
//                if (hour == 8 || hour == 14 || hour == 20 || hour == 23){
//                    days.add(day);
//                    hours.add(hour);
//                }
//
//            } else {
//                if (hour == 8 || hour == 14 || hour == 20){
//                    days.add(day);
//                    hours.add(hour);
//                }
//            }
////            System.out.println(w.getTemperature());
////            System.out.println(w.getForecastTime());
////            System.out.println(w.getWeatherState().getIconId());
////            parts = w.getTemperature().toString().split(" ");
//
////            System.out.println(parts[1]);
////            System.out.println(w.getForecastTime().getClass().getName());
////            System.out.println(w.getForecastTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));//string
//        }
//
//        return;
//    }

    private void setWeatherDate(){

        days = setDataInArrays(TYPE_DAYS);
        hours = setDataInArrays(TYPE_HOURS);

        return;
    }
}
