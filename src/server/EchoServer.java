package server;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import Config.ConnectedClient;
import Config.Question;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import JDBC.DBController;
import JDBC.mysqlConnection;
import client.ChatClient;
import gui.QuestionBankController;
import gui.ServerPortFrameController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
   
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public static String serverIP;
  
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {
	  if(msg instanceof String) {
		  
		  if(((String)msg).equals("GetAllQuestionsFromDB")) {		
			  ArrayList<Question> questions = DBController.getAllQuestions();
			  try {
				  client.sendToClient((ArrayList<Question>)questions);
			  } catch (IOException e) {
				  e.printStackTrace();
			  }
		  }
		  
	  }
	  
	  if(msg instanceof ArrayList) {
		  if(((ArrayList<String>)msg).get(0).equals("ClientConnecting"))
		  {
			  ServerPortFrameController.addConnectedClient(new ConnectedClient(((ArrayList<String>)msg).get(1), ((ArrayList<String>)msg).get(2)));
			  try {
				client.sendToClient("client connected");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  
		  else if(((ArrayList<String>)msg).get(0).equals("UpdateQuestionDataByID")){
			  String returnStr = DBController.UpdateQuestionDataByID((ArrayList<String>)msg);
			  try {
				client.sendToClient(returnStr);
			} catch (IOException e) {
				e.printStackTrace();
			}

		  }
		  else if(((ArrayList<String>)msg).get(0).equals("ClientQuitting")){  
			  ServerPortFrameController.removeConnectedClientFromTable(((ArrayList<String>)msg).get(1), ((ArrayList<String>)msg).get(2)); // call function to remove the client from the table
		  }

	  }
	  System.out.println("Message received: " + msg.toString() + " from " + client);
  
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
 /* public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    try {
		serverIP = InetAddress.getLocalHost().getHostAddress();
	} catch (UnknownHostException e) {
		e.printStackTrace();
	}
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }*/
}
//End of EchoServer class
