package ClientAndServerLogin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class LoginFrameController implements Initializable{
	
	@FXML
	private AnchorPane root;

	@FXML
	private JFXSnackbar snackbarError;
	
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
	    loginAs.getItems().add("Lecturer"); // Add "Lecturer" option to the loginAs dropdown menu
	    loginAs.getItems().add("Student"); // Add "Student" option to the loginAs dropdown menu
	    loginAs.getItems().add("Head Of Department"); // Add "Head Of Department" option to the loginAs dropdown menu
	    lblMessage.setTextFill(Color.color(1, 0, 0)); // Set the text color of lblMessage to red
	}
	
	/**
	 * Handles the action when the close button is clicked.
	 *
	 * @param event The action event
	 * @throws Exception If an exception occurs during the process
	 */
	public void getCloseBtn(ActionEvent event) throws Exception {
	    ((Node) event.getSource()).getScene().getWindow().hide(); // Hiding the primary window
	    ClientUI.chat.client.quit(); // Sending a quit signal to the client
	}

	
	/**
	 * Handles the action when the login button is clicked.
	 *
	 * @param event The action event
	 * @throws Exception If an exception occurs during the process
	 */
	public void getLoginBtn(ActionEvent event) throws Exception {
	    if (txtUsername.getText().equals("") || txtPassword.getText().equals("") || loginAs.getSelectionModel().getSelectedItem() == null) {
	    	snackbarError = new JFXSnackbar(root);
			JFXSnackbarLayout snackbarLayout = new JFXSnackbarLayout("Error: Missing fields");
			snackbarError.setPrefWidth(root.getPrefWidth() - 40);
	        snackbarError.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
	    } else {
	        lblMessage.setText(""); // Clear the error message
	        
	        CurrEvent = event; // Save the current scene to hide
	        
	        ArrayList<String> userInfo = new ArrayList<>();
	        userInfo.add("UserLogin"); // Add the login action to the user info
	        userInfo.add(loginAs.getSelectionModel().getSelectedItem()); // Add the selected login role to the user info
	        userInfo.add(txtUsername.getText()); // Add the entered username to the user info
	        userInfo.add(txtPassword.getText()); // Add the entered password to the user info
	        
	        ClientUI.chat.accept(userInfo); // Send the user info to the server to check the username and password and retrieve user details from the database
	    }
	}

	
	/**
	 * Hides the current JavaFX scene.
	 * call this function from non javaFX
	 *
	 * @throws Exception If an exception occurs during the process
	 */
	public static void hideCurrentScene() throws Exception {
	    Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            ((Node) CurrEvent.getSource()).getScene().getWindow().hide(); // Hides the primary window
	        }
	    });
	}

	
	/**
	 * Starts the login screen.
	 *
	 * @throws IOException If an I/O exception occurs during the process
	 */
	public static void start() throws IOException {
	    SceneManagment.createNewStage("/ClientAndServerLogin/LoginGUI.fxml", null, "Login").show(); // Creates and shows the login screen stage
	}


	/**
	 * Displays the reason for login failure on the login screen.
	 * call this function from non javaFX
	 *
	 * @param reason The reason for login failure
	 * @throws IOException If an I/O exception occurs during the process
	 */
	public void userLoginFailed(String reason) throws IOException {
	    Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	        	snackbarError = new JFXSnackbar(root);
				JFXSnackbarLayout snackbarLayout = new JFXSnackbarLayout(reason);
				snackbarError.setPrefWidth(root.getPrefWidth() - 40);
		        snackbarError.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null)); // Sets the login screen's label with the reason for login failure
	        }
	    });
	}
	
	@FXML
    void adminError(MouseEvent event) {
		snackbarError = new JFXSnackbar(root);
		JFXSnackbarLayout snackbarLayout = new JFXSnackbarLayout("Contact your admin to perform this operation");
		snackbarError.setPrefWidth(root.getPrefWidth() - 40);
        snackbarError.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
		
		
		/*
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setHeading(new Text("Contact your administrator"));
		dialogLayout.setBody(new Text("hello world"));
		dialogLayout.setPrefSize(stackPane.getPrefWidth(), stackPane.getPrefHeight());
		JFXButton buttonOkay = new JFXButton("Okay");
		dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
		buttonOkay.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent arg0) {
				dialog.close();
				stackPane.toBack();
				
			}
		});
		dialogLayout.setActions(buttonOkay);
		stackPane.toFront();
		dialog.show();
		*/
    }

}

