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
	private Map<String, String> courses_id_name = new HashMap<>();
	private String questionText;
	private String questionNumber;
	private ArrayList<String> answers;
	private String lecturer;
	private String lecturerID;
	private ArrayList<String> subject;

	/**
	 * Constructs a Question object with the specified attributes.
	 *
	 * @param id The ID of the question. If null, the ID will be generated based on the subject and question number.
	 * @param subject The subject of the question.
	 * @param courses_id_name The map of course IDs and names associated with the question.
	 * @param questionText The text of the question.
	 * @param answers The list of possible answers for the question.
	 * @param questionNumber The number of the question.
	 * @param lecturer The name of the lecturer who created the question.
	 * @param lecturerID The ID of the lecturer who created the question.
	 */
	public Question(String id, ArrayList<String> subject, Map<String, String> courses_id_name, String questionText, ArrayList<String> answers, String questionNumber,
			String lecturer, String lecturerID) {
		super();
		if(id == null) {
			this.id = subject.get(0) + "" + questionNumber;
		}
		else {
			this.id = id;
		}
		this.courses_id_name = courses_id_name;
		this.questionText = questionText;
		this.questionNumber = questionNumber;
		this.answers = answers;
		this.lecturer = lecturer;
		this.lecturerID = lecturerID;
		this.subject = subject;
	}



	/**
	 * Retrieves the subject name of the question.
	 *
	 * @return The subject name of the question.
	 */
	public String getSubjectName() {
		return subject.get(1);
	}

	/**
	 * Retrieves the subject ID of the question.
	 *
	 * @return The subject ID of the question.
	 */
	public String getSubjectId() {
		return subject.get(0);
	}

	/**
	 * Retrieves the subject of the question.
	 *
	 * @return The subject of the question.
	 */
	public ArrayList<String> getSubject() {
		return subject;
	}

	/**
	 * Sets the subject of the question.
	 *
	 * @param subject The subject to set for the question.
	 */
	public void setSubject(ArrayList<String> subject) {
		this.subject = subject;
	}


	/**
	 * Retrieves the ID of the question.
	 *
	 * @return The ID of the question.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the ID of the question.
	 *
	 * @param id The ID to set for the question.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrieves the courses associated with the question.
	 *
	 * @return The courses associated with the question.
	 */
	public Map<String, String> getCourses() {
		return courses_id_name;
	}

	/**
	 * Retrieves the text of the question.
	 *
	 * @return The text of the question.
	 */
	public String getQuestionText() {
		return questionText;
	}

	/**
	 * Sets the text of the question.
	 *
	 * @param questionText The text to set for the question.
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	/**
	 * Retrieves the question number.
	 *
	 * @return The question number.
	 */
	public String getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Sets the question number.
	 *
	 * @param questionNumber The question number to set.
	 */
	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}


	/**
	 * Retrieves the lecturer (author) of the question.
	 *
	 * @return The lecturer (author) of the question.
	 */
	public String getLecturer() {
		return lecturer;
	}

	/**
	 * Sets the lecturer (author) of the question.
	 *
	 * @param lecturer The lecturer (author) to set for the question.
	 */
	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	/**
	 * Returns a formatted string representation of the Question object.
	 *
	 * @return A formatted string representation of the Question object.
	 */
	@Override
	public String toString() {
		return String.format("%s %s %s %s\n", id, subject, questionText, lecturer);
	}

	/**
	 * Not implemented.
	 *
	 * @param arg0 The TableView argument.
	 * @return Always returns null.
	 */
	@Override
	public TableRow<Question> call(TableView<Question> arg0) {
		return null;
	}


	/**
	 * Retrieves the answers of the question.
	 *
	 * @return The answers of the question.
	 */
	public ArrayList<String> getAnswers() {
		return answers;
	}

	/**
	 * Sets the answers of the question.
	 *
	 * @param answers The answers to set for the question.
	 */
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}

	/**
	 * Retrieves the ID of the lecturer.
	 *
	 * @return The ID of the lecturer.
	 */
	public String getLecturerID() {
		return lecturerID;
	}

	/**
	 * Sets the courses associated with the question.
	 *
	 * @param courses_id_name The courses to set for the question.
	 */
	public void setCourses(Map<String, String> courses_id_name) {
		this.courses_id_name = courses_id_name;
	}



}
