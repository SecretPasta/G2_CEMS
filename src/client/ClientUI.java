package client;
import javafx.application.Application;

import Config.ConnectedClient; // to remove

import javafx.stage.Stage;
import server.EchoServer;
import Config.Question;

import java.net.InetAddress; // to remove
import java.util.Vector;

import client.ClientController;
import gui.QuestionBankController;
import gui.ServerPortFrameController;

public class ClientUI extends Application {
	
	public static ClientController chat; //only one instance

	public static void main( String args[] ) throws Exception {
		launch(args);  
	}
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		chat = new ClientController(EchoServer.serverIP, 5555);				  		
		QuestionBankController aFrame = new QuestionBankController(); // create QuestionBankFrame
		aFrame.start(primaryStage);
		
		// all this need to be after client click on connect and not here. 
		/*String ip = InetAddress.getLocalHost().getHostAddress(); // clients connected table
		String username = "ClientTest"; // clients connected table
		ConnectedClient user = new ConnectedClient(ip, username); // clients connected table
		ServerPortFrameController.addConnectedClient(user);*/ // clients connected table
		
	}
}
