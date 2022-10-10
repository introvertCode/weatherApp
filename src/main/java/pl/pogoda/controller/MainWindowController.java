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

    List<TextField> citiesTextFields = new ArrayList<>();
    List<City> cities = new ArrayList<>();
    LocalDateTime today;
//    WeatherManager cityWeatherForecastController;
//    List<Label> hourLabels = new ArrayList<>();
    List<List<Label>> hourLabelsListsList = new ArrayList<>();
//    List<Label> temperatureLabels = new ArrayList<>();
    List<List<Label>> temperatureLabelsListsList = new ArrayList<>();
//    List<ImageView> imageViews = new ArrayList<>();
    List<List<ImageView>> imageViewsListsList = new ArrayList<>();

    List<TitledPane> allTitledPanes = new ArrayList<>();
    List<List<TitledPane>> titledPanesListsList = new ArrayList<>();
    List<AnchorPane> dayAnchorPanes = new ArrayList<>();
//    List<List<AnchorPane>> dayAnchorPanesListsList = new ArrayList<>();

    List<AnchorPane> citiesAnchorPanes = new ArrayList<>();

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
    private AnchorPane firstCityAnchorPane;

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
        setToday();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCitiesAnchorPanesAndTextFieldsList(mainPane);
        setToday();

        setCitiesList();

        for(AnchorPane pane : citiesAnchorPanes){
            createLabelsPanesAndImageViewsLists(pane);
        }

        int cityCounter = 0;
        for(City city : cities){
            viewWeatherForCity(city, cityCounter);
            cityCounter++;
        }



        //        System.out.println(homeCity.getCity());
//        setWeatherController(homeCity);
//        setTemperaturesHoursDatesAndStates();


//        showWeatherOnMainView(cityWeatherForecastController);

        setCollapsingTitledPanes();



    }

    private void setCitiesAnchorPanesAndTextFieldsList(AnchorPane mainPane){
        for (Node nodeUnderMainPane : mainPane.getChildren()) {
            if (nodeUnderMainPane instanceof AnchorPane) {
                citiesAnchorPanes.add((AnchorPane) nodeUnderMainPane);
            } else if (nodeUnderMainPane instanceof TextField ){
                if (nodeUnderMainPane.getId().contains("City")){
                    citiesTextFields.add((TextField)nodeUnderMainPane);
                }
            }
        }
    }



    private void viewWeatherForCity(City city, int cityCounter){

        WeatherManager weatherManager = setWeatherController(city);
        setTemperaturesHoursDatesAndStates(weatherManager);
        showWeatherOnMainView(weatherManager, cityCounter);
        TitledPanesSetText();

    }

    private void setToday(){
        today = DateService.getTodayDate();
    }





     private void setCitiesList(){
         homeCityTextField.setText("Chorzów");
         destinationCityTextField.setText("Londyn");
         for(TextField tf : citiesTextFields) {
             if(!tf.getText().isEmpty()){

                 City city = new City(tf.getText());
                 cities.add(city);
             }
         }
//         homeCity = new City(citiesTextFields.get(0).getText());
    }
    private WeatherManager setWeatherController(City city){
//        cityWeatherForecastController = new WeatherManager(city);
        return new WeatherManager(city);
    }

    private void setTemperaturesHoursDatesAndStates(WeatherManager weatherManager){
        temperatures = weatherManager.getTemperatures();
        hours = weatherManager.getHours();
        days = weatherManager.getDays();
        weatherStatesInPolish = weatherManager.getWeatherStates();
        weatherStatesImages = weatherManager.getWeatherStatesImg();
    }

    public void createLabelsPanesAndImageViewsLists(AnchorPane cityPane){
        List<Label> hourLabels = new ArrayList<>();
        List<Label> temperatureLabels = new ArrayList<>();
        List<ImageView> imageViews = new ArrayList<>();
        List<TitledPane> titledPanes = new ArrayList<>();
        Node nodeUnderTitledPane = null;
        for (Node nodeUnderCityPane : cityPane.getChildren()) {
            if (nodeUnderCityPane instanceof TitledPane) {
                titledPanes.add((TitledPane) nodeUnderCityPane);
                allTitledPanes.add((TitledPane) nodeUnderCityPane);
                nodeUnderTitledPane = ((TitledPane) nodeUnderCityPane).getContent();
                if (nodeUnderTitledPane instanceof AnchorPane) {
                    dayAnchorPanes.add((AnchorPane) nodeUnderTitledPane);
                    for (Node nodeUnderAnchorPane : ((AnchorPane) nodeUnderTitledPane).getChildren()) {
                        if (nodeUnderAnchorPane instanceof HBox) {
                            for (Node nodeUnderHBox : ((HBox) nodeUnderAnchorPane).getChildren()) {
                                if (nodeUnderHBox instanceof GridPane) {
                                    for (Node nodeUnderGridPane : ((GridPane) nodeUnderHBox).getChildren()) {
                                        if (nodeUnderGridPane instanceof Label) {
                                            if (nodeUnderGridPane.getId().contains("hour")) {
                                                hourLabels.add((Label) nodeUnderGridPane);
                                            } else if (nodeUnderGridPane.getId().contains("temp")) {
                                                temperatureLabels.add((Label) nodeUnderGridPane);
                                            }
                                        } else if (nodeUnderGridPane instanceof ImageView) {
                                            imageViews.add((ImageView) nodeUnderGridPane);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        hourLabelsListsList.add(hourLabels);
        temperatureLabelsListsList.add(temperatureLabels);
        imageViewsListsList.add(imageViews);
        titledPanesListsList.add(titledPanes);
//        this.titledPanes.add(titledPanes);

    }


    public void TitledPanesSetText(){
//        int amountOfDays = 5;
        int counterOfDays = 0;
        String dayOfWeekInPolish;

        if (days.get(0) != today.getDayOfMonth()){
            counterOfDays = 1;
        }
        int initialDays = counterOfDays;

        int amountOfLists = titledPanesListsList.size();
        for(int i =0; i < amountOfLists; i++){
            counterOfDays = initialDays;
            for(TitledPane pane : titledPanesListsList.get(i)){
                dayOfWeekInPolish = dayOfWeekTranslationToPolish(today.plusDays(counterOfDays).getDayOfWeek().toString());
                pane.setText(dayOfWeekInPolish +" " + today.plusDays(counterOfDays).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                counterOfDays++;
            }
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

    private void showWeatherOnMainView(WeatherManager wm, int cityCounter){
        int offsetDependingOnCurrentHour = 0;

            if( wm.getHours().get(0) > 14){
                offsetDependingOnCurrentHour = 2;
            } else if (wm.getHours().get(0) > 8) {
                offsetDependingOnCurrentHour = 1;
            }

        int amountOfRecordsToDisplay = hourLabelsListsList.get(0).size() - offsetDependingOnCurrentHour;
        fillLabelsAndImagesWithData(amountOfRecordsToDisplay,offsetDependingOnCurrentHour, cityCounter );
    }
    
    private void fillLabelsAndImagesWithData(int amountOfRecordsToDisplay, int offsetDependingOnCurrentHour, int cityCounter){
        List<Label> hourLabels;
        List<Label> temperatureLabels;
        List<ImageView> imageViews;


            hourLabels = hourLabelsListsList.get(cityCounter);
            temperatureLabels = temperatureLabelsListsList.get(cityCounter);
            imageViews = imageViewsListsList.get(cityCounter);

            for (int i = 0; i < amountOfRecordsToDisplay; i++) {
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

    private void setCollapsingTitledPanes(){
        int amountOfTitlePanes = allTitledPanes.size();

        for (int i = 0; i < amountOfTitlePanes; i++){
            int finalI = i;
            allTitledPanes.get(i).expandedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    allTitledPanes.get(finalI).setDisable(true);
                    TitledPane currentTitledPane = allTitledPanes.get(finalI);
                    int indexOfCollapsedTitledPane = finalI;
                    boolean isTitledPaneExpanded = allTitledPanes.get(finalI).isExpanded();
                    collapsingTitledPanesAnimation(isTitledPaneExpanded, indexOfCollapsedTitledPane, currentTitledPane);
                }
            });
        }
    }

    private void collapsingTitledPanesAnimation(boolean isExpanded, int indexOfCollapsedTitledPane, TitledPane currentTitledPane){

        double positionOfTitledPane;
        int indexOfCurrentTp;
        TranslateTransition in;
        int yOfsset;

        if (isExpanded){
            yOfsset = 100;
        } else {
            yOfsset = -100;
        }

        for (TitledPane tp : allTitledPanes) {
            positionOfTitledPane = tp.translateYProperty().getValue();
            indexOfCurrentTp = allTitledPanes.indexOf(tp);
            tp.setDisable(true);
            in = new TranslateTransition(Duration.millis(300), tp);
            if (indexOfCurrentTp > indexOfCollapsedTitledPane && tp.getParent().getId() == currentTitledPane.getParent().getId() ) {
                in.setFromY(positionOfTitledPane);
                in.setToY(positionOfTitledPane + yOfsset);
                in.setInterpolator(Interpolator.EASE_IN);
            }
            in.setOnFinished(e -> {
                tp.setDisable(false);
                allTitledPanes.get(indexOfCollapsedTitledPane).setDisable(false);
            });
            in.play();
        }
    }
}
