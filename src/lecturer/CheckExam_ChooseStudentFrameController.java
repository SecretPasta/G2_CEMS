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

	public static void showStageFrom_StudentList() throws IOException {
		currStage.show();
	}

	@FXML
	void getBtnShowExam(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		CheckExam_ReviewAndApproveFrameController.start(); // starting the exam review screen.

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
            	
            }
        });
    }


}
