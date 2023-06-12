package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSnackbar;

import ClientAndServerLogin.SceneManagment;
import Config.Exam;
import Config.Lecturer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CheckExam_ChooseStudentFrameController implements Initializable {

	@FXML
	private TableColumn<?, ?> answers_tableExam;

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
	private TableColumn<?, ?> studentIdColumn_tableExam;

	@FXML
	private TableColumn<?, ?> studentNameColumn_tableExam;

	@FXML
	private TableView<?> tableExam;

	private static Stage currStage; // save current stage
	
	private static Lecturer luecturer;
	private static Exam examSelectedForChecking;

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
		// TODO Auto-generated method stub

	}

}
