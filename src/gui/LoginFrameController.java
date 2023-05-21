package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import client.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginFrameController implements Initializable{
	@FXML
	private Button btnClose;
	
    @FXML
    private Label lblMessage;
	
    @FXML
    private JFXButton loginBtn;

    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private TextField txtUsername;
    
    @FXML
    private JFXComboBox<String> loginAs;
    
    private static ActionEvent CurrEvent;
    
    public static LoginFrameController instance;
    
    public LoginFrameController() {
    	instance = this;
	}
    
    public static LoginFrameController getInstance() {
    	return instance;
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginAs.getItems().add("Lecturer");
		loginAs.getItems().add("Student");
		loginAs.getItems().add("Head Of Department");
		lblMessage.setTextFill(Color.color(1, 0, 0));
		//To get a String value of JFXComboBox
		//loginAs.getSelectionModel().getSelectedItem();
		
		
	}
	
	public void getCloseBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		ClientUI.chat.client.quit();
	}
	
	public void getLoginBtn(ActionEvent event) throws Exception {
		if(txtUsername.getText().equals("") || txtPassword.getText().equals("") || loginAs.getSelectionModel().getSelectedItem() == null) {
			lblMessage.setText("[Error] Missing fields");
		}
		
		else {		
			lblMessage.setText("");
			CurrEvent = event; // save current scene to hide
			ArrayList<String> userInfo = new ArrayList<>();
			userInfo.add("UserLogin");
			userInfo.add(loginAs.getSelectionModel().getSelectedItem());
			userInfo.add(txtUsername.getText());
			userInfo.add(txtPassword.getText());
			ClientUI.chat.accept(userInfo); // send to server to info of the user to check username and password and to get details about the user from DB
				
		}
		
	}
	
	// call this function from non javaFX
	public static void hideCurrentScene() throws Exception {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	((Node) CurrEvent.getSource()).getScene().getWindow().hide(); // hiding primary window
            }
        });
	}
	
	public static void start() throws IOException {
		SceneManagment.createNewStage("/gui/LoginGUI.fxml", null, "Login").show();
	}

	// call this function from non javaFX
	public void loginFailedInvalidUserPass() throws IOException {	
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblMessage.setText("[Error] Wrong Username or Password");
            }
        });
	}
}

