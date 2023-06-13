package JDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Config.*;

public class DBController {

	// func to get all questions from DB that created by specific lecturer and/or specific courseName and/or subjectID.
	public static ArrayList<Question> getAllQuestions(String lecturerID, String courseID, String subjectID) {
	    ArrayList<Question> questions = new ArrayList<Question>();
	    try {
	        try {
	            if (mysqlConnection.getConnection() != null) {
	                PreparedStatement ps = null;
	                if (lecturerID == null && courseID == null && subjectID == null) {
	                    ps = mysqlConnection.getConnection().prepareStatement("SELECT q.*, qc.courseID, c.Name, s.SubjectID, s.Name FROM question q JOIN questioncourse qc ON q.id = qc.questionID JOIN course c ON qc.courseID = c.courseID JOIN subjects s ON q.subjectID = s.SubjectID");
	                } else if (lecturerID != null && courseID == null && subjectID == null) {
	                    ps = mysqlConnection.getConnection().prepareStatement("SELECT q.*, qc.courseID, c.Name, s.SubjectID, s.Name FROM question q JOIN questioncourse qc ON q.id = qc.questionID JOIN course c ON qc.courseID = c.courseID JOIN subjects s ON q.subjectID = s.SubjectID WHERE q.lecturerID = ?");
	                    ps.setString(1, lecturerID);
	                } else if (lecturerID == null && courseID != null && subjectID == null) {
	                    ps = mysqlConnection.getConnection().prepareStatement("SELECT q.*, qc.courseID, c.Name, s.SubjectID, s.Name FROM question q JOIN questioncourse qc ON q.id = qc.questionID JOIN course c ON qc.courseID = c.courseID JOIN subjects s ON q.subjectID = s.SubjectID WHERE qc.courseID = ?");
	                    ps.setString(1, courseID);
	                } else if (lecturerID == null && courseID == null && subjectID != null) {
	                    ps = mysqlConnection.getConnection().prepareStatement("SELECT q.*, qc.courseID, c.Name, s.SubjectID, s.Name FROM question q JOIN questioncourse qc ON q.id = qc.questionID JOIN course c ON qc.courseID = c.courseID JOIN subjects s ON q.subjectID = s.SubjectID WHERE q.subjectID = ?");
	                    ps.setString(1, subjectID);
	                } else if (lecturerID != null && courseID != null && subjectID == null) {
	                    ps = mysqlConnection.getConnection().prepareStatement("SELECT q.*, qc.courseID, c.Name, s.SubjectID, s.Name FROM question q JOIN questioncourse qc ON q.id = qc.questionID JOIN course c ON qc.courseID = c.courseID JOIN subjects s ON q.subjectID = s.SubjectID WHERE q.lecturerID = ? AND qc.courseID = ?");
	                    ps.setString(1, lecturerID);
	                    ps.setString(2, courseID);
	                } else if (lecturerID != null && courseID == null && subjectID != null) {
	                    ps = mysqlConnection.getConnection().prepareStatement("SELECT q.*, qc.courseID, c.Name, s.SubjectID, s.Name FROM question q JOIN questioncourse qc ON q.id = qc.questionID JOIN course c ON qc.courseID = c.courseID JOIN subjects s ON q.subjectID = s.SubjectID WHERE q.lecturerID = ? AND q.subjectID = ?");
	                    ps.setString(1, lecturerID);
	                    ps.setString(2, subjectID);
	                } else if (lecturerID == null && courseID != null && subjectID != null) {
	                    ps = mysqlConnection.getConnection().prepareStatement("SELECT q.*, qc.courseID, c.Name, s.SubjectID, s.Name FROM question q JOIN questioncourse qc ON q.id = qc.questionID JOIN course c ON qc.courseID = c.courseID JOIN subjects s ON q.subjectID = s.SubjectID WHERE qc.courseID = ? AND q.subjectID = ?");
	                    ps.setString(1, courseID);
	                    ps.setString(2, subjectID);
	                } else if (lecturerID != null && courseID != null && subjectID != null) {
	                    ps = mysqlConnection.getConnection().prepareStatement("SELECT q.*, qc.courseID, c.Name, s.SubjectID, s.Name FROM question q JOIN questioncourse qc ON q.id = qc.questionID JOIN course c ON qc.courseID = c.courseID JOIN subjects s ON q.subjectID = s.SubjectID WHERE qc.lecturerID = ? AND qc.courseID = ? AND q.subjectID = ?");
	                    ps.setString(1, lecturerID);
	                    ps.setString(2, courseID);
	                    ps.setString(3, subjectID);
	                }

	                ResultSet rs = ps.executeQuery();
	                while (rs.next()) {
	                    String questionId = rs.getString(1);

	                    // Check if the question with the same ID already exists
	                    Question existingQuestion = null;
	                    for (Question question : questions) {
	                        if (question.getId().equals(questionId)) {
	                            existingQuestion = question;
	                            break;
	                        }
	                    }

	                    // If the question exists, update its courses
	                    if (existingQuestion != null) {
	                        String courseid = rs.getString("courseID");
	                        String courseName = rs.getString("c.Name");
	                        if (courseid != null && courseName != null) {
	                            String[] courseArray = courseid.split(",");
	                            for (String course : courseArray) {
	                                existingQuestion.getCourses().put(course, courseName);
	                            }
	                        }
	                    } else {
	                        ArrayList<String> answers = new ArrayList<>();
	                        answers.add(rs.getString(5));
	                        answers.add(rs.getString(6));
	                        answers.add(rs.getString(7));
	                        answers.add(rs.getString(8));

	                        // Create a map to store the courses for the question
	                        Map<String, String> courses = new HashMap<>();
	                        String courseid = rs.getString("courseID");
	                        String courseName = rs.getString("c.Name");
	                        if (courseid != null && courseName != null) {
	                            String[] courseArray = courseid.split(",");
	                            for (String course : courseArray) {
	                                courses.put(course, courseName);
	                            }
	                        }
	                        
	                        // Create an array to store the subjects for the question
	                        ArrayList<String> subject = new ArrayList<>();
	                        String subjectId = rs.getString("SubjectID");
	                        String subjectName = rs.getString("s.Name");
	                        if (subjectId != null && subjectName != null) {
	                            subject.add(subjectId);
	                            subject.add(subjectName);
	                        }
	                        
	                        Question question = new Question(questionId, subject, courses, rs.getString(3), answers, rs.getString(4), rs.getString(9), rs.getString(10));


	                        questions.add(question);
	                    }
	                }
	                rs.close();
	            } else {
	                System.out.println("myConn is NULL!");
	            }
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
	    String deleteQuestionQuery = "DELETE FROM question WHERE id = ?";
	    String deleteQuestionCourseQuery = "DELETE FROM questioncourse WHERE questionID = ?";
	    try {
	        if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps1 = mysqlConnection.getConnection().prepareStatement(deleteQuestionCourseQuery);
	            ps1.setString(1, questionID);
	            ps1.executeUpdate();

	            PreparedStatement ps2 = mysqlConnection.getConnection().prepareStatement(deleteQuestionQuery);
	            ps2.setString(1, questionID);
	            ps2.executeUpdate();

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

	public static void addNewQuestion(Question question) {
		String query = "INSERT INTO question (id, subjectID, questionText, questionNumber, answerCorrect, answerWrong1"
				+ ", answerWrong2, answerWrong3, lecturer, lecturerID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int i = 0;
	    try {
	    	
	    	if (mysqlConnection.getConnection() != null) {
	    		PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	    		ps.setString(1, question.getId());
	    		ps.setString(2, question.getSubjectId());
	    		ps.setString(3, question.getQuestionText());
	    		ps.setString(4, question.getQuestionNumber());
	    		ps.setString(5, question.getAnswers().get(0));
	    		ps.setString(6, question.getAnswers().get(1));
	    		ps.setString(7, question.getAnswers().get(2));
	    		ps.setString(8, question.getAnswers().get(3));
	    		ps.setString(9, question.getLecturer());
	    		ps.setString(10, question.getLecturerID());
	    		ps.executeUpdate();
	    		
		    	for(String courseid : question.getCourses().keySet()) {
		    		PreparedStatement ps2 = mysqlConnection.getConnection().prepareStatement("INSERT INTO questioncourse (questionID, courseID) VALUES (?, ?)");
		    		ps2.setString(1, question.getId());
		    		ps2.setString(2, courseid);
		    		ps2.executeUpdate();
		    	}
	    		
	    	}

	    	updateSubjectMaxQuestionNumber(question.getSubjectId(), 1);
		
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

	public static String getMaxExamIdFromCourse(String courseID) {
		String query = "SELECT course.MaxExamNumber "
		        + "FROM course "
		        + "WHERE CourseID = ?";
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, courseID);
	            try (ResultSet rs = ps.executeQuery()) {
	                if(rs.next()) {
	                	updateCourseMaxExamNumber(courseID, rs.getString(1), 1);
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
	
	private static void updateCourseMaxExamNumber(String courseID, String maxExamNumber_temp, int i) {
		
		String maxExamNumber = maxExamNumber_temp;
		
		String query = "UPDATE course "
	             + "SET MaxExamNumber = ? "
	             + "WHERE CourseID = ?";
		try {
			if (mysqlConnection.getConnection() != null) {
				PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
				
				maxExamNumber = Integer.toString(Integer.parseInt(maxExamNumber_temp) + i);
				maxExamNumber = String.format("%02d", Integer.parseInt(maxExamNumber));
				
				
				ps.setString(1, maxExamNumber);
				
				ps.setString(2, courseID);
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





	public static void addNewExam(Exam exam) {
		
		
		/*
		 * String examID, String subjectID, String subjectName, String courseID, String courseName, ArrayList<QuestionInExam> questions, 
			String commentsForLecturer, String commentsForStudent, int duration, String author, String code)
		 * 
		 * 
		 */
		
		String query = "INSERT INTO exams (ID, subjectID, courseID, commentsLecturer, commentsStudents, duration"
				+ ", author, questionsInExam, code, isActive, authorID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    try {
	    	
	    	if (mysqlConnection.getConnection() != null) {
	    		PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	    		ps.setString(1, exam.getExamID());
	    		ps.setString(2, exam.getSubjectID());
	    		ps.setString(3, exam.getCourseID());
	    		ps.setString(4, exam.getCommentsForLecturer());
	    		ps.setString(5, exam.getCommentsForStudent());
	    		ps.setInt(6, exam.getDuration());
	    		ps.setString(7, exam.getAuthor());	
	    		ArrayList<String> questionsID = new ArrayList<>();
	    		for(int i = 0; i<exam.getQuestions().size(); i++) {
	    			questionsID.add(exam.getQuestions().get(i).getId());
	    		}		
	    		ps.setString(8, String.join(",", questionsID));
	    		ps.setString(9, exam.getCode());
	    		ps.setString(10, "0");
	    		ps.setString(11, exam.getAuthorID());
	    		ps.executeUpdate();
	    		
	    	}
		
	    } catch (SQLException | ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
	}


	public static void addNewQuestionsInExam(ArrayList<QuestionInExam> questionsList) {
		String query = "INSERT INTO questionsexam (questionID, examID, questionText, answerCorrect, answerWrong1, answerWrong2"
				+ ", answerWrong3, points) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    try {
	    	
	    	String examID = questionsList.get(0).getQuestionText(); // the exam id is here
	    	questionsList.remove(0);
	    	if (mysqlConnection.getConnection() != null) {
	    		
	    		
	    		for(QuestionInExam question : questionsList) {
	    			PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	    			ps.setString(1, question.getId());
	    			ps.setString(2, examID);
	    			ps.setString(3, question.getQuestionText());
	    			ps.setString(4, question.getAnswers().get(0));
	    			ps.setString(5, question.getAnswers().get(1));
	    			ps.setString(6, question.getAnswers().get(2));
	    			ps.setString(7, question.getAnswers().get(3));
	    			ps.setDouble(8, question.getPoints());
	    			ps.executeUpdate();
	    		}
	    		
	    	}
		
	    } catch (SQLException | ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
		
	}
	
	public static ArrayList<Exam> getExamsByActiveness(String active, String authorID) {
	    String query;
	    ArrayList<Exam> examsArr = new ArrayList<>();
	    PreparedStatement ps = null;
	    try {
	        if (mysqlConnection.getConnection() != null) {
	            if (authorID == null) {
	                query = "SELECT exams.*, course.Name AS courseName, subjects.Name AS subjectName " +
	                        "FROM exams " +
	                        "JOIN course ON exams.courseID = course.CourseID " +
	                        "JOIN subjects ON exams.subjectID = subjects.SubjectID " +
	                        "WHERE exams.isActive = ?";

	                ps = mysqlConnection.getConnection().prepareStatement(query);
	                ps.setString(1, active);
	            } else {
	                query = "SELECT exams.*, course.Name AS courseName, subjects.Name AS subjectName " +
	                        "FROM exams " +
	                        "JOIN course ON exams.courseID = course.CourseID " +
	                        "JOIN subjects ON exams.subjectID = subjects.SubjectID " +
	                        "WHERE exams.isActive = ? AND exams.authorID = ?";

	                ps = mysqlConnection.getConnection().prepareStatement(query);
	                ps.setString(1, active);
	                ps.setString(2, authorID);
	            }
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    examsArr.add(new Exam(rs.getString(1), rs.getString(2), rs.getString("subjectName"),
	                            rs.getString(3), rs.getString("courseName"), null,
	                            rs.getString("commentsLecturer"), rs.getString("commentsStudents"), rs.getInt(6),
	                            rs.getString(7), rs.getString(9), rs.getString(11)));
	                }
	            }
	        }
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return examsArr;
	}


	public static ArrayList<QuestionInExam> retrieveQuestionsInExamById(String examID){
		ArrayList<QuestionInExam> questions = new ArrayList<>();

		try {
			// Query to get the questionsInExam from the exams table
			String query = "SELECT questionsInExam FROM exams WHERE ID=?";
			PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
			ps.setString(1, examID);
			ResultSet rs = ps.executeQuery();

			// Retrieve the questionsInExam
			String questionsInExam = "";
			while (rs.next()) {
				questionsInExam = rs.getString("questionsInExam");
			}

			// Split the string by the comma to get question IDs
			String[] questionIDs = questionsInExam.split(",");

			// Iterate over the question IDs
			for (String questionID : questionIDs) {
				// Query to get the question details from the questionexams table
				query = "SELECT * FROM questionsexam WHERE examID=? AND questionID=?";
				ps = mysqlConnection.getConnection().prepareStatement(query);
				ps.setString(1, examID);
				ps.setString(2, questionID);
				rs = ps.executeQuery();

				// Retrieve the question details and add to the list
				while (rs.next()) {
					String questionText = rs.getString("questionText");
					String answerCorrect = rs.getString("answerCorrect");
					String answerWrong1 = rs.getString("answerWrong1");
					String answerWrong2 = rs.getString("answerWrong2");
					String answerWrong3 = rs.getString("answerWrong3");
					Double points = rs.getDouble("points");

					ArrayList<String> questionAnswers = new ArrayList<>();
					questionAnswers.add(answerCorrect);
					questionAnswers.add(answerWrong1);
					questionAnswers.add(answerWrong2);
					questionAnswers.add(answerWrong3);

					QuestionInExam question = new QuestionInExam(questionID, questionText, questionAnswers, null);
					question.setPoints(points);
					question.toString();
					questions.add(question);
				}
			}
		} catch (SQLException e) {
			// Handle SQL errors
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		return questions;
	}


	//A method to return the question by ID from the DB
	public static QuestionInExam retrieveQuestionsByExamId(String questionID, String examID) {
		QuestionInExam returnQuestions = new QuestionInExam(null, null, null, null);

		try {
			if (mysqlConnection.getConnection() != null) {
				String query = "SELECT * FROM questionsexam WHERE questionID = ? AND examID = ?";
				PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
				ps.setString(1, questionID);
				ps.setString(2, examID);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					String id = rs.getString("questionID");
					String subjectID = rs.getString("examID");
					String questionText = rs.getString("questionText");
					String answerCorrect = rs.getString("answerCorrect");
					String answerWrong1 = rs.getString("answerWrong1");
					String answerWrong2 = rs.getString("answerWrong2");
					String answerWrong3 = rs.getString("answerWrong3");
					int points = rs.getInt("points");

					ArrayList<String> subject = new ArrayList<>();
					subject.add(subjectID);

					ArrayList<String> answers = new ArrayList<>();
					answers.add(answerCorrect);
					answers.add(answerWrong1);
					answers.add(answerWrong2);
					answers.add(answerWrong3);

					QuestionInExam questionInExam = new QuestionInExam(id, questionText, answers, null);
					questionInExam.setPoints((double) points);
					returnQuestions = questionInExam;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return returnQuestions;
	}





	public static void changeExamActivenessByID(String examID, String activenessChangeTo) {

		String query = "UPDATE exams "
	             + "SET isActive = ? "
	             + "WHERE ID = ?";
		try {
			if (mysqlConnection.getConnection() != null) {
				PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);		
				ps.setString(1, activenessChangeTo);
				ps.setString(2, examID);
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




	// return ArrayList<HeadOfDepartment> that relevant to the lecturer ID. get only id and name
	public static ArrayList<HeadOfDepartment> getHeadOfDepartmentsByLecturer(String lecturerID) {
		ArrayList<HeadOfDepartment> relevantHODForLecturer = new ArrayList<>();
		String query = "SELECT HOD.HeadOfDepartmentID, HOD.Name " +
				"FROM HeadOfDepartment HOD " +
				"JOIN lecturerdepartment LD ON HOD.DepartmentID = LD.DepartmentID " +
				"WHERE LD.LecturerID = ? ";
		
		try {
			if(mysqlConnection.getConnection() != null) {
				PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
				ps.setString(1,lecturerID);
				try(ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						String HODdepartmentID = rs.getString("HeadOfDepartmentID");
						String HODdepartmentName = rs.getNString("Name");
						HeadOfDepartment hod = new HeadOfDepartment(HODdepartmentID,null,null,HODdepartmentName,null);
						relevantHODForLecturer.add(hod);
					}
				}
			}
		} catch(ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relevantHODForLecturer;
	}
	
	


	  

	 



	// save the requests that he got from the lecturer in DB
	public static void saveRequestForHodInDB(ArrayList<String> requestInfo_arr) {
		// 1 - exam ID
		// 2 - subject name
		// 3 - course name
		// 4 - lecturer id that sent the request
		// 5 - lecturer name
		// 6 - lecturer's explanation
		// 7 - add to exam duration
		// 8 - head of department ID
		
		String query = "INSERT INTO headofdepartmentrequests (HeadOfDepartmentID, subject, course, lecturerID, examID, lecturerName"
				+ ", explanation, examDurationAdd) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    try {
	    	if (mysqlConnection.getConnection() != null) {
	    		PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	    		ps.setString(1, requestInfo_arr.get(8));
	    		ps.setString(2, requestInfo_arr.get(2));
	    		ps.setString(3, requestInfo_arr.get(3));
	    		ps.setString(4, requestInfo_arr.get(4));
	    		ps.setString(5, requestInfo_arr.get(1));
	    		ps.setString(6, requestInfo_arr.get(5));
	    		ps.setString(7, requestInfo_arr.get(6));
	    		ps.setString(8, requestInfo_arr.get(7));
	    		ps.executeUpdate();
	    	}
		
	    } catch (SQLException | ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
		
	}


	public static void saveFinishedExamToDB(FinishedExam finishedExam) {
		String query = "INSERT INTO finishedexam (examID, studentID, answers, lecturer, grade, approved, checkExam) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			if (mysqlConnection.getConnection() != null) {
				PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
				ps.setString(1, finishedExam.getExamID());
				ps.setString(2, finishedExam.getStudentID());
				ps.setString(3, finishedExam.getAnswers());
				ps.setString(4, finishedExam.getLecturer());
				ps.setDouble(5, finishedExam.getGrade());
				ps.setInt(6, finishedExam.getApproved());
				ps.setInt(7, finishedExam.getCheckExam());

				ps.executeUpdate();
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<StudentGrade> getAllStudentGradesById(String studentID) {
		ArrayList<StudentGrade> grades = new ArrayList<>();
		String query = "SELECT exams.ID, course.Name AS courseName, subjects.Name AS subjectName, exams.author, finishedexam.grade " +
				"FROM exams " +
				"JOIN finishedexam ON exams.ID = finishedexam.examID " +
				"JOIN course ON exams.courseID = course.CourseID " +
				"JOIN subjects ON exams.subjectID = subjects.SubjectID " +
				"WHERE finishedexam.studentID = ? " +
				"AND finishedexam.approved = 1 " +
				"AND finishedexam.checkExam = 1";


		try  {
			PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);;
			ps.setString(1, studentID);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String examID = rs.getString("ID");
					String course = rs.getString("courseName");
					String subject = rs.getString("subjectName");
					String lecturer = rs.getString("author");
					double grade = rs.getDouble("grade");
					grades.add(new StudentGrade(examID, course, subject, lecturer, grade));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		System.out.println(grades);
		return grades;
	}


	public static ArrayList<String> getRequestsForHod(String hodID) {

		String query = "SELECT * FROM headofdepartmentrequests WHERE HeadOfDepartmentID = ?";
		ArrayList<String> requests_arr = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, hodID);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	requests_arr.add(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4)
	                	 + "," + rs.getString(5) + "," + rs.getString(6) + "," + rs.getString(7) + "," + rs.getString(8));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requests_arr;
		
	}


	public static void removeRequestForHodFromDB(String hodID, String lecturerID, String examID, String examDurrationToAdd) {
		
		// 1 - headofdepartment ID
		// 2 - lecturer ID
		// 3 - exam ID
		// 4 - exam Duration to Add
		
	    String query = "DELETE FROM headofdepartmentrequests "
	    			+ "WHERE HeadOfDepartmentID = ? AND lecturerID = ? AND examID = ? AND examDurationAdd = ?";
	    try {
	        if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, hodID);
	            ps.setString(2,lecturerID);
	            ps.setString(3, examID);
	            ps.setString(4, examDurrationToAdd);
	            ps.executeUpdate();
	        }
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static ArrayList<FinishedExam> getFinishedExamsByExamID(String examID) { // not approved yet
		
		String query = "SELECT * FROM finishedexam WHERE examID = ? AND approved = 0";
		ArrayList<FinishedExam> finishedexams_arr = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, examID);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	FinishedExam finishedExam = new FinishedExam(examID, rs.getString(4), rs.getString(2), rs.getDouble(5), rs.getString(3));
	                	finishedexams_arr.add(finishedExam);
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return finishedexams_arr;
	}

}

