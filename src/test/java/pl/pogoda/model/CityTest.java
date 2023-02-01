package pl.pogoda.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CityTest {

    @Test
    public void createCityAndCheck(){
        //given
        String cityName = "Chorzów";
        //when
        City city = new City("Chorzów");
        //then
        assertThat(city.getCity(), equalTo(cityName));
    }


}
