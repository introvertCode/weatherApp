package pl.pogoda.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.pogoda.WeatherManager;
import pl.pogoda.controller.services.WeatherStateAsImage;
import pl.pogoda.model.City;
import pl.pogoda.view.ViewFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable{
    City homeCity;
    City destinationCity;
    WeatherManager cityWeatherForecastController;

    @FXML
    private Button checkWeatherBtn;

    @FXML
    private TextField destinationCityTextField;

    @FXML
    private TextField destinationCountry;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField homeCityTextField;

    @FXML
    private Label hour1Day1Label;

    @FXML
    private Label hour2Day1Label;

    @FXML
    private Label hour3Day1Label;

    @FXML
    private Label temp1Day1Label;

    @FXML
    private Label temp2Day1Label;

    @FXML
    private Label temp3Day1Label;

    @FXML
    private ImageView weatherPic1Day1ImgView;

    @FXML
    private ImageView weatherPic2Day1ImgView;

    @FXML
    private ImageView weatherPic3Day1ImgView;

    @FXML
    private TextField yourCountry;



    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);




    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        homeCityTextField.setText("Chorzów");

        homeCity = new City(homeCityTextField.getText());
        System.out.println(homeCity.getCity());

//        Image image = new Image("/icons/img1.png");
//        weatherPic1Day1ImgView.setImage(new Image(getClass().getResourceAsStream("/icons/img1.png")));
        WeatherStateAsImage wsai = new WeatherStateAsImage();
        weatherPic1Day1ImgView.setImage(wsai.getImage("shower rain"));

        Tooltip t = new Tooltip("Button of doom");
        Tooltip.install(weatherPic1Day1ImgView,t);


        weatherPic1Day1ImgView.setSmooth(true);

        WeatherManager wm = new WeatherManager(homeCity);
//        weatherPic1Day1ImgView.setImage(new Image(wm.getWeather()));
        wm.getTemperatures();
        wm.getWeatherDates();
        wm.getWeatherStates();
//        temp1Day1Label.setText(wm.getTemperature());

    }
}
