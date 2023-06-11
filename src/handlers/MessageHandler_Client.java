package handlers;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.JOptionPane;

import Config.Exam;
import Config.HeadOfDepartment;
import Config.Question;
import Config.QuestionInExam;
import lecturer.AddQuestionFrameController;
import lecturer.CreateExam_ReviewFrameController;
import lecturer.LecturerDashboardFrameController;
import lecturer.ManageExam_ChangeTimeFrameController;
import ClientAndServerLogin.LoginFrameController;
import student.ComputerizedExamController;
import student.StudentDashboardFrameController;

public class MessageHandler_Client {
	
	private static String userID = null;
	
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
	        case ARRAY_LIST_QUESTIONINEXAM:
	            handleQuestionInExamArrayListMessage((ArrayList<QuestionInExam>) msg);
	            break;
	        case ARRAY_LIST_HOD:
	            handleHodArrayListMessage((ArrayList<HeadOfDepartment>) msg);
	            break;	
	        case ARRAY_LIST_EXAM:
	            handleExamArrayListMessage((ArrayList<Exam>) msg);
	            break;
	        case MAP_STRING_ARRAYLIST_STRING:
	            handleMapStringKeyArrayListStringValueMessage((Map<String, ArrayList<String>>) msg);
	            break;
	        case MAP_STRING_STRING:
	            handleMapStringStringValueMessage((Map<String, String>) msg);
	            break;
	        default:
	            System.out.println("Message type does not exist");
	            break;
	    }
	}



	// This method is used to determine the type of a message.
	private static MessageType getMessageType(Object msg) {
		// Check if the message is a String.
		if (msg instanceof String) {
			// If it is, return the corresponding MessageType.
			return MessageType.STRING;
		}
		// Check if the message is an ArrayList.
		else if (msg instanceof ArrayList) {
			// Cast the message to an ArrayList.
			ArrayList<?> arrayList = (ArrayList<?>) msg;
			// Check if the ArrayList is not empty.
			if (!arrayList.isEmpty()) {
				// Get the first element of the ArrayList.
				Object firstElement = arrayList.get(0);
				// Check the type of the first element and return the corresponding MessageType.
				if (firstElement instanceof String) {
					return MessageType.ARRAY_LIST_STRING;
				} else if (firstElement instanceof QuestionInExam) {
					return MessageType.ARRAY_LIST_QUESTIONINEXAM;
				} else if (firstElement instanceof Question && !(firstElement instanceof QuestionInExam)) {
					return MessageType.ARRAY_LIST_QUESTION;
				} else if (firstElement instanceof Exam) {
					return MessageType.ARRAY_LIST_EXAM;
				} else if (firstElement instanceof HeadOfDepartment) {
					return MessageType.ARRAY_LIST_HOD;
				}
			}
		}
		// Check if the message is a Map.
		else if (msg instanceof Map) {
			// Cast the message to a Map.
			Map<?, ?> map = (Map<?, ?>) msg;
			// Check if the Map is not empty.
			if (!map.isEmpty()) {
				// Get the first key and value from the Map.
				Object firstKey = map.keySet().iterator().next();
				Object firstValue = map.get(firstKey);
				// Check the types of the first key and value and return the corresponding MessageType.
				if (firstKey instanceof String && firstValue instanceof ArrayList
						&& ((ArrayList<?>) firstValue).get(0) instanceof String) {
					return MessageType.MAP_STRING_ARRAYLIST_STRING;
				} else if (firstKey instanceof String && firstValue instanceof String) {
					return MessageType.MAP_STRING_STRING;
				}
			}
		}
		// If none of the above conditions are met, return null.
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
	    			LoginFrameController.getInstance().userLoginFailed("Error: This user is already connected");
	    			break;
	    			
    			case "UserEnteredWrondPasswwordOrUsername":
    				LoginFrameController.getInstance().userLoginFailed("Error: Wrong Username or Password");
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
						else if(arrayListStr.get(1).equals("Student")) { // login as student
							// 7 - Department Name
							// 8 - DepartmentID
							System.out.println("Student Login in\n");
							StudentDashboardFrameController.start(arrayListStr);
						}
						else if(arrayListStr.get(1).equals("Hod")) { // login as head of department
							//HodDashboardFrameController.start(arrayListStr);
						}
						System.out.println("logged in succesfully");
						userID = arrayListStr.get(2);
	                    break;
	                    
	                case "MaximunQuestionIdForSelectedSubject":
	                	// 1 - maximum question id for selected subject
	                	AddQuestionFrameController.saveMaxIdOfQuestionInSelectedSubject(arrayListStr.get(1));
	                	
	                	break;
	                	
	                case "MaxExamNumberOfCourse":
	                	// 1 - max exam number in the course
	                	CreateExam_ReviewFrameController.saveIdOfExamInCourse(arrayListStr.get(1));
	                	break;
	                	
	                case "an exam has been closed": // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	                	// 1 - examID
	                	/*try {
		                	if(ComputerizedExamController.getCurrExamID().equals(arrayListStr.get(1))) { // if in the specific exam
	
		                		ComputerizedExamController.examClosed(); // close the exam for the student in the specific exam
		                		
		                		System.out.println("exam closed");
		                	}
	                	}catch (NullPointerException e){}*/
	                	
	                	break;
	                	
	                case "SendToHeadOfDepartmentsThatRequestRecieved":
	                	// 1 - Head of department ID
	                	try {
		                	if(userID.equals(arrayListStr.get(1))) { // if the current client is the head of department
		                		
		                		System.out.println("you have new message"); // alert new request: function or something else
		                	}
                		}catch (NullPointerException e){}
	                	
	            
	                	break;
	            }       
	            
            }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            }
        }
    
    
    // must remove the first question (the question that identified)
    private static void handleQuestionArrayListMessage(ArrayList<Question> questionList) {
        // Handle ArrayList<Question> messages
    	
    	String messageType = questionList.get(0).getId();

    	switch (messageType) {
    		case "LoadQuestionsFromDB":
    			// Handle LoadQuestionsFromDB message
    			questionList.remove(0); // remove the first question (the question that identified)
				LecturerDashboardFrameController.getInstance().loadArrayQuestionsToTable_ManageQuestions(questionList);
				System.out.println("The questions succesfully loaded from the DB to the table.");
				break;
				
    		case "LoadQuestionsFromDB_CreateExamTable":
    			
    			questionList.remove(0); // remove the first question (the question that identified)
    			LecturerDashboardFrameController.getInstance().loadArrayQuestionsToTable_CreateExam(questionList);
    			System.out.println("The questions succesfully loaded from the DB to the create exam table.");
    			break;
    	} 	
    }
    
    private static void handleMapStringKeyArrayListStringValueMessage(Map<String, ArrayList<String>> map) {
        // Handle Map<String, ArrayList<String>> messages
    		
    	if(map.containsKey("HashMapWithLecturerSubjectsAndCourses")) {
			map.remove("HashMapWithLecturerSubjectsAndCourses");
			LecturerDashboardFrameController.loadLecturerSubjectsAndCourses(map);
    	}

    }
    
    
    private static void handleMapStringStringValueMessage(Map<String, String> map) {
        // Handle the Map<String, String> here
    	
    	if(map.containsKey("HashMapWithSubjects_names_ids")) {
			map.remove("HashMapWithSubjects_names_ids");
			LecturerDashboardFrameController.loadAllSubjectsFromDB(map);
    	}
    	
    	else if(map.containsKey("HashMapWithCourses_names_ids")) {
			map.remove("HashMapWithCourses_names_ids");
			LecturerDashboardFrameController.loadAllCoursesFromDB(map);
    	}
    	
    }

	//This method is to get Computerized Exams to the client
	private static void handleExamArrayListMessage(ArrayList<Exam> examList) {
		System.out.println("Reached the handleExamArrayListMessage | ClientHandler");
		// Handle ArrayList<Exam> messages
		// You're supposed to call a function within the Controller class and pass the List to it to update the fields
    	String messageType = examList.get(0).getExamID();

	    	switch (messageType) {
	    	
	    		case "loadActiveExamsIntoLecturerTable":
	    			examList.remove(0); // removing the identifying exam
	    			LecturerDashboardFrameController.getInstance().loadAllActiveExamsToTable(examList);
					break;
					
	    		case "loadInActiveExamsIntoLecturerTable":
	    			examList.remove(0); // removing the identifying exam
	    			LecturerDashboardFrameController.getInstance().loadAllInActiveExamsToTable(examList);
					break;
				case "computerizedExamsForStudentTable":
					System.out.println("Reached the Client Handler");
					examList.remove(0);
					StudentDashboardFrameController.getInstance().loadComputerizedExamsIntoTable(examList);
					break;
	    	}
		
	}


	private static void handleQuestionInExamArrayListMessage(ArrayList<QuestionInExam> questionInExamList) {
		// Handle ArrayList<QuestionInExam> messages

		System.out.println("Reached handleQuestionInExamArrayListMessage | ClientHandler");
		System.out.println(questionInExamList + " Inside Handler!");
    	String messageType = questionInExamList.get(0).getId();
		questionInExamList.remove(0);

	    	switch (messageType) {
	    	
	    		case "questionsByExamIdForClient":
					ComputerizedExamController.getInstance().loadExamQuestions(questionInExamList);
					break;
	    	} 
	    	

		
	}
	
	private static void handleHodArrayListMessage(ArrayList<HeadOfDepartment> hodList) {
		System.out.println("Reached the handleHodArrayListMessage | ClientHandler");
		// Handle ArrayList<HeadOfDepartment> messages

    	String messageType = hodList.get(0).getId();

	    	switch (messageType) {
	    	
	    		case "LoadRelevantHodForLecturer":
	    			hodList.remove(0); // removing the identifying exam
	    			ManageExam_ChangeTimeFrameController.getInstance().loadHODsForLecturer(hodList);
					break;
					
	    	}
		
	}

}
