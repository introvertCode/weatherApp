package pl.pogoda.controller.weatherData;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class WeatherTest{


    @ParameterizedTest
    @ValueSource(ints = {8,14,20,7,13,19})
    void checkIfcheckHourFunctionreturnsTrue(int hour){
       //given
        Weather weather = Mockito.mock(
                Weather.class,
                Mockito.CALLS_REAL_METHODS);
        //then
        assertThat( weather.checkHour(hour), equalTo(true));
    }
    @ParameterizedTest
    @ValueSource(ints = {9,10,11,15,21,22,25,18})
    public void checkIfcheckHourFunctionreturnsFalse(int hour){
        //given
        Weather weather = Mockito.mock(
                Weather.class,
                Mockito.CALLS_REAL_METHODS);
        //then
        assertThat( weather.checkHour(hour), equalTo(false));
    }
}
