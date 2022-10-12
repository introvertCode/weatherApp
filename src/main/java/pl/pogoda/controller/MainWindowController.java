package pl.pogoda.controller;

import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import pl.pogoda.WeatherManager;
import pl.pogoda.controller.services.DateService;
import pl.pogoda.controller.services.GuiObjectsControll;
import pl.pogoda.controller.services.WeatherServices;

import pl.pogoda.model.City;
import pl.pogoda.view.ColorTheme;
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

    List<TextField> citiesTextFields = new ArrayList<>();
    List<City> cities = new ArrayList<>();
    LocalDateTime today;
    List<List<Label>> hourLabelsListsList = new ArrayList<>();
    List<List<Label>> temperatureLabelsListsList = new ArrayList<>();
    List<List<ImageView>> imageViewsListsList = new ArrayList<>();
    List<TitledPane> allTitledPanes = new ArrayList<>();
    List<List<TitledPane>> titledPanesListsList = new ArrayList<>();
    List<AnchorPane> dayAnchorPanes = new ArrayList<>();
    List<AnchorPane> citiesAnchorPanes = new ArrayList<>();
    List<Integer> hours = new ArrayList<>();
    List<Integer> days = new ArrayList<>();
    List<String> weatherStatesInPolish = new ArrayList<>();
    List<Image> weatherStatesImages = new ArrayList<>();
    List<String> temperatures = new ArrayList<>();

    List<Label> cityLabelsList = new ArrayList<>();
    @FXML
    private Button themeSwitch;

    @FXML
    private Label city1;

    @FXML
    private Label city2;

    @FXML
    private Button checkWeatherBtn;

    @FXML
    private TextField destinationCityTextField;


    @FXML
    private Label errorLabel;

    @FXML
    private TextField homeCityTextField;

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
        homeCityTextField.setText("Chorzów");
        destinationCityTextField.setText("Londyn");

        setCitiesAnchorPanesAndTextFieldsList(mainPane);
        setToday();

        createLabelsPanesAndImageViewsLists();
        setCitiesList();

        viewWeatherForCities();


        GuiObjectsControll.setCollapsingTitledPanes(allTitledPanes);

        checkWeatherBtn.setOnAction(e -> {
            clearData();
            errorLabel.setText("");
            setCitiesList();
            viewWeatherForCities();

        });

        themeSwitch.setOnAction(f -> {

            if (viewFactory.getColorTheme() == ColorTheme.DARK){
                viewFactory.setColorTheme(ColorTheme.LIGHT);
                viewFactory.updateStyles();
            } else {
                viewFactory.setColorTheme(ColorTheme.DARK);
                viewFactory.updateStyles();
            }
        });

    }

    private void clearData() {
        hours.clear();
        days.clear();
        weatherStatesInPolish.clear();
        weatherStatesImages.clear();
        temperatures.clear();

        int amountOfLists = titledPanesListsList.size();
        for(int i =0; i < amountOfLists; i++){
            for(TitledPane pane : titledPanesListsList.get(i)){
                pane.setText("");
            }

            for(Label label : hourLabelsListsList.get(i)){
                label.setText("");
            }

            for(Label label : temperatureLabelsListsList.get(i)){
                label.setText("");
            }

            for(ImageView image : imageViewsListsList.get(i)){
                image.setImage(null);
            }
        }

        for (Label label : cityLabelsList){
            label.setText("");
        }



    }


    private void setCitiesAnchorPanesAndTextFieldsList(AnchorPane mainPane){
        for (Node nodeUnderMainPane : mainPane.getChildren()) {
            if (nodeUnderMainPane instanceof AnchorPane) {
                citiesAnchorPanes.add((AnchorPane) nodeUnderMainPane);
            } else if (nodeUnderMainPane instanceof TextField ){
                if (nodeUnderMainPane.getId().contains("City")){
                    citiesTextFields.add((TextField)nodeUnderMainPane);
                }
            } else if (nodeUnderMainPane instanceof Label){
                if (nodeUnderMainPane.getId() != null){
                    if (nodeUnderMainPane.getId().contains("city")){
                        cityLabelsList.add((Label)nodeUnderMainPane);
                    }
                }

            }
        }
    }

    private void viewWeatherForCities(){

        int cityCounter = 0;
        try {

            for (City city : cities) {
                WeatherManager weatherManager = setWeatherController(city);
                setTemperaturesHoursDatesAndStates(weatherManager);
                showWeatherOnMainView(weatherManager, cityCounter);
                TitledPanesSetText();
                cityLabelsList.get(cityCounter).setText(city.getCity());
                cityCounter++;
            }
        }  catch (NoDataFoundException e) {
                errorLabel.setText("Sprawdź wprowadzone dane lub połączenie internetowe!");
                clearData();
                cities.remove(cityCounter);
                viewWeatherForCities();
        }
        catch (Exception e){
            errorLabel.setText("Sprawdź swoje połączenie internetowe lub spróbuj za chwilę ponownie!");
        }

    }

    private void setToday(){
        today = DateService.getTodayDate();
    }

     private void setCitiesList(){
         List<City> cities = new ArrayList<>();
         for(TextField tf : citiesTextFields) {
             if(!tf.getText().isEmpty()){
                 City city = new City(tf.getText());
                 cities.add(city);
             }
         }
         this.cities = cities;
    }
    private WeatherManager setWeatherController(City city) {
        return new WeatherManager(city);
    }

    private void setTemperaturesHoursDatesAndStates(WeatherManager weatherManager){
        temperatures = weatherManager.getTemperatures();
        hours = weatherManager.getHours();
        days = weatherManager.getDays();
        weatherStatesInPolish = weatherManager.getWeatherStates();
        weatherStatesImages = weatherManager.getWeatherStatesImg();
    }

    private void createLabelsPanesAndImageViewsLists(){
        for(AnchorPane cityPane : citiesAnchorPanes){

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
        }
    }


    private void TitledPanesSetText(){
        int counterOfDays = 0;
        String dayOfWeekInPolish;

        if (days.get(0) != today.getDayOfMonth()){
            counterOfDays = 1;
        }
        int initialDays = counterOfDays;

        int amountOfLists = cities.size();
        for(int i =0; i < amountOfLists; i++){
            counterOfDays = initialDays;
            for(TitledPane pane : titledPanesListsList.get(i)){
                dayOfWeekInPolish = WeatherServices.dayOfWeekTranslationToPolish(today.plusDays(counterOfDays).getDayOfWeek().toString());
                pane.setText(dayOfWeekInPolish +" " + today.plusDays(counterOfDays).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                counterOfDays++;
            }
        }
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
                GuiObjectsControll.centerImage(imageViews.get(i + offsetDependingOnCurrentHour));
                Tooltip.install(imageViews.get(i + offsetDependingOnCurrentHour), new Tooltip(weatherStatesInPolish.get(i)));
            }

    }




}
