package Config;

import java.util.ArrayList;
import java.util.Arrays;

public class Student extends User{

    private ArrayList<String> courses;
    public Student(String id, String username, String password, String name, String email,String courses) {
        super(id, username, password, name, email);
        this.courses = new ArrayList<>(Arrays.asList(courses.split(String.valueOf(','))));
    }

    public ArrayList<String> getCourses(){
        return courses;
    }
}
