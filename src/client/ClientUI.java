package client;
import javafx.application.Application;

import Config.ConnectedClient; // to remove

import javafx.stage.Stage;
import server.EchoServer;
import Config.Question;

import java.io.IOException;
import java.net.InetAddress; // to remove
import java.util.Vector;

import client.ClientController;
import gui.ClientConnectController;
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
            if(ChatClient.getInstance().isConnected()) // if the client is connect to the server
            {
            	chat.client.quit(); // send the server message to remove the client from the connected clients and terminates the client
            }
        } 
	}
	
	
	// function to create the client and to connect him
	public static void connectClient(String host, int port) throws IOException {
		chat = new ClientController(host, port);	
		chat.client.openConnection();
	}
	 
	@Override
	public void start(Stage primaryStage) throws Exception {			  		
		ClientConnectController aFrame = new ClientConnectController(); // create ClientConnectFrameController
		aFrame.start(primaryStage);
		
	}
}
