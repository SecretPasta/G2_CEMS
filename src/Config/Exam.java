package Config;
import java.io.Serializable;
import java.util.ArrayList;

public class Exam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<QuestionInExam> questions = new ArrayList<>();
	private String commentsForLecturer;
	private String commentsForStudent;
	private int duration;
	private String author;
	private String subjectID;
	private String subjectName;
	private String courseID;
	private String courseName;
	private String examID;
	private String code;
	
	public Exam(String examID, String subjectID, String subjectName, String courseID, String courseName, ArrayList<QuestionInExam> questions, 
			String commentsForLecturer, String commentsForStudent, int duration, String author, String code) {
		this.subjectID = subjectID;
		this.subjectName = subjectName;
		this.courseID = courseID;
		this.courseName = courseName;
		this.questions = questions;
		this.commentsForLecturer = commentsForLecturer;
		this.commentsForStudent = commentsForStudent;
		this.duration = duration;
		this.author = author;
		this.code = code;
		if(examID == null) {
			examID = "";
		}
		else {
			this.examID = examID;
		}
	}

	public String getExamID() {
		return examID;
	}

	public void setExamID(String examID) {
		this.examID = subjectID + courseID + examID;
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

}
