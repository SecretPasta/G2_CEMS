package Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lecturer extends User{
	
	private Map<String, ArrayList<String>> subjectsCoursesMap = new HashMap<>(); // map for subjects and courses for the lecturer
	
	private Map<String, String> subjects_Name_ID = new HashMap<>();

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
    
    public void setSubjectsIdByNames(Map<String, String> subjects_map_id_name) { 	
    	subjects_Name_ID = subjects_map_id_name;
    }
    
    public String getSubjectIdByName(String subjectName) { 	
		return subjects_Name_ID.get(subjectName);
    }
}
