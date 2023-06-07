package Config;

public class FinishedExam{

    private String examID;
    private String studentID;
    private int grade;
    private int approved;
    private boolean checkExam;

    public FinishedExam(String examID,String studentID, int grade){
        this.examID = examID;
        this.studentID = studentID;
        this.grade = grade;
        this.approved = 0;
        this.checkExam = false;
    }
    public String getExamID() {
        return examID;
    }

    public String getStudentID() {
        return studentID;
    }

    public int getGrade() {
        return grade;
    }

    public void approveGrade(){
        this.grade = 1;
    }
    public void CheckExam(){
        this.checkExam = true;
    }
}
