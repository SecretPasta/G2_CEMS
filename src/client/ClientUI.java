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
		try {
			launch(args); 
        } catch (SecurityException se) {
            System.out.println("Program exited with error: " + se.getMessage());
        } finally {
            System.out.println("exited");
            ClientUI.chat.client.quit(); // send the server message to remove the client from the connected clients and terminates the client
        } 
	}
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		chat = new ClientController(EchoServer.serverIP, 5555);				  		
		QuestionBankController aFrame = new QuestionBankController(); // create QuestionBankFrame
		aFrame.start(primaryStage);
		
	}
}
