package pl.pogoda.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.pogoda.controller.BaseController;
import pl.pogoda.controller.MainWindowController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewFactory {

    private final List<Stage> activeStages = new ArrayList<>();
    private ColorTheme colorTheme = ColorTheme.DARK;

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(this, "/fxml/mainWindow.fxml");
        initializeStage(controller);
    }

    private void initializeStage(BaseController baseController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);

        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setMaxWidth(980);
        stage.setMaxHeight(940);
        stage.setScene(scene);
        stage.show();
        activeStages.add(stage);
        updateStyles();
    }
    public void updateStyles() {
        for(Stage stage:activeStages) {
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(colorTheme.path)).toExternalForm());
        }
    }


}
