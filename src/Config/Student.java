package Config;

import java.util.ArrayList;
import java.util.Arrays;

public class Student extends User{

    private ArrayList<String> courses;

    /**

     Constructs a new Student object with the specified attributes.
     @param id The ID of the student.
     @param username The username of the student.
     @param password The password of the student.
     @param name The name of the student.
     @param email The email of the student.
     @param courses The courses of the student, separated by commas.
     */
    public Student(String id, String username, String password, String name, String email,String courses) {
        super(id, username, password, name, email);
        this.courses = new ArrayList<>(Arrays.asList(courses.split(String.valueOf(','))));
    }

    /**

     Returns the list of courses associated with the question.
     @return The list of courses.
     */
    public ArrayList<String> getCourses(){
        return courses;
    }
}
