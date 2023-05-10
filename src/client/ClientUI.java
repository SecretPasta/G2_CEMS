package client;
import javafx.application.Application;

import javafx.stage.Stage;
import Config.Question;

import java.util.Vector;

import client.ClientController;
import gui.QuestionBankController;

public class ClientUI extends Application {
	public static ClientController chat; //only one instance

	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		 chat= new ClientController("localhost", 5555);
		// TODO Auto-generated method stub
						  		
		QuestionBankController aFrame = new QuestionBankController(); // create QuestionBankFrame
		 
		aFrame.start(primaryStage);
	}
}
