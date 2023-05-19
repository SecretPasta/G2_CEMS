package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable{
	@FXML
	private Button btnClose;
	
    @FXML
    private JFXButton loginBtn;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsrname;
    
    @FXML
    private JFXComboBox<String> loginAs;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginAs.getItems().add("Lecturer");
		loginAs.getItems().add("Student");
		loginAs.getItems().add("Head Of Department");
		
		//To get a String value of JFXComboBox
		//loginAs.getSelectionModel().getSelectedItem();
		
		
	}
	
	public static void start() throws IOException {
		SceneManagment.createNewStage("/gui/Login.fxml", null, "Login").show();
	}

}

