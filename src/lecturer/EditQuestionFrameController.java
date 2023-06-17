package lecturer;

import ClientAndServerLogin.SceneManagment;


import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
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
	private Label lblAuthorInfo;
	@FXML
	private Label txtQuestionID;
	@FXML
	private Label txtSubject;
	@FXML
	private Label txtCourseName;
	@FXML
	private Label txtQuestionAuthor;
	@FXML
    private JFXSnackbar snackbar;
	@FXML
    private AnchorPane root;
	
	@FXML
	private TextField txtAnswerCorrect;
	@FXML
	private TextField txtAnswerWrong1;
	@FXML
	private TextField txtAnswerWrong2;
	@FXML
	private TextField txtAnswerWrong3;
	@FXML
	private TextField txtQuestionText;
	

	@FXML
	private Button btnBack = null;
	@FXML
	private Button btnSave = null;
	
	private static Question questionSelected; // the question that selected for editing will be saved here
	
	private static String questionText;

	
	/**
	 * Loads the selected question into the GUI.
	 */
	public void loadSelectedQuestion() {
	    // Set the text fields in the GUI with the properties of the question object

	    txtQuestionID.setText(questionSelected.getId());
	    txtSubject.setText(questionSelected.getSubjectName());

	    // Initialize an empty string to hold the course names
	    String courses_str = "";
	      
	    int i = 1; // Initialize a counter variable for numbering the courses
	    
	    // Iterate through the course names and concatenate them with their respective numbers
	    for (String coursename : questionSelected.getCourses().values()) {
	        courses_str += i + ". " + coursename + "\n\n";
	        i++;
	    }

	    // Set the concatenated course names in the text field
	    txtCourseName.setText(courses_str);

	    txtQuestionText.setText(questionSelected.getQuestionText());

	    txtAnswerCorrect.setText(questionSelected.getAnswers().get(0));
	    txtAnswerWrong1.setText(questionSelected.getAnswers().get(1));
	    txtAnswerWrong2.setText(questionSelected.getAnswers().get(2));
	    txtAnswerWrong3.setText(questionSelected.getAnswers().get(3));

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
		    		questionSelected.getId(), questionText, false);
	}
	

	/**
	 * Handles the event when the save button is clicked.
	 *
	 * @param event The action event triggered by the save button
	 * @throws Exception If an exception occurs during the execution
	 */
	public void getSavebtn(ActionEvent event) throws Exception {
		
		try {
			
			if(txtQuestionText.getText().trim().equals("") || 
				txtAnswerCorrect.getText().trim().equals("") || txtAnswerWrong1.getText().trim().equals("") || 
				txtAnswerWrong2.getText().trim().equals("") || txtAnswerWrong3.getText().trim().equals("")) {
				throw new NullPointerException();
		        
			} else {
				
		        // Create an ArrayList to hold the data for updating the question and send it to the server
		        ArrayList<String> updateQuestionArr = new ArrayList<>();
		        updateQuestionArr.add("UpdateQuestionDataByID");
		        updateQuestionArr.add(questionSelected.getId());
		        updateQuestionArr.add(txtQuestionText.getText());
		        updateQuestionArr.add(txtAnswerCorrect.getText());
		        updateQuestionArr.add(txtAnswerWrong1.getText());
		        updateQuestionArr.add(txtAnswerWrong2.getText());
		        updateQuestionArr.add(txtAnswerWrong3.getText());
		        ClientUI.chat.accept(updateQuestionArr);
		        
		        questionText = txtQuestionText.getText();
		        
			    ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				LecturerDashboardFrameController.getInstance().showDashboardFrom_EditQuestions(
						questionSelected.getId(), questionText, true);
		        
			}
	    
		}catch (NullPointerException e) {
			displayErrorMessage();
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
	    questionText = questionSelected.getQuestionText();
	    SceneManagment.createNewStage("/lecturer/EditQuestionGUI.fxml", null, "Question Edit Management Tool").show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadSelectedQuestion();
	}
	
	private void displayErrorMessage() {
	    snackbar = new JFXSnackbar(root);
	    JFXSnackbarLayout snackbarLayout = new JFXSnackbarLayout("Error: Missing fields");
	    snackbar.setPrefWidth(root.getPrefWidth() - 40);
	    snackbar.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
	}

}
