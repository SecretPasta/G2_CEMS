package gui;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CemsHomeController {
	public CreateExamScreenController cesc;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button typeLecturerButton;

    @FXML
    private Button typeStudentButton;

    @FXML
    public void initCemsHomeController(Stage primaryStage) {
    	try {
			
			// set title for the stage
			primaryStage.setTitle("CEMS - Start Page");
			
			// create a AnchorPane
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("CemsStart.fxml"));
			
			// create a scene
			Scene scene = new Scene(root,700,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// set the scene
			primaryStage.setScene(scene);
			
			// window icon
			//primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("logo.png")));
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    public void createExamScreen(ActionEvent event) throws Exception{
    	this.cesc = new CreateExamScreenController();
    	this.cesc.initCreateExamScreenController();
    }
    
    @FXML
    public void lecturerScreen() {
    	
    }

}
