package client;

import java.io.IOException;

public class ChatClient extends AbstractClient {
    ChatIF clientUI;

    public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
        super(host, port);
        this.clientUI = clientUI;
        this.openConnection();
    }

    @Override
    public void handleMessageFromServer(Object msg) {
    	clientUI.display(msg.toString());
    }

    public void handleMessageFromClientUI(Object message) {
    	try {
        	sendToServer(message);
        }catch(IOException e) {
          clientUI.display("Could not send message to server.  Terminating client.");
          quit();
        }
    }

    public void quit() {
        try {
            this.closeConnection();
        }catch (IOException iOException) {
            // empty catch block
        }
        System.out.println("BYE");
        System.exit(0);
    }
}

