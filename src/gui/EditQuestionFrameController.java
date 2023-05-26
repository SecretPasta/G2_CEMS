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

public class EditQuestionFrameController implements Initializable {
	
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
	private TextField txtAnswerCorrect;
	@FXML
	private TextField txtAnswerWrong1;
	@FXML
	private TextField txtAnswerWrong2;
	@FXML
	private TextField txtAnswerWrong3;
	
	@FXML
	private TextArea txtQuestionText;
	
	@FXML
	private Label txtQuestionAuthor;

	@FXML
	private Button btnBack = null;
	@FXML
	private Button btnSave = null;
	
	private static Question questionSelected;

	/**
	 * Loads a specific question into the GUI table.
	 *
	 * @param question The question object to be loaded
	 */
	public void loadSelectedQuestion() {
		
	    // Set the text fields in the GUI with the properties of the question object
	    txtQuestionID.setText(questionSelected.getId());
	    txtSubject.setText(questionSelected.getSubject());
	    txtCourseName.setText(questionSelected.getCourseName());
	    txtQuestionText.setText(questionSelected.getQuestionText());
	    
	    txtAnswerCorrect.setText(questionSelected.getAnswers().get(0));
	    txtAnswerWrong1.setText(questionSelected.getAnswers().get(1));
	    txtAnswerWrong2.setText(questionSelected.getAnswers().get(2));
	    txtAnswerWrong3.setText(questionSelected.getAnswers().get(3));
	    
	    txtQuestionNumber.setText(questionSelected.getQuestionNumber());
	    txtQuestionAuthor.setText(questionSelected.getLecturer());
	}

	/**
	 * Handles the event when the back button is clicked.
	 *
	 * @param event The action event triggered by the back button
	 * @throws Exception If an exception occurs during the execution
	 */
	public void getBackbtn(ActionEvent event) throws Exception {
		
	    ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	    
	    // When getting back, update the edited question in the question's lecturer table in the dashboard screen
	    // Pass the updated question details to the LecturerDashboardFrameController's showDashboardFrom_EditQuestions() method
	    LecturerDashboardFrameController.getInstance().showDashboardFrom_EditQuestions(
	    		questionSelected.getId(), txtQuestionText.getText(), txtQuestionNumber.getText());
	}

	/**
	 * Handles the event when the save button is clicked.
	 *
	 * @param event The action event triggered by the save button
	 * @throws Exception If an exception occurs during the execution
	 */
	public void getSavebtn(ActionEvent event) throws Exception {
		
	    if (txtQuestionText.getText().equals("") || txtQuestionNumber.getText().equals("") || txtAnswerCorrect.getText().equals("")
	    		 || txtAnswerWrong1.getText().equals("") || txtAnswerWrong2.getText().equals("") || txtAnswerWrong3.getText().equals("")) {
	        lblMessage.setTextFill(Color.color(1, 0, 0));
	        lblMessage.setText("[Error] Missing fields");
	    } else {
	        lblMessage.setTextFill(Color.rgb(0, 102, 0));
	        lblMessage.setText("Question Saved Successfully");
	        
	        // Create an ArrayList to hold the data for updating the question and send it to the server
	        ArrayList<String> updateQuestionArr = new ArrayList<>();
	        updateQuestionArr.add("UpdateQuestionDataByID");
	        updateQuestionArr.add(questionSelected.getId());
	        updateQuestionArr.add(txtQuestionText.getText());
	        updateQuestionArr.add(txtAnswerCorrect.getText());
	        updateQuestionArr.add(txtAnswerWrong1.getText());
	        updateQuestionArr.add(txtAnswerWrong2.getText());
	        updateQuestionArr.add(txtAnswerWrong3.getText());
	        updateQuestionArr.add(txtQuestionNumber.getText());
	        ClientUI.chat.accept(updateQuestionArr);
	        
	    }
	}


	/**
	 * Starts the question editing management tool by opening the "Edit Question" screen.
	 *
	 * @param questionSelected_temp The question selected for editing.
	 * @throws Exception If an exception occurs during the opening of the "Edit Question" screen.
	 */
	public static void start(Question questionSelected_temp) throws Exception {
	    questionSelected = questionSelected_temp;
	    SceneManagment.createNewStage("/gui/EditQuestionGUI.fxml", null, "Question Edit Management Tool").show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadSelectedQuestion();
	}

}
