package pl.pogoda.controller.services;

import javafx.scene.image.Image;

import java.util.Locale;
import java.util.Objects;

public class WeatherServices {

    public Image getImage(String weatherInEnglish){
        String imageAddress;
        imageAddress = "/icons/" + weatherInEnglish.toLowerCase(Locale.ROOT) +".png";
       return new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageAddress)));
    }

    public static String dayOfWeekTranslationToPolish(String dayOfWeekInEnglish){
        String dayOfWeekInPolish = "";
        switch(dayOfWeekInEnglish){
            case "MONDAY":
                dayOfWeekInPolish = "PONIEDZIAŁEK";
                break;
            case "TUESDAY":
                dayOfWeekInPolish = "WTOREK";
                break;
            case "WEDNESDAY":
                dayOfWeekInPolish = "ŚRODA";
                break;
            case "THURSDAY":
                dayOfWeekInPolish = "CZWARTEK";
                break;
            case "FRIDAY":
                dayOfWeekInPolish = "PIĄTEK";
                break;
            case "SATURDAY":
                dayOfWeekInPolish = "SOBOTA";
                break;
            case "SUNDAY":
                dayOfWeekInPolish = "NIEDZIELA";
                break;
        }
        return dayOfWeekInPolish;
    }
}