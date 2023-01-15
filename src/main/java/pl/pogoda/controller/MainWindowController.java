package pl.pogoda.controller;

import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import pl.pogoda.WeatherManager;
import pl.pogoda.controller.persistence.DataAccess;
import pl.pogoda.controller.persistence.SavedState;
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
import java.util.*;
import java.awt.Desktop;

public class MainWindowController extends BaseController implements Initializable{

    final List<TextField> citiesTextFields = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    private final List<String> citiesInPolish = new ArrayList<>();
    private LocalDateTime today;
    private final List<TitledPane> titledPanes = new ArrayList<>();
    private final List<AnchorPane> citiesAnchorPanes = new ArrayList<>();
    List<Integer> hours = new ArrayList<>();
    private List<Integer> days = new ArrayList<>();
    private List<String> weatherStatesInPolish = new ArrayList<>();
    private List<Image> weatherStatesImages = new ArrayList<>();
    private List<String> temperatures = new ArrayList<>();
    private final List<Label> cityLabelsList = new ArrayList<>();
    private final List<Label> hourLabels = new ArrayList<>();
    private final List<Label> temperatureLabels = new ArrayList<>();
    private final List<ImageView> imageViews = new ArrayList<>();
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
        setSavedData();
        setItemsLists();
        viewWeatherForCities();
        setButtons();
        GuiObjectsControl.setCollapsingTitledPanes(titledPanes);

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
        createTitledPanesList();
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
            DataAccess dataAccess = new DataAccess();
            try {
                List<String> providedData = new ArrayList<>();
                providedData.add(viewFactory.getColorTheme().toString());
                providedData.add(homeCityTextField.getText());
                providedData.add(destinationCityTextField.getText());
                dataAccess.writeToFile(providedData);
                savedLabel.setTextFill(Color.GREEN);
                savedLabel.setText("Zapisano!");
            } catch (Exception exc){
                exc.printStackTrace();
                savedLabel.setTextFill(Color.RED);
                savedLabel.setText("Błąd");
                errorLabel.setText(dataAccess.getError());
            }
        });
    }

    public void setSavedData(){
        SavedState savedState = new SavedState();
        homeCityTextField.setText(savedState.getSavedHomeCity());
        destinationCityTextField.setText(savedState.getSavedDestinationCity());
        String theme = savedState.getSavedColorTheme();
            if (Objects.equals(theme, "DARK")) {
                viewFactory.setColorTheme(ColorTheme.DARK);
            } else {
                viewFactory.setColorTheme(ColorTheme.LIGHT);
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

    void clearData() {
        hours.clear();
        days.clear();
        weatherStatesInPolish.clear();
        weatherStatesImages.clear();
        temperatures.clear();

        for(TitledPane pane : titledPanes){
                pane.setText("");
            }
        for(Label label : hourLabels){
            label.setText("");
        }
        for(Label label : temperatureLabels){
            label.setText("");
        }
        for(ImageView image : imageViews){
            image.setImage(null);
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
        setToday();
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
                e.printStackTrace();
                errorLabel.setText("Sprawdź wprowadzone dane lub połączenie internetowe!");
                clearData();
                cities.remove(cityCounter);
                viewWeatherForCities();
            } catch (Exception e) {
                errorLabel.setText("Sprawdź swoje połączenie internetowe lub spróbuj za chwilę ponownie!");
                e.printStackTrace();
            }
        }
    }

    private void setToday(){
        DateService dataService = new DateService();
        today = dataService.getTodayDate();
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

    WeatherManager setWeatherController(City city) {
        return new WeatherManager(city);
    }

    private void setTemperaturesHoursDatesAndStates(WeatherManager weatherManager){
        temperatures = weatherManager.getTemperatures();
        hours = weatherManager.getHours();
        days = weatherManager.getDays();
        weatherStatesInPolish = weatherManager.getWeatherStates();
        weatherStatesImages = weatherManager.getWeatherStatesImg();
    }

    private void createTitledPanesList(){
        for(AnchorPane cityPane : citiesAnchorPanes) {
            Node nodeUnderTitledPane;
            List<TitledPane> titledPanes = new ArrayList<>();
            List<Node> titledPanesNd;

            titledPanesNd = cityPane.getChildren().filtered(node -> node instanceof TitledPane);
            for (Node nodeUnderCityPane : titledPanesNd) {
                if (nodeUnderCityPane instanceof TitledPane) {

                    titledPanes.add((TitledPane) nodeUnderCityPane);
                    this.titledPanes.add((TitledPane) nodeUnderCityPane);
                    nodeUnderTitledPane = ((TitledPane) nodeUnderCityPane).getContent();
                    searchPanes(nodeUnderTitledPane);
                }
            }
        }

    }

    private void searchPanes(Node nodeUpper) {
        if (nodeUpper instanceof Label) {
            if (nodeUpper.getId().contains("hour")) {
                hourLabels.add((Label) nodeUpper);
            } else if (nodeUpper.getId().contains("temp")) {
                temperatureLabels.add((Label) nodeUpper);
            }
        } else if (nodeUpper instanceof ImageView) {
            imageViews.add((ImageView)nodeUpper);
        } else if (nodeUpper instanceof Parent) {
            for (Node node : ((Parent) nodeUpper).getChildrenUnmodifiable()) {
                searchPanes(node);
            }
        }
    }

    private void TitledPanesSetText(){
        String dayOfWeekInPolish;
        int counterOfDays = 0;
        int amountOfTitlePanesForEachCity;

        amountOfTitlePanesForEachCity = titledPanes.size()/ citiesAnchorPanes.size();
        if (days.get(0) != today.getDayOfMonth()){
            counterOfDays = 1;
        }
        int initialDays = counterOfDays;
        int amountOfCities = cities.size();

        int amountOfPanesToFill = amountOfTitlePanesForEachCity * amountOfCities;
            counterOfDays = initialDays;
            TitledPane pane;
            for(int i = 0; i < amountOfPanesToFill;i++){
                pane = titledPanes.get(i);
                dayOfWeekInPolish = WeatherServices.dayOfWeekTranslationToPolish(today.plusDays(counterOfDays).getDayOfWeek().toString());
                pane.setText(dayOfWeekInPolish +" " + today.plusDays(counterOfDays).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                counterOfDays++;

                if (i == amountOfTitlePanesForEachCity - 1){
                    if (days.get(0) != today.getDayOfMonth()){
                        counterOfDays = 1;
                    } else {
                        counterOfDays = 0;
                    }
                }

            }
//        }
    }

    private void showWeatherOnMainView(WeatherManager wm, int cityCounter){
        int offsetDependingOnCurrentHour = 0;
            if( wm.getHours().get(0) > 18){
                offsetDependingOnCurrentHour = 2;
            } else if (wm.getHours().get(0) > 12) {
                offsetDependingOnCurrentHour = 1;
            }
        int amountOfCitiesAnchorPanes = citiesAnchorPanes.size();
        int amountOfRecordsToDisplay = hourLabels.size()/amountOfCitiesAnchorPanes - offsetDependingOnCurrentHour;
        fillLabelsAndImagesWithData(amountOfRecordsToDisplay,offsetDependingOnCurrentHour, cityCounter );
    }
    
    private void fillLabelsAndImagesWithData(int amountOfRecordsToDisplay, int offsetDependingOnCurrentHour, int cityCounter){
        int offsetDependingOnCurrentHourFixed = offsetDependingOnCurrentHour + (cityCounter * offsetDependingOnCurrentHour);
        int anchorPaneOffset = cityCounter * amountOfRecordsToDisplay;
        for (int i = 0 ; i < amountOfRecordsToDisplay; i++) {
            hourLabels.get(i + offsetDependingOnCurrentHourFixed + anchorPaneOffset).setText(hours.get(i).toString() + ":00");
            hourLabels.get(i + offsetDependingOnCurrentHourFixed+ anchorPaneOffset).setAlignment(Pos.CENTER);
            temperatureLabels.get(i + offsetDependingOnCurrentHourFixed + anchorPaneOffset).setText(temperatures.get(i));
            temperatureLabels.get(i + offsetDependingOnCurrentHourFixed + anchorPaneOffset).setAlignment(Pos.CENTER);
            imageViews.get(i + offsetDependingOnCurrentHourFixed + anchorPaneOffset).setImage(weatherStatesImages.get(i));
            GuiObjectsControl.centerImage(imageViews.get(i + offsetDependingOnCurrentHourFixed + anchorPaneOffset));
            Tooltip.install(imageViews.get(i + offsetDependingOnCurrentHourFixed + anchorPaneOffset), new Tooltip(weatherStatesInPolish.get(i)));
        }
    }
}
