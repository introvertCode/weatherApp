package pl.pogoda.controller;

import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import pl.pogoda.WeatherManager;
import pl.pogoda.model.City;
import pl.pogoda.view.ViewFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable{
    City cityWeatherForecast;
    WeatherManager cityWeatherForecastController;

    @FXML
    private Button checkWeatherBtn;

    @FXML
    private TextField destinationCity;

    @FXML
    private TextField destinationCountry;

    @FXML
    private Label errorLabel;

    @FXML
    private Label temp1Day1;

    @FXML
    private ImageView weatherPic1Day1;

    @FXML
    private TextField yourCity;

    @FXML
    private TextField yourCountry;


    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);




    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        temp1Day1.setText("AAAA");
        yourCity.setText("Chorzów");
    }
}
