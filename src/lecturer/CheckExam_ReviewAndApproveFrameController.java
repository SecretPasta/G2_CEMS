package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import ClientAndServerLogin.SceneManagment;
import Config.Exam;
import Config.FinishedExam;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckExam_ReviewAndApproveFrameController implements Initializable {

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
	
	private static ArrayList<QuestionInExam> questionsInExam = new ArrayList<>();

	private static Stage currStage; // save current stage
	
	private static Lecturer lecturer;
	private static FinishedExam finishedExamSelected;
	private String[] studentAnswers;

	@FXML
	void getBtnApproveGrade(ActionEvent event) throws IOException {
		
		if(!(txtNewGrade.getText().trim().equals("")) && txtCommentForNewGrade.getText().trim().equals("")) {
			System.out.println("[Error] You must write comments to student for changing the grade");
		}
		else {
			
			// database stuff
			
			// change approved to 1
			// change the grade: finishedExamSelected.getGrade();
			approve_SetGrade_FinishedExam();
			
			
			
			
			((Node) event.getSource()).getScene().getWindow().hide();
			CheckExam_ChooseStudentFrameController.getInstance().showStageFrom_StudentList(finishedExamSelected); // to update the table in the previous screen
			
		}
		
		
		


	}

	private void approve_SetGrade_FinishedExam() {
		ArrayList<String> approveexam_arr = new ArrayList<>();
		approveexam_arr.add("ApproveAndSetGradeForFinishedExamByLecturer");
		approveexam_arr.add(finishedExamSelected.getExamID());
		approveexam_arr.add(finishedExamSelected.getStudentID());
		approveexam_arr.add(Double.toString(finishedExamSelected.getGrade()));
		approveexam_arr.add(finishedExamSelected.getCourseName());
		approveexam_arr.add(lecturer.getName());
		approveexam_arr.add(txtCommentsStudent.getText());
		approveexam_arr.add(txtCommentForNewGrade.getText());
		ClientUI.chat.accept(approveexam_arr);
		
	}

	@FXML
	void getBtnBack(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();

		CheckExam_ChooseStudentFrameController.getInstance().showStageFrom_StudentList(null);

	}

	@FXML
	void getBtnChangeGrade(ActionEvent event) {
		
		try {
			if(txtNewGrade.getText().trim().equals("") || txtNewGrade == null) {
				System.out.println("[Error] New grade field is empty");
			}
			else if(Double.parseDouble(txtNewGrade.getText()) < 0){
				throw new NumberFormatException();
			}
			else {
				finishedExamSelected.setGrade(Double.parseDouble(txtNewGrade.getText()));
				System.out.println("exam grade changed to: " + finishedExamSelected.getGrade());
			}
		
		}catch (NumberFormatException e) {
			System.out.println("[Error] New grade must be valid numver >=0");
			txtNewGrade.clear();
		}

	}

	public static void start(Lecturer luecturer_temp, FinishedExam finishedWxamSelected_temp) throws IOException {
		
		lecturer = luecturer_temp;
		finishedExamSelected = finishedWxamSelected_temp;
		
		currStage = SceneManagment.createNewStage("/lecturer/CheckExam_ReviewAndApprove.fxml", null, "Check Exam");
		currStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getQuestionsInExam();
		
		loadQuestionsInExamToVBox();
		
		lblSubjectName.setText(finishedExamSelected.getSubjectName() + " (" + finishedExamSelected.getSubjectID() + ")");
		lblCourseName.setText(finishedExamSelected.getCourseName() + " (" + finishedExamSelected.getCourseID() + ")");
		lblStudent.setText(finishedExamSelected.getStudentID());
		
		if(finishedExamSelected.getGrade() >= 55.0) {
			lblAutoGrade.setText(Double.toString(finishedExamSelected.getGrade()) + " (Passed)");
			//lblAutoGrade.setStyle("-fx-color: green; -fx-font-weight: bold;");
		}
		else {
			lblAutoGrade.setText(Double.toString(finishedExamSelected.getGrade()) + " (Failed)");
			//lblAutoGrade.setStyle("-fx-color: red; -fx-font-weight: bold;");
		}
		
		
	}


	private void loadQuestionsInExamToVBox() {
		try {
			studentAnswers = finishedExamSelected.getAnswers().split("\\|");
	
			int i = 0; // numbering the questions
	        for (QuestionInExam question : questionsInExam) {
	        	// place the question in the vbox
	            Label questionLabel = new Label((i+1) + ") " + question.getQuestionText() + "( " + question.getPoints() + " points )");
				questionLabel.setStyle("-fx-font-weight: bold");
	            vbox.getChildren().add(questionLabel);
	
				char answerLetter = 'a';
	            // place the answers in the vbox
				Label answerLabel;
	            for (int j = 0; j < question.getAnswers().size(); j++) {
	            	
					answerLabel = new Label("  " + answerLetter + ") " + question.getAnswers().get(j));
					
					if(j == 0) { // correct answer
						answerLabel.setStyle("-fx-background-color: green; -fx-font-weight: bold;");
					}
					else {
						if(studentAnswers[i] != null && studentAnswers[i].equals(question.getAnswers().get(j))) {
							answerLabel.setStyle("-fx-background-color: red; -fx-font-weight: bold;");
						}
						if(studentAnswers[i] == null || studentAnswers[i].trim().equals("")) {
							questionLabel.setStyle("-fx-background-color: red; -fx-font-weight: bold;");
							System.out.println(1);
						}
					}
					
	
					
					vbox.getChildren().add(answerLabel);
					answerLetter++;
	            }
	
	            Label spaceLabel = new Label("\n\n");
	            vbox.getChildren().add(spaceLabel);
	            i++;
	        }
		}catch (ArrayIndexOutOfBoundsException e) {}
		
	}

	private void getQuestionsInExam() {
		ArrayList<String> getquestioninexam_arr = new ArrayList<>();
		getquestioninexam_arr.add("GetQuestionsInExamToAproove");
		getquestioninexam_arr.add(finishedExamSelected.getExamID());
		ClientUI.chat.accept(getquestioninexam_arr);
	}
	
	public static void saveQuestionsInExam(ArrayList<QuestionInExam> questionsInExam_temp) {
		questionsInExam = questionsInExam_temp;
	}

}
