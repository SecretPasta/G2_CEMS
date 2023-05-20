package JDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Config.Question;

public class DBController {
	
	
	public static ArrayList<Question> getAllQuestions(String lecturerName) {
		ArrayList<Question> questions = new ArrayList<Question>();
		questions.add(new Question("LoadQuestionsFromDB", null, null, null, null, null)); // set the first in the array to know how to handle it
		try {
			try {
				if (mysqlConnection.getConnection() != null) {
					PreparedStatement ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM question WHERE lecturer =?");
					ps.setString(1, lecturerName);
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
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement("SELECT EXISTS (SELECT * FROM " + userInfoArr.get(1) + " WHERE UserName =? AND Password =?)");
	            ps.setString(1, userInfoArr.get(2));
	            ps.setString(2, userInfoArr.get(3));
	
	            ResultSet resultSet = ps.executeQuery();
	            if(resultSet.next()) {
	                if(resultSet.getInt(1) == 1) {
	                	return getUserDetails(userInfoArr.get(1), userInfoArr.get(2), userInfoArr.get(3));
	                }
	            }
	            return null;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {}
		return null;
	}
	
	// func to return details of user by his username and password
	public static ArrayList<String> getUserDetails(String loginAs, String username, String password) {
	    String name = null;
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
	    return userDetailsArr;
	}
}

