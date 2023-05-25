package handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import Config.Question;
import gui.AddQuestionFrameController;
import gui.LecturerDashboardFrameController;
import gui.LoginFrameController;
import ocsf.server.ConnectionToClient;

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
            case MAP_STRING_ARRAYLIST_STRING:
            	handleMapStringKeyArrayListStringValueMessage((Map<String, ArrayList<String>>) msg);
			break;
		default:
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
	    } else if (msg instanceof Map) {
	        Map<?, ?> map = (Map<?, ?>) msg;
	        if (!map.isEmpty()) {
	            Object firstKey = map.keySet().iterator().next();
	            Object firstValue = map.get(firstKey);
	            if (firstKey instanceof String && firstValue instanceof ArrayList
	                    && ((ArrayList<?>) firstValue).get(0) instanceof String) {
	                return MessageType.MAP_STRING_ARRAYLIST_STRING;
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
	                    
	                case "MaximunQuestionIdForSelectedDEpartment":
	                	// 1 - maximum question id for selected department
	                	AddQuestionFrameController.saveMaxIdOfQuestionInSelectedDepartment(arrayListStr.get(1));
	                	
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
    	
    	String messageType = questionList.get(0).getId();
    	
    	switch (messageType) {
    		case "LoadQuestionsFromDB":
    			// Handle LoadQuestionsFromDB message
    			
    			questionList.remove(0); // remove the first question (the question that identified)
				LecturerDashboardFrameController.getInstance().loadArrayQuestionsToTable(questionList);
				System.out.println("The questions succesfully loaded from the DB to the table.");
				break;
    	} 	
    }
    
    // must remove the messageType (last entry) from the map - map.remove(messageType);
    private static void handleMapStringKeyArrayListStringValueMessage(Map<String, ArrayList<String>> map) {
        // Handle Map<String, ArrayList<String>> messages
    	
    	Entry<String, ArrayList<String>> lastEntry = getLastEntry(map);
    	String messageType = lastEntry.getKey();
    	
    	switch (messageType) {
    	
	    	case "HashMapWithLecturerDepartmentsAndCourses":
	    		
				map.remove(messageType);
				AddQuestionFrameController.loadLecturerDepartmentsAndCourses(map);
				
				break;
    	}


    }
    
    public static <K, V> Entry<K, V> getLastEntry(Map<K, V> map) {
        Entry<K, V> lastEntry = null;
        for (Entry<K, V> entry : map.entrySet()) {
            lastEntry = entry;
        }
        return lastEntry;
    }

}
