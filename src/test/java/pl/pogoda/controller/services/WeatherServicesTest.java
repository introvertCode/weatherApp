package pl.pogoda.controller.services;

import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class WeatherServicesTest {

    @Test
    void checkTranslationDayToPolish() {
        //given
        String dayInEnglish = "MONDAY";
        //when
        String dayInPolish = WeatherServices.dayOfWeekTranslationToPolish(dayInEnglish);
        System.out.println(dayInPolish);
        //then
        assertThat(dayInPolish, equalTo("PONIEDZIAŁEK"));
    }

    @ParameterizedTest
    @MethodSource("weekdaysCreation")
    void checkTranslationDaysToPolish(String dayInPolish, String dayInEnglish) {
        //then
        assertThat(dayInPolish, equalTo(WeatherServices.dayOfWeekTranslationToPolish(dayInEnglish)));
    }

    private static Stream<Arguments> weekdaysCreation() {
        return Stream.of(
                Arguments.of("PONIEDZIAŁEK", "MONDAY" ),
                Arguments.of("WTOREK", "TUESDAY"),
                Arguments.of("ŚRODA", "WEDNESDAY" ),
                Arguments.of("CZWARTEK", "THURSDAY" ),
                Arguments.of("PIĄTEK", "FRIDAY" ),
                Arguments.of("SOBOTA", "SATURDAY" ),
                Arguments.of("NIEDZIELA", "SUNDAY" )
        );
    }

    @Test
    void checkIfFileExists(){
        //given
        WeatherServices ws = new WeatherServices();
        String imgName = "clear";
        //when
        Image img = ws.getImage(imgName);
        //then
        assertThat(img,notNullValue());
    }

}
