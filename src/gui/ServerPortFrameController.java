package gui;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

import Config.ConnectedClient;

import JDBC.mysqlConnection;
import client.ClientUI;
import server.EchoServer;
import server.ServerUI;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ServerPortFrameController implements Initializable {
	
	private static ServerPortFrameController instance;

	private static EchoServer serverCommunication;

	private static final String DEFAULT_DB_USER = "root";

	private static final String DEFAULT_DB_NAME = "jdbc:mysql://localhost/QuestionBank?serverTimezone=IST";

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
	private Button btnExit = null;
	@FXML
	private Button btnConnect = null;

	@FXML
	private Button btnDiscon = null;

	@FXML
	private Label lblStatus;
	@FXML
	private Label lblMessage;
	@FXML
	private Label txtIPAddress;
//	@FXML
//	private Label txtHost;

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

	public static ObservableList<ConnectedClient> getConnectedClients() { // clients
		return connectedClients;
	}

	public static void addConnectedClient(ConnectedClient client) { // clients
		try {
			connectedClients.add(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void removeConnectedClient(ConnectedClient client) { // clients
		try {
			connectedClients.remove(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getConnectbtn(ActionEvent event) throws Exception {
		
		if (getPort().trim().isEmpty() || getPassWord().equals("") || getURL().equals("") || getUserName().equals("")) {
			lblMessage.setText("[Error] Missing fields");
			lblMessage.setTextFill(Color.color(1, 0, 0));
		}

		else {

			if (serverCommunication == null) {
				serverCommunication = ServerUI.runServer(getPort());
			}

			try {
				mysqlConnection.conn = mysqlConnection.connect(getURL(), getUserName(), getPassWord());
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				// e.printStackTrace();
			}
			if (mysqlConnection.conn != null) {
				lblMessage.setText("");
				lblStatus.setTextFill(Color.rgb(0, 102, 0));
				lblStatus.setText("Connected");
				serverCommunication.listen(); // connecting back to the port
				setVisabilityForUI(true);
			}
		}
	}

	private void setVisabilityForUI(boolean isVisable) {
		this.btnDiscon.setDisable(!isVisable);
		this.txtPort.setDisable(isVisable);
		this.txtURL.setDisable(isVisable);
		this.txtUserName.setDisable(isVisable);
		this.txtPassWord.setDisable(isVisable);
		this.btnConnect.setDisable(isVisable);
	}

	public void DisconnectServer() {
		// console.add("The server is Disconnected\n");
	    lblStatus.setTextFill(Color.color(1, 0, 0));
	    lblStatus.setText("Disconnected");
		int idx = 0;
		while (idx < connectedClients.size()) {
			connectedClients.remove(idx);
			++idx;
		}
		serverCommunication.stopListening();
		setVisabilityForUI(false);
	}

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
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerPort.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
		System.exit(0);
	}

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    loadInfo();
	    btnDiscon.setDisable(true);
	    lblStatus.setTextFill(Color.color(1, 0, 0));
	    lblStatus.setText("Disconnected");
	    usernameColumn.setCellValueFactory(new PropertyValueFactory<ConnectedClient, String>("clientname"));
	    ipColumn.setCellValueFactory(new PropertyValueFactory<ConnectedClient, String>("ip"));
	    
	    tableView.setItems(connectedClients);
	}

}