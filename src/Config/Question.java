package Config;

import java.io.Serializable;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class Question implements Callback<TableView<Question>, TableRow<Question>> , Serializable{

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
	public Question(String id, String subject, String courseName, String questionText, String questionNumber, String lecturer) {
		super();
		this.id = id;
		this.subject = subject;
		this.courseName = courseName;
		this.questionText = questionText;
		this.questionNumber = questionNumber;
		this.lecturer = lecturer;
	}
	
	
	
	
	
	public String getId() {
		return id;
	}





	public void setId(String id) {
		this.id = id;
	}





	public String getSubject() {
		return subject;
	}





	public void setSubject(String subject) {
		this.subject = subject;
	}





	public String getCourseName() {
		return courseName;
	}





	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}





	public String getQuestionText() {
		return questionText;
	}





	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}





	public String getQuestionNumber() {
		return questionNumber;
	}





	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}





	public String getLecturer() {
		return lecturer;
	}





	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}





	public String toString(){
		return String.format("%s %s %s %s %s %s\n",id,subject,courseName,questionText,questionNumber,lecturer);
	}

	@Override
	public TableRow<Question> call(TableView<Question> param) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
