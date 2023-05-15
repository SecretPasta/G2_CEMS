package Config;

import java.io.Serializable;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class Question implements Callback<TableView<Question>, TableRow<Question>>, Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String subject;
	private String courseName;
	private String questionText;
	private String questionNumber;
	private String lecturer;

	/**
	 * @param question id
	 * @param question subject
	 * @param question course name
	 * @param question text
	 * @param question number
	 * @param question author
	 */
	public Question(String id, String subject, String courseName, String questionText, String questionNumber,
			String lecturer) {
		super();
		this.id = id;
		this.subject = subject;
		this.courseName = courseName;
		this.questionText = questionText;
		this.questionNumber = questionNumber;
		this.lecturer = lecturer;
	}

	public String getId() {
		return id; // Retrieve the ID of the question
	}

	public void setId(String id) {
		this.id = id; // Set the ID of the question
	}

	public String getSubject() {
		return subject; // Retrieve the subject of the question
	}

	public void setSubject(String subject) {
		this.subject = subject; // Set the subject of the question
	}

	public String getCourseName() {
		return courseName; // Retrieve the course name associated with the question
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName; // Set the course name associated with the question
	}

	public String getQuestionText() {
		return questionText; // Retrieve the text of the question
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText; // Set the text of the question
	}

	public String getQuestionNumber() {
		return questionNumber; // Retrieve the question number
	}

	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber; // Set the question number
	}

	public String getLecturer() {
		return lecturer; // Retrieve the author/lecturer of the question
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer; // Set the author/lecturer of the question
	}

	// Return a formatted string representation of the Question object
	public String toString() {
		return String.format("%s %s %s %s %s %s\n", id, subject, courseName, questionText, questionNumber, lecturer);
	}

	@Override
	public TableRow<Question> call(TableView<Question> param) {
		// TODO Auto-generated method stub
		return null; // Callback method that is not implemented in this class
	}

}
