package gui;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import Config.Question;
import client.ClientUI;

public class UpdateQuestionFrameController implements Initializable {
	private Question question;
	@FXML
	private Label lblidQuestion;
	@FXML
	private Label lblSubjectInfo;
	@FXML
	private Label lblCourseNameInfo;
	@FXML
	private Label lblQuestionTextInfo;
	@FXML
	private Label lblQuestionNumberInfo;
	@FXML
	private Label lblAuthorInfo;
	@FXML
	private Label lblMessage;

	@FXML
	private Label txtQuestionID;
	@FXML
	private Label txtSubject;
	@FXML
	private Label txtCourseName;
	@FXML
	private TextField txtQuestionNumber;
	@FXML
	private TextArea txtQuestionText;
	@FXML
	private Label txtQuestionAuthor;

	@FXML
	private Button btnBack = null;

	@FXML
	private Button btnSave = null;

	// Loading a specific qustion into the GUI table
	public void loadSelectedQuestion(Question question) {
		this.question = question;
		this.txtQuestionID.setText(question.getId());
		this.txtSubject.setText(question.getSubject());
		this.txtCourseName.setText(question.getCourseName());
		this.txtQuestionText.setText(question.getQuestionText());
		this.txtQuestionNumber.setText(question.getQuestionNumber());
		this.txtQuestionAuthor.setText(question.getLecturer());
	}

	// Back Button functionality
	public void getBackbtn(ActionEvent event) throws Exception {
		
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		
		LecturerDashboardFrameController.start(question.getLecturer());
	}

	// Save button functionality
	public void getSavebtn(ActionEvent event) throws Exception {
		if (txtQuestionText.getText().equals("") || txtQuestionNumber.getText().equals("")) {
			lblMessage.setTextFill(Color.color(1, 0, 0));
			lblMessage.setText("[Error] Missing fields");
		} else {
			lblMessage.setTextFill(Color.rgb(0, 102, 0));
			lblMessage.setText("Question Saved Successfully");
			// Create an ArrayList to hold the data for updating the question
			ArrayList<String> updateQuestionArr = new ArrayList<>();
			updateQuestionArr.add("UpdateQuestionDataByID");
			updateQuestionArr.add(question.getId()); // Add the question ID
			updateQuestionArr.add(txtQuestionText.getText()); // Add the updated question text
			updateQuestionArr.add(txtQuestionNumber.getText()); // Add the updated question number
			// Send the ArrayList to the server for updating the question data
			ClientUI.chat.accept(updateQuestionArr);
		}
	}

	public static void start() throws Exception {
		SceneManagment.createNewStage("/gui/UpdateQuestionGUI.fxml", null, "Question Update Managment Tool").show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadSelectedQuestion(LecturerDashboardFrameController.questionSelected);
	}

}
