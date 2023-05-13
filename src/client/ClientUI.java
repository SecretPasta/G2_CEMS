package client;
import javafx.application.Application;

import Config.ConnectedClient; // to remove

import javafx.stage.Stage;
import server.EchoServer;
import Config.Question;

import java.net.InetAddress; // to remove
import java.util.Vector;

import client.ClientController;
import gui.ClientConnectController;
import gui.ServerPortFrameController;

public class ClientUI extends Application {

	public static void main( String args[] ) throws Exception {
		try {
			launch(args); 
        } catch (SecurityException se) {
            System.out.println("Program exited with error: " + se.getMessage());
        } finally {
            System.out.println("exited");
            if(ChatClient.getInstance().isConnected()) // if the client is connect to the server
            {
            	ClientConnectController.chat.client.quit(); // send the server message to remove the client from the connected clients and terminates the client
            }
        } 
	}
	 
	@Override
	public void start(Stage primaryStage) throws Exception {			  		
		ClientConnectController aFrame = new ClientConnectController(); // create ClientConnectFrameController
		aFrame.start(primaryStage);
		
	}
}
