package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import ClientAndServerLogin.SceneManagment;
import Config.Exam;
import Config.Lecturer;
import Config.QuestionInExam;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ManageExam_ChangeTimeFrameController implements Initializable {
	
	@FXML
	private TextField txtNewExamDuration;
	@FXML
	private TextField txtExplanationExamDurationChange;

	private static Exam exam;
	private static Lecturer lecturer;

	public static void start(Exam temp_exam, Lecturer temp_lecturer) throws IOException {
	    exam = temp_exam; // save the exam
	    lecturer = temp_lecturer;
	    SceneManagment.createNewStage("/lecturer/ManageExam_ChangeTimeGUI.fxml", null, "Manage Exam").show();
	}
	
	public void getBtnSendRequest(ActionEvent event) throws Exception {
		if(txtExplanationExamDurationChange.getText().trim().equals("") || txtNewExamDuration.getText().trim().equals("")) {
			System.out.println("[Error] Missing fields.");
		}
		else {
			ArrayList<String> infoOfRequest_Arr = new ArrayList<>();
			infoOfRequest_Arr.add("RequestToChangeAnExamDurationFromLecturerToHOD");
			infoOfRequest_Arr.add(exam.getExamID());
			infoOfRequest_Arr.add(exam.getSubjectName());
			infoOfRequest_Arr.add(exam.getCourseName());
			infoOfRequest_Arr.add(lecturer.getId());
			infoOfRequest_Arr.add(lecturer.getName());
			infoOfRequest_Arr.add(txtExplanationExamDurationChange.getText());
			infoOfRequest_Arr.add(txtNewExamDuration.getText());
			ClientUI.chat.accept(infoOfRequest_Arr);
		}
	}
	
	public void getBtnBack(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // Hide the primary window
		LecturerDashboardFrameController.getInstance().showDashboardFrom_ChangeTime();
	}


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}

