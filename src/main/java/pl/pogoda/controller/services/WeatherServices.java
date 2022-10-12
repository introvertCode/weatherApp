package pl.pogoda.controller.services;

import javafx.scene.image.Image;

import java.util.Locale;

public class WeatherServices {


    public Image getImage(String weatherInEnglish){
        String imageAddress;
//        System.out.println(weatherInEnglish);
        imageAddress = "/icons/" + weatherInEnglish.toLowerCase(Locale.ROOT) +".png";
            Image img = new Image(getClass().getResourceAsStream(imageAddress));
       return img;
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
