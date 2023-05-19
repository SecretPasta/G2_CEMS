package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
	public static void start() throws IOException {
		SceneManagment.createNewStage("/gui/Login.fxml", null, "Login").show();
	}

}

