package ClientAndServerLogin;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Config.ConnectedClient;
import JDBC.DBController;
import JDBC.mysqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ocsf.server.ConnectionToClient;
import server.EchoServer;
import server.ServerUI;

public class ServerPortFrameController implements Initializable {
	
	private static ServerPortFrameController instance;

	private static EchoServer serverCommunication;

	private static final String DEFAULT_DB_USER = "root";

	private static final String DEFAULT_DB_NAME = "jdbc:mysql://localhost/cemsdatabase?serverTimezone=IST";

	public static ObservableList<ConnectedClient> connectedClients = FXCollections.observableArrayList(); // clients
																											// connected
	// table

	@FXML
	private TableView<ConnectedClient> tableView = new TableView<>(); // clients connected table

	@FXML
	private TableColumn<ConnectedClient, String> ipColumn; // clients connected table
	@FXML
	private TableColumn<ConnectedClient, String> usernameColumn; // clients connected table
	
	@FXML
    private JFXButton btnConnect;

    @FXML
    private JFXButton btnDisconnect;
    
    @FXML
    private JFXButton btnExit;
    
	@FXML
	private Label lblStatus;
	@FXML
	private Label lblMessage;
	@FXML
	private Label txtIPAddress;

	// Connection Detail Variables
	@FXML
	private TextField txtPort = new TextField();

	@FXML
	private TextField txtServerIP = new TextField();

	// Data Base Details
	
	@FXML
	private TextField txtURL = new TextField();

	@FXML
	private TextField txtUserName = new TextField();

	@FXML
	private PasswordField txtPassWord = new PasswordField();
	
	//private clientPinger pinger = new clientPinger();
	
	public ServerPortFrameController() {
		instance = this;
	}

	/**
	 * Retrieves the instance of the ServerPortFrameController.
	 *
	 * @return The instance of the ServerPortFrameController
	 */
	public static ServerPortFrameController getInstance() {
	    return instance;
	}

	/**
	 * Adds a connected client to the list of connected clients using their hostname and IP.
	 *
	 * @param userHostName The hostname of the connected client
	 * @param userIP The IP address of the connected client
	 */
	public static void addConnectedClient(String userHostName, String userIP, ConnectionToClient client, String role) {
	    try {
	        // Create a new instance of ConnectedClient with the provided hostname and IP
	        connectedClients.add(new ConnectedClient(userHostName, userIP, client, role));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/**

	 Retrieves the list of connected clients.
	 @return the ObservableList of ConnectedClient objects representing the connected clients
	 */
	public static ObservableList<ConnectedClient> getConnectedClients() {
		return connectedClients;
	}

	/**
	 * Removes a connected client from the list of connected clients.
	 *
	 * @param client The connected client to be removed
	 */
	public static void removeConnectedClient(ConnectedClient client) {
	    try {
	        // Remove the specified connected client from the list
	        connectedClients.remove(client);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/**

	 Handles the event when the "Import All External Users" button is clicked.
	 Calls the DBController to import users data from an external source.
	 @param event the ActionEvent triggered by clicking the button
	 @throws Exception if an error occurs during the import process
	 */
	public void importAllExternalUsers(ActionEvent event) throws Exception {
		DBController.importUsersData();
	}


	/**
	 * Handles the Connect button click event.
	 *
	 * @param event The action event triggered by the Connect button
	 */
	public void connectBtn(ActionEvent event) throws Exception {
	    if (getPort().trim().isEmpty() || getPassWord().equals("") || getURL().equals("") || getUserName().equals("")) {
	        // Check if any required fields are empty
	        lblMessage.setText("[Error] Missing fields!");
	    } else {
	        lblMessage.setText("");
	        // Connect to the MySQL database
	        boolean sqlConnectionSucceed = mysqlConnection.connect(getURL(), getUserName(), getPassWord());
	        if (sqlConnectionSucceed) {
	            // If the MySQL connection is successful
	            lblStatus.setTextFill(Color.rgb(93, 210, 153));
	            lblStatus.setText("Connected");
	            setVisabilityForUI(true);
	            importAllExternalUsers(event);
	            DBController.setAllUsersNotIsLogged(); // Set all users' isLogged to 0
	            // Start the server
	            serverCommunication = ServerUI.runServer(getPort());
	        } else if (!sqlConnectionSucceed) {
	            // If the MySQL connection fails due to wrong password
	            lblMessage.setText("[Error] Wrong password!");
	        }
	    }
	}

	/**
	 * Set the visibility of UI components when Connecting and Disconnecting.
	 *
	 * @param isVisible True if the components should be visible/enabled, false otherwise
	 */
	private void setVisabilityForUI(boolean isVisible) {
	    this.btnDisconnect.setDisable(!isVisible);
	    this.txtPort.setDisable(isVisible);
	    this.txtURL.setDisable(isVisible);
	    this.txtUserName.setDisable(isVisible);
	    this.txtPassWord.setDisable(isVisible);
	    this.btnConnect.setDisable(isVisible);
	}

	/**

	 Handles the disconnection process of the server.

	 Sends a disconnection message to all connected clients and closes the server.

	 Updates the UI components and clears the connectedClients table.
	 */
	public void disconnectBtn() {
	    try {
	        // Send message to clients only if the server is on. If the server is not connected, the connection is null
	        serverCommunication.sendToAllClients("server is disconnected");
	    } catch (NullPointerException e) {
	        // Server is not connected, exit the program
	        System.exit(0);
	    }
	    
	    lblStatus.setTextFill(Color.rgb(254, 119, 76));
	    lblStatus.setText("Disconnected");
	    lblMessage.setText("");
	    
	    // Clear the connectedClients table
	    for(int idx = 0; idx < connectedClients.size(); idx++) {
	    	connectedClients.remove(idx);
	    }
	    
	    try {
	        serverCommunication.close(); // Close the server
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    setVisabilityForUI(false); // Set the visibility and enable/disable state of UI components
	}



	//Text Field getters
	/**

	 Retrieves the entered password from the password field.
	 @return The entered password as a string.
	 */
	private String getPassWord() {
		return txtPassWord.getText();
	}

	/**

	 Retrieves the entered username from the username field.
	 @return The entered username as a string.
	 */
	private String getUserName() {
		return txtUserName.getText();
	}

	/**

	 Retrieves the entered URL from the URL field.
	 @return The entered URL as a string.
	 */
	private String getURL() {
		return txtURL.getText();
	}

	/**

	 Retrieves the entered port number from the port field.
	 @return The entered port number as a string.
	 */
	private String getPort() {
		return txtPort.getText();
	}

	/**

	 Starts the application by creating and showing the server GUI stage.
	 @param primaryStage The primary stage of the application.
	 @throws Exception If an exception occurs during the process.
	 */
	public void start(Stage primaryStage) throws Exception {
		SceneManagment.createNewStage("/ClientAndServerLogin/ServerGUI.fxml", null, "CEMS-Server").show();
	}

	/**

	 Handles the action event for the exit button.
	 @param event The action event triggered by the exit button.
	 @throws Exception If an exception occurs during the process.
	 */
	public void exitBtn(ActionEvent event) throws Exception {
	    //System.out.println("exit Academic Tool");
	    disconnectBtn(); // Disconnect from the server and perform necessary cleanup
	    System.exit(0); // Exit the program
	    System.gc(); // Perform garbage collection
	}

	
	/**
	 * Loads default values into the UI text fields.
	 */
	public void loadInfo() {
	    txtPort.setText("5555"); // Set default port number
	    
	    try {
	        txtServerIP.setText(InetAddress.getLocalHost().getHostAddress()); // Set default server IP address
	        txtServerIP.setDisable(true); // Disable editing of server IP address field
	    } catch (UnknownHostException e) {
	        //System.out.println("Error: " + e.getMessage());
	    }
	    
	    txtURL.setText(DEFAULT_DB_NAME); // Set default database URL
	    txtUserName.setText(DEFAULT_DB_USER); // Set default database username
	}


	

	/** Removes the client from the table after it disconnects.
		@param ip The IP address of the client.
		@param clientName The name of the client.
	*/
	public static void removeConnectedClientFromTable(String ip, String clientName) {
		for(int idx = 0; idx < connectedClients.size(); idx++) {
			if(connectedClients.get(idx).getIp().equals(ip)) {
				if(connectedClients.get(idx).getClientname().equals(clientName)) {
					removeConnectedClient(connectedClients.get(idx));
				}
			}
		}
	}

	/**

	 Initializes the controller.

	 @param location The location used to resolve relative paths for the root object, or null if the location is not known.

	 @param resources The resources used to localize the root object, or null if the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    loadInfo();
	    btnDisconnect.setDisable(true);
	    lblStatus.setText("Disconnected");
	    lblMessage.setTextFill(Color.rgb(254, 119, 76));
	    usernameColumn.setCellValueFactory(new PropertyValueFactory<ConnectedClient, String>("clientname"));
	    ipColumn.setCellValueFactory(new PropertyValueFactory<ConnectedClient, String>("ip"));
	   
	    tableView.setItems(connectedClients);
	}
	
	
}