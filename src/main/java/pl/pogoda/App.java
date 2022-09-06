package pl.pogoda;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.pogoda.view.ViewFactory;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
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
