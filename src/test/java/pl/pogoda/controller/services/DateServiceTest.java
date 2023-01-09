package pl.pogoda.controller.services;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;

public class DateServiceTest {

    @Test
    public void testTodayDay(){
        //given
        LocalDateTime todayCheck;
        int day = LocalDateTime.now().getDayOfMonth();
        DateService dataService = spy(DateService.class);
        //when
        todayCheck = dataService.getTodayDate();
        //then
        assertThat(todayCheck.getDayOfMonth(), equalTo(day));
    }
}
