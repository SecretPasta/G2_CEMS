package Config;

public class FinishedExam{

    private String examID;
    private String studentID;
    private String answers;

    public void FinishedExam(String examID,String studentID, String grade){
        this.examID = examID;
        this.studentID = studentID;
        this.answers = grade;
    }
    public String getExamID() {
        return examID;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getAnswers() {
        return answers;
    }
}
