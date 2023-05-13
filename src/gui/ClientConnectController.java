package gui;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import server.EchoServer;

public class ClientConnectController implements Initializable {
	
	@FXML
	private TextField txtPort;

	@FXML
	private TextField txtServerIP;
	
	@FXML
	private Label lblMessage;
	
	@FXML
	private Button btnExit;
	@FXML
	private Button btnConnect;
	
	public String getTxtPort() {
		return txtPort.getText();
	}

	public String getTxtServerIP() {
		return txtServerIP.getText();
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
	}
	
	public void getConnectBtn(ActionEvent event) throws Exception {
		
		if(getTxtServerIP().equals("") || getTxtPort().equals("")) {
			lblMessage.setText("[Error] Missing fields");
			lblMessage.setTextFill(Color.color(1, 0, 0));
		}
		else {
			lblMessage.setText("");
			ClientUI.connectClient(getTxtServerIP(), Integer.valueOf(getTxtPort())); // create the client and connect him to server
																					// the use of client: ClientUI.chat.client
		    ArrayList<String> clientInfo = new ArrayList<>();
		    clientInfo.add("ClientConnecting");
		    clientInfo.add(getTxtServerIP());
		    clientInfo.add(InetAddress.getLocalHost().getHostName());
		    ClientUI.chat.client.sendToServer(clientInfo);
		    
		    ((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		    
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/gui/QuestionBankGUI.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/gui/QuestionBank.css").toExternalForm());
			primaryStage.setTitle("Question Bank Managment Tool");
			primaryStage.setScene(scene);
			
			primaryStage.show();	
		}
	    
	    
	}
	
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ClientConnectGUI.fxml"));
				
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/ClientConnectGUI.css").toExternalForm()); // need to create css file for design
		primaryStage.setTitle("Client Connect Managment Tool");
		primaryStage.setScene(scene);
		
		primaryStage.show();	   
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtPort.setText("5555");
		try {
			txtServerIP.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
