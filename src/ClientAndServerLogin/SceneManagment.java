package ClientAndServerLogin;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;




/**
 * A utility class for managing JavaFX scenes.
 */
public class SceneManagment {

    /**
     * Creates a new JavaFX stage with the specified FXML file, CSS file, and title.
     *
     * @param fxmlFilePath the path to the FXML file
     * @param cssFilePath the path to the CSS file (or null if no CSS file should be used)
     * @param title the title of the stage
     * @return the new stage
     * @throws IOException if the FXML file could not be loaded
     */
    public static Stage createNewStage(String fxmlFilePath, String cssFilePath, String title) throws IOException {
        Parent root = FXMLLoader.load(SceneManagment.class.getResource(fxmlFilePath));
        Scene scene = new Scene(root);
        if (cssFilePath != null) {
            scene.getStylesheets().add(SceneManagment.class.getResource(cssFilePath).toExternalForm());
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false); // disable window resize option
        //***Round Corners For Window + Disable top menu
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        //stage.initStyle(StageStyle.UNDECORATED); // disable the menu row on the top of the window
        // we can move window without the menu row
        scene.setOnMousePressed(pressEvent -> scene.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
        return stage;
    }

}


