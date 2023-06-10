package Config;

public class FinishedExam{

    private String examID;
    private String studentID;
    private int grade;
    private int approved;
    private int checkExam;
    private String answers;

    public FinishedExam(String examID,String studentID, int grade,String answers){
        this.examID = examID;
        this.studentID = studentID;
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

    public int getGrade() {
        return grade;
    }

    public void approveGrade(){
        this.grade = 1;
    }
    public void CheckExam(){
        this.checkExam = 1;
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
}
