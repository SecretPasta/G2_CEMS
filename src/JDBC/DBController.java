package JDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Config.*;

public class DBController {

	/**

	 Retrieves all questions from the database that are created by a specific lecturer and/or have a specific course ID and/or subject ID.

	 @param lecturerID the ID of the lecturer (can be null if not filtering by lecturer)

	 @param courseID the ID of the course (can be null if not filtering by course)

	 @param subjectID the ID of the subject (can be null if not filtering by subject)

	 @return an ArrayList of Question objects matching the specified criteria
	 */
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
	                //System.out.println("myConn is NULL!");
	            }
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return questions;
	}




	/**
	 * Updates question data in the database based on the provided question ID.
	 *
	 * @param qArr An ArrayList containing the question data in the following order:
	 *             1. Question ID
	 *             2. Question text
	 *             3. Correct answer
	 *             4. Wrong answer 1
	 *             5. Wrong answer 2
	 *             6. Wrong answer 3
	 * @return A string indicating the success of the update operation.
	 */
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

	/**
	 * Checks if a user exists in the database and verifies the login credentials.
	 *
	 * @param userInfoArr An ArrayList containing user information in the following order:
	 *                    1. loginAs: Lecturer, Student, Head Of Department
	 *                    2. Username
	 *                    3. Password
	 * @return An ArrayList containing user details if the login is successful and the user is not already logged in.
	 *         If the login fails due to an incorrect password or username, an entry "UserEnteredWrongPasswordOrUsername" is added to the list.
	 *         If the user is already logged in, an entry "UserAlreadyLoggedIn" is added to the list.
	 *         If there is a problem with the sign-in process, an empty ArrayList is returned.
	 * @throws ClassNotFoundException If the required class for database connection is not found.
	 */
	public static ArrayList<String> userExist(ArrayList<String> userInfoArr) throws ClassNotFoundException {
		// 1 - loginAs: Lecturer, Student, Head Of Department
		// 2 - Username
		// 3 - Password
		ArrayList<String> userCannotConnect = new ArrayList<>(); // an array if there was a problem with sign in - wrond pass/username or user already exist
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement("SELECT * FROM " + userInfoArr.get(1) + " WHERE UserName =? AND Password =?");
	            ps.setString(1, userInfoArr.get(2));
	            ps.setString(2, userInfoArr.get(3));
	
	            ResultSet resultSet = ps.executeQuery();
	            if(resultSet.next()) {
	                	ArrayList<String> getUserDetails_arr = new ArrayList<>();
		    			for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
		    				getUserDetails_arr.add(resultSet.getString(i));
		    			}
	                	// check if the user is already logged
	                	if(resultSet.getInt("isLogged") == 0) {
	                		// userInfoArr.get(1) - loginAs, getUserDetails_arr.get(0) - user ID
	                		setUserIsLogin("1", userInfoArr.get(1), getUserDetails_arr.get(0));
	                		return getUserDetails_arr;
	                	}
	                	else {
	                		userCannotConnect.add("UserAlreadyLoggedIn");
	                		return userCannotConnect;
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



	/**

	 Sets the login status of a user in the database by their ID.
	 @param loggedInStatus The login status to be set (1 or 0).
	 @param loginAs The user role/table (Lecturer, Student, Head Of Department).
	 @param id The ID of the user. If "all" is provided, the operation is performed for all users in the table.

		set the user 'isLogin' in DB to 1 or 0 by his ID, loginAs = {Lecturer, Student, Head Of Department} - the table
	 	if id is "all", do for all users in table
	 */
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


	/**

	 Removes a question from the database based on the provided question ID.

	 @param questionID The ID of the question to be removed.

	 @return {@code true} if the question is successfully removed, {@code false} otherwise.
	 */
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

	/**

	 Sets the "isLogged" status of all users in the database to 0 (not logged in).
	 Used when connecting to the server for a reset of all users
	 */
	public static void setAllUsersNotIsLogged() {
		setUserIsLogin("0", "lecturer", "all");
		setUserIsLogin("0", "student", "all");
		setUserIsLogin("0", "headofdepartment", "all");
	}

	/**

	 Retrieves the mapping of subjects and their corresponding courses for a given lecturer.

	 @param lecturerID The ID of the lecturer for whom the subjects and courses are to be retrieved.

	 @return A map containing subjects as keys and a list of courses as values.
	 */
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
		
		return lecDepartmentCoursesMap;
	}

	/**

	 Retrieves the maximum question ID for a given subject.

	 @param subjectID The ID of the subject for which the maximum question ID is to be retrieved.

	 @return The maximum question ID as a string, or null if no result is found.
	 */
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

	/**

	 Adds a new question to the database.

	 @param question The question object to be added.
	 */
	public static void addNewQuestion(Question question) {
		String query = "INSERT INTO question (id, subjectID, questionText, questionNumber, answerCorrect, answerWrong1"
				+ ", answerWrong2, answerWrong3, lecturer, lecturerID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

	/**

	 Updates the maximum question number for a given subject.

	 @param subjectID The ID of the subject for which the maximum question number is to be updated.

	 @param i The amount by which to increment the maximum question number.
	 */
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

	/**

	 Retrieves the names and IDs of all subjects.

	 @return A map containing subject IDs as keys and subject names as values.
	 */
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

	/**

	 Retrieves the names and IDs of all courses.

	 @return A map containing course IDs as keys and course names as values.
	 */
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

	/**

	 Retrieves the maximum exam ID for a given course and updates the course's maximum exam number.

	 @param courseID The ID of the course for which the maximum exam ID is to be retrieved.

	 @return The maximum exam ID as a string, or null if no result is found.
	 */
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

	/**

	 Updates the maximum exam number for a given course.

	 @param courseID The ID of the course for which the maximum exam number is to be updated.

	 @param maxExamNumber_temp The current maximum exam number for the course.

	 @param i The amount by which to increment the maximum exam number.
	 */
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




	/**

	 Adds a new exam to the database.

	 @param exam The exam object to be added.
	 */
	public static void addNewExam(Exam exam) {

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

	/**

	 Adds new questions to an exam in the database.

	 @param questionsList The list of QuestionInExam objects to be added.
	 */
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

	/**

	 Retrieves exams based on their activeness and author ID.

	 @param active The activeness status of the exams to be retrieved ("0" for inactive, "1" for active).

	 @param authorID The ID of the author of the exams. Can be null to retrieve exams from all authors.

	 @return An ArrayList of Exam objects that match the specified criteria.
	 */
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

	/**

	 Retrieves the questions in an exam based on the exam ID.

	 @param examID The ID of the exam for which the questions are to be retrieved.

	 @return An ArrayList of QuestionInExam objects that belong to the specified exam.
	 */
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


	/**

	 Retrieves a question in an exam based on the question ID and exam ID.

	 @param questionID The ID of the question to be retrieved.

	 @param examID The ID of the exam to which the question belongs.

	 @return A QuestionInExam object that matches the specified question ID and exam ID.
	 */
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


	/**

	 Changes the activeness of an exam by its ID.

	 @param examID The ID of the exam for which the activeness is to be changed.

	 @param activenessChangeTo The value to which the activeness is to be changed ("0" for inactive, "1" for active).
	 */
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



	/**

	 Retrieves the head of departments relevant to a lecturer based on the lecturer ID.

	 @param lecturerID The ID of the lecturer.

	 @return An ArrayList of HeadOfDepartment objects containing the ID and name of the relevant head of departments.
	 */
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

	/**
	 * Saves the request received from the lecturer in the database.
	 *
	 * @param requestInfo_arr An ArrayList containing the request information in the following order:
	 *                        1. Exam ID
	 *                        2. Subject name
	 *                        3. Course name
	 *                        4. Lecturer ID
	 *                        5. Lecturer name
	 *                        6. Lecturer's explanation
	 *                        7. Duration to add to exam
	 *                        8. Head of Department ID
	 */
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

	/**

	 Saves a finished exam to the database.
	 @param finishedExam The FinishedExam object to be saved.
	 @return true if the exam is successfully saved, false otherwise.
	 */
	public static boolean saveFinishedExamToDB(FinishedExam finishedExam) {
		String query = "INSERT INTO finishedexam (examID, studentID, answers, lecturer, grade, approved, checkExam) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			if (mysqlConnection.getConnection() != null) {
				PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
				ps.setString(1, finishedExam.getExamID());
				ps.setString(2, finishedExam.getStudentID());
				ps.setString(3, finishedExam.getAnswers());
				ps.setString(4, finishedExam.getAuthor());
				ps.setDouble(5, finishedExam.getGrade());
				ps.setInt(6, finishedExam.getApproved());
				ps.setInt(7, finishedExam.getCheckExam());

				ps.executeUpdate();
			}
		} catch (SQLException | ClassNotFoundException e) {
			return false;
		}
		return true;
	}


	/**

	 Retrieves all grades for a student based on the student ID.
	 @param studentID The ID of the student.
	 @return An ArrayList of FinishedExam objects containing the exam ID, course name, subject name, lecturer, and grade for each exam.
	 */
	public static ArrayList<FinishedExam> getAllStudentGradesById(String studentID) {
		ArrayList<FinishedExam> grades = new ArrayList<>();
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
					grades.add(new FinishedExam(examID, lecturer, studentID, grade, null, subject, course));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		//System.out.println(grades);
		return grades;
	}

	/**

	 Retrieves requests for a head of department based on the head of department ID.

	 @param hodID The ID of the head of department.

	 @return An ArrayList of strings representing the requests, each string containing the request information separated by commas.
	 */
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

	/**

	 Removes a request for a head of department from the database.

	 @param hodID The ID of the head of department.

	 @param lecturerID The ID of the lecturer who sent the request.

	 @param examID The ID of the exam in the request.

	 @param examDurrationToAdd The duration to add to the exam in the request.
	 */
	public static void removeRequestForHodFromDB(String hodID, String lecturerID, String examID, String examDurrationToAdd) {
		
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

	/**

	 Retrieves finished exams by exam ID that have not been approved yet.
	 @param examID The ID of the exam.
	 @return An ArrayList of FinishedExam objects representing the finished exams that match the exam ID and have not been approved.
	 */
	public static ArrayList<FinishedExam> getFinishedExamsByExamID(String examID) { // not approved yet
		
		String query = "SELECT * FROM finishedexam WHERE examID = ? AND approved = 0";
		ArrayList<FinishedExam> finishedexams_arr = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, examID);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	FinishedExam finishedExam = new FinishedExam(examID, rs.getString(4), rs.getString(2), rs.getDouble(5), rs.getString(3), null, null);
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

	/**

	 Sets the approval status and grade for a finished exam.

	 @param examID The ID of the exam.

	 @param studentID The ID of the student who took the exam.

	 @param grade The grade to be assigned to the exam.
	 */
	public static void setFinishedExamApproved(String examID, String studentID, String grade) {
		
		String query = "UPDATE finishedexam "
	             + "SET approved = 1, grade = ? "
	             + "WHERE examID = ? AND studentID = ?";
		try {
			if (mysqlConnection.getConnection() != null) {
				PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);		
				ps.setDouble(1, Double.parseDouble(grade));
				ps.setString(2, examID);
				ps.setString(3, studentID);
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

	/**

	 Retrieves finished exams information by author ID for statistics.
	 @param authorID The ID of the author.
	 @return An ArrayList of FinishedExam objects representing the finished exams information for statistics.
	 */
	public static ArrayList<FinishedExam> getFinishedExamsInfoByAuthorID(String authorID){ // get finished exams info for statics
		
		String query = "SELECT E.ID, FE.grade, E.subjectID, E.courseID FROM finishedexam FE "
	             + "JOIN exams E ON E.ID = FE.examID "
	             + "WHERE E.authorID = ? AND FE.checkExam = 1 AND E.isActive = ?";
		
		ArrayList<FinishedExam> examInfo_arr = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, authorID);
	            ps.setString(2, "2");
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	examInfo_arr.add(new FinishedExam(rs.getString(1), null, null, rs.getDouble(2), null, rs.getString(3), rs.getString(4)));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return examInfo_arr;	
	}

	/**

	 Saves exam statistics to the database.

	 @param examID The ID of the exam.

	 @param actualDuration The actual duration of the exam.

	 @param totalStudents The total number of students who took the exam.

	 @param completedStudents The number of students who completed the exam.

	 @param incompletedStudents The number of students who did not complete the exam.
	 */
	public static void saveExamStatisticsToDB(String examID, String actualDuration, int totalStudents, int completedStudents, int incompletedStudents) {
		try {
			if (mysqlConnection.getConnection() != null) {
				// Retrieve the duration from the exams table
				String durationQuery = "SELECT duration FROM exams WHERE ID = ?";
				PreparedStatement durationPs = mysqlConnection.getConnection().prepareStatement(durationQuery);
				durationPs.setString(1, examID);
				ResultSet durationResult = durationPs.executeQuery();
				int duration = 0;
				if (durationResult.next()) {
					duration = durationResult.getInt("duration");
				}

				// Insert values into examparticipation table
				String insertQuery = "INSERT INTO examparticipation (examID, date, duration, actualDuration, totalStudents, completedStudents, incompletedStudents) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insertPs = mysqlConnection.getConnection().prepareStatement(insertQuery);
				insertPs.setString(1, examID);

				// Format the date as DD/MM/YYYY
				LocalDate currentDate = LocalDate.now();
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String formattedDate = currentDate.format(dateFormatter);

				insertPs.setString(2, formattedDate);
				insertPs.setInt(3, duration);
				insertPs.setInt(4, Integer.parseInt(actualDuration));
				insertPs.setInt(5, totalStudents);
				insertPs.setInt(6, completedStudents);
				insertPs.setInt(7, incompletedStudents);
				insertPs.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}




	/**

	 Retrieves the statistics of an exam by its ID.
	 @param examID The ID of the exam.
	 @return An ArrayList of strings representing the exam statistics. The first element is the total number of students,
	 the second element is the number of completed students, and the third element is the number of incompleted students.
	 */
	public static ArrayList<String> getStatisticsOfExamByID(String examID) {

		String query = "SELECT totalStudents, completedStudents, incompletedStudents FROM examparticipation "
	             + "WHERE examID = ?";
		
		ArrayList<String> examStats_arr = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, examID);
	            try (ResultSet rs = ps.executeQuery()) {
	                if(rs.next()) {
	                	examStats_arr.add(String.valueOf(rs.getInt(1)));
	                	examStats_arr.add(String.valueOf(rs.getInt(2)));
	                	examStats_arr.add(String.valueOf(rs.getInt(3)));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return examStats_arr;	
	}

	/**

	 Retrieves all students associated with a Head of Department (HOD) by HOD ID.

	 @param hodID The ID of the Head of Department.

	 @return An ArrayList of strings representing the students. Each string contains the student's name and ID in the format "Name - ID".
	 */
	public static ArrayList<String> getAllStudentsOfHod(String HodID) {

		String query = "SELECT S.Name, S.studentID FROM student S "
				+ "JOIN headofdepartment H ON S.DepartmentID = H.DepartmentID "
				+ "WHERE H.HeadOfDepartmentID = ?";
		
		ArrayList<String> studentsID_arr = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, HodID);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	studentsID_arr.add(rs.getString(1) + " - " + rs.getString(2));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentsID_arr;	
	}

	/**

	 Retrieves all courses associated with a Head of Department (HOD) by HOD ID.
	 @param HodID The ID of the Head of Department.
	 @return An ArrayList of strings representing the courses. Each string contains the course's name and ID in the format "Name - ID".
	 */
	public static ArrayList<String> getAllCoursesOfHod(String HodID) {

		String query = "SELECT C.Name, C.CourseID "
				+ "FROM course C "
				+ "JOIN coursedepartment CD ON CD.CourseID = C.CourseID "
				+ "JOIN headofdepartment H ON CD.DepartmentID = H.DepartmentID "
				+ "WHERE H.HeadOfDepartmentID = ?";
		
		ArrayList<String> coursesID_arr = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, HodID);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	coursesID_arr.add(rs.getString(1) + " - " + rs.getString(2));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coursesID_arr;	
	}

	/**

	 Retrieves all lecturers associated with a Head of Department (HOD) by HOD ID.
	 @param HodID The ID of the Head of Department.
	 @return An ArrayList of strings representing the lecturers. Each string contains the lecturer's name and ID in the format "Name - ID".
	 */
	public static ArrayList<String> getAllLecturersOfHod(String HodID) {

		String query = "SELECT L.Name, L.LecturerID "
				+ "FROM lecturer L "
				+ "JOIN lecturerdepartment LD ON LD.LecturerID = L.LecturerID "
				+ "JOIN headofdepartment H ON LD.DepartmentID = H.DepartmentID "
				+ "WHERE H.HeadOfDepartmentID = ?";
		
		ArrayList<String> lecturerID_arr = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, HodID);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	lecturerID_arr.add(rs.getString(1) + " - " + rs.getString(2));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lecturerID_arr;	
	}

	/**

	 Retrieves all grades of a student by student ID.

	 @param studentID The ID of the student.

	 @return An ArrayList of strings representing the grades of the student.
	 */
	public static ArrayList<String> getAllStudentGrades(String studentID) {

		String query = "SELECT grade "
				+ "FROM finishedexam "
				+ "WHERE studentID = ? AND checkExam = 1";
		
		ArrayList<String> studentGrades_arr = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, studentID);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	studentGrades_arr.add(Double.toString(rs.getDouble(1)));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentGrades_arr;	
	}

	/**

	 Retrieves all grades of a lecturer's exams by lecturer ID.
	 @param lecturerID The ID of the lecturer.
	 @return An ArrayList of strings representing the grades of the lecturer's exams.
	 */
	public static ArrayList<String> getAllLecturerGrades(String lecturerID) {
		
		String query = "SELECT FE.grade FROM finishedexam FE "
				+ "JOIN exams E ON E.ID = FE.examID "
				+ "WHERE E.isActive = ? AND FE.checkExam = 1 AND E.authorID = ?";
		
		ArrayList<String> lecturerGrades_arr = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, "2");
	            ps.setString(2, lecturerID);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	lecturerGrades_arr.add(Double.toString(rs.getDouble(1)));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lecturerGrades_arr;
	}

	/**

	 Retrieves all grades of exams in a course by course ID.
	 @param courseID The ID of the course.
	 @return An ArrayList of strings representing the grades of exams in the course.
	 */
	public static ArrayList<String> getAllCourseGrades(String courseID) {
		
		String query = "SELECT FE.grade FROM finishedexam FE "
				+ "JOIN exams E ON E.ID = FE.examID "
				+ "WHERE E.isActive = ? AND FE.checkExam = 1 AND E.courseID = ?";
		
		ArrayList<String> courseGrades_arr = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, "2");
	            ps.setString(2, courseID);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	courseGrades_arr.add(Double.toString(rs.getDouble(1)));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseGrades_arr;
	}


	/**

	 Imports users' data from the external_users table and inserts it into the corresponding user tables.

	 Prints the number of successfully imported users and the number of users that failed to import.
	 */
	public static void importUsersData() {
		
		int importSucceedCnt = 0;
		int importFailedCnt = 0;
		
		try {
            PreparedStatement ps1 = mysqlConnection.conn.prepareStatement("SELECT * FROM external_users");
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
            	String role = rs.getString(8);
                User user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));

                try {
                	String query = null;
                	String departmentName = null;
                	String departmentID = null;
                	String roleID = null;

                    if(role.equals("HeadOfDepartment") || role.equals("Student")) {
                    	departmentName = rs.getString(6);
                    	departmentID = rs.getString(7);
                    	roleID = role.toLowerCase() + "ID";
                    	query = "INSERT INTO " + role + " ( " + roleID + ", `UserName`, `Password`, `Name`, `Email`, `Department`, `DepartmentID`,`isLogged`) VALUES (?, ?, ?, ?, ?, ?, ?, 0);";
                        ps1 = mysqlConnection.conn.prepareStatement(query);
                        ps1.setString(1, user.getId());
                        ps1.setString(2, user.getUsername());
                        ps1.setString(3, user.getPassword());
                        ps1.setString(4, user.getName());
                        ps1.setString(5, user.getEmail());
                        ps1.setString(6, departmentName);
                        ps1.setString(7, departmentID);
                        ps1.executeUpdate();
                    }
                    else if(role.equals("Lecturer")) {
                    	roleID = role.toLowerCase() + "ID";
                    	query = "INSERT INTO " + role + " ( " + roleID + ", `UserName`, `Password`, `Name`, `Email`,`isLogged`) VALUES (?, ?, ?, ?, ?, 0);";
                        ps1 = mysqlConnection.conn.prepareStatement(query);
                        ps1.setString(1, user.getId());
                        ps1.setString(2, user.getUsername());
                        ps1.setString(3, user.getPassword());
                        ps1.setString(4, user.getName());
                        ps1.setString(5, user.getEmail());
                        ps1.executeUpdate();
                    }
                    importSucceedCnt ++;
                }
                catch (Exception e2) {
                	importFailedCnt ++;
                }
            }
        }catch (SQLException e) {
        	System.out.println("no external_users found");
        }
        System.out.println("imported: " + importSucceedCnt + "\nfailed (already exist): " + importFailedCnt);
	}

	/**

	 Retrieves manual exams by their activeness and author ID.

	 @param active The activeness status of the exams.

	 @param authorID The ID of the author.

	 @return An ArrayList of Exam objects representing the manual exams that match the activeness and author ID.
	 */
	public static ArrayList<Exam> getManualExamsByActiveness(String active, String authorID) {
		String query;
		ArrayList<Exam> manualExamsArr = new ArrayList<>();
		PreparedStatement ps = null;
		try {
			if (mysqlConnection.getConnection() != null) {
				if (authorID == null) {
					query = "SELECT me.examId, me.subjectID, me.courseID, me.filename, me.commentsStudent, me.commentsLecturer, me.duration, me.code, me.author, me.authorID, me.isActive, c.Name AS courseName, s.Name AS subjectName " +
							"FROM manualexams AS me " +
							"JOIN course AS c ON me.courseID = c.CourseID " +
							"JOIN subjects AS s ON me.subjectID = s.SubjectID " +
							"WHERE me.isActive = ?";

					ps = mysqlConnection.getConnection().prepareStatement(query);
					ps.setString(1, active);
				} else {
					query = "SELECT me.examId, me.subjectID, me.courseID, me.filename, me.commentsStudent, me.commentsLecturer, me.duration, me.code, me.author, me.authorID, me.isActive, c.Name AS courseName, s.Name AS subjectName " +
							"FROM manualexams AS me " +
							"JOIN course AS c ON me.courseID = c.CourseID " +
							"JOIN subjects AS s ON me.subjectID = s.SubjectID " +
							"WHERE me.isActive = ? AND me.authorID = ?";

					ps = mysqlConnection.getConnection().prepareStatement(query);
					ps.setString(1, active);
					ps.setString(2, authorID);
				}
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						String examId = rs.getString("examId");
						String subjectID = rs.getString("subjectID");
						String courseID = rs.getString("courseID");
						String filename = rs.getString("filename");
						//String file = rs.getString("file");
						String commentsStudent = rs.getString("commentsStudent");
						String commentsLecturer = rs.getString("commentsLecturer");
						int duration = rs.getInt("duration");
						String code = rs.getString("code");
						String author = rs.getString("author");
						//String authorID = rs.getString("authorID");
						String isActive = rs.getString("isActive");
						String courseName = rs.getString("courseName");
						String subjectName = rs.getString("subjectName");

						Exam exam = new Exam(examId, subjectID, subjectName, courseID, courseName, null, commentsLecturer,
								commentsStudent, duration, author, code, authorID);

						manualExamsArr.add(exam);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return manualExamsArr;
	}

	/**

	 Retrieves the file path of a manual exam by its ID.

	 @param examID The ID of the exam.

	 @return A string representing the file path of the manual exam.
	 */
	public static String getManualExamPath(String examID) {
		String query = "SELECT filename, filePath FROM manualexams WHERE examId = ?";
		String examPath = null;

		try {
			if (mysqlConnection.getConnection() != null) {
				PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
				ps.setString(1, examID);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						String filename = rs.getString("filename");
						String filePath = rs.getString("filePath");
						examPath = filePath + "/" + filename;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return examPath;
	}

	/**

	 Retrieves general information questions for a Head of Department (HOD) by HOD ID.
	 @param HodID The ID of the Head of Department.
	 @return An ArrayList of strings representing the general information questions for the HOD. Each string contains the question ID,
	 question text, and the four answer options.
	 */
	public static ArrayList<String> GeneralInformationQuestions(String HODid) {
		String query = "SELECT Q.id, Q.questionText, Q.answerCorrect, Q.answerWrong1, Q.answerWrong2, Q.answerWrong3 FROM question Q "
				+ "JOIN questioncourse QC ON Q.id = QC.questionID "
				+ "JOIN coursedepartment CD ON QC.courseID = CD.courseID "
				+ "JOIN headofdepartment HOD ON HOD.HeadOfDepartmentID = ? "
				+ "WHERE CD.DepartmentID = HOD.DepartmentID";
		
		ArrayList<String> HODQuestions = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, HODid);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	HODQuestions.add(rs.getString(1) + " - " + rs.getString(2) + "\n\t 1) " + rs.getString(3) + "\n\t 2) " + rs.getString(4)
	                	+ "\n\t 3) " + rs.getString(5) + "\n\t 4) " + rs.getString(6));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return HODQuestions;
	}

	/**

	 Retrieves general information exams for a Head of Department (HOD) by HOD ID.
	 @param HodID The ID of the Head of Department.
	 @return An ArrayList of strings representing the general information exams for the HOD. Each string contains the exam ID, code, duration,
	 and the status of the exam. It also includes the list of questions in the exam.
	 */
	public static ArrayList<String> GeneralInformationExams(String HODid) {
		
		ArrayList<QuestionInExam> questions = new ArrayList<>();// get all questions from an exam
		ArrayList<String> questionsText = new ArrayList<>(); // get all questions texts from an exam
		
		
		String query = "SELECT E.ID, E.code, E.duration, E.isActive "
			    + "FROM exams E "
			    + "JOIN coursedepartment CD ON E.CourseID = CD.CourseID "
			    + "JOIN headofdepartment HOD ON HOD.HeadOfDepartmentID = ? "
			    + "WHERE CD.DepartmentID = HOD.DepartmentID ";
		
		ArrayList<String> HODExams = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, HODid);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	questions = retrieveQuestionsInExamById(rs.getString(1));
	                	for(int i = 0; i < questions.size(); i++) {
	                		questionsText.add(questions.get(i).getQuestionText());
	                	}
	                	HODExams.add(rs.getString(1) + " - Code: " + rs.getString(2) + " - Duration (in minutes): " + rs.getString(3) + " - " + 
	                "IsActive (0-no, 1-yes, 2-finished): " + rs.getString(4) + "\nQuestions:\n- " + String.join("\n- " , questionsText));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return HODExams;
	}

	/**

	 Retrieves general information subjects for a Head of Department (HOD) by HOD ID.
	 @param HodID The ID of the Head of Department.
	 @return An ArrayList of strings representing the general information subjects for the HOD. Each string contains the subject ID and name.
	 */
	public static ArrayList<String> GeneralInformationSubjects(String HODid) {
		String query = "SELECT DISTINCT S.SubjectID, S.Name FROM subjects S "
				+ "JOIN coursesubject CS ON S.SubjectID = CS.SubjectID "
				+ "JOIN coursedepartment CD ON CS.CourseID = CD.CourseID "
				+ "JOIN headofdepartment HOD ON HOD.HeadOfDepartmentID = ? "
				+ "WHERE CD.DepartmentID = HOD.DepartmentID";
		
		ArrayList<String> HODSubjectss = new ArrayList<>();
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, HODid);
	            try (ResultSet rs = ps.executeQuery()) {
	                while(rs.next()) {
	                	HODSubjectss.add(rs.getString(1) + " - " + rs.getString(2));
	                }
	            }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return HODSubjectss;

	}

	/**

	 Retrieves the email of a student by student ID.
	 @param studentID The ID of the student.
	 @return A string representing the email of the student.
	 */
	public static String getStudentEmailByID(String studentID) {
		
		String query = "SELECT Email FROM student WHERE StudentID = ?";
		try {
			if (mysqlConnection.getConnection() != null) {
	            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(query);
	            ps.setString(1, studentID);
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
	


}

