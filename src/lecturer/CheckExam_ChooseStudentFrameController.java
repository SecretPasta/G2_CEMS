package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;

import ClientAndServerLogin.SceneManagment;
import Config.Exam;
import Config.FinishedExam;
import Config.Lecturer;
import client.ClientUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CheckExam_ChooseStudentFrameController implements Initializable {

	@FXML
	private Label lblCourse;
	@FXML
	private Label lblExamId;
	@FXML
	private Label lblSubject;

	@FXML
	private AnchorPane root;

	@FXML
	private JFXSnackbar snackbar;
	
	private JFXSnackbarLayout snackbarLayout;

	@FXML
	private TableColumn<FinishedExam, String> studentIdColumn_tableExam;
	@FXML
	private TableColumn<FinishedExam, String> studentgradeColumn_tableExam;

	@FXML
	private TableView<FinishedExam> finishedExams_tableView;
	
    @FXML
    private JFXListView<String> examsCheating_listView;
    
    private ObservableList<String> examsCheating_observablelist = FXCollections.observableArrayList();
	
	private ObservableList<FinishedExam> finishedExams_observablelist = FXCollections.observableArrayList();

	private static Stage currStage; // save current stage
	
	private static Lecturer luecturer;
	private static Exam examSelectedForChecking;
	
	private static FinishedExam finishedExamSelected;
	
	private static CheckExam_ChooseStudentFrameController instance;
	
	public CheckExam_ChooseStudentFrameController() {
		instance = this;
	}
	
	public static CheckExam_ChooseStudentFrameController getInstance() {
		return instance;
	}

	/**
	 * Starts the process of checking the exam by selecting a student.
	 *
	 * @param examSelectedForChecking_temp The exam selected for checking.
	 * @param lecturer_temp                The lecturer conducting the checking.
	 * @throws IOException If an I/O error occurs while creating the stage.
	 */
	public static void start(Exam examSelectedForChecking_temp, Lecturer lecturer_temp) throws IOException {
	    luecturer = lecturer_temp;
	    examSelectedForChecking = examSelectedForChecking_temp;
		currStage = SceneManagment.createNewStage("/lecturer/CheckExam_ChooseStudent.fxml", null,
				"Lecturer->CheckExam->ChooseStudent");
	    currStage.show();
	}


	/**
	 * Handles the event when the "Back" button is clicked.
	 *
	 * @param event The action event triggered by clicking the button.
	 */
	@FXML
	void getBtnBack(ActionEvent event) {
	    ((Node) event.getSource()).getScene().getWindow().hide();
	    LecturerDashboardFrameController.getInstance().showDashboardFrom_CheckExam(); // Display the previous screen
	}


	/**
	 * Shows the stage for the selected student's finished exam from the student list.
	 *
	 * @param finishedExamSelected_temp The finished exam selected by the lecturer.
	 * @throws IOException If an I/O error occurs.
	 */
	public void showStageFrom_StudentList(FinishedExam finishedExamSelected_temp) throws IOException {
	    try {
	        
	        // Remove the student exam from the finishedExams_observablelist and refresh the table view
	        for (int i = 0; i < finishedExams_observablelist.size(); i++) {
	            if (finishedExams_observablelist.get(i).getExamID().equals(finishedExamSelected_temp.getExamID()) &&
	                    finishedExams_observablelist.get(i).getStudentID().equals(finishedExamSelected_temp.getStudentID())) {
	                finishedExams_observablelist.remove(i);
	                break;
	            }
	        }
	        
	        examsCheating_listView.setMouseTransparent(true);
	        examsCheating_listView.setFocusTraversable(false);
	        finishedExams_tableView.refresh();
	    } catch (NullPointerException e) {}
	    
	    currStage.show();
	}


	/**
	 * Handles the action when the "Show Exam" button is clicked.
	 *
	 * @param event The action event.
	 * @throws IOException If an I/O error occurs.
	 */
	@FXML
	void getBtnShowExam(ActionEvent event) throws IOException {	
	    try {
	        finishedExamSelected = finishedExams_tableView.getSelectionModel().getSelectedItem();
	        
	        if (finishedExamSelected == null) {
	            throw new NullPointerException();
	        }
	        
	        // Set the subject and course information for the selected finished exam
	        finishedExamSelected.setSubjectName(examSelectedForChecking.getSubjectName());
	        finishedExamSelected.setCourseName(examSelectedForChecking.getCourseName());
	        finishedExamSelected.setSubjectID(examSelectedForChecking.getSubjectID());
	        finishedExamSelected.setCourseID(examSelectedForChecking.getCourseID());
	        
	        // Hide the current window
	        ((Node) event.getSource()).getScene().getWindow().hide();
	        
	        // Start the CheckExam_ReviewAndApproveFrameController with the lecturer and finished exam
	        CheckExam_ReviewAndApproveFrameController.start(luecturer, finishedExamSelected);
	        
	    } catch (NullPointerException e) {
	        // Handle the case when a student exam is not selected
			displayErrorMessage("Error: Student was not selected!");
	    }
	    
	    // Clear the selection in the finished exams table view
	    finishedExams_tableView.getSelectionModel().clearSelection();

	    finishedExamSelected = null;
	}

	
	
	/**
	 * Retrieves and identifies suspect exams for cheating.
	 * This method compares the answers of each finished exam with all other finished exams
	 * to find any exams with the same set of answers.
	 */
	public void getSuspectExamsForCheating() {
	    // Create a map to store exams with the same answers
	    Map<FinishedExam, ArrayList<FinishedExam>> examsWithSameAnswers = new HashMap<>();

	    // Iterate through the finished exams
	    for (int i = 0; i < finishedExams_observablelist.size(); i++) {
	        FinishedExam exam1 = finishedExams_observablelist.get(i);

	        // Compare the current exam with the remaining exams
	        for (int j = i + 1; j < finishedExams_observablelist.size(); j++) {
	            FinishedExam exam2 = finishedExams_observablelist.get(j);

	            // Check if the answers of exam1 and exam2 are the same
	            if (exam1.getAnswers().equals(exam2.getAnswers())) {
	                // Add exam2 to the list of exams with the same answers as exam1
	                ArrayList<FinishedExam> exams1 = examsWithSameAnswers.get(exam1);
	                if (exams1 == null) {
	                    exams1 = new ArrayList<>();
	                    examsWithSameAnswers.put(exam1, exams1);
	                }
	                if (examsWithSameAnswers.containsKey(exam2)) {
	                    exams1.add(exam2);
	                }

	                // Add exam1 to the list of exams with the same answers as exam2
	                ArrayList<FinishedExam> exams2 = examsWithSameAnswers.get(exam2);
	                if (exams2 == null) {
	                    exams2 = new ArrayList<>();
	                    examsWithSameAnswers.put(exam2, exams2);
	                }
	                if (examsWithSameAnswers.containsKey(exam1)) {
	                    exams2.add(exam1);
	                }
	            }
	        }
	    }

	    // Create a list to store the formatted cheaters' information
	    ArrayList<String> cheaters_list = new ArrayList<>();

	    // Iterate through the exams with the same answers
	    for (Map.Entry<FinishedExam, ArrayList<FinishedExam>> entry : examsWithSameAnswers.entrySet()) {
	        StringBuilder modified_cheater = new StringBuilder();
	        FinishedExam exam = entry.getKey();
	        ArrayList<FinishedExam> exams = entry.getValue();

	        // Build the cheater's information string
	        modified_cheater.append("Student ID: ").append(exam.getStudentID());
	        modified_cheater.append(" same answers as Students ID:\n");
	        for (FinishedExam examcpy : exams) {
	            modified_cheater.append("                              ");
	            modified_cheater.append(examcpy.getStudentID());
	        }

	        // Add the formatted cheater's information to the list
	        if (!entry.getValue().isEmpty()) {
	            cheaters_list.add(modified_cheater.toString());
	        }
	    }

	    // Update the observable list and list view with the cheaters' information
	    examsCheating_observablelist.setAll(cheaters_list);
	    examsCheating_listView.setItems(examsCheating_observablelist);
	    examsCheating_listView.refresh();

	    // Disable mouse interaction and focus for the exams cheating list view
	    examsCheating_listView.setMouseTransparent(true);
	    examsCheating_listView.setFocusTraversable(false);
	}

	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblExamId.setText("Exam ID: " + examSelectedForChecking.getExamID());
		lblSubject.setText(examSelectedForChecking.getSubjectName() + " (" + examSelectedForChecking.getSubjectID() + ")");
		lblCourse.setText(examSelectedForChecking.getCourseName() + " (" + examSelectedForChecking.getCourseID() + ")");
		
		getAllFinishedExamsOfSelectedExam();
		
		studentIdColumn_tableExam.setCellValueFactory(new PropertyValueFactory<FinishedExam, String>("studentID"));
		studentgradeColumn_tableExam.setCellValueFactory(new PropertyValueFactory<FinishedExam, String>("grade"));    
		finishedExams_tableView.getSelectionModel().clearSelection();
	}

	/**
	 * Retrieves all finished exams of the selected exam for checking by the lecturer.
	 * This method sends a request to the server to get all exams with the specified exam ID.
	 */
	private void getAllFinishedExamsOfSelectedExam() {
	    // Create an array list to store the request parameters
	    ArrayList<String> finishedExamsReq_arr = new ArrayList<>();
	    finishedExamsReq_arr.add("GetAllExamsByExamIDForLecturerForChecking");
	    finishedExamsReq_arr.add(examSelectedForChecking.getExamID());

	    // Send the request to the server using the client's chat instance
	    ClientUI.chat.accept(finishedExamsReq_arr);
	}

	
	/**
	 * Loads all finished exams of the selected exam into the UI.
	 * if there are no exams to approve, warning the lecturer
	 * This method is called after receiving the finished exams from the server.
	 * It updates the UI components with the new data.
	 *
	 * @param finishedExams The list of finished exams to be loaded into the UI.
	 */
	public void loadAllFinishedExamsOfSelectedExam(ArrayList<FinishedExam> finishedExams) {
	    // Run the UI update on the JavaFX application thread
	    Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	        	if(finishedExams.isEmpty()) {
					displayErrorMessage("Error: No exams to approve!");
	        	}
	        	else {
		            // Set the finished exams to the observable list
		            finishedExams_observablelist.setAll(finishedExams);
	
		            // Set the observable list as the items of the table view
		            finishedExams_tableView.setItems(finishedExams_observablelist);
	
		            finishedExams_tableView.refresh();
	
		            // Clear the selection in the table view
		            finishedExams_tableView.getSelectionModel().clearSelection();
	
		            // Get suspect exams for cheating
		            getSuspectExamsForCheating();
	        	}
	        }
	    });
	}
	
	/**
	 * Displays an error message using a Snackbar.
	 *
	 * @param message The error message to display.
	 */
	public void displayErrorMessage(String message) {
	    Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
				snackbar = new JFXSnackbar(root);
				String css = this.getClass().getClassLoader().getResource("css/SnackbarError.css").toExternalForm();
		        snackbar.setPrefWidth(root.getPrefWidth() - 40);
		        snackbarLayout = new JFXSnackbarLayout(message);
		        snackbarLayout.getStylesheets().add(css);
		        snackbar.getStylesheets().add(css);
		        snackbar.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
	        }
	    });
	}
	
	/**
	 * Displays a success message using a Snackbar.
	 *
	 * @param message The success message to display.
	 */
	public void displaySuccessMessage(String message) {
	    Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            snackbar = new JFXSnackbar(root);
				String css = this.getClass().getClassLoader().getResource("css/SnackbarSuccess.css").toExternalForm();
				snackbar.setPrefWidth(root.getPrefWidth() - 40);
				snackbarLayout = new JFXSnackbarLayout(message);
				snackbarLayout.getStylesheets().add(css);
				snackbar.getStylesheets().add(css);
				snackbar.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
	        }
	    });
	}

}
