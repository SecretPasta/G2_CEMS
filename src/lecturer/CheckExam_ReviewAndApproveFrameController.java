package lecturer;

import java.io.IOException;

import ClientAndServerLogin.SceneManagment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckExam_ReviewAndApproveFrameController {

	@FXML
	private Label lblAutoGrade;

	@FXML
	private Label lblCourseName;

	@FXML
	private Label lblStudent;

	@FXML
	private Label lblSubjectName;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private TextField txtCommentForNewGrade;

	@FXML
	private TextField txtCommentsStudent;

	@FXML
	private TextField txtNewGrade;

	@FXML
	private VBox vbox;

	private static Stage currStage; // save current stage

	@FXML
	void getBtnApproveGrade(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();

		CheckExam_ChooseStudentFrameController.showStageFrom_StudentList();

	}

	@FXML
	void getBtnBack(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();

		CheckExam_ChooseStudentFrameController.showStageFrom_StudentList();

	}

	@FXML
	void getBtnChangeGrade(ActionEvent event) {


	}

	public static void start() throws IOException {
		currStage = SceneManagment.createNewStage("/lecturer/CheckExam_ReviewAndApprove.fxml", null, "Check Exam");
		currStage.show();
	}

}
