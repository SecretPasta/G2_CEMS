package JDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import Config.Question;

public class DBController {
	
	
	// func to get all questions from DB that created by specific lecturer and/or specific courseName and/or subjectID.
	public static ArrayList<Question> getAllQuestions(String lecturerID, String courseID, String subjectID) {
		ArrayList<Question> questions = new ArrayList<Question>();
		try {
			try {
				if (mysqlConnection.getConnection() != null) {
					PreparedStatement ps = null;
					if(lecturerID == null && courseID == null && subjectID == null) {
						ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question");
					}
					else if(lecturerID != null && courseID == null && subjectID == null) {
						ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question WHERE lecturerID = ?");
						ps.setString(1, lecturerID);	
					}
					else if(lecturerID == null && courseID != null && subjectID == null) {
						ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question WHERE courseID = ?");
						ps.setString(1, courseID);	
					}	
					else if(lecturerID == null && courseID == null && subjectID != null) {
						ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question WHERE subjectID = ?");
						ps.setString(1, subjectID);	
					}
					else if (lecturerID != null && courseID != null && subjectID == null) {
					    ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question WHERE lecturerID = ? AND courseID = ?");
					    ps.setString(1, lecturerID);
					    ps.setString(2, courseID);
					}
					else if (lecturerID != null && courseID == null && subjectID != null) {
					    ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question WHERE lecturerID = ? AND subjectID = ?");
					    ps.setString(1, lecturerID);
					    ps.setString(2, subjectID);
					}
					else if (lecturerID == null && courseID != null && subjectID != null) {
					    ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question WHERE courseID = ? AND subjectID = ?");
					    ps.setString(1, courseID);
					    ps.setString(2, subjectID);
					}		
					else if (lecturerID != null && courseID != null && subjectID != null) {
					    ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question WHERE lecturerID = ? AND courseID = ? AND subjectID = ?");
					    ps.setString(1, lecturerID);
					    ps.setString(2, courseID);
					    ps.setString(3, subjectID);
					}
					
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						ArrayList<String> answers= new ArrayList<>();
						answers.add(rs.getString(6));
						answers.add(rs.getString(7));
						answers.add(rs.getString(8));
						answers.add(rs.getString(9));
						
						Question question = new Question(null, rs.getString(2), rs.getString(3), rs.getString(4), answers, rs.getString(5), rs.getString(10), rs.getString(11));

						questions.add(question);
					}
					rs.close();
				} else
					System.out.println("myConn is NULL!");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
	}
	
	public static String UpdateQuestionDataByID(ArrayList<String> qArr) {
		// 1 - question ID
		// 2 - question text
		// 3 - correct answer
		// 4 - wrong answer1
		// 5 - wrong answer2
		// 6 - wrong answer3
		try {
			if (mysqlConnection.getConnection() != null) {
				PreparedStatement ps = mysqlConnection.getConnection().prepareStatement("UPDATE `question` SET `questionText` =?, "
						+ "`answerCorrect` =?, `answerWrong1` =?, `answerWrong2` =?, `answerWrong3` =? "
						+ "WHERE (`id` =?);");
				ps.setString(1,qArr.get(2));
				ps.setString(2,qArr.get(3));
				ps.setString(3,qArr.get(4));
				ps.setString(4,qArr.get(5));
				ps.setString(5,qArr.get(6));
				ps.setString(6,qArr.get(1));
		 		ps.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Question updated succesfully";
	}
	
	public static ArrayList<String> userExist(ArrayList<String> userInfoArr) throws ClassNotFoundException {
		// 1 - loginAs: Lecturer, Student, Head Of Department
		// 2 - Username
		// 3 - Password
		ArrayList<String> userCannotConnect = new ArrayList<>(); // an array if there was a problem with sign in - wrond pass/username or user already exist
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement("SELECT EXISTS (SELECT * FROM " + userInfoArr.get(1) + " WHERE UserName =? AND Password =?)");
	            ps.setString(1, userInfoArr.get(2));
	            ps.setString(2, userInfoArr.get(3));
	
	            ResultSet resultSet = ps.executeQuery();
	            if(resultSet.next()) {
	                if(resultSet.getInt(1) == 1) {
	                	ArrayList<String> getUserDetails_arr = getUserDetails(userInfoArr.get(1), userInfoArr.get(2), userInfoArr.get(3));
	                	// check if the user is already logged in by his ID and loginAs
	                	if(!UserAlreadyLoggedin(getUserDetails_arr.get(0), userInfoArr.get(1))) {
	                		// userInfoArr.get(1) - loginAs, getUserDetails_arr.get(0) - user ID
	                		setUserIsLogin("1", userInfoArr.get(1), getUserDetails_arr.get(0));
	                		return getUserDetails_arr;
	                	}
	                	else {
	                		userCannotConnect.add("UserAlreadyLoggedIn");
	                		return userCannotConnect;
	                	}	
	                }
	            }
	            userCannotConnect.add("UserEnteredWrondPasswwordOrUsername");
	            return userCannotConnect;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {}
		return null;
	}
	
	// set the user 'isLogin' in DB to 1 or 0 by his ID, loginAs = {Lecturer, Student, Head Of Department} - the table
	// if id is "all", do for all users in table
	public static void setUserIsLogin(String loggedInStatus, String loginAs, String id) {
		try {
			if (mysqlConnection.getConnection() != null) {
				PreparedStatement ps;
				if(id.equals("all")) {
					ps = mysqlConnection.getConnection().prepareStatement("UPDATE "+ loginAs +" SET `isLogged` =?;");	
					ps.setString(1, loggedInStatus);
				}
				else {
					String userId = loginAs + "id";
					ps = mysqlConnection.getConnection().prepareStatement("UPDATE "+ loginAs +" SET `isLogged` =? WHERE (`" + userId + "` =?);");
					ps.setString(1, loggedInStatus);
					ps.setString(2, id);
				}
		 		ps.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// func to return details of user by his username and password
	public static ArrayList<String> getUserDetails(String loginAs, String username, String password) {
	    String query = "SELECT * FROM " + loginAs + " WHERE username = ? AND password = ?";
	    ArrayList<String> userDetailsArr = new ArrayList<>();
	    try {
	    	if (mysqlConnection.getConnection() != null) {
	    		PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	    		ps.setString(1, username);
	    		ps.setString(2, password);
	    		ResultSet resultSet = ps.executeQuery();
	    		if(resultSet.next()) {
	    			for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
	    				userDetailsArr.add(resultSet.getString(i));
	    			}
	    		}
		    } 
	    } catch (SQLException | ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
		  // 0 - user ID
		  // 1 - userName
		  // 2 - user Password
		  // 3 - user Name
		  // 4 - user Email
	    // 5 - isLogged
	    return userDetailsArr;
	}
	
	// check if the user is already logged in
	public static boolean UserAlreadyLoggedin(String userID, String logginAs) {
		String userId = logginAs + "id";
		String query = "SELECT IF(isLogged = 1, true, false) AS isLoggedIn FROM " + logginAs + " WHERE " + userId + "= ?";
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, userID);
	            try (ResultSet resultSet = ps.executeQuery()) {
	                if (resultSet.next()) {
	                    return resultSet.getBoolean("isLoggedIn"); // isLoggedIn: true / false
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;	
	}

	public static boolean removeQuestion(String questionID) {
		String query = "DELETE FROM question WHERE id = ?";
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, questionID);
	            ps.executeUpdate();
	            return true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;	
	}

	public static void setAllUsersNotIsLogged() {
		setUserIsLogin("0", "lecturer", "all");
		setUserIsLogin("0", "student", "all");
		setUserIsLogin("0", "headofdepartment", "all");
	}

public static Map<String, ArrayList<String>> getLecturerSubjectCourses(String lecturerID) {
		
		Map<String, ArrayList<String>> lecDepartmentCoursesMap = new HashMap<>();
		
		String query = "SELECT subjects.Name AS SubjectName, course.Name AS CourseName "
				+ "FROM subjects "
				+ "JOIN coursesubject ON subjects.SubjectID = coursesubject.SubjectID "
				+ "JOIN lecturercourse ON coursesubject.CourseID = lecturercourse.CourseID "
				+ "JOIN course ON coursesubject.CourseID = course.CourseID "
				+ "WHERE lecturercourse.LecturerID = ?";
		
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, lecturerID);
	            try (ResultSet resultSet = ps.executeQuery()) {
	            	while (resultSet.next()) {
	            	    String subjectName = resultSet.getString("SubjectName");
	            	    String courseName = resultSet.getString("CourseName");

	            	    //Check if the subject is already in the map
	            	    if (lecDepartmentCoursesMap.containsKey(subjectName)) {
	            	        //Add the course to the existing list of courses for the subject
	            	    	lecDepartmentCoursesMap.get(subjectName).add(courseName);
	            	    } else {
	            	        //Create a new list and add the course
	            	        ArrayList<String> courses = new ArrayList<>();
	            	        courses.add(courseName);
	            	        lecDepartmentCoursesMap.put(subjectName, courses);
	            	    }
	            	}
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Print the subjects and their corresponding courses
		/*for (Entry<String, ArrayList<String>> entry : lecDepartmentCoursesMap.entrySet()) {
		    String subject = entry.getKey();
		    ArrayList<String> courses = entry.getValue();

		    System.out.println("Subject: " + subject);
		    System.out.println("Courses:");

		    for (String course : courses) {
		        System.out.println("- " + course);
		    }
		    //Optional line to add a blank line between subjects
		    System.out.println();
		}*/
		
		return lecDepartmentCoursesMap;
	}

	public static String getMaxQuestionIdFromSubject(String subjectID) {
		String query = "SELECT subjects.MaxQuestionNumber "
		        + "FROM subjects "
		        + "WHERE SubjectID = ?";
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, subjectID);
	            try (ResultSet rs = ps.executeQuery()) {
	                if(rs.next()) {
	                	return rs.getString(1);
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}

	public static void addNewQuestion(ArrayList<Question> questionList) {
		String query = "INSERT INTO question (id, subjectID, courseID, questionText, questionNumber, answerCorrect, answerWrong1"
				+ ", answerWrong2, answerWrong3, lecturer, lecturerID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int i = 0;
	    try {
	    	for(Question question : questionList) {
	    		i ++;
		    	if (mysqlConnection.getConnection() != null) {
		    		PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
		    		ps.setString(1, question.getId());
		    		ps.setString(2, question.getsubjectID());
		    		ps.setString(3, question.getCourseID());
		    		ps.setString(4, question.getQuestionText());
		    		ps.setString(5, question.getQuestionNumber());
		    		ps.setString(6, question.getAnswers().get(0));
		    		ps.setString(7, question.getAnswers().get(1));
		    		ps.setString(8, question.getAnswers().get(2));
		    		ps.setString(9, question.getAnswers().get(3));
		    		ps.setString(10, question.getLecturer());
		    		ps.setString(11, question.getLecturerID());
		    		ps.executeUpdate();
		    	}
	    	}
	    	updateSubjectMaxQuestionNumber(questionList.get(0).getsubjectID(), i);
		
	    } catch (SQLException | ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
	}

	private static void updateSubjectMaxQuestionNumber(String subjectID, int i) { // @@@@@@@@@@@@@@@@@@@@@
		
		String maxNumQuestion= getMaxQuestionIdFromSubject(subjectID); // 005
		// i = 4
		 // 009
		
		String query = "UPDATE subjects "
	             + "SET MaxQuestionNumber = ? "
	             + "WHERE SubjectID = ?";
		System.out.println(i);
		System.out.println(subjectID);
		try {
			if (mysqlConnection.getConnection() != null) {
				PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
				
				maxNumQuestion = Integer.toString(Integer.parseInt(maxNumQuestion) + i);
				maxNumQuestion = String.format("%03d", Integer.parseInt(maxNumQuestion));
				
				
				ps.setString(1, maxNumQuestion);
				
				ps.setString(2, subjectID);
		 		ps.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Map<String, String> getAllSubjectsNamesAndIds() {
		
		String query = "SELECT subjects.SubjectID, subjects.Name FROM subjects";
		Map<String, String> map = new HashMap<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	map.put(rs.getString(1), rs.getString(2));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static Map<String, String> getAllCoursesNamesAndIds() {
		
		String query = "SELECT course.CourseID, course.Name FROM course";
		Map<String, String> map = new HashMap<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	map.put(rs.getString(1), rs.getString(2));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
}

