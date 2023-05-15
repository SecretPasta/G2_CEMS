// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import server.EchoServer;
import client.*;
import gui.ClientConnectFrameController;
import gui.QuestionBankFrameController;
import gui.ServerPortFrameController;
import ClientServerComm.ChatIF;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Config.ConnectedClient;
import Config.Question;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
	private static ChatClient instance;
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  public static boolean awaitResponse = false;

  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
	 
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    instance = this;
  }

  //Instance methods ************************************************
  public static ChatClient getInstance() {
  	return instance;
  }
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
	  System.out.println("--> handleMessageFromServer");
	  awaitResponse = false;
	  
	  if(msg instanceof String) {
		  if(((String)msg).equals("server is disconnected")){ // if get client get the meesage server is disconnected, get him out of the program
			  JOptionPane.showMessageDialog(null, "Couldn't connect to server.", "Connect to Server", JOptionPane.INFORMATION_MESSAGE);
			  System.out.println("exited");
			  System.exit(0); 
		  }
	  }
	  
	  // its important to get an idea how to check different arraylist like we did in echoserver with: handlemessagefromclient
	  if(msg instanceof ArrayList) { // get the arraylist from server and set in the table
		  ArrayList<Question> questions = (ArrayList<Question>)msg;
		  QuestionBankFrameController.getInstance().loadArrayQuestionsToTable(questions);
		  System.out.println("The questions succesfully loaded from the DB to the table.");
	  }
	  else {
		  System.out.println(msg);
	  }
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param str The message from the UI.    
   */
  
  public void handleMessageFromClientUI(Object str)  
  {
    try
    {
    	openConnection();//in order to send more than one message
       	awaitResponse = true;
    	sendToServer(str);
		// wait for response
		while (awaitResponse) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
    catch(IOException e)
    {
    	e.printStackTrace();
      clientUI.display("Could not send message to server: Terminating client."+ e);
      quit();
    }
  }

  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
	if(isConnected()) {
		System.out.println("exited");
		ArrayList<String> clientInfo = new ArrayList<>();
		clientInfo.add("ClientQuitting");
	    try {
			clientInfo.add(InetAddress.getLocalHost().getHostAddress());
			clientInfo.add(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientUI.chat.accept(clientInfo);
	    try
	    {
	      closeConnection();
	    }
	    catch(IOException e) {}
	    System.exit(0);
	}
	System.exit(0);
  }
  
}
//End of ChatClient class
