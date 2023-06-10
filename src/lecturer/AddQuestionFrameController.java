package lecturer;

import java.io.IOException;
import java.util.HashMap;


import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

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
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class AddQuestionFrameController implements Initializable {
	
    @FXML
    private JFXComboBox<String> subjectSelectBox;
    @FXML
    private JFXListView<String> courseSelectList;
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private JFXSnackbar snackbarError;

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
    private JFXButton backBtn;
    @FXML
    private JFXButton addQuestionBtn;
    
    private static Lecturer lecturer; // current lecturer
    
    private ArrayList<Question> newQuestion; // the new question will be here in get(1). get(0) is the command to the server to add the new question
    
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
	    // the subjects are the key in: lecturer.getLecturerSubjectsAndCourses()
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
	    try {
	    	LecturerDashboardFrameController.getInstance().showDashboardFrom_AddQuestion(newQuestion.get(1));
	    }catch (NullPointerException e) { // if the question is null when no question was added
	    	LecturerDashboardFrameController.getInstance().showDashboardFrom_AddQuestion(null);
	    }
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
	    
		    // Check if any required fields are empty
		    if (areFieldsMissing(coursesSelect, subjectSelect)) {
		        displaySnackbarError(); // Display error message in a snackbar
		    } else {
		        getMaxQuestionIdFromCurrentSubject(subjectSelect); // Retrieve the maximum question ID for the selected subject
		        addQuestionToDatabase(subjectSelect, coursesSelect); // Add the question to the database
		        getBackBtn(event); // Go back to the previous screen
		    }
	    
	    }catch (NullPointerException | IndexOutOfBoundsException e) {
	    	displaySnackbarError(); // Display error message in a snackbar
		}
	}
	

	/**
	 * Checks if all the required fields are filled.
	 *
	 * @param coursesSelect The selected courses for the question.
	 * @param subjectSelect The selected subject for the question.
	 * @return True if any of the required fields are empty, false otherwise.
	 */
	private boolean areFieldsMissing(ObservableList<String> coursesSelect, String subjectSelect) {
	    return (subjectSelect == null || subjectSelect.isEmpty() || coursesSelect.get(0).equals("Please select a subject first") ||
	            coursesSelect.isEmpty() || textQuestionText.getText().trim().equals("") ||
	            txtAnswerCorrect.getText().trim().equals("") ||
	            txtAnswerWrong1.getText().trim().equals("") || txtAnswerWrong2.getText().trim().equals("") ||
	            txtAnswerWrong3.getText().trim().equals(""));
	}
	

	/**
	 * Displays an error message in a snackbar for missing fields.
	 */
	private void displaySnackbarError() {
	    snackbarError = new JFXSnackbar(root);
	    JFXSnackbarLayout snackbarLayout = new JFXSnackbarLayout("Error: Missing fields");
	    snackbarError.setPrefWidth(root.getPrefWidth() - 40);
	    snackbarError.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
	}
	

	/**
	 * Retrieves the maximum question ID for the selected subject.
	 *
	 * @param subjectSelect The selected subject for the question.
	 */
	private void getMaxQuestionIdFromCurrentSubject(String subjectSelect) {
	    ArrayList<String> getMaxQuestionIdFromCurrentSubjectArr = new ArrayList<>();
	    getMaxQuestionIdFromCurrentSubjectArr.add("GetMaxQuestionIdFromProvidedSubject");
	    getMaxQuestionIdFromCurrentSubjectArr.add(LecturerDashboardFrameController.getSubjectIdByName(subjectSelect));
	    ClientUI.chat.accept(getMaxQuestionIdFromCurrentSubjectArr);
	}
	

	/**
	 * Adds the question to the database.
	 *
	 * @param subjectSelect  The selected subject for the question.
	 * @param coursesSelect  The selected courses for the question.
	 */
	private void addQuestionToDatabase(String subjectSelect, ObservableList<String> coursesSelect) {
	    // Create an ArrayList of answers based on the input values
	    ArrayList<String> answersArr = new ArrayList<>();
	    answersArr.add(txtAnswerCorrect.getText());
	    answersArr.add(txtAnswerWrong1.getText());
	    answersArr.add(txtAnswerWrong2.getText());
	    answersArr.add(txtAnswerWrong3.getText());

	    newQuestion = new ArrayList<>(); // Initialize a newQuestion ArrayList
	    newQuestion.add(new Question("AddNewQuestionToDB", null, null, null, null, null, null, null)); // To identify

	    Map<String, String> courses_id_name = new HashMap<>();
	    for (String course : coursesSelect) { // add to the question the related courses
	        courses_id_name.put(LecturerDashboardFrameController.getCourseIdByName(course), course);
	    }

	    String id = maxIdOfQuestionInCurrentSubject; // Retrieve the current maximum question ID

	    // Increment the question ID and format it
	    String formattedQuestionNum = String.format("%03d", Integer.parseInt(id) + 1);
	    
	    ArrayList<String> subject = new ArrayList<>();
	    subject.add(LecturerDashboardFrameController.getSubjectIdByName(subjectSelect));
	    subject.add(subjectSelect);
	    newQuestion.add(new Question(null, subject, courses_id_name,
	            textQuestionText.getText(), answersArr, formattedQuestionNum, lecturer.getName(), lecturer.getId()));

	    // Send the addQuestionToDBArr to the server to add the questions to the database
	    ClientUI.chat.accept(newQuestion);
	}

}
