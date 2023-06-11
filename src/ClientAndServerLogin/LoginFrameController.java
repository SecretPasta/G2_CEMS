package ClientAndServerLogin;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import client.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LoginFrameController implements Initializable{
	
	@FXML
	private AnchorPane dialogRoot;
	
	@FXML
	private AnchorPane snackbarRoot;

	@FXML
	private JFXSnackbar snackbarError;
	
	@FXML
	private Button btnClose;
		
	@FXML
	private StackPane dialogPane;
	
	@FXML
	private Label lblForgotPassword;
	
	@FXML
	private Label lblSignUp;
	
	@FXML
	private JFXDialog dialog;
	
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
	    	snackbarError = new JFXSnackbar(snackbarRoot);
			JFXSnackbarLayout snackbarLayout = new JFXSnackbarLayout("Error: Missing fields");
			snackbarError.setPrefWidth(snackbarRoot.getPrefWidth() - 40);
	        snackbarError.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
	    } else {
	        
	        CurrEvent = event; // Save the current scene to hide
	        
	        ArrayList<String> userInfo = new ArrayList<>();
	        userInfo.add("UserLogin"); // Add the login action to the user info
	        if(loginAs.getSelectionModel().getSelectedItem().equals("Head Of Department")) {
	        	userInfo.add("Hod"); // Add the selected login role to the user info
	        }
	        else {
	        	userInfo.add(loginAs.getSelectionModel().getSelectedItem()); // Add the selected login role to the user info
	        }
	        userInfo.add(txtUsername.getText()); // Add the entered username to the user info
	        userInfo.add(txtPassword.getText()); // Add the entered password to the user info
	        userInfo.add(InetAddress.getLocalHost().getHostAddress()); // ip of the user
	        
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
	        	snackbarError = new JFXSnackbar(snackbarRoot);
				JFXSnackbarLayout snackbarLayout = new JFXSnackbarLayout(reason);
				snackbarError.setPrefWidth(snackbarRoot.getPrefWidth() - 40);
		        snackbarError.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null)); // Sets the login screen's label with the reason for login failure
	        }
	    });
	}
	
	@FXML
    void adminError(MouseEvent event) {
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		Text titleText = new Text("Contact your administrator");
		Text bodyText = new Text("\nWe apologize for any inconvenience caused during your 'forgot password' and 'sign up' processes. To complete these"+
		"\nactions successfully, please contact our administrator. They are available to assist you promptly." +
		"\nThank you for your understanding!");
		titleText.setFont(Font.font("System", 24));
		titleText.setStyle("-fx-font-weight: bold;");
		titleText.setFill(Color.web("#FAF9F6"));
		bodyText.setFont(Font.font("System", 12));
		bodyText.setFill(Color.web("#1E1E1E"));
		dialogLayout.setHeading(titleText);	
		dialogLayout.setBody(bodyText);
		dialogLayout.setPrefSize(dialogPane.getPrefWidth(), dialogPane.getPrefHeight());
		JFXButton btnOkay = new JFXButton("Okay");
		dialogLayout.getChildren().add(btnOkay);	
		dialog = new JFXDialog(dialogPane, dialogLayout, JFXDialog.DialogTransition.TOP);
		btnOkay.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent arg0) {
				dialog.close();
				snackbarRoot.setDisable(false);
				dialogPane.toBack();
			}
		});
		dialogLayout.setActions(btnOkay);
		dialog.show();
		dialogPane.toFront();
		snackbarRoot.setDisable(true);
		
    }

}

