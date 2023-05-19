package server;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import Config.ConnectedClient;
import Config.Question;

import java.util.ArrayList;

import JDBC.DBController;
import gui.ServerPortFrameController;
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
  @SuppressWarnings("unchecked")
public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {
	  try {
		  if(msg instanceof String) {
			  
			  if(((String)msg).equals("GetAllQuestionsFromDB")) {		
				  ArrayList<Question> questions = DBController.getAllQuestions();
				  client.sendToClient((ArrayList<Question>)questions);
			  }
			  
		  }
		  
		  if(msg instanceof ArrayList) {
			  if(((ArrayList<String>)msg).get(0).equals("ClientConnecting"))
			  {
				  ServerPortFrameController.addConnectedClient(new ConnectedClient(((ArrayList<String>)msg).get(1), ((ArrayList<String>)msg).get(2)));
				  client.sendToClient("client connected");
			  }
			  
			  else if(((ArrayList<String>)msg).get(0).equals("UpdateQuestionDataByID")){
				  String returnStr = DBController.UpdateQuestionDataByID((ArrayList<String>)msg);
				  client.sendToClient(returnStr);
	
	
			  }
			  else if(((ArrayList<String>)msg).get(0).equals("ClientQuitting")){  
				  ServerPortFrameController.removeConnectedClientFromTable(((ArrayList<String>)msg).get(1), ((ArrayList<String>)msg).get(2)); // call function to remove the client from the table
			  }
	
		  }
	  } catch (IOException e) {
		  e.printStackTrace();
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
  
}
//End of EchoServer class
