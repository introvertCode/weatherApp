package pl.pogoda;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import javafx.application.Application;
import javafx.stage.Stage;
import pl.pogoda.model.City;
import pl.pogoda.view.ViewFactory;


public class App extends Application
{
    public static void main( String[] args ) {
        launch(args);
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
