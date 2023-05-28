package Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lecturer extends User{
	
	private Map<String, ArrayList<String>> subjectsCoursesMap = new HashMap<>(); // map for subjects and courses for the lecturer	

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
    
}
