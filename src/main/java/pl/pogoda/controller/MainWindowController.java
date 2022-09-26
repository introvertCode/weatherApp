package pl.pogoda.controller;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import pl.pogoda.WeatherManager;
import pl.pogoda.controller.services.WeatherStateAsImage;
import pl.pogoda.model.City;
import pl.pogoda.view.ViewFactory;

import javafx.geometry.Pos;

import javafx.fxml.FXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable{
    City homeCity;
    City destinationCity;
    WeatherManager cityWeatherForecastController;
    List<Label> hourLabels = new ArrayList<>();
    List<Label> temperatureLabels = new ArrayList<>();
    List<ImageView> imageViews = new ArrayList<>();

    List<Integer> hours = new ArrayList<Integer>();
    List<String> weatherStatesInPolish = new ArrayList<String>();
    List<Image> weatherStatesImages = new ArrayList<Image>();
    List<String> temperatures = new ArrayList<String>();


    @FXML
    private AnchorPane day1AnchorPane;

    @FXML
    private AnchorPane day2AnchorPane;

    @FXML
    private AnchorPane day3AnchorPane;

    @FXML
    private AnchorPane day4AnchorPane;

    @FXML
    private AnchorPane day5AnchorPane;

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

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TitledPane firstDayPane;


    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        homeCityTextField.setText("Chorzów");

        homeCity = new City(homeCityTextField.getText());
        System.out.println(homeCity.getCity());


        weatherPic1Day1ImgView.setSmooth(true);

        WeatherManager wm = new WeatherManager(homeCity);
        temperatures = wm.getTemperatures();
        hours = wm.getHours();
//        System.out.println(wm.getDays());
       weatherStatesInPolish = wm.getWeatherStates();
       weatherStatesImages = wm.getWeatherStatesImg();

        createObjectLists(day1AnchorPane);
        createObjectLists(day2AnchorPane);
        createObjectLists(day3AnchorPane);
        createObjectLists(day4AnchorPane);
        createObjectLists(day5AnchorPane);
//        int i = 0;

        for(int i =0; i<wm.getHours().size();i++){
            if( wm.getHours().get(0) > 12){
                hourLabels.get(i + 2).setText(hours.get(i).toString()+":00");
                hourLabels.get(i + 2).setAlignment(Pos.CENTER);
                temperatureLabels.get(i + 2).setText(temperatures.get(i));
                temperatureLabels.get(i + 2).setAlignment(Pos.CENTER);
                imageViews.get(i + 2).setImage(weatherStatesImages.get(i));
                centerImage(imageViews.get(i + 2));
                Tooltip.install(imageViews.get(i + 2), new Tooltip(weatherStatesInPolish.get(i)));

            } else if (wm.getHours().get(0) > 8) {
                hourLabels.get(i + 1).setText(wm.getHours().get(i).toString() + ":00");
            } else {
                hourLabels.get(i).setText(wm.getHours().get(i).toString() + ":00");
            }
        }
//        hourLabels.get(1).setText(wm.getHours().get(1).toString()+":00");
//        imageViews.get(1).setImage(wm.getWeatherStatesImg().get(1));
//        System.out.println(getAllNodes(mainPane));

    }

    public void createObjectLists(AnchorPane pane){

        for (Node node : pane.getChildren()) {
//            System.out.println("Id: " + node.getId());
            if (node instanceof HBox) {
                for (Node node1 : ((HBox) node).getChildren()) {
//                    System.out.println("Id: " + node1.getId());
                    if (node1 instanceof GridPane) {
                        for (Node node2 : ((GridPane) node1).getChildren()) {
//                            System.out.println("Id: " + node2.getId());
                            if (node2 instanceof Label){
                                if(node2.getId().contains("hour")){
                                    hourLabels.add((Label)node2);
                                } else if(node2.getId().contains("temp")){
                                    temperatureLabels.add((Label)node2);
                                }

                            } else if (node2 instanceof ImageView){
                                imageViews.add((ImageView) node2);
                            }



//
                        }
                    }
                }
            }
        }
//        System.out.println(hourLabels);
//        System.out.println(temperatureLabels);
//        System.out.println(imageViews);
    }

    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

//            System.out.println(ratioX);
//            System.out.println(ratioY);

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

//            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setTranslateX((imageView.getFitWidth() - w) / 2);
//            imageView.setX(2);

//            imageView.setY((imageView.getFitHeight() - h) / 2);
            imageView.setTranslateY((imageView.getFitHeight() - h) / 2);

        }
    }

}
