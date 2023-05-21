package server;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

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

		  }

		  if(msg instanceof ArrayList) {
			  ArrayList<?> arrayList = (ArrayList<?>) msg;
			  
			  if(arrayList.get(0) instanceof String) { // handle all arraylist type String
				  ArrayList<String> arrayListStr = (ArrayList<String>) msg;
					  
				  // when client connecting
				  // sending to the server the client hostname and ip to add him to the connected clients table
				  if(arrayListStr.get(0).equals("ClientConnecting")) 
				  {
					  ServerPortFrameController.addConnectedClient(arrayListStr.get(1), arrayListStr.get(2));
					  client.sendToClient("client connected");
				  }
	
				  // if the user exist, send him that
				  else if(arrayListStr.get(0).equals("UserLogin")) {
					  ArrayList<String> userDetails;
					  userDetails = DBController.userExist(arrayListStr); // getting from DB details about the user
					  //System.out.println(userDetails);
					  if(userDetails != null) { // if the func return the details of the user -> succeed
						  ArrayList<String> loginSucceedArr = new ArrayList<>();
						  loginSucceedArr.add("UserLoginSucceed");
						  loginSucceedArr.add(arrayListStr.get(1)); // send to client to know the correct dashboard to open
						  for(int i = 0; i < userDetails.size(); i++) { // to send the details of the user to the user
							  loginSucceedArr.add(userDetails.get(i));
						  }
						  client.sendToClient(loginSucceedArr);
					  }
					  else {
						  client.sendToClient("UserLoginFailed");
					  }
				  }
				  
				  // the DB update the question
				  else if(arrayListStr.get(0).equals("UpdateQuestionDataByID")){
					  String returnStr = DBController.UpdateQuestionDataByID(arrayListStr);
					  client.sendToClient(returnStr);
				  }
				  
				  // 1 - UserFullName
				  else if(arrayListStr.get(0).equals("GetAllQuestionsFromDB")) {		
					  ArrayList<Question> questions = DBController.getAllQuestions(arrayListStr.get(1), null); // send the full name of the user
					  client.sendToClient((ArrayList<Question>)questions);
				  }
				  
				  else if(arrayListStr.get(0).equals("RemoveQuestionFromDB")) {
					  // 1 - question id to remove
					  if(DBController.removeQuestion(arrayListStr.get(1))) {
						  client.sendToClient("question removed");
					  }
					  else {
						  client.sendToClient("question not removed");
					  }
				  }
				  
				  else if(arrayListStr.get(0).equals("ClientQuitting")){  
					  ServerPortFrameController.removeConnectedClientFromTable(arrayListStr.get(1), arrayListStr.get(2)); // call function to remove the client from the table
				  }
			  }
			  
	
		  }
	  } catch (IOException | ClassNotFoundException e) {
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
