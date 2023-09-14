package ClientAndServerLogin;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;

import client.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class LoginFrameController implements Initializable{
	
	@FXML
	private AnchorPane root;
	
	@FXML
	private AnchorPane snackbarRoot;

	@FXML
	private JFXSnackbar snackbarError;
	
	@FXML
	private Button btnClose;		
	
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
			JFXSnackbarLayout snackbarLayout = new JFXSnackbarLayout("Error: Missing fields!");
			snackbarError.setPrefWidth(snackbarRoot.getPrefWidth() - 40);
	        snackbarError.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
	    } else {
	        
	        CurrEvent = event; // Save the current scene to hide
	        
	        ArrayList<String> userInfo = new ArrayList<>();
	        userInfo.add("UserLogin"); // Add the login action to the user info
	        if(loginAs.getSelectionModel().getSelectedItem().equals("Head Of Department")) {
	        	userInfo.add("HeadOfDepartment"); // Add the selected login role to the user info
	        }
	        else {
	        	userInfo.add(loginAs.getSelectionModel().getSelectedItem().trim()); // Add the selected login role to the user info
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
		SceneManagment.createNewStage("/ClientAndServerLogin/LoginGUI.fxml", null, "CEMS-Login").show(); // Creates and
																											// shows the
																											// login
																											// screen
																											// stage
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
		root.setDisable(true);
		Alert alert = new Alert(AlertType.INFORMATION);

	    alert.initStyle(StageStyle.UTILITY);
	    alert.setTitle("System message");
		alert.setHeaderText("Contact your administrator");

	    alert.setContentText("\nWe apologize for any inconvenience caused during your 'forgot password' and 'sign up' processes. "
	    		            + "To complete these actions successfully, please contact our administrator. They are available to "
	    		            + "assist you promptly." +
			    		    "\nThank you for your understanding!");

		alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
		alert.getDialogPane().setPrefSize(700, 250);
		alert.showAndWait();
		root.setDisable(false);

		
    }

}

