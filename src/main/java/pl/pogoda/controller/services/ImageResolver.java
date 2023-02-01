package pl.pogoda.controller.services;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.pogoda.view.ColorTheme;

public abstract class ImageResolver {

    public static ImageView setThemeSwitchButtonImg(ColorTheme colorTheme){
        Image img;
        if (colorTheme == ColorTheme.LIGHT){
            img = new Image("/other icons/moon.png");
        } else{
            img = new Image("/other icons/sun.png");
        }
        ImageView view = new ImageView(img);
        view.setFitHeight(25);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        return view;
    }

    public static ImageView setSaveBtnImg(){
        Image img = new Image("/other icons/save.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(25);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        return view;
    }
}
