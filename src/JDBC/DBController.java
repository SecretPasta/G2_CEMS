package JDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Config.Question;

public class DBController {
	
	
	// func to get all questions from DB that created by specific lecturer and/or specific courseName.
	// if the lecturerName and courseName is null, getting all questions from DB.
	public static ArrayList<Question> getAllQuestions(String lecturerName, String courseName) {
		ArrayList<Question> questions = new ArrayList<Question>();
		questions.add(new Question("LoadQuestionsFromDB", null, null, null, null, null)); // set the first in the array to know how to handle it
		try {
			try {
				if (mysqlConnection.getConnection() != null) {
					PreparedStatement ps = null;
					if(lecturerName == null && courseName == null) {
						ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question");
					}
					else if(lecturerName != null && courseName == null) {
						ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question WHERE lecturer =?");
						ps.setString(1, lecturerName);	
					}
					else if(lecturerName == null && courseName != null) {
						ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question WHERE courseName =?");
						ps.setString(1, courseName);	
					}	
					else if(lecturerName != null && courseName != null) {
						ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question WHERE lecturer =? AND courseName =?");
						ps.setString(1, lecturerName);
						ps.setString(2, courseName);
					}
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						Question question = new Question(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
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
		// 3 - question number
		try {
			if (mysqlConnection.getConnection() != null) {
				PreparedStatement ps = mysqlConnection.getConnection().prepareStatement("UPDATE `question` SET `questionText` =?, `questionNumber` =? WHERE (`id` =?);");
				ps.setString(1,qArr.get(2));
				ps.setString(2,qArr.get(3));
				ps.setString(3,qArr.get(1));
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

	public static Map<String, ArrayList<String>> getLecturerDepartmentCourses(String lecturerID) {
		
		Map<String, ArrayList<String>> lecDepartmentCoursesMap = new HashMap<>();
		
        String query = "SELECT d.Name, c.Name " +
                "FROM department d " +
                "JOIN course c ON d.departmentID = c.departmentID";

	    try {
	    	if (mysqlConnection.getConnection() != null) {
	    		PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	    		ps.setString(1, lecturerID);
	    		ResultSet resultSet = ps.executeQuery();
	    		while (resultSet.next()) {
	                String department = resultSet.getString("d.Name");
	                String course = resultSet.getString("c.Name");
	                
	                System.out.println(department);
	                System.out.println(course);

	                // Check if the department already exists in the map
	                if (lecDepartmentCoursesMap.containsKey(department)) {
	                    // Add the course to the existing ArrayList of courses for the department
	                    ArrayList<String> courses = lecDepartmentCoursesMap.get(department);
	                    courses.add(course);
	                } else {
	                    // Create a new ArrayList and add the course for the department
	                	ArrayList<String> courses = new ArrayList<>();
	                    courses.add(course);
	                    lecDepartmentCoursesMap.put(department, courses);
	                }
	            }
	    	}
		     
	    } catch (SQLException | ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
		
		return lecDepartmentCoursesMap;
	}
}

