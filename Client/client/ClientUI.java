package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ClientUI implements ChatIF {
	
	final public static int DEFAULT_PORT = 5555;
	ChatClient client;

    public ClientUI(String host, int port) {
        try {
            this.client = new ChatClient(host, port, this);
        }catch (IOException exception) {
            System.out.println("Error: Can't setup connection! Terminating client.");
            System.exit(1);
        }
    }
    
    public void accept() {
      try {
        BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
        String message;
        while (true) {
          message = fromConsole.readLine();
          client.handleMessageFromClientUI(message);
        }
      }catch (Exception ex) {
        System.out.println("Unexpected error while reading from console!");
      }
    }
    
    @Override
    public void display(String message) {
      System.out.println("> " + message);
    }
    
    public static void main(String[] args) {
        String host = "";
        int port = 0;  //The port number

        try{
          host = args[0];
        } catch(ArrayIndexOutOfBoundsException e){
          host = "localhost";
        }
        ClientUI chat = new ClientUI(host, DEFAULT_PORT);
        chat.accept();  //Wait for console data
    }

}

