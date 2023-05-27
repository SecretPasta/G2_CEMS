package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import Config.Lecturer;
import Config.Question;
import client.ClientUI;
import ClientAndServerLogin.SceneManagment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;


public class AddQuestionFrameController implements Initializable {
	
    @FXML
    private JFXComboBox<String> subjectSelectBox;
    @FXML
    private JFXListView<String> courseSelectList;
    
    @FXML
    private Label lblMessage;

	@FXML
    private TextField textQuestionText;
    @FXML
    private TextField txtAnswerCorrect;
    @FXML
    private TextField txtAnswerWrong1;
    @FXML
    private TextField txtAnswerWrong2;
    @FXML
    private TextField txtAnswerWrong3;
    @FXML
    private TextField txtQuestionNumber;
    
    @FXML
    private JFXButton backBtn;
    @FXML
    private JFXButton addQuestionBtn;
    
    private static Lecturer lecturer; // current lecturer
    
    private ArrayList<Question> newQuestion;
    
    private static String maxIdOfQuestionInCurrentSubject;
    
    /**
     * Starts the question add management tool by opening the "Add Question" screen.
     *
     * @param lecturer_temp The lecturer associated with the question add management.
     * @throws IOException If an I/O exception occurs during the opening of the "Add Question" screen.
     */
    public static void start(Lecturer lecturer_temp) throws IOException {
        lecturer = lecturer_temp;
        SceneManagment.createNewStage("/lecturer/AddQuestionGUI.fxml", null, "Question Add Management Tool").show();
    }

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	    // Set up the courseSelectList
	    courseSelectList.getItems().add("Please select a subject first");
	    courseSelectList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	    // Add subjects to the subjectSelectBox
	    for (Map.Entry<String, ArrayList<String>> entry : lecturer.getLecturerSubjectsAndCourses().entrySet()) {
	        subjectSelectBox.getItems().add(entry.getKey());
	    }
	}
	
	/**
	 * Handles the action when the back button is clicked.
	 *
	 * @param event The event triggered by clicking the back button.
	 * @throws Exception If an exception occurs during the handling of the back action.
	 */
	public void getBackBtn(ActionEvent event) throws Exception {
		
	    ((Node) event.getSource()).getScene().getWindow().hide(); // Hide the primary window

	    // Show the dashboard screen and update it with the new question
	    LecturerDashboardFrameController.getInstance().showDashboardFrom_AddQuestion(newQuestion);
	}

	
	/**
	 * Handles the action when a subject is selected from the subject select box.
	 *
	 * @param event The event triggered by selecting a subject from the subject select box.
	 * @throws Exception If an exception occurs during the handling of the subject selection.
	 */
	public void getSubjectSelectBox(ActionEvent event) throws Exception {
		
	    courseSelectList.getItems().clear();

	    // Add the courses from the selected subject to the course select list
	    for (Map.Entry<String, ArrayList<String>> entry : lecturer.getLecturerSubjectsAndCourses().entrySet()) {
	        if (subjectSelectBox.getSelectionModel().getSelectedItem().equals(entry.getKey())) {
	            courseSelectList.getItems().addAll(entry.getValue());
	            break;
	        }
	    }
	}

	
	/**
	 * Saves the maximum ID of a question in the selected subject. (to generate id's)
	 *
	 * @param maxId The maximum ID of a question in the selected subject.
	 */
	public static void saveMaxIdOfQuestionInSelectedSubject(String maxId) {
	    maxIdOfQuestionInCurrentSubject = maxId;
	}


	/**
	 * Handles the action when the "Add Question" button is clicked.
	 *
	 * @param event The event triggered by clicking the "Add Question" button.
	 * @throws Exception If an exception occurs during the handling of the "Add Question" action.
	 */
	public void getAddQuestionBtn(ActionEvent event) throws Exception {
		
		ObservableList<String> coursesSelect = courseSelectList.getSelectionModel().getSelectedItems();
		String subjectSelect = subjectSelectBox.getSelectionModel().getSelectedItem();
		try {
		    if (subjectSelect == null || subjectSelect.isEmpty() || coursesSelect.get(0).equals("Please select a subject first") || 
		    		coursesSelect.isEmpty() || textQuestionText.getText().trim().equals("") || 
		    		txtQuestionNumber.getText().trim().equals("") || txtAnswerCorrect.getText().trim().equals("") || 
		    		txtAnswerWrong1.getText().trim().equals("") || txtAnswerWrong2.getText().trim().equals("") || 
		    		txtAnswerWrong3.getText().trim().equals("")) {
		    	
		        lblMessage.setTextFill(Color.color(1, 0, 0));
		        lblMessage.setText("[Error] Missing fields");
		        
		    } else {
		    	//lblMessage.setTextFill(Color.rgb(0, 102, 0));
		    	//lblMessage.setText("Question added Successfully");
		    	
		    	lblMessage.setText(""); // Clear the error message
		    	
	            // Request the maximum question ID for the selected subject from the server        
		        ArrayList<String> getMaxQuestionIdFromCurrentSubjectArr = new ArrayList<>();
		        getMaxQuestionIdFromCurrentSubjectArr.add("GetMaxQuestionIdFromProvidedSubject");
		        getMaxQuestionIdFromCurrentSubjectArr.add(subjectSelect);
		        ClientUI.chat.accept(getMaxQuestionIdFromCurrentSubjectArr);
		        
		        // Create an ArrayList of answers based on the input values
		        ArrayList<String> answersArr = new ArrayList<>();
		        answersArr.add(txtAnswerCorrect.getText());
		        answersArr.add(txtAnswerWrong1.getText());
		        answersArr.add(txtAnswerWrong2.getText());
		        answersArr.add(txtAnswerWrong3.getText());
		        
		        newQuestion = new ArrayList<>(); // Initialize a newQuestion ArrayList
		        
		        String id = maxIdOfQuestionInCurrentSubject; // Retrieve the current maximum question ID
		        
		        //String id = "03101"; //  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		        
		        int i = 0;
		        ArrayList<Question> addQuestionToDBArr = new ArrayList<>();
		        addQuestionToDBArr.add(new Question("AddNewQuestionToDB", null, null, null, null, null, null)); // to identifying
		        
		        // Iterate over the selected courses and create a new Question object for each course
		        for(String courses : coursesSelect) {
		        	
		        	// Increment the question ID and format it
		        	id = "0" + Integer.toString(Integer.parseInt(id) + 1);
		        	
		        	// Create a new Question object with the input values
			        newQuestion.add(new Question(id, subjectSelect, courses, textQuestionText.getText(), answersArr, txtQuestionNumber.getText(), lecturer.getName()));		        
			        // Add the question to the addQuestionToDBArr
			        addQuestionToDBArr.add(newQuestion.get(i));	        
			        i++;
		        }
		        
	            // Send the addQuestionToDBArr to the server to add the questions to the database
		        ClientUI.chat.accept(addQuestionToDBArr);
		        
		        getBackBtn(event); // Go back to the previous screen
		        
		    }
		}catch (NullPointerException | IndexOutOfBoundsException e) {
	        lblMessage.setTextFill(Color.color(1, 0, 0));
	        lblMessage.setText("[Error] Missing fields");	

		}
	}
}
