package pl.pogoda;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import javafx.application.Application;
import javafx.stage.Stage;
import pl.pogoda.model.City;
import pl.pogoda.view.ViewFactory;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args ) {

//        OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(Config.API_KEY);
//        List<WeatherForecast> weatherList;
//
//        final Forecast weather = openWeatherClient
//            .forecast5Day3HourStep()
//            .byCityName("Chorzów")
//            .language(Language.POLISH)
//            .unitSystem(UnitSystem.METRIC)
//            .count(1)
//            .retrieve()
//            .asJava();

//        System.out.println(openWeatherClient.currentWeather());
//        weatherList = weather.getWeatherForecasts();
//        weather.setWeatherForecasts(weatherList);
//        System.out.println(weatherList.toString());
//        for (WeatherForecast w :weatherList) {
////            System.out.println(w.getTemperature());
//            System.out.println(w.getForecastTime().getClass().getName());
//            System.out.println(w.getForecastTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));//string
//
//
//        }
        //modele:cityWeather i controllery, ikony, layout szczegółowy
//        System.out.println(weather.getWeatherForecasts());

        launch(args);


//        City cityWeatherForecast = new City("Chorzów");
//
//        WeatherManager cityWeatgerForecastController = new WeatherManager(cityWeatherForecast);
//
//
//
//        for (WeatherForecast w :cityWeatgerForecastController.prepareWeatherForecastAsList()) {
//            System.out.println(w.getTemperature());
//        }


    }

    @Override
    public void start(Stage stage) throws Exception {

        ViewFactory viewFactory = new ViewFactory();


        viewFactory.showMainWindow();

    }

    @Override
    public void stop() throws Exception {

    }


}
