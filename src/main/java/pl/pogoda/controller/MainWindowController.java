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
import javafx.scene.paint.Color;
import pl.pogoda.WeatherManager;
import pl.pogoda.controller.persistence.DataAccess;
import pl.pogoda.controller.services.DateService;
import pl.pogoda.controller.services.GuiObjectsControl;
import pl.pogoda.controller.services.ImageResolver;
import pl.pogoda.controller.services.WeatherServices;
import org.apache.commons.lang3.StringUtils;
import pl.pogoda.model.City;
import pl.pogoda.view.ColorTheme;
import pl.pogoda.view.ViewFactory;
import javafx.geometry.Pos;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.awt.Desktop;

public class MainWindowController extends BaseController implements Initializable{

    private final List<TextField> citiesTextFields = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    private final List<String> citiesInPolish = new ArrayList<>();
    private LocalDateTime today;
    private final List<List<Label>> hourLabelsListsList = new ArrayList<>();
    private final List<List<Label>> temperatureLabelsListsList = new ArrayList<>();
    private final List<List<ImageView>> imageViewsListsList = new ArrayList<>();
    private final List<TitledPane> allTitledPanes = new ArrayList<>();
    private final List<List<TitledPane>> titledPanesListsList = new ArrayList<>();
    private final List<AnchorPane> citiesAnchorPanes = new ArrayList<>();
    private List<Integer> hours = new ArrayList<>();
    private List<Integer> days = new ArrayList<>();
    private List<String> weatherStatesInPolish = new ArrayList<>();
    private List<Image> weatherStatesImages = new ArrayList<>();
    private List<String> temperatures = new ArrayList<>();
    private final List<Label> cityLabelsList = new ArrayList<>();
    @FXML
    private Button themeSwitch;
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
    private Button saveCitiesAndThemeBtn;
    @FXML
    private Button checkCountriesCodesBtn;
    @FXML
    private Label savedLabel;

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
        setToday();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setToday();
        loadDataFromFile();
        setItemsLists();
        viewWeatherForCities();
        setButtons();
        GuiObjectsControl.setCollapsingTitledPanes(allTitledPanes);

    }

    private void setButtons(){
        themeSwitch.setGraphic(ImageResolver.setThemeSwitchButtonImg(viewFactory.getColorTheme()));
        saveCitiesAndThemeBtn.setGraphic(ImageResolver.setSaveBtnImg());
        setCheckWeatherBtnClickAction();
        setThemeSwitchBtnClickAction();
        setWriteDataToFileBtnClickAction();
        setCheckCountriesCodesBtnClickAction();
        checkWeatherBtn.setDefaultButton(true);
        themeSwitch.setTooltip(new Tooltip("Zmień tryb kolorów"));
        checkWeatherBtn.setTooltip(new Tooltip("Sprawdź pogodę dla miast (enter)"));
        saveCitiesAndThemeBtn.setTooltip(new Tooltip("Zapisz miasta oraz schemat kolorów"));
    }

    private void setItemsLists(){
        setCitiesAnchorPanesAndTextFieldsList(mainPane);
        createLabelsPanesAndImageViewsLists();
        setCitiesList();
    }

    private void setThemeSwitchBtnClickAction(){
        themeSwitch.setOnAction(f -> {
            if (viewFactory.getColorTheme() == ColorTheme.DARK){
                viewFactory.setColorTheme(ColorTheme.LIGHT);
                viewFactory.updateStyles();
                themeSwitch.setGraphic(ImageResolver.setThemeSwitchButtonImg(ColorTheme.LIGHT));
            } else {
                viewFactory.setColorTheme(ColorTheme.DARK);
                viewFactory.updateStyles();
                themeSwitch.setGraphic(ImageResolver.setThemeSwitchButtonImg(ColorTheme.DARK));
            }
        });
    }

    public void setWriteDataToFileBtnClickAction(){
        saveCitiesAndThemeBtn.setOnAction(e->
        {
            try {
                List<String> providedData = new ArrayList<>();
                providedData.add(viewFactory.getColorTheme().toString());
                providedData.add(homeCityTextField.getText());
                providedData.add(destinationCityTextField.getText());
                DataAccess dataAccess = new DataAccess();
                dataAccess.createFile();
                dataAccess.writeToFile(providedData);
                savedLabel.setTextFill(Color.GREEN);
                savedLabel.setText("Zapisano!");
            } catch (Exception exc){
                exc.printStackTrace();
                savedLabel.setTextFill(Color.RED);
                savedLabel.setText("Błąd");
            }
        });
    }

    public void loadDataFromFile(){
        DataAccess dataAccess = new DataAccess();
        List<String> savedData = dataAccess.readFromFile();
        if (savedData.size()>2) {
            String theme = savedData.get(0);
            if (Objects.equals(theme, "DARK")) {
                viewFactory.setColorTheme(ColorTheme.DARK);
            } else {
                viewFactory.setColorTheme(ColorTheme.LIGHT);
            }
            homeCityTextField.setText(savedData.get(1));
            destinationCityTextField.setText(savedData.get(2));
        }
    }

    private void setCheckWeatherBtnClickAction(){
        checkWeatherBtn.setOnAction(e -> {
            clearData();
            savedLabel.setText("");
            errorLabel.setText("");
            setCitiesList();
            viewWeatherForCities();
        });
    }
    private void setCheckCountriesCodesBtnClickAction(){
        checkCountriesCodesBtn.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URL("https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements").toURI());
            } catch (IOException | URISyntaxException f) {
                f.printStackTrace();
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
        if (cities.size()>0) {
            int cityCounter = 0;
            try {
                for (City city : cities) {
                    WeatherManager weatherManager = setWeatherController(city);
                    setTemperaturesHoursDatesAndStates(weatherManager);
                    showWeatherOnMainView(weatherManager, cityCounter);
                    TitledPanesSetText();
                    cityLabelsList.get(cityCounter).setText(citiesInPolish.get(cityCounter));
                    cityCounter++;
                }
            } catch (NoDataFoundException e) {
                errorLabel.setText("Sprawdź wprowadzone dane lub połączenie internetowe!");
                clearData();
                cities.remove(cityCounter);
                viewWeatherForCities();
            } catch (Exception e) {
                errorLabel.setText("Sprawdź swoje połączenie internetowe lub spróbuj za chwilę ponownie!");
            }
        }
    }

    private void setToday(){
        today = DateService.getTodayDate();
    }

     private void setCitiesList(){
         citiesInPolish.clear();
         List<City> cities = new ArrayList<>();
         for(TextField tf : citiesTextFields) {
             if(!tf.getText().isEmpty()){
                 citiesInPolish.add(tf.getText());
                 String rawCity = tf.getText();
                 byte[] bytes = rawCity.getBytes(StandardCharsets.UTF_8);
                 String utf8EncodedCity = new String(bytes, StandardCharsets.UTF_8);
                 utf8EncodedCity = StringUtils.stripAccents(utf8EncodedCity);
                 City city = new City(utf8EncodedCity);
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
            Node nodeUnderTitledPane;
            for (Node nodeUnderCityPane : cityPane.getChildren()) {
                if (nodeUnderCityPane instanceof TitledPane) {
                    titledPanes.add((TitledPane) nodeUnderCityPane);
                    allTitledPanes.add((TitledPane) nodeUnderCityPane);
                    nodeUnderTitledPane = ((TitledPane) nodeUnderCityPane).getContent();
                    if (nodeUnderTitledPane instanceof AnchorPane) {
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
            GuiObjectsControl.centerImage(imageViews.get(i + offsetDependingOnCurrentHour));
            Tooltip.install(imageViews.get(i + offsetDependingOnCurrentHour), new Tooltip(weatherStatesInPolish.get(i)));
        }
    }
}
