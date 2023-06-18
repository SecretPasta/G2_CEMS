package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;

import ClientAndServerLogin.SceneManagment;
import Config.Exam;
import Config.Lecturer;
import Config.QuestionInExam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CreateExam_CommentsAndTimeFrameController implements Initializable {
	
	@FXML
	private TableView<QuestionInExam> tableExam = new TableView<>();
	
	@FXML
	private TableColumn<QuestionInExam, String> idColumn_tableExam;
	@FXML
	private TableColumn<QuestionInExam, String> questionTextColumn_tableExam;
	@FXML
	private TableColumn<QuestionInExam, Double> pointsColumn_tableExam;
	
	@FXML
	private TextField txtCommentsLecturer;
	@FXML
	private TextField txtCommentsStudent;
	@FXML
	private TextField txtExamDuration;
	@FXML
	private TextField txtExamCode;
	
	@FXML
	private JFXSnackbar snackbar;
	private JFXSnackbarLayout snackbarLayout;
	
	@FXML
	private AnchorPane root;
	
	
	
	private static Stage currStage; // save current stage
	
	private static Lecturer lecturer;
	private static ObservableList<QuestionInExam> questionsToCreateExamObservableList = FXCollections.observableArrayList();
	private static String subjectID;
	private static String courseID;
	private static String subjectName;
	private static String courseName;
	
	
	/**
	 * Starts the Create Exam process with the provided parameters.
	 *
	 * @param temp_lecturer                     The lecturer associated with the exam.
	 * @param temp_questionsToCreateExamObservableList The list of questions to include in the exam.
	 * @param subjectID_temp                    The ID of the subject associated with the exam.
	 * @param subjectName_temp                  The name of the subject associated with the exam.
	 * @param courseID_temp                     The ID of the course associated with the exam.
	 * @param courseName_temp                   The name of the course associated with the exam.
	 * @throws IOException                      If an error occurs during the process.
	 */
	public static void start(Lecturer temp_lecturer, ObservableList<QuestionInExam> temp_questionsToCreateExamObservableList,
	                         String subjectID_temp, String subjectName_temp, String courseID_temp, String courseName_temp) throws IOException {
	    lecturer = temp_lecturer;
	    questionsToCreateExamObservableList = temp_questionsToCreateExamObservableList;
	    subjectID = subjectID_temp;
	    subjectName = subjectName_temp;
	    courseID = courseID_temp;
	    courseName = courseName_temp;
		currStage = SceneManagment.createNewStage("/lecturer/CreateExam_CommentsAndTimeGUI.fxml", null,
				"Lecturer->CreateExam->CommentsAndTime");
	    currStage.show();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//initialize the table of questions in the exam.
		idColumn_tableExam.setCellValueFactory(new PropertyValueFactory<QuestionInExam, String>("id"));
		questionTextColumn_tableExam.setCellValueFactory(new PropertyValueFactory<QuestionInExam, String>("questionText"));    
		pointsColumn_tableExam.setCellValueFactory(new PropertyValueFactory<QuestionInExam, Double>("points")); 
		tableExam.setItems(questionsToCreateExamObservableList);
		tableExam.getSelectionModel().clearSelection();
		
	}
	
	/**
	 * Handles the action when the "Show Review" button is clicked.
	 *
	 * @param event The action event triggered by the "Show Review" button.
	 * @throws Exception If an error occurs during the process.
	 */
	public void getBtnShowReview(ActionEvent event) throws Exception {
	    try {
	        if (txtExamDuration.getText().trim().equals("") || txtExamCode.getText().trim().equals("")) {
				displayErrorMessage("Error: Missing fields!");
	        } else {
	            if (txtExamCode.getText().length() != 4) {
					displayErrorMessage("Error: Exam code has to be 4 digits!");
	                return;
	            }
	            int examDuration = Integer.parseInt(txtExamDuration.getText());

	            if (examDuration <= 0) {
	                throw new NumberFormatException();
	            }

	            ArrayList<QuestionInExam> questionsInExam_arr = new ArrayList<>();
	            questionsInExam_arr.addAll(questionsToCreateExamObservableList);

	            // creating new exam with the parameters. id is null because still not saved by the lecturer.
	            Exam exam = new Exam(null, subjectID, subjectName, courseID, courseName,
	                    questionsInExam_arr, txtCommentsLecturer.getText(), txtCommentsStudent.getText(),
	                    examDuration, lecturer.getName(), txtExamCode.getText(), lecturer.getId());

	            ((Node) event.getSource()).getScene().getWindow().hide();
	            CreateExam_ReviewFrameController.start(exam); // starting the exam review screen.
	        }

	    } catch (NullPointerException e) {
			displayErrorMessage("Error: Missing fields!");
	    } catch (NumberFormatException e) {
			displayErrorMessage("Error: Exam duration has to be a valid number!");
	    }
	}

    
	/**
	 * Shows the stage from the review process.
	 *
	 * @throws IOException If an error occurs during the process.
	 */
	public static void showStageFrom_Review() throws IOException {
	    currStage.show();
	}

	
	/**
	 * Handles the action when the "Back" button is clicked.
	 *
	 * @param event The action event triggered by the "Back" button.
	 * @throws Exception If an error occurs during the process.
	 */
	@FXML
	public void getBtnBack(ActionEvent event) throws Exception {
	    ((Node) event.getSource()).getScene().getWindow().hide();
	    LecturerDashboardFrameController.getInstance().showDashboardFrom_CreateExam(); // Previous screen
	}
	
	/**
	 * Displays an error message in a snackbar for missing fields.
	 */
	private void displayErrorMessage(String message) {
	    snackbar = new JFXSnackbar(root);
	    snackbarLayout = new JFXSnackbarLayout(message);
	    snackbar.setPrefWidth(root.getPrefWidth() - 40);
	    snackbar.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
	}


}
