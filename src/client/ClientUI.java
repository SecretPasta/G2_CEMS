package client;
import javafx.application.Application;

import Config.ConnectedClient; // to remove

import javafx.stage.Stage;
import server.EchoServer;
import Config.Question;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress; // to remove
import java.net.UnknownHostException;
import java.util.Vector;

import client.ClientController;
import gui.ClientConnectFrameController;
import gui.ServerPortFrameController;

public class ClientUI extends Application {
	
	public static ClientController chat; //only one instance

	public static void main( String args[] ) throws Exception {
		try {
			launch(args); 
        } catch (SecurityException se) {
            System.out.println("Program exited with error: " + se.getMessage());
        } finally {
        	try {
            	chat.client.quit(); // send the server message to remove the client from the connected clients and terminates the client
        	}catch (NullPointerException e){ // if catches, the client still not connected
        		System.exit(0);
        		System.out.println("exited");
        	}
        }
	}
	
	// function to create the client and to connect him
	// if it return true, the client connected to the server successfully
	public static boolean connectClient(String host, int port) throws IOException {
		chat = new ClientController(host, port);	
		try {
		chat.client.openConnection();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	 
	@Override
	public void start(Stage primaryStage) throws Exception {			  		
		ClientConnectFrameController aFrame = new ClientConnectFrameController(); // create ClientConnectController
		aFrame.start(primaryStage);
		
	}
}
