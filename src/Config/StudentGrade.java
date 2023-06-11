package Config;

import java.io.Serializable;

public class StudentGrade implements Serializable {
    private String examID;
    private String course;
    private String subject;
    private String lecturer;
    private double grade;

    public StudentGrade(String examID, String course, String subject, String lecturer, double grade) {
        this.examID = examID;
        this.course = course;
        this.subject = subject;
        this.lecturer = lecturer;
        this.grade = grade;
    }

    public String getExamID() {
        return examID;
    }

    public String getCourse() {
        return course;
    }

    public String getSubject() {
        return subject;
    }

    public String getLecturer() {
        return lecturer;
    }

    public double getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "StudentGrade{" +
                "examID='" + examID + '\'' +
                ", course='" + course + '\'' +
                ", subject='" + subject + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", grade=" + grade +
                '}';
    }
}
