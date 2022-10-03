package pl.pogoda.controller.services;

import javafx.scene.image.Image;

import java.util.Locale;

public class WeatherStateAsImage {


    public Image getImage(String weatherInEnglish){
        String imageAddress;
//        System.out.println(weatherInEnglish);
        imageAddress = "/icons/" + weatherInEnglish.toLowerCase(Locale.ROOT) +".png";
            Image img = new Image(getClass().getResourceAsStream(imageAddress));
       return img;
    }

}
