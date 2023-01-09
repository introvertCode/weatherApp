package pl.pogoda;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

public class WeatherManagerTest {
    WeatherManager weathermanager;
    @BeforeEach
    void createWeatherManagerObj(){
        weathermanager = Mockito.mock(WeatherManager.class);
    }

    @Test
    void checkIfGetHoursReturnHasValidSize(){
        //given
        List<Integer> hours = new ArrayList<>();
        hours.add(8);
        hours.add(10);
        given(weathermanager.getHours()).willReturn(hours);
        //when
        List<Integer> hoursFromList = weathermanager.getHours();
        //then
        assertThat(hoursFromList, hasSize(2));
    }

    @Test
    void checkIfGetDaysReturnHasValidSize(){
        //given
        List<Integer> days = new ArrayList<>();
        days.add(1);
        days.add(2);
        given(weathermanager.getDays()).willReturn(days);
        //when
        List<Integer> daysFromList = weathermanager.getDays();
        //then
        assertThat(daysFromList, hasSize(2));
    }

    @Test
    void checkIfGetWeatherStatesReturnHasValidSize(){
        //given
        List<String> states = new ArrayList<>();
        states.add("Sunny");
        states.add("Cloudy");
        given(weathermanager.getWeatherStates()).willReturn(states);
        //when
        List<String> statesFromList = weathermanager.getWeatherStates();
        //then
        assertThat(statesFromList, hasSize(2));
    }

    @Test
    void checkIfGetWeatherStatesImgReturnHasValidSize(){
        //given
        List<Image> statesImg = new ArrayList<>();
        Image img = Mockito.mock(Image.class);
        statesImg.add(img);
        given(weathermanager.getWeatherStatesImg()).willReturn(statesImg);
        //when
        List<Image> statesImgFromList = weathermanager.getWeatherStatesImg();
        //then
        assertThat(statesImgFromList, hasSize(1));
    }
}
