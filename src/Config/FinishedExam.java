package Config;

import java.io.Serializable;

public class FinishedExam extends Exam implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String studentID;
    private double grade;
    private int approved;
    private int checkExam;
    private String answers;


    /**
     * Constructs a FinishedExam object with the specified parameters.
     *
     * @param examID the ID of the exam
     * @param author the author of the exam
     * @param studentID the ID of the student who took the exam
     * @param grade the grade obtained by the student
     * @param answers the answers provided by the student
     * @param subjectName the name of the subject
     * @param courseName the name of the course
     */
    public FinishedExam(String examID,String author ,String studentID, double grade, String answers, String subjectName, String courseName){
    	super(examID, null, subjectName, null, courseName, null, null, null, 0, author, null, null);
        this.studentID = studentID;
        this.grade = grade;
        this.answers = answers;
        this.approved = 0;
        this.checkExam = 0;
    }

    /**
     * Gets the ID of the student who took the exam.
     *
     * @return the student ID
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Gets the grade obtained by the student.
     *
     * @return the grade
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade obtained by the student.
     *
     * @param grade the grade to set
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Gets the approval status of the exam.
     *
     * @return the approval status
     */
    public int getApproved() {
        return approved;
    }

    /**
     * Gets the check exam status.
     *
     * @return the check exam status
     */
    public int getCheckExam() {
        return checkExam;
    }

    /**
     * Gets the answers provided by the student.
     *
     * @return the answers
     */
    public String getAnswers() {
        return answers;
    }

    /**
     * Approves the grade of the exam.
     */
    public void approveGrade() {
        this.grade = 1;
    }

    /**
     * Sets the check exam status.
     */
    public void checkExam() {
        this.checkExam = 1;
    }

    /**
     * Returns a string representation of the FinishedExam object.
     *
     * @return a string representation of the FinishedExam object
     */
    @Override
    public String toString() {
        return "FinishedExam{" +
                "examID='" + getExamID() + '\'' +
                ", studentID='" + studentID + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", grade=" + grade +
                ", approved=" + approved +
                ", checkExam=" + checkExam +
                ", answers='" + answers + '\'' +
                '}';
    }
}
