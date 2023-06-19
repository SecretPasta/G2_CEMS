/**
 * The ServerUI class is responsible for managing the user interface of the server application.
 * It provides methods for starting the server, retrieving the server instance, and launching the UI.
 */
package server;

import javafx.application.Application;
import javafx.stage.Stage;

import ClientAndServerLogin.ServerPortFrameController;

public class ServerUI extends Application {

	/**
	 * The EchoServer instance used by the server.
	 */
	private static EchoServer echoServer = null;

	/**
	 * The main method of the server application.
	 *
	 * @param args the command-line arguments
	 * @throws Exception if an error occurs during execution
	 */
	public static void main(String args[]) throws Exception {
		try {
			launch(args);
		} catch (SecurityException se) {
			// System.out.println("Program exited with error: " + se.getMessage());
		} finally {
			// System.out.println("Server exited");
			ServerPortFrameController.getInstance().disconnectBtn(); // call disconnect server function to exit the server correctly
		}
	}

	/**
	 * Starts the server UI.
	 *
	 * @param primaryStage the primary stage for the UI
	 * @throws Exception if an error occurs during UI initialization
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		ServerPortFrameController aFrame = new ServerPortFrameController(); // create ServerPortFrame
		aFrame.start(primaryStage);
	}

	/**
	 * Runs the server on the specified port.
	 *
	 * @param p the port to listen on
	 * @return the EchoServer instance used by the server
	 */
	public static EchoServer runServer(String p) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(p); // Set port to 5555
		} catch (Throwable t) {
			// System.out.println("ERROR - Connection Failed!\n" + t.getMessage());
		}

		echoServer = EchoServer.getInstance(port);

		try {
			echoServer.listen(); // Start listening for connections
		} catch (Exception ex) {
			// System.out.println("ERROR - Could not listen for clients!\n" + ex.getMessage());
		}

		return echoServer;
	}

	/**
	 * Retrieves the EchoServer instance used by the server.
	 *
	 * @return the EchoServer instance
	 */
	public static EchoServer getCommunication() {
		return echoServer;
	}
}
