package gui;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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
import javafx.stage.StageStyle;
import server.EchoServer;

public class ClientConnectFrameController implements Initializable {

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
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		System.exit(0);
		System.gc();
	}

	public void getConnectBtn(ActionEvent event) throws Exception {

		if (getTxtServerIP().equals("") || getTxtPort().equals("")) {
			lblMessage.setText("[Error] Missing fields");
			lblMessage.setTextFill(Color.color(1, 0, 0));
		} else {

			// create the client and connect him to server
			// the use of client: ClientUI.chat.client
			// to send the server message, use: ClientUI.chat.accept(object)
			if (!ClientUI.connectClient(getTxtServerIP(), Integer.valueOf(getTxtPort()))) {
				JOptionPane.showMessageDialog(null, "Couldn't connect to server.", "Connect to Server",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
				return;
			}

			// Adding Client Info
			lblMessage.setText("");
			ArrayList<String> clientInfo = new ArrayList<>();
			clientInfo.add("ClientConnecting");
			clientInfo.add(InetAddress.getLocalHost().getHostAddress());
			clientInfo.add(InetAddress.getLocalHost().getHostName());
			ClientUI.chat.accept(clientInfo);

			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window

	        LoginController.start();
		}

	}

	public void start(Stage primaryStage) throws Exception {
		SceneManagment.createNewStage("/gui/ClientConnectGUI.fxml", null, "Client Connect Managment Tool").show();
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
