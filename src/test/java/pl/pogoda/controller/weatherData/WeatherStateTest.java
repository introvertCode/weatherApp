package pl.pogoda.controller.weatherData;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class WeatherStateTest {
    @Test
    public void testFirstParenthesisIsOnSecondPlaceAndThereAre2ParenthesisInString(){
        //given
        String stringToSearch = "ab(asdf(";
        WeatherState weatherState = Mockito.mock(
                WeatherState.class,
                Mockito.CALLS_REAL_METHODS);
        //when
        ArrayList<Integer> parenthesisIndex = weatherState.findParenthesis(stringToSearch);
        //then
        assertThat(parenthesisIndex, hasSize(2));
        assertThat(parenthesisIndex.get(0), equalTo(2));
    }


}
