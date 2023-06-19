package Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lecturer extends User{
	
	private Map<String, ArrayList<String>> subjectsCoursesMap = new HashMap<>(); // map for subjects and courses for the lecturer	
	private ArrayList<FinishedExam> studentGrades;

	/**
	 * Constructs a new Lecturer object with the specified attribute values.
	 *
	 * @param id       the ID of the lecturer
	 * @param username the username of the lecturer
	 * @param password the password of the lecturer
	 * @param name     the name of the lecturer
	 * @param email    the email of the lecturer
	 */
    // Constructor to initialize the Lecturer object with attribute values
    public Lecturer(String id, String username, String password, String name, String email) {
    	super(id, username, password, name, email);
    }
    
    /**
     * Retrieves the map of subjects and courses for the lecturer.
     * @return The map containing subjects and their corresponding courses.
     */
    public Map<String, ArrayList<String>> getLecturerSubjectsAndCourses(){
		return subjectsCoursesMap;
    }
    
    /**
     * Sets the map of subjects and courses for the lecturer.
     * @param subjectsCoursesMap The map containing subjects and their corresponding courses.
     */
    public void setLecturerSubjectsAndCourses(Map<String, ArrayList<String>> subjectsCoursesMap){
    	this.subjectsCoursesMap = subjectsCoursesMap;	 	
    }

	/**
	 * Sets the ArrayList of student grades.
	 *
	 * @param studentGrades the ArrayList of student grades to set
	 */
	public void setStudentsGrades(ArrayList<FinishedExam> studentGrades) {
		this.studentGrades = studentGrades;
	}

	/**
	 * Returns the ArrayList of student grades.
	 *
	 * @return the ArrayList of student grades
	 */
	public ArrayList<FinishedExam> getStudentsGrades() {
		return studentGrades;
	}
    
}
