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
	private String authorID;

	/**
	 * Constructs an Exam object with the specified parameters.
	 *
	 * @param examID the ID of the exam
	 * @param subjectID the ID of the subject
	 * @param subjectName the name of the subject
	 * @param courseID the ID of the course
	 * @param courseName the name of the course
	 * @param questions the list of questions in the exam
	 * @param commentsForLecturer the comments for the lecturer
	 * @param commentsForStudent the comments for the student
	 * @param duration the duration of the exam
	 * @param author the author of the exam
	 * @param code the code of the exam
	 * @param authorID the ID of the author
	 */
	public Exam(String examID, String subjectID, String subjectName, String courseID, String courseName, ArrayList<QuestionInExam> questions, 
			String commentsForLecturer, String commentsForStudent, int duration, String author, String code, String authorID) {
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
		this.authorID = authorID;
		if(examID == null) {
			examID = "";
		}
		else {
			this.examID = examID;
		}
	}

	/**
	 * Returns the ID of the exam.
	 *
	 * @return the ID of the exam
	 */
	public String getExamID() {
		return examID;
	}

	/**
	 * Sets the ID of the exam.
	 *
	 * @param examID the ID of the exam to set
	 */
	public void setExamID(String examID) {
		this.examID = examID;
	}

	/**
	 * Returns the list of questions in the exam.
	 *
	 * @return the list of questions in the exam
	 */
	public ArrayList<QuestionInExam> getQuestions() {
		return questions;
	}

	/**
	 * Returns the comments for the lecturer.
	 *
	 * @return the comments for the lecturer
	 */
	public String getCommentsForLecturer() {
		return commentsForLecturer;
	}

	/**
	 * Returns the comments for the student.
	 *
	 * @return the comments for the student
	 */
	public String getCommentsForStudent() {
		return commentsForStudent;
	}

	/**
	 * Returns the duration of the exam.
	 *
	 * @return the duration of the exam
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Returns the author of the exam.
	 *
	 * @return the author of the exam
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Returns the ID of the subject.
	 *
	 * @return the ID of the subject
	 */
	public String getSubjectID() {
		return subjectID;
	}

	/**
	 * Returns the ID of the course.
	 *
	 * @return the ID of the course
	 */
	public String getCourseID() {
		return courseID;
	}

	/**
	 * Sets the list of questions in the exam.
	 *
	 * @param questions the list of questions to set
	 */
	public void setQuestions(ArrayList<QuestionInExam> questions) {
		this.questions = questions;
	}

	/**
	 * Sets the comments for the lecturer.
	 *
	 * @param commentsForLecturer the comments for the lecturer to set
	 */
	public void setCommentsForLecturer(String commentsForLecturer) {
		this.commentsForLecturer = commentsForLecturer;
	}

	/**
	 * Sets the comments for the student.
	 *
	 * @param commentsForStudent the comments for the student to set
	 */
	public void setCommentsForStudent(String commentsForStudent) {
		this.commentsForStudent = commentsForStudent;
	}

	/**
	 * Sets the duration of the exam.
	 *
	 * @param duration the duration of the exam to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Sets the author of the exam.
	 *
	 * @param author the author of the exam to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Sets the ID of the subject.
	 *
	 * @param subjectID the ID of the subject to set
	 */
	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	/**
	 * Sets the ID of the course.
	 *
	 * @param courseID the ID of the course to set
	 */
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	/**
	 * Returns the name of the course.
	 *
	 * @return the name of the course
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the name of the course.
	 *
	 * @param courseName the name of the course to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Returns the name of the subject.
	 *
	 * @return the name of the subject
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Sets the name of the subject.
	 *
	 * @param subjectName the name of the subject to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * Returns the code of the exam.
	 *
	 * @return the code of the exam
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code of the exam.
	 *
	 * @param code the code of the exam to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns a string representation of the exam.
	 *
	 * @return a string representation of the exam
	 */
	@Override
	public String toString() {
		return "Exam{" +
				"questions=" + questions +
				", commentsForLecturer='" + commentsForLecturer + '\'' +
				", commentsForStudent='" + commentsForStudent + '\'' +
				", duration=" + duration +
				", author='" + author + '\'' +
				", subjectID='" + subjectID + '\'' +
				", subjectName='" + subjectName + '\'' +
				", courseID='" + courseID + '\'' +
				", courseName='" + courseName + '\'' +
				", examID='" + examID + '\'' +
				", code='" + code + '\'' +
				'}';
	}

	/**
	 * Returns the ID of the author.
	 *
	 * @return the ID of the author
	 */
	public String getAuthorID() {
		return authorID;
	}

	/**
	 * Sets the ID of the author.
	 *
	 * @param authorID the ID of the author to set
	 */
	public void setAuthorID(String authorID) {
		this.authorID = authorID;
	}
}
