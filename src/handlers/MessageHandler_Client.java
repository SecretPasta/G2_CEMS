package handlers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Config.Question;
import gui.LecturerDashboardFrameController;
import gui.LoginFrameController;

public class MessageHandler_Client {
	@SuppressWarnings("unchecked")
	public static void handleMessage(Object msg) {
        MessageType messageType = getMessageType(msg);
        if (messageType == null) {
            return;
        }

        switch (messageType) {
            case STRING:
                handleStringMessage((String) msg);
                break;
            case ARRAY_LIST_STRING:
                handleStringArrayListMessage((ArrayList<String>) msg);
                break;
            case ARRAY_LIST_QUESTION:
                handleQuestionArrayListMessage((ArrayList<Question>) msg);
                break;
        }
    }
	
	private static MessageType getMessageType(Object msg) {
        if (msg instanceof String) {
            return MessageType.STRING;
        } else if (msg instanceof ArrayList) {
            ArrayList<?> arrayList = (ArrayList<?>) msg;
            if (!arrayList.isEmpty()) {
                Object firstElement = arrayList.get(0);
                if (firstElement instanceof String) {
                    return MessageType.ARRAY_LIST_STRING;
                } else if (firstElement instanceof Question) {
                    return MessageType.ARRAY_LIST_QUESTION;
                }
            }
        }
        return null;
    }
	
    private static void handleStringMessage(String message) {
        // Handle string messages
    	
    	try {
	    	switch (message) {
	    		case "server is disconnected":
	    			// if get client get the message server is disconnected, get him out of the program
					JOptionPane.showMessageDialog(null, "server is disconnected.", "Connect to Server", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("exited");
					System.exit(0); 
	    			break;
	    			
	    		case "UserAlreadyLoggedIn":
	    			LoginFrameController.getInstance().userLoginFailed("[Error] this user is already connected");
	    			break;
	    			
    			case "UserEnteredWrondPasswwordOrUsername":
    				LoginFrameController.getInstance().userLoginFailed("[Error] Wrong Username or Password");
    				break;
	    	}
	    	
    	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}
    	
    }
    
    @SuppressWarnings("unchecked")
    private static void handleStringArrayListMessage(ArrayList<?> arrayList) {

            ArrayList<String> arrayListStr = (ArrayList<String>) arrayList;
            String messageType = arrayListStr.get(0);
            try {
	            switch (messageType) {
	                case "UserLoginSucceed":
	                    // Handle UserLoginSucceed message
	                	
		  				// 1 - login As
		  				// 2 - user ID
		  				// 3 - userName
		  				// 4 - user Password
		  				// 5 - user Name
		  				// 6 - user Email
						LoginFrameController.hideCurrentScene(); // hide login frame
						if(arrayListStr.get(1).equals("Lecturer")) { // login as Lecturer
							LecturerDashboardFrameController.start(arrayListStr); // to save the user details in the dashboard controller
						}
						/*else if() { // login as lecturer
							  
						}
						else if() { // login as head of department
							  
						}*/
						System.out.println("logged in succesfully");
						
	                    break;
	            }
            }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            }
        }
    
    
    // must removing the first question (the question that identified)
    private static void handleQuestionArrayListMessage(ArrayList<Question> questionList) {
        // Handle ArrayList<Question> messages
    	
    	ArrayList<Question> arrayListQue = (ArrayList<Question>) questionList;
    	String messageType = arrayListQue.get(0).getId();
    	
    	switch (messageType) {
    		case "LoadQuestionsFromDB":
    			// Handle LoadQuestionsFromDB message
    			
    			arrayListQue.remove(0); // remove the first question (the question that identified)
				LecturerDashboardFrameController.getInstance().loadArrayQuestionsToTable(arrayListQue);
				System.out.println("The questions succesfully loaded from the DB to the table.");
				break;
    	}
    	
    	
    }
}
