package handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Config.Question;
import JDBC.DBController;
import gui.LecturerDashboardFrameController;
import gui.ServerPortFrameController;
import ocsf.server.ConnectionToClient;

public class MessageHandler_Server {

	@SuppressWarnings("unchecked")
	public static void handleMessage(Object msg, ConnectionToClient client) {
        MessageType messageType = getMessageType(msg);
        if (messageType == null) {
            return;
        }

        switch (messageType) {
            case STRING:
                handleStringMessage((String) msg, client);
                break;
            case ARRAY_LIST_STRING:
                handleStringArrayListMessage((ArrayList<String>) msg, client);
                break;
            case ARRAY_LIST_QUESTION:
                handleQuestionArrayListMessage((ArrayList<Question>) msg, client);
                break;
            case MAP_STRING_ARRAYLIST_STRING:
            	handleMapStringKeyArrayListStringValueMessage((Map<String, ArrayList<String>>) msg, client);
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

    private static void handleStringMessage(String message, ConnectionToClient client) {
        // Handle string messages
    }
    
    // must client.sendToClient(obj); after handling the message from the client to get response from the server
    @SuppressWarnings("unchecked")
    private static void handleStringArrayListMessage(ArrayList<?> arrayList, ConnectionToClient client) {
    	
            ArrayList<String> arrayListStr = (ArrayList<String>) arrayList;
            String messageType = arrayListStr.get(0);
            try {
	            switch (messageType) {
	                case "ClientConnecting":
	                    // Handle ClientConnecting message
	                	
						ServerPortFrameController.addConnectedClient(arrayListStr.get(1), arrayListStr.get(2));
						client.sendToClient("client connected");
						
	                    break;
	                case "UserLogin":
	                    // Handle UserLogin message
	                	
	                	ArrayList<String> userDetails;
						userDetails = DBController.userExist(arrayListStr); // getting from DB details about the user
						// if the func return the details of the user -> succeed
						if(!(userDetails.get(0)).equals("UserAlreadyLoggedIn") && !(userDetails.get(0)).equals("UserEnteredWrondPasswwordOrUsername")) {
							ArrayList<String> loginSucceedArr = new ArrayList<>();
							loginSucceedArr.add("UserLoginSucceed");
							loginSucceedArr.add(arrayListStr.get(1)); // send to client to know the correct dashboard to open
							for(int i = 0; i < userDetails.size(); i++) { // to send the details of the user to the user
								loginSucceedArr.add(userDetails.get(i));
							}
							client.sendToClient(loginSucceedArr);
						}
						else {
							client.sendToClient(userDetails.get(0)); // send back to the client the reason he failed to login
						}
						
	                    break;
	                case "UpdateQuestionDataByID":
	                    // Handle UpdateQuestionDataByID message
	                	
						String returnStr = DBController.UpdateQuestionDataByID(arrayListStr);
						client.sendToClient(returnStr);
						
	                    break;
	                case "GetAllQuestionsFromDB":
	                    // Handle GetAllQuestionsFromDB message
	                	
	                	ArrayList<Question> questions = DBController.getAllQuestions(arrayListStr.get(1), null); // send the full name of the user
						client.sendToClient((ArrayList<Question>)questions);
						
	                    break;
	                case "RemoveQuestionFromDB":
	                    // Handle RemoveQuestionFromDB message
	                	
						// 1 - question id to remove
						if(DBController.removeQuestion(arrayListStr.get(1))) {
							client.sendToClient("question removed");
						}
						else {
							client.sendToClient("question not removed");
						}
						
	                    break;
	                case "UserLogout":
	                    // Handle UserLogout message
	                	
						// 1 - loggedAs
						// 2 - userID
						DBController.setUserIsLogin("0", arrayListStr.get(1), arrayListStr.get(2));
						client.sendToClient("logged out");
						
	                    break;
	                case "ClientQuitting":
	                    // Handle ClientQuitting message
	                	
						// 1 - HostAddress
						// 2 - HostName
						// 3 - UserID
						// 4 - userLoginAs
						// 5 - isLogged
						ServerPortFrameController.removeConnectedClientFromTable(arrayListStr.get(1), arrayListStr.get(2)); // call function to remove the client from the table
						DBController.setUserIsLogin("0", arrayListStr.get(4), arrayListStr.get(3));
						client.sendToClient("quit");
						
	                    break;
	                    
	                case "GetLecturerDepartmentsAndCourses": // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	                	// 1 - lecturer ID
				    	//Map<String, ArrayList<String>> lecDepartmentsCoursesHashMap = DBController.getLecturerDepartmentCourses(arrayListStr.get(1));
				    	
	                	Map<String, ArrayList<String>> lecDepartmentsCoursesHashMap = new HashMap<>(); 
	                	
				    	ArrayList<String> values1 = new ArrayList<>();
				        values1.add("Value1");
				        values1.add("Value2");
				        lecDepartmentsCoursesHashMap.put("Key1", values1);

				        ArrayList<String> values2 = new ArrayList<>();
				        values2.add("Value3");
				        values2.add("Value4");
				        lecDepartmentsCoursesHashMap.put("Key2", values2);
				        
				        lecDepartmentsCoursesHashMap.put("HashMapWithLecturerDepartmentsAndCourses", null);
				    	
				    	client.sendToClient(lecDepartmentsCoursesHashMap);
				    	
				    	break;
				    	
	                case "GetMaxQuestionIdFromProvidedDepartment":
	                	// 1 - Department Name
	                	String questionID;
	                	questionID = DBController.getMaxQuestionIdFromDepartment(arrayListStr.get(1));
	                	
	                	ArrayList<String> questionIdArr = new ArrayList<>();
	                	questionIdArr.add("MaximunQuestionIdForSelectedDEpartment");
	                	questionIdArr.add(questionID);
	                	client.sendToClient(questionIdArr);
	                	break;
	            }
            }catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            }
        }
    
    private static void handleQuestionArrayListMessage(ArrayList<Question> questionList, ConnectionToClient client) {
        // Handle ArrayList<Question> messages
    	
    	String messageType = questionList.get(0).getId();
    	try {
	    	switch (messageType) {
	    		case "AddNewQuestionToDB":
	    			// Handle AddNewQuestionToDB message
	    			// 1 - newQuestion
	    			
	    			DBController.addNewQuestion(questionList.get(1));
	    			client.sendToClient("new question was added");
	
					break;
	    	} 
	    	
        }catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
        }
    	
    }
    
    private static void handleMapStringKeyArrayListStringValueMessage(Map<String, ArrayList<String>> map, ConnectionToClient client) {
        // Handle Map<String, ArrayList<String>> messages
    	

    }
    
    
}
