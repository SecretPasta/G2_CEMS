package Config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class Question implements Callback<TableView<Question>, TableRow<Question>>, Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String subjectID;
	private Map<String, String> courses_id_name = new HashMap<>();

	private String questionText;
	private String questionNumber;
	private ArrayList<String> answers;
	private String lecturer;
	private String lecturerID;
	private String subject;

	/**
	 * @param question id
	 * @param question subject
	 * @param question course name
	 * @param question text
	 * @param question number
	 * @param question author
	 */
	public Question(String id, String subjectID, Map<String, String> courses_id_name, String questionText, ArrayList<String> answers, String questionNumber,
			String lecturer, String lecturerID) {
		super();
		if(id == null) {
			this.id = subjectID + "" + questionNumber;
		}
		else {
			this.id = id;
		}
		this.subjectID = subjectID;
		this.courses_id_name = courses_id_name;
		this.questionText = questionText;
		this.questionNumber = questionNumber;
		this.setAnswers(answers);
		this.lecturer = lecturer;
		this.lecturerID = lecturerID;
		this.subject = "";
	}
	
	public String getSubject() {
		return subject; // Retrieve the subject Name of the question
	}

	public void setSubject(String subject) {
		this.subject = subject; // Set the subject Name of the question
	}

	public String getId() {
		return id; // Retrieve the ID of the question
	}

	public void setId(String id) {
		this.id = id; // Set the ID of the question
	}

	public String getsubjectID() {
		return subjectID; // Retrieve the subject of the question
	}

	public void setsubjectID(String subject) {
		this.subjectID = subject; // Set the subject of the question
	}

	public Map<String, String> getCourses() {
		return courses_id_name; // Retrieve the course name associated with the question
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
		return String.format("%s %s %s %s\n", id, subject, questionText, lecturer);
	}

	@Override
	public TableRow<Question> call(TableView<Question> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}

	public String getLecturerID() {
		return lecturerID;
	}

	public void setCourses(Map<String, String> courses_id_name) {
		this.courses_id_name = courses_id_name;
	}

}
