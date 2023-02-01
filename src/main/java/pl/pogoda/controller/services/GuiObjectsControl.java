package pl.pogoda.controller.services;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;

public class GuiObjectsControl {

    private GuiObjectsControl(){

    }

    public static void setCollapsingTitledPanes(List<TitledPane> allTitledPanes){
        int amountOfTitlePanes = allTitledPanes.size();
        for (int i = 0; i < amountOfTitlePanes; i++){
            int indexOfCurrentTitledPane = i;
            allTitledPanes.get(i).expandedProperty().addListener((observableValue, aBoolean, t1) -> {
                allTitledPanes.get(indexOfCurrentTitledPane).setDisable(true);
                TitledPane currentTitledPane = allTitledPanes.get(indexOfCurrentTitledPane);
                boolean isTitledPaneExpanded = allTitledPanes.get(indexOfCurrentTitledPane).isExpanded();
                collapsingTitledPanesAnimation(isTitledPaneExpanded, indexOfCurrentTitledPane, currentTitledPane, allTitledPanes);
            });
        }
    }

    private static void collapsingTitledPanesAnimation(boolean isExpanded, int indexOfCollapsedTitledPane, TitledPane currentTitledPane, List<TitledPane> allTitledPanes){

        double positionOfTitledPane;
        int indexOfCurrentTp;
        TranslateTransition in;
        int yOfsset;

        if (isExpanded){
            yOfsset = 100;
        } else {
            yOfsset = -100;
        }

        for (TitledPane tp : allTitledPanes) {
            positionOfTitledPane = tp.translateYProperty().getValue();
            indexOfCurrentTp = allTitledPanes.indexOf(tp);
            tp.setDisable(true);
            in = new TranslateTransition(Duration.millis(300), tp);
            if (indexOfCurrentTp > indexOfCollapsedTitledPane && tp.getParent().getId().equals( currentTitledPane.getParent().getId())) {
                in.setFromY(positionOfTitledPane);
                in.setToY(positionOfTitledPane + yOfsset);
                in.setInterpolator(Interpolator.EASE_IN);
            }
            in.setOnFinished(e -> {
                tp.setDisable(false);
                allTitledPanes.get(indexOfCollapsedTitledPane).setDisable(false);
            });
            in.play();
        }
    }

    public static void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();
            double reducCoeff = Math.min(ratioX, ratioY);
            double w = img.getWidth() * reducCoeff;
            double h = img.getHeight() * reducCoeff;
            imageView.setTranslateX((imageView.getFitWidth() - w) / 2);
            imageView.setTranslateY((imageView.getFitHeight() - h) / 2);
        }
    }
}
