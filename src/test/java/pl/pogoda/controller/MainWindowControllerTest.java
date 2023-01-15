package pl.pogoda.controller;

import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import pl.pogoda.WeatherManager;
import pl.pogoda.model.City;
import pl.pogoda.view.ViewFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class MainWindowControllerTest {
    @Test
    public void checkMethodsRunning(){
        //given
        ViewFactory viewFactory = new ViewFactory();
        String fxml = "/fxml/mainWindow.fxml";
        MainWindowController mwc = new MainWindowController(viewFactory,fxml);
        //when
        String fxmlName = mwc.getFxmlName();
        //then
       assertThat(fxmlName, equalTo(fxml));
    }

    @Test
    public void verifyclearData(){
        //given
        ViewFactory viewFactory = new ViewFactory();
        String fxml = "/fxml/mainWindow.fxml";
        MainWindowController mwc = new MainWindowController(viewFactory,fxml);
        mwc.hours.add(9);
        mwc.hours.add(10);
        assertThat(mwc.hours, hasSize(2));
        //when
        mwc.clearData();
        //then
        assertThat(mwc.hours, hasSize(0));

    }
    @Test
    public void verifyCitiesList(){
        //given
        MainWindowController mwc = mock(MainWindowController.class);
        City city = new City("Chorzów");
        WeatherManager wm = new WeatherManager(city);
        given(mwc.setWeatherController(city)).willReturn(wm);
        //when
        //then
        assertThat(wm.getCity(), equalTo(city));


    }
}
