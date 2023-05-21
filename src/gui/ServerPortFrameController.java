package gui;

import java.io.IOException;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;



import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Config.ConnectedClient;

import JDBC.mysqlConnection;
import server.EchoServer;
import server.ServerUI;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
	
	public ServerPortFrameController() {
		instance = this;
	}
	
	public static ServerPortFrameController getInstance() {
		return instance;
	}

	public static void addConnectedClient(String userHostName, String userIP) {
	    // Add a connected client to the list of connected clients by his hostname and ip
	    try {
	        connectedClients.add(new ConnectedClient(userHostName, userIP));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static void removeConnectedClient(ConnectedClient client) {
	    // Remove a connected client from the list of connected clients
	    try {
	        connectedClients.remove(client);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void connectBtn(ActionEvent event) throws Exception {
	    // Handle the Connect button click event
	    if (getPort().trim().isEmpty() || getPassWord().equals("") || getURL().equals("") || getUserName().equals("")) {
	        lblMessage.setText("[Error] Missing fields!");
	    } else {
	        lblMessage.setText("");
	        // Connect to the MySQL database
	        boolean sqlConnectionSucceed = mysqlConnection.connect(getURL(), getUserName(), getPassWord());
	        if (sqlConnectionSucceed) {
	            lblStatus.setTextFill(Color.rgb(93, 210, 153));
	            lblStatus.setText("Connected");
	            setVisabilityForUI(true);
	            // Start the server
	            serverCommunication = ServerUI.runServer(getPort());
	        } else if (!sqlConnectionSucceed) {
	            lblMessage.setText("[Error] Wrong password!");
	        }
	    }
	}

	private void setVisabilityForUI(boolean isVisible) {
	    // Set the visibility of UI components when Connecting and Disconnecting
	    this.btnDisconnect.setDisable(!isVisible);
	    this.txtPort.setDisable(isVisible);
	    this.txtURL.setDisable(isVisible);
	    this.txtUserName.setDisable(isVisible);
	    this.txtPassWord.setDisable(isVisible);
	    this.btnConnect.setDisable(isVisible);
	}


	public void disconnectBtn() {
		try { // send message to clients only if the server is on. if the server in not connected, the connection is null
			serverCommunication.sendToAllClients("server is disconnected"); // send to all clients to close the program of every client
		}catch (NullPointerException e) {
			System.exit(0);
		}
	    lblStatus.setTextFill(Color.rgb(254, 119, 76));
	    lblStatus.setText("Disconnected");
	    lblMessage.setText("");
		int idx = 0;
		while (idx < connectedClients.size()) { // clear the connectedClients table
			connectedClients.remove(idx);
			++idx;
		}
		try {
			serverCommunication.close(); // close the server
		} catch (IOException e) {
			e.printStackTrace();
		}
		setVisabilityForUI(false);
	}
	
	//Text Field getters
	private String getPassWord() {
		return txtPassWord.getText();
	}

	private String getUserName() {
		return txtUserName.getText();
	}

	private String getURL() {
		return txtURL.getText();
	}

	private String getPort() {
		return txtPort.getText();
	}

	public void start(Stage primaryStage) throws Exception {
		SceneManagment.createNewStage("/gui/ServerGUI.fxml", "/gui/ServerGUI.css", "Server").show();
	}

	//Exit Button functionality 
	public void exitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
		disconnectBtn();
		System.exit(0);
		System.gc();
	}
	
	//Instantiating the text field with default strings
	public void loadInfo() {
		txtPort.setText("5555");
		try {
			txtServerIP.setText(InetAddress.getLocalHost().getHostAddress());
			txtServerIP.setDisable(true);

		} catch (UnknownHostException e) {
			System.out.println("Error: " + e.getMessage());
		}
		txtURL.setText(DEFAULT_DB_NAME);
		txtUserName.setText(DEFAULT_DB_USER);
	}
	
	//Removing the client from the table after it disconnects
	public static void removeConnectedClientFromTable(String ip, String clientName) {
		for(int idx = 0; idx < connectedClients.size(); idx++) {
			if(connectedClients.get(idx).getIp().equals(ip)) {
				if(connectedClients.get(idx).getClientname().equals(clientName)) {
					removeConnectedClient(connectedClients.get(idx));
				}
			}
		}
	}

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