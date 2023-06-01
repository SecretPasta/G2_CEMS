package Config;
import java.util.ArrayList;

public class Exam {
	
	private ArrayList<QuestionInExam> questions = new ArrayList<>();
	private String commentsForLecturer;
	private String commentsForStudent;
	private int duration;
	private String author;
	private String subjectID;
	private String courseID;
	private String examID;
	
	public Exam(String subjectID,String courseID, ArrayList<QuestionInExam> questions, 
			String commentsForLecturer, String commentsForStudent, int duration, String author) {
		this.subjectID = subjectID;
		this.courseID = courseID;
		this.questions = questions;
		this.commentsForLecturer = commentsForLecturer;
		this.commentsForStudent = commentsForStudent;
		this.duration = duration;
		this.author = author;
		examID = "";
	}

	public String getExamID() {
		return examID;
	}

	public void setExamID(String examID) {
		this.examID = examID;
	}

	public ArrayList<QuestionInExam> getQuestions() {
		return questions;
	}

	public String getCommentsForLecturer() {
		return commentsForLecturer;
	}

	public String getCommentsForStudent() {
		return commentsForStudent;
	}

	public int getDuration() {
		return duration;
	}

	public String getAuthor() {
		return author;
	}

	public String getSubjectID() {
		return subjectID;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setQuestions(ArrayList<QuestionInExam> questions) {
		this.questions = questions;
	}

	public void setCommentsForLecturer(String commentsForLecturer) {
		this.commentsForLecturer = commentsForLecturer;
	}

	public void setCommentsForStudent(String commentsForStudent) {
		this.commentsForStudent = commentsForStudent;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	

}
