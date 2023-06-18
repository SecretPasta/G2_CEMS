// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;

import handlers.MessageHandler_Client;
import ClientServerComm.ChatIF;
import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;

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
	  //System.out.println("--> handleMessageFromServer");
	  awaitResponse = false;
	  
	  MessageHandler_Client.handleMessage(msg); // handle the message from the server in different class
	  //System.out.println(msg);

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
  public void quit(String userID, String userLoginAs)
  {
	if(isConnected()) {
		//System.out.println("exited2");
		ArrayList<String> clientInfo = new ArrayList<>();
		clientInfo.add("ClientQuitting");
	    try {
			clientInfo.add(InetAddress.getLocalHost().getHostAddress());
			clientInfo.add(InetAddress.getLocalHost().getHostName());
			clientInfo.add(userID);
			clientInfo.add(userLoginAs);
			sendToServer(clientInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try
	    {
	      closeConnection();
	    }
	    catch(IOException e) {}
	    System.exit(0);
	}
	System.exit(0);
  }
  
  // another quit function to handle with clients that are not yet connected to the system via login
  public void quit()
  {
	if(isConnected()) {
		//System.out.println("exited1");
		ArrayList<String> clientInfo = new ArrayList<>();
		clientInfo.add("ClientQuitting");
	    try {
			clientInfo.add(InetAddress.getLocalHost().getHostAddress());
			clientInfo.add(InetAddress.getLocalHost().getHostName());
			sendToServer(clientInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
