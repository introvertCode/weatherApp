package pl.pogoda;


import javafx.application.Application;
import javafx.stage.Stage;
import pl.pogoda.view.ViewFactory;


public class App extends Application
{
    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage stage){
        ViewFactory viewFactory = new ViewFactory();
        viewFactory.showMainWindow();
    }




    @Override
    public void stop(){
    }


}
