package lecturer;

import java.io.IOException;
import java.util.HashMap;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
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
			if(finishedExamSelected_temp != null) {
				// message to lecturer for succeed
				System.out.println("Exam approved!");
			}
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
	
	
	public void getSuspectExamsForCheating() {
		
		Map<FinishedExam, ArrayList<FinishedExam>> examsWithSameAnswers = new HashMap<>();

        for (int i = 0; i < finishedExams_observablelist.size(); i++) {
            FinishedExam exam1 = finishedExams_observablelist.get(i);

            for (int j = i + 1; j < finishedExams_observablelist.size(); j++) {
                FinishedExam exam2 = finishedExams_observablelist.get(j);

                if (exam1.getAnswers().equals(exam2.getAnswers())) {

                	ArrayList<FinishedExam> exams1 = examsWithSameAnswers.get(exam1);
                    if (exams1 == null) {
                        exams1 = new ArrayList<>();
                        examsWithSameAnswers.put(exam1, exams1);
                    }
                    exams1.add(exam2);

                    ArrayList<FinishedExam> exams2 = examsWithSameAnswers.get(exam2);
                    if (exams2 == null) {
                        exams2 = new ArrayList<>();
                        examsWithSameAnswers.put(exam2, exams2);
                    }
                    exams2.add(exam1);
                }
            }
        }

        ArrayList<String> cheaters_list = new ArrayList<>();

        for (Map.Entry<FinishedExam, ArrayList<FinishedExam>> entry : examsWithSameAnswers.entrySet()) {
        	
        	StringBuilder modified_cheater = new StringBuilder();
        	
            FinishedExam exam = entry.getKey();
            ArrayList<FinishedExam> exams = entry.getValue();
            modified_cheater.append("Student ID: " + exam.getStudentID());
            modified_cheater.append(" same answers as Students ID: \n");
            for(FinishedExam examcpy : exams) {
            	modified_cheater.append("                              ");
            	modified_cheater.append(examcpy.getStudentID());
            }
            cheaters_list.add(modified_cheater.toString());

        }	
        
        examsCheating_observablelist.setAll(cheaters_list);	
        examsCheating_listView.setItems(examsCheating_observablelist);
        examsCheating_listView.refresh();
        
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

		//getSuspectExamsForCheating();
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
				getSuspectExamsForCheating();
            	
            }
        });
    }


}
