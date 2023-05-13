package server;

import javafx.application.Application;
import javafx.stage.Stage;
import Config.Question;
import client.ClientUI;

import java.util.Vector;
import gui.ServerPortFrameController;
import gui.UpdateQuestionFrameController;
import server.EchoServer;

public class ServerUI extends Application {

	final public static int DEFAULT_PORT = 5555;
	public static Vector<Question> students = new Vector<Question>();

	public static void main(String args[]) throws Exception {	
		try {
			launch(args); 
        } catch (SecurityException se) {
            System.out.println("Program exited with error: " + se.getMessage());
        } finally {
            System.out.println("Server exited");
            ServerPortFrameController.getInstance().DisconnectServer(); // call the btn disconnect to exit the server correctly
        } 
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ServerPortFrameController aFrame = new ServerPortFrameController(); // create ServerPortFrame
		aFrame.start(primaryStage);
	}

	public static EchoServer runServer(String p) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(p); // Set port to 5555

		} catch (Throwable t) {
			System.out.println("ERROR - Connection Failed!\n" + t.getMessage());
		}

		EchoServer echoServer = new EchoServer(port);

		try {
			echoServer.listen(); // Start listening for connections

		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!\n" + ex.getMessage());
		}
		return echoServer;
	}

}
