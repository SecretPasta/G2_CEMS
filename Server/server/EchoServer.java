package server;

import gui.ServerGUIController;

public class EchoServer extends AbstractServer {
	final public static int DEFAULT_PORT = 5555;
	private ServerGUIController server;
	
	public EchoServer(int port) {
		super(port);
	}

    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
	    System.out.println("Message received: " + msg + " from " + client);
	    this.sendToAllClients(msg);
	}

    protected void serverStarted(){
    	System.out.println("Server listening for connections on port " + getPort());
    }
  
	protected void serverStopped() {
	    System.out.println("Server has stopped listening for connections.");
	}
  
    public static void main(String[] args) {
    	int port = 0; //Port to listen on
    	try {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t) {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
	    EchoServer sv = new EchoServer(port);
	    try {
	      sv.listen(); //Start listening for connections
	    } 
	    catch (Exception ex) {
	      System.out.println("ERROR - Could not listen for clients!");
	    }
	}
}

