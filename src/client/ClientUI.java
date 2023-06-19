package client;
import javafx.application.Application;


import javafx.stage.Stage;

import java.io.IOException;

import ClientAndServerLogin.ClientConnectFrameController;

public class ClientUI extends Application {
	
	public static ClientController chat; //only one instance

	/**

	 The main method of the client application.
	 Launches the JavaFX application by calling the {@link Application#launch(String[])} method with the specified arguments.
	 Handles the termination of the client by sending a quit message to the server to remove the client from the connected clients and terminates the client.
	 @param args the command-line arguments
	 @throws Exception if an exception occurs during the execution of the client application
	 */
	public static void main( String args[] ) throws Exception {
		try {
			launch(args); 
        } catch (SecurityException se) {
            //System.out.println("Program exited with error: " + se.getMessage());
        } finally {
        	try {
            	chat.client.quit(); // send the server message to remove the client from the connected clients and terminates the client
        	}catch (NullPointerException e){ // if catches, the client still not connected
        		System.exit(0);
        		//System.out.println("exited3");
        	}
        }
	}

	/**

	 Connects the client to the server.
	 Creates an instance of the {@link ClientController} class with the specified host and port,
	 and establishes a connection to the server.
	 @param host the server host address
	 @param port the server port number
	 @return true if the client successfully connects to the server, false otherwise
	 @throws IOException if an I/O error occurs during the connection process
	 */
	public static boolean connectClient(String host, int port) throws IOException {
		chat = new ClientController(host, port);	
		try {
		chat.client.openConnection();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**

	 The entry point of the client application.
	 Initializes and starts the client application by creating an instance of the {@link ClientConnectFrameController}
	 and calling its {@code start} method with the primary stage as a parameter.
	 @param primaryStage the primary stage of the JavaFX application
	 @throws Exception if an error occurs during application startup
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {			  		
		ClientConnectFrameController aFrame = new ClientConnectFrameController(); // create ClientConnectController
		aFrame.start(primaryStage);
		
	}
}
