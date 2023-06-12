package Config;

import java.io.Serializable;

public class FinishedExam implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String examID;
    private String studentID;
    private String lecturer;
    private double grade;
    private int approved;
    private int checkExam;
    private String answers;

    public FinishedExam(String examID,String lecturer ,String studentID, double grade, String answers){
        this.examID = examID;
        this.studentID = studentID;
        this.lecturer = lecturer;
        this.grade = grade;
        this.answers = answers;
        this.approved = 0;
        this.checkExam = 0;
    }
    public String getExamID() {
        return examID;
    }

    public String getStudentID() {
        return studentID;
    }

    public double getGrade() {
        return grade;
    }

    public int getApproved() {
        return approved;
    }

    public int getCheckExam() {
        return checkExam;
    }

    public String getAnswers() {
        return answers;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void approveGrade(){
        this.grade = 1;
    }

    public void checkExam(){
        this.checkExam = 1;
    }
    @Override
    public String toString() {
        return "FinishedExam{" +
                "examID='" + examID + '\'' +
                ", studentID='" + studentID + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", grade=" + grade +
                ", approved=" + approved +
                ", checkExam=" + checkExam +
                ", answers='" + answers + '\'' +
                '}';
    }
}
