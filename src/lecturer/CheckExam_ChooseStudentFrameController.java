package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSnackbar;

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
	private JFXSnackbar snackbarError;

	@FXML
	private TableColumn<FinishedExam, String> studentIdColumn_tableExam;

	@FXML
	private TableColumn<FinishedExam, String> studentgradeColumn_tableExam;

	@FXML
	private TableView<FinishedExam> finishedExams_tableView;
	
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

	public static void start(Exam examSelectedForChecking_temp, Lecturer lecturer_temp) throws IOException {
		luecturer = lecturer_temp;
		examSelectedForChecking = examSelectedForChecking_temp;
		currStage = SceneManagment.createNewStage("/lecturer/CheckExam_ChooseStudent.fxml", null, "Check Exam");
		currStage.show();
	}

	@FXML
	void getBtnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		LecturerDashboardFrameController.getInstance().showDashboardFrom_CheckExam(); // Previous screen

	}

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

	        finishedExams_tableView.refresh();
			
			
		} catch (NullPointerException e) {}
		currStage.show();
	}

	@FXML
	void getBtnShowExam(ActionEvent event) throws IOException {
		
		
		
		
		try {
			
			finishedExamSelected = finishedExams_tableView.getSelectionModel().getSelectedItem();
			
			if (finishedExamSelected == null) {
				throw new NullPointerException();
			}
			
			finishedExamSelected.setSubjectName(examSelectedForChecking.getSubjectName());
			finishedExamSelected.setCourseName(examSelectedForChecking.getCourseName());
			finishedExamSelected.setSubjectID(examSelectedForChecking.getSubjectID());
			finishedExamSelected.setCourseID(examSelectedForChecking.getCourseID());
			
			((Node) event.getSource()).getScene().getWindow().hide();
			CheckExam_ReviewAndApproveFrameController.start(luecturer, finishedExamSelected);
			
		} catch (NullPointerException e) {
			//displayErrorMessage("Error: Student exam was not selected");
			System.out.println("Error: Student exam was not selected");
		}
			    
		finishedExams_tableView.getSelectionModel().clearSelection();

		finishedExamSelected = null;
		
		


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

	private void getAllFinishedExamsOfSelectedExam() {
		ArrayList<String> finishedExamsReq_arr = new ArrayList<>();
		finishedExamsReq_arr.add("GetAllExamsByExamIDForLecturerForChecking");
		finishedExamsReq_arr.add(examSelectedForChecking.getExamID());
		ClientUI.chat.accept(finishedExamsReq_arr);
	}
	
	public void loadAllFinishedExamsOfSelectedExam(ArrayList<FinishedExam> finishedExams) {
		
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	
                finishedExams_observablelist.setAll(finishedExams);	
				finishedExams_tableView.setItems(finishedExams_observablelist);
				finishedExams_tableView.refresh();
				finishedExams_tableView.getSelectionModel().clearSelection();
            	
            }
        });
    }


}
