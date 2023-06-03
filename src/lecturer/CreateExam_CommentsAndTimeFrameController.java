package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import Config.Exam;
import ClientAndServerLogin.SceneManagment;
import Config.Lecturer;
import Config.Question;
import Config.QuestionInExam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private Label lblMessage;
	
	protected static Stage currStage; // save current stage
	
	private static Lecturer lecturer;
	private static ObservableList<QuestionInExam> questionsToCreateExamObservableList = FXCollections.observableArrayList();
	private static String subjectID;
	private static String courseID;
	private static String subjectName;
	private static String courseName;
	
	
    public static void start(Lecturer temp_lecturer, ObservableList<QuestionInExam> temp_questionsToCreateExamObservableList, 
    		String subjectID_temp, String subjectName_temp, String courseID_temp, String courseName_temp) throws IOException {
    	lecturer = temp_lecturer;
    	questionsToCreateExamObservableList = temp_questionsToCreateExamObservableList;
    	subjectID = subjectID_temp;
    	subjectName = subjectName_temp;
    	courseID = courseID_temp;
    	courseName = courseName_temp;
    	currStage = SceneManagment.createNewStage("/lecturer/CreateExam_CommentsAndTimeGUI.fxml", null, "Create Exam");
    	currStage.show();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblMessage.setText("");
		idColumn_tableExam.setCellValueFactory(new PropertyValueFactory<QuestionInExam, String>("id"));
		questionTextColumn_tableExam.setCellValueFactory(new PropertyValueFactory<QuestionInExam, String>("questionText"));    
		pointsColumn_tableExam.setCellValueFactory(new PropertyValueFactory<QuestionInExam, Double>("points")); 
		tableExam.setItems(questionsToCreateExamObservableList);
		tableExam.getSelectionModel().clearSelection();
		
	}
	
    public void getBtnShowReview(ActionEvent event) throws Exception {
    	try {
	    	if(txtExamDuration.getText().trim().equals("") || txtExamCode.getText().trim().equals("")) {
	    		lblMessage.setText("[Error] Missing fields.");
	    	}
	    	else {
	    		if(txtExamCode.getText().length() != 4) {
	    			lblMessage.setText("[Error] exam code has to be 4 digits.");
	    			return;
	    		}
	    		int examDuration = Integer.parseInt(txtExamDuration.getText());
	    		
	    		if(examDuration <= 0) {
	    			throw new NumberFormatException();
	    		}
	    		
	    		ArrayList<QuestionInExam> questionsInExam_arr = new ArrayList<>();
	    		questionsInExam_arr.addAll(questionsToCreateExamObservableList);
	    		
	    		/*
	    		 * 	Exam(String subjectID,String courseID, ArrayList<QuestionInExam> questions, 
	    				String commentsForLecturer, String commentsForStudent, int duration, String author)
	    		 */

	    		Exam exam = new Exam(null, subjectID, subjectName, courseID, courseName,  
	    				questionsInExam_arr, txtCommentsLecturer.getText(), txtCommentsStudent.getText(), 
	    				examDuration, lecturer.getName(), txtExamCode.getText());
	    		
	    		((Node) event.getSource()).getScene().getWindow().hide();
	    		CreateExam_ReviewFrameController.start(exam, lecturer);
	    		
	    		
	    	}
    	}catch (NumberFormatException | NullPointerException e) {
    		lblMessage.setText("[Error] Missing fields.");
    	}
    }
    
    public static void showStageFrom_Review() throws IOException {
    	currStage.show();
    }
	
    public void getBtnBack(ActionEvent event) throws Exception {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	LecturerDashboardFrameController.getInstance().showDashboardFrom_CreateExam(/*questionsToCreateExamObservableList*/);
    }

}
