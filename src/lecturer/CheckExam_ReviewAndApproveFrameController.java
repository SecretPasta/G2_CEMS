package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;

import ClientAndServerLogin.SceneManagment;
import Config.FinishedExam;
import Config.Lecturer;
import Config.QuestionInExam;
import client.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	private AnchorPane root;
	@FXML
	private JFXSnackbar snackbar;
	private JFXSnackbarLayout snackbarLayout;

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

	/**
	 * Handles the event when the "Approve Grade" button is clicked.
	 * 
	 * @param event The event triggered by the button click.
	 * @throws IOException If an error occurs during the handling process.
	 */
	@FXML
	void getBtnApproveGrade(ActionEvent event) throws IOException {
	    // Check if the new grade is provided without comments
	    if (!(txtNewGrade.getText().trim().equals("")) && txtCommentForNewGrade.getText().trim().equals("")) {
			displayErrorMessage("Error: You must write comments to the student for changing the grade!");
	    } else {
	        approve_SetGrade_FinishedExam(); // send to the server to approve

	        ((Node) event.getSource()).getScene().getWindow().hide();
	        CheckExam_ChooseStudentFrameController.getInstance().showStageFrom_StudentList(finishedExamSelected); // to update the table in the previous screen
	    }
	}


	/**
	 * Approves and sets the grade for the finished exam by the lecturer.
	 */
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


	/**
	 * Handles the event when the "Back" button is clicked.
	 *
	 * @param event The event triggered by the button click.
	 * @throws IOException If an error occurs during the handling process.
	 */
	@FXML
	void getBtnBack(ActionEvent event) throws IOException {
	    ((Node) event.getSource()).getScene().getWindow().hide();
	    CheckExam_ChooseStudentFrameController.getInstance().showStageFrom_StudentList(null);
	}


	/**
	 * Handles the event when the "Change Grade" button is clicked.
	 *
	 * @param event The event triggered by the button click.
	 */
	@FXML
	void getBtnChangeGrade(ActionEvent event) {
	    try {
	        if (txtNewGrade.getText().trim().equals("") || txtNewGrade == null) {
				displayErrorMessage("Error: New Grade field is empty!");
			} else if (Double.parseDouble(txtNewGrade.getText()) < 0
					|| Double.parseDouble(txtNewGrade.getText()) > 100) {
	            throw new NumberFormatException();
	        } else {
	            finishedExamSelected.setGrade(Double.parseDouble(txtNewGrade.getText()));
				displaySuccessMessage("Exam grade changed to: " + finishedExamSelected.getGrade() + "!");
	        }
	    } catch (NumberFormatException e) {
			displayErrorMessage("Error: New grade must be in range [0-100]!");
	        txtNewGrade.clear();
	    }
	}


	/**
	 * Starts the Check Exam feature for the lecturer.
	 *
	 * @param lecturer_temp The lecturer object.
	 * @param finishedExamSelected_temp The selected finished exam object.
	 * @throws IOException If an error occurs during the process.
	 */
	public static void start(Lecturer lecturer_temp, FinishedExam finishedExamSelected_temp) throws IOException {
	    lecturer = lecturer_temp;
	    finishedExamSelected = finishedExamSelected_temp;

		currStage = SceneManagment.createNewStage("/lecturer/CheckExam_ReviewAndApprove.fxml", null,
				"Lecturer->CheckExam->ApproveGrade");
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
			lblAutoGrade.setStyle("-fx-text-fill:  #5DD299; -fx-font-weight: bold;");
		}
		else {
			lblAutoGrade.setText(Double.toString(finishedExamSelected.getGrade()) + " (Failed)");
			lblAutoGrade.setStyle("-fx-text-fill:  #FE774C; -fx-font-weight: bold;");
		}
		
		
	}


	/**
	 * Loads the questions in the exam to the VBox.
	 */
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

	                if (j == 0) { // correct answer
	                    answerLabel.setStyle("-fx-background-color: #5DD299; -fx-font-weight: bold;");
	                } else {
	                    if (studentAnswers[i] != null && studentAnswers[i].equals(question.getAnswers().get(j))) {
	                        answerLabel.setStyle("-fx-background-color: #FE774C; -fx-font-weight: bold;");
	                    }
	                    if (studentAnswers[i] == null || studentAnswers[i].trim().equals("")) {
	                        questionLabel.setStyle("-fx-background-color: #FE774C; -fx-font-weight: bold;");
	                        //System.out.println(1);
	                    }
	                }

	                vbox.getChildren().add(answerLabel);
	                answerLetter++;
	            }

	            Label spaceLabel = new Label("\n\n");
	            vbox.getChildren().add(spaceLabel);
	            i++;
	        }
	    } catch (ArrayIndexOutOfBoundsException e) {}
	}


	/**
	 * Retrieves the questions in the exam.
	 */
	private void getQuestionsInExam() {
	    ArrayList<String> getquestioninexam_arr = new ArrayList<>();
	    getquestioninexam_arr.add("GetQuestionsInExamToAproove");
	    getquestioninexam_arr.add(finishedExamSelected.getExamID());
	    ClientUI.chat.accept(getquestioninexam_arr);
	}

	
	/**
	 * Saves the questions.
	 *
	 * @param questionsInExam_temp The list of questions in the exam to be saved.
	 */
	public static void saveQuestionsInExam(ArrayList<QuestionInExam> questionsInExam_temp) {
	    questionsInExam = questionsInExam_temp;
	}
	
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
