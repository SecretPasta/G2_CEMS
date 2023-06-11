package handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import Config.*;
import JDBC.DBController;
import javafx.collections.ObservableList;
import ClientAndServerLogin.ServerPortFrameController;
import ocsf.server.ConnectionToClient;

public class MessageHandler_Server {

	@SuppressWarnings("unchecked")
	public static void handleMessage(Object msg, ConnectionToClient client) {
		System.out.println("Reached the handleMessage Method | Server");
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
	        case ARRAY_LIST_QUESTIONINEXAM:
	            handleQuestionInExamArrayListMessage((ArrayList<QuestionInExam>) msg, client);
	            break;
	        case ARRAY_LIST_EXAM:
	            handleExamArrayListMessage((ArrayList<Exam>) msg, client);
	            break;
	        case MAP_STRING_ARRAYLIST_STRING:
	            handleMapStringKeyArrayListStringValueMessage((Map<String, ArrayList<String>>) msg, client);
	            break;
	        case MAP_STRING_STRING:
	            handleMapStringStringValueMessage((Map<String, String>) msg, client);
	            break;
			case ARRAY_LIST_FINISHED_EXAM:
				handleFinishedExamArrayListValueMessage((ArrayList<FinishedExam>) msg, client);
				break;
	        default:
	            System.out.println("Message type does not exist");
	            break;
	    }
	}


	private static MessageType getMessageType(Object msg) {
		System.out.println("Reached the getMessageType Method");
	    if (msg instanceof String) {
	        return MessageType.STRING;
	    } else if (msg instanceof ArrayList) {
	        ArrayList<?> arrayList = (ArrayList<?>) msg;
	        if (!arrayList.isEmpty()) {
	            Object firstElement = arrayList.get(0);
	            if (firstElement instanceof String) {
	                return MessageType.ARRAY_LIST_STRING;
	            }
	             else if (firstElement instanceof QuestionInExam) {
		            return MessageType.ARRAY_LIST_QUESTIONINEXAM;
	            } else if (firstElement instanceof Question && !(firstElement instanceof QuestionInExam)) {
	                return MessageType.ARRAY_LIST_QUESTION;
	            } else if (firstElement instanceof Exam) {
	            	return MessageType.ARRAY_LIST_EXAM;
	            } else if (firstElement instanceof FinishedExam) {
					return MessageType.ARRAY_LIST_FINISHED_EXAM;
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
	            } else if (firstKey instanceof String && firstValue instanceof String) {
	                return MessageType.MAP_STRING_STRING;
	            }
	        }
	    }
	    return null;
	}
	
	
    private static void handleStringMessage(String message, ConnectionToClient client) {
		System.out.println("Reached the handleStringMessage Method");
        // Handle string messages
    	try {
	    	switch (message) {
			    case "getAllSubjectsNamesAndIdsFromDB":
			    	Map<String, String> subjects_name_id_map_arr = DBController.getAllSubjectsNamesAndIds();
			    	subjects_name_id_map_arr.put("HashMapWithSubjects_names_ids", "forchecking");
			    	client.sendToClient(subjects_name_id_map_arr);
			    	break;
			    	
			    case "getAllCoursesNamesAndIdsFromDB":
			    	Map<String, String> courses_name_id_map_arr = DBController.getAllCoursesNamesAndIds();
			    	courses_name_id_map_arr.put("HashMapWithCourses_names_ids", "forchecking");
			    	client.sendToClient(courses_name_id_map_arr);
			    	break;
			    	
			    default: break;
	    	}
    	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}
    }
    
    // must client.sendToClient(obj); after handling the message from the client to get response from the server
    @SuppressWarnings("unchecked")
    private static void handleStringArrayListMessage(ArrayList<?> arrayList, ConnectionToClient client) {
		System.out.println("Reached the handleStringArrayListMessage method");
            ArrayList<String> arrayListStr = (ArrayList<String>) arrayList;
			System.out.println(arrayListStr);
            String messageType = arrayListStr.get(0);
            try {
	            switch (messageType) {
	                case "ClientConnecting":
	                    // Handle ClientConnecting message
	                	
						ServerPortFrameController.addConnectedClient(arrayListStr.get(1), arrayListStr.get(2), client, "User");
						client.sendToClient("client connected");
						
	                    break;
	                case "UserLogin":
	                    // Handle UserLogin message
	                	
	                	ArrayList<String> userDetails;
						userDetails = DBController.userExist(arrayListStr); // getting from DB details about the user
						// if the func return the details of the user -> succeed
						if(!(userDetails.get(0)).equals("UserAlreadyLoggedIn") && !(userDetails.get(0)).equals("UserEnteredWrondPasswwordOrUsername")) {
							ObservableList<ConnectedClient> connectedClients = ServerPortFrameController.getConnectedClients();
							for(int i = 0; i<connectedClients.size(); i++) {
								if(connectedClients.get(i).getIp().equals(arrayListStr.get(4)) && connectedClients.get(i).getRole().equals("User")) {
									connectedClients.get(i).setRole(arrayListStr.get(1));
								}
							}
							arrayListStr.remove(4); // remove the ip from info
							
							ArrayList<String> loginSucceedArr = new ArrayList<>();
							loginSucceedArr.add("UserLoginSucceed");
							loginSucceedArr.add(arrayListStr.get(1)); // send to client to know the correct dashboard to open
							loginSucceedArr.addAll(userDetails); // to send the details of the user to the user
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
	                	
	                	ArrayList<Question> questions = DBController.getAllQuestions(arrayListStr.get(1), null, null); // send the id of the user
	                	questions.add(0, new Question("LoadQuestionsFromDB",  null, null, null, null, null, null, null));
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
	                    
	                case "GetLecturerSubjectsAndCourses":
	                	// 1 - lecturer ID
				    	Map<String, ArrayList<String>> lecSubjectsCoursesHashMap = DBController.getLecturerSubjectCourses(arrayListStr.get(1));
				    	
						ArrayList<String> checkArr = new ArrayList<>();
						checkArr.add("forchecking");
						lecSubjectsCoursesHashMap.put("HashMapWithLecturerSubjectsAndCourses", checkArr);
				    	
				    	client.sendToClient(lecSubjectsCoursesHashMap);
				    	
				    	break;
				    	
	                case "GetMaxQuestionIdFromProvidedSubject":
	                	// 1 - subject ID
	                	String questionID;
	                	questionID = DBController.getMaxQuestionIdFromSubject(arrayListStr.get(1));
	                	
	                	ArrayList<String> questionIdArr = new ArrayList<>();
	                	questionIdArr.add("MaximunQuestionIdForSelectedSubject");
	                	questionIdArr.add(questionID);
	                	client.sendToClient(questionIdArr);
	                	break;
	                	
	                case "GetQuestionsForLecturerBySubjectAndCourseToCreateExamTable":
	                	// 1 - Subject selected
	                	// 2 - Course Select
	                	ArrayList<Question> questionArr = DBController.getAllQuestions(null, arrayListStr.get(2), arrayListStr.get(1));
	                	questionArr.add(0, new Question("LoadQuestionsFromDB_CreateExamTable",  null, null, null, null, null, null, null));
	                	client.sendToClient(questionArr);
	                	break;
	                	
	                case "GetUpdateMaxExamIdFromCourse":
	                	// 1 - Course ID
	                	ArrayList<String> maxexamnumbercourse_arr = new ArrayList<>();
	                	maxexamnumbercourse_arr.add("MaxExamNumberOfCourse");
	                	maxexamnumbercourse_arr.add(DBController.getMaxExamIdFromCourse(arrayListStr.get(1)));
	                	client.sendToClient(maxexamnumbercourse_arr);
	                	
	                	break;
	                	
					case "GetAllComputerizedExamsFromDB": // Getting all the computerized Exams from the DB
						ArrayList<Exam> computerizedExams = new ArrayList<>();
						computerizedExams.add(new Exam("computerizedExamsForStudentTable",null,null,null,null,null,null,null,0,null,null,null));
						computerizedExams.addAll(DBController.getExamsByActiveness("1", null));
						client.sendToClient(computerizedExams);
						break;
						
					case "ChangeExamActiveness":
						// 1 - exam ID
						// 2 - the activeness to change to: 1 / 0
						DBController.changeExamActivenessByID(arrayListStr.get(1), arrayListStr.get(2));
						// check if the exam closed (activeness == 0) -> interrupt all the users
						// send to all clients a message that an exam was closed with the examID
						if(arrayListStr.get(2).equals("0")) {
							ArrayList<String> examActivenessChanged_arr = new ArrayList<>();
							examActivenessChanged_arr.add("an exam has been closed");
							examActivenessChanged_arr.add(arrayListStr.get(1));
							
							ObservableList<ConnectedClient> connectedClients = ServerPortFrameController.getConnectedClients();
							
							for(int i = 0; i<connectedClients.size(); i++) {
								if(connectedClients.get(i).getRole().equals("Student")) {
									connectedClients.get(i).getClient().sendToClient(examActivenessChanged_arr);
								}
							}
						}
						else {
							client.sendToClient("exam is open");
						}
						break;				
						
					case "RequestToChangeAnExamDurationFromLecturerToHOD": // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
						// 1 - exam ID
						// 2 - subject name
						// 3 - course name
						// 4 - lecturer id that sent the request
						// 5 - lecturer name
						// 6 - lecturer's explanation
						// 7 - add to exam duration
						// 8 - head of department ID
						
						DBController.saveRequestForHodInDB(arrayListStr);
						
						ObservableList<ConnectedClient> connectedClients = ServerPortFrameController.getConnectedClients();
						ArrayList<String> requestrecieved_arr = new ArrayList<>();
						requestrecieved_arr.add("SendToHeadOfDepartmentsThatRequestRecieved");
						requestrecieved_arr.add(arrayListStr.get(8)); // hod ID
						for(int i = 0; i<connectedClients.size(); i++) {
							if(connectedClients.get(i).getRole().equals("HeadOfDepartment")) {
								connectedClients.get(i).getClient().sendToClient(requestrecieved_arr);
							}
						}
						
						break;
					case "getQuestionsInExamById": //Get Questions for exam

						ArrayList<QuestionInExam> questionInExamArray= new ArrayList<>();
						questionInExamArray.add(new QuestionInExam("questionsByExamIdForClient",null,null,null));
						questionInExamArray.addAll(DBController.retrieveQuestionsInExamById(arrayListStr.get(1)));
						client.sendToClient(questionInExamArray);
						break;
						
				    case "GetAllExamsFromDBtoManageExamsTablesByLecturerID":
				    	// 1 - lecturer ID
				    	ArrayList<Exam> activeExams_arr = new ArrayList<>();
				    	activeExams_arr.add(0, new Exam("loadActiveExamsIntoLecturerTable", null, null, null, null, null, null, null, 0, null, null, null));
				    	activeExams_arr.addAll(DBController.getExamsByActiveness("1", arrayListStr.get(1)));
				    	
				    	ArrayList<Exam> inActiveExams_arr = new ArrayList<>();
				    	inActiveExams_arr.add(0, new Exam("loadInActiveExamsIntoLecturerTable", null, null, null, null, null, null, null, 0, null, null, null));
				    	inActiveExams_arr.addAll(DBController.getExamsByActiveness("0", arrayListStr.get(1)));
				    	
				    	client.sendToClient(activeExams_arr);
				    	client.sendToClient(inActiveExams_arr);
				    	
				    	
				    	break;
				    	
				    case "GetRelevantHodForLecturer":
				    	// 1 - lecturer ID
				    	
				    	ArrayList<HeadOfDepartment> hod_arr = new ArrayList<>();
				    	hod_arr.add(0, new HeadOfDepartment("LoadRelevantHodForLecturer", null, null, null, null));
				    	hod_arr.addAll(DBController.getHeadOfDepartmentsByLecturer(arrayListStr.get(1)));
				    	client.sendToClient(hod_arr);
				    	
				    	break;
						

	            }
            }catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            }
        }
    
    private static void handleQuestionArrayListMessage(ArrayList<Question> questionList, ConnectionToClient client) {
		System.out.println("Reached the handleQuestionArrayListMessage method");
        // Handle ArrayList<Question> messages
    	// 1 - question to add
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
    
	private static void handleQuestionInExamArrayListMessage(ArrayList<QuestionInExam> questionInExamList, ConnectionToClient client) {
        // Handle ArrayList<QuestionInExam> messages
		System.out.println("Reached the handleQuestionInExamArrayListMessage method");

    	String messageType = questionInExamList.get(0).getId();

    	try {
	    	switch (messageType) {
	    	
	    		case "SaveAllQuestionsInExam":
	    			// in the question 0, the question text saved the exam id
	    			// questionInExamList from 1 - all questions in exam to add
	    			DBController.addNewQuestionsInExam(questionInExamList);
	    			client.sendToClient("questions in exam added");
	
					break;
	    	} 
	    	
        }catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
        }
	}
    
	private static void handleExamArrayListMessage(ArrayList<Exam> examList, ConnectionToClient client) {
        // Handle ArrayList<Exam> messages
		System.out.println("Reached the handleExamArrayListMessage method");

    	String messageType = examList.get(0).getExamID();

    	try {
	    	switch (messageType) {
	    	
	    		case "SaveExamInDB":
	    			// Handle AddNewQuestionToDB message
	    			// 1 - new exam
	    			DBController.addNewExam(examList.get(1));
	    			client.sendToClient("new exam was added");
	
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
    
	private static void handleMapStringStringValueMessage(Map<String, String> msg, ConnectionToClient client) {
		// Handle Map<String, String> messages
		
	}
	private static void handleFinishedExamArrayListValueMessage(ArrayList<FinishedExam> finishedExam, ConnectionToClient client){
		//Handle ArrayList<FinishedExam> messages
		System.out.println("Reached handleFinishedExamValueMessage | Server Handler");
		String messageType = finishedExam.get(0).getExamID();
		switch (messageType){
			case "saveFinishedExamToDB":
				//finishedExam.remove(0);
				DBController.saveFinishedExamToDB(finishedExam.get(1));
				break;
		}
	}
    
}
