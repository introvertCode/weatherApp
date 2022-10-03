package pl.pogoda.controller;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import pl.pogoda.WeatherManager;
import pl.pogoda.controller.services.DateService;
import pl.pogoda.model.City;
import pl.pogoda.view.ViewFactory;

import javafx.geometry.Pos;

import javafx.fxml.FXML;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable{
    City homeCity;
    City destinationCity;
    LocalDateTime today;
    WeatherManager cityWeatherForecastController;
    List<Label> hourLabels = new ArrayList<>();
    List<Label> temperatureLabels = new ArrayList<>();
    List<ImageView> imageViews = new ArrayList<>();
    List<TitledPane> titledPanes = new ArrayList<>();
    List<AnchorPane> dayAnchorPanes = new ArrayList<>();

    List<Integer> hours = new ArrayList<>();
    List<Integer> days = new ArrayList<>();
    List<String> weatherStatesInPolish = new ArrayList<>();
    List<Image> weatherStatesImages = new ArrayList<>();
    List<String> temperatures = new ArrayList<>();


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
    private TextField yourCountry;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane firstCity;

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
        setToday();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setHomeCity();
        //        System.out.println(homeCity.getCity());
        setWeatherController();
        setTemperaturesHoursDatesAndStates();
        setToday();
        createLabelsPanesAndImageViewsLists(firstCity);
        showWeatherOnMainView(cityWeatherForecastController);
        TitledPanesSetText();
        setCollapsingTitledPanes();



    }

    private void setToday(){
        today = DateService.getTodayDate();
    }

    private void setCollapsingTitledPanes(){
        for (int i = 0; i < 5; i++){
            int finalI = i;
            titledPanes.get(i).expandedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    titledPanes.get(finalI).setDisable(true);
                    int indexOfCollapsedTitledPane = finalI;
                    boolean isTitledPaneExpanded = titledPanes.get(finalI).isExpanded();
                    collapsingTitledPanesAnimation(isTitledPaneExpanded, indexOfCollapsedTitledPane);
                }
            });
        }
    }

    private void collapsingTitledPanesAnimation(boolean isExpanded, int indexOfCollapsedTitledPane){

        double positionOfTitledPane;
        int indexOfCurrentTp;
        TranslateTransition in;
        int yOfsset;

        if (isExpanded){
            yOfsset = 100;
        } else {
            yOfsset = -100;
        }

        for (TitledPane tp : titledPanes) {
            positionOfTitledPane = tp.translateYProperty().getValue();
            indexOfCurrentTp = titledPanes.indexOf(tp);
            tp.setDisable(true);
            in = new TranslateTransition(Duration.millis(300), tp);
            if (indexOfCurrentTp > indexOfCollapsedTitledPane) {
                in.setFromY(positionOfTitledPane);
                in.setToY(positionOfTitledPane + yOfsset);
                in.setInterpolator(Interpolator.EASE_IN);
            }
            in.setOnFinished(e -> {
                tp.setDisable(false);
                titledPanes.get(indexOfCollapsedTitledPane).setDisable(false);
            });
            in.play();
        }
    }

     private void setHomeCity(){
         homeCityTextField.setText("Mediolan");
         homeCity = new City(homeCityTextField.getText());
    }
    private void setWeatherController(){
        cityWeatherForecastController = new WeatherManager(homeCity);
    }

    private void setTemperaturesHoursDatesAndStates(){
        temperatures = cityWeatherForecastController.getTemperatures();
        hours = cityWeatherForecastController.getHours();
        days = cityWeatherForecastController.getDays();
        weatherStatesInPolish = cityWeatherForecastController.getWeatherStates();
        weatherStatesImages = cityWeatherForecastController.getWeatherStatesImg();
    }

    public void createLabelsPanesAndImageViewsLists(AnchorPane mainPane){
        Node nodeFromTitledPane = null;
        for (Node nodeFromMainPane : mainPane.getChildren()) {
            if (nodeFromMainPane instanceof TitledPane) {
                titledPanes.add((TitledPane) nodeFromMainPane);
                nodeFromTitledPane = ((TitledPane) nodeFromMainPane).getContent();
                if (nodeFromTitledPane instanceof AnchorPane) {
                    dayAnchorPanes.add((AnchorPane) nodeFromTitledPane);
                    for (Node nodeFromAnchorPane : ((AnchorPane) nodeFromTitledPane).getChildren()) {
                        if (nodeFromAnchorPane instanceof HBox) {
                            for (Node nodeFromHBox : ((HBox) nodeFromAnchorPane).getChildren()) {
                                if (nodeFromHBox instanceof GridPane) {
                                    for (Node nodeFromGridPane : ((GridPane) nodeFromHBox).getChildren()) {
                                        if (nodeFromGridPane instanceof Label) {
                                            if (nodeFromGridPane.getId().contains("hour")) {
                                                hourLabels.add((Label) nodeFromGridPane);
                                            } else if (nodeFromGridPane.getId().contains("temp")) {
                                                temperatureLabels.add((Label) nodeFromGridPane);
                                            }
                                        } else if (nodeFromGridPane instanceof ImageView) {
                                            imageViews.add((ImageView) nodeFromGridPane);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public void TitledPanesSetText(){
//        int amountOfDays = 5;
        int counterOfDays = 0;
        String dayOfWeekInPolish;

        if (days.get(0) != today.getDayOfMonth()){
            counterOfDays = 1;
        }
        for(TitledPane pane : titledPanes){
            dayOfWeekInPolish = dayOfWeekTranslationToPolish(today.plusDays(counterOfDays).getDayOfWeek().toString());
            pane.setText(dayOfWeekInPolish +" " + today.plusDays(counterOfDays).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            counterOfDays++;
        }
    }

    private String dayOfWeekTranslationToPolish(String dayOfWeekInEnglish){
        String dayOfWeekInPolish = "";

        switch(dayOfWeekInEnglish){
            case "MONDAY":
                dayOfWeekInPolish = "PONIEDZIAŁEK";
                break;
            case "TUESDAY":
                dayOfWeekInPolish = "WTOREK";
                break;
            case "WEDNESDAY":
                dayOfWeekInPolish = "ŚRODA";
                break;
            case "THURSDAY":
                dayOfWeekInPolish = "CZWARTEK";
                break;
            case "FRIDAY":
                dayOfWeekInPolish = "PIĄTEK";
                break;
            case "SATURDAY":
                dayOfWeekInPolish = "SOBOTA";
                break;
            case "SUNDAY":
                dayOfWeekInPolish = "NIEDZIELA";
                break;
        }

        return dayOfWeekInPolish;
    }

    private void showWeatherOnMainView(WeatherManager wm){
        int offsetDependingOnCurrentHour = 0;

            if( wm.getHours().get(0) > 12){
                offsetDependingOnCurrentHour = 2;
            } else if (wm.getHours().get(0) > 8) {
                offsetDependingOnCurrentHour = 1;
            }
        int amountOfRecordsToDisplay = hourLabels.size() - offsetDependingOnCurrentHour;
        fillLabelsAndImagesWithData(amountOfRecordsToDisplay,offsetDependingOnCurrentHour );
    }
    
    private void fillLabelsAndImagesWithData(int amountOfRecordsToDisplay, int offsetDependingOnCurrentHour){
        for(int i =0; i<amountOfRecordsToDisplay;i++) {
            hourLabels.get(i + offsetDependingOnCurrentHour).setText(hours.get(i).toString() + ":00");
            hourLabels.get(i + offsetDependingOnCurrentHour).setAlignment(Pos.CENTER);
            temperatureLabels.get(i + offsetDependingOnCurrentHour).setText(temperatures.get(i));
            temperatureLabels.get(i + offsetDependingOnCurrentHour).setAlignment(Pos.CENTER);
            imageViews.get(i + offsetDependingOnCurrentHour).setImage(weatherStatesImages.get(i));
            centerImage(imageViews.get(i + offsetDependingOnCurrentHour));
            Tooltip.install(imageViews.get(i + offsetDependingOnCurrentHour), new Tooltip(weatherStatesInPolish.get(i)));
        }
    }

    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();
            double reducCoeff = Math.min(ratioX, ratioY);

            double w = img.getWidth() * reducCoeff;
            double h = img.getHeight() * reducCoeff;

            imageView.setTranslateX((imageView.getFitWidth() - w) / 2);
            imageView.setTranslateY((imageView.getFitHeight() - h) / 2);
        }
    }
}
