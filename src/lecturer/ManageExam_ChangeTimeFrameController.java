package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import ClientAndServerLogin.SceneManagment;
import Config.Exam;
import Config.HeadOfDepartment;
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
	private TextField txtExamDuration; // can be negative
	@FXML
	private TextField txtExplanationExamDurationChange;
	
	@FXML
	private JFXComboBox<String> hodSelectBox;

	private static Exam exam;
	private static Lecturer lecturer;
	private String hodSelected;
	
	private static ManageExam_ChangeTimeFrameController instance;
	
	public ManageExam_ChangeTimeFrameController() {
		instance = this;
	}
	
	public static ManageExam_ChangeTimeFrameController getInstance() {
		return instance;
	}

	public static void start(Exam temp_exam, Lecturer temp_lecturer) throws IOException {
	    exam = temp_exam; // save the exam
	    lecturer = temp_lecturer;
	    SceneManagment.createNewStage("/lecturer/ManageExam_ChangeTimeGUI.fxml", null, "Manage Exam").show();
	}
	
	public void getBtnSendRequest(ActionEvent event) throws Exception {
		
		
		
		hodSelected = hodSelectBox.getSelectionModel().getSelectedItem();
		
		if(txtExplanationExamDurationChange.getText().trim().equals("") || txtExamDuration.getText().trim().equals("")
				|| hodSelected == null || hodSelected.equals("Please select Head Of Department")) {
			System.out.println("[Error] Missing fields."); // error
		}
		else {
			
			String[] hod_name_id = hodSelected.split(" - ");
			
			try {

			    int number = Integer.parseInt(txtExamDuration.getText());
			    if(number <= 0) {
			    	throw new NumberFormatException();
			    }
			
				ArrayList<String> infoOfRequest_Arr = new ArrayList<>();
				infoOfRequest_Arr.add("RequestToChangeAnExamDurationFromLecturerToHOD");
				infoOfRequest_Arr.add(exam.getExamID());
				infoOfRequest_Arr.add(exam.getSubjectName());
				infoOfRequest_Arr.add(exam.getCourseName());
				infoOfRequest_Arr.add(lecturer.getId());
				infoOfRequest_Arr.add(lecturer.getName());
				infoOfRequest_Arr.add(txtExplanationExamDurationChange.getText());
				infoOfRequest_Arr.add(txtExamDuration.getText());
				infoOfRequest_Arr.add(hod_name_id[1]);
				ClientUI.chat.accept(infoOfRequest_Arr);
				
				System.out.println("Request gor changing the time of the exam sent succesfuly to the head of department!"); // succees
				
				getBtnBack(event);


			} catch (NumberFormatException e) {
				System.out.println("only positives minutes"); // error
			}
		}	
		
	}
	
	public void getBtnBack(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // Hide the primary window
		LecturerDashboardFrameController.getInstance().showDashboardFrom_ChangeTime();
	}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	hodSelected = null;
    	hodSelectBox.getItems().add("Please select Head Of Department");
    	
    	ArrayList<String> hod_arr = new ArrayList<>();
    	hod_arr.add("GetRelevantHodForLecturer");
    	hod_arr.add(lecturer.getId());
    	ClientUI.chat.accept(hod_arr);
    }
    
    public void loadHODsForLecturer(ArrayList<HeadOfDepartment> hod_arr){
	    for (HeadOfDepartment hods : hod_arr) {
	    		hodSelectBox.getItems().add(hods.toString());
	    }
    }


}

