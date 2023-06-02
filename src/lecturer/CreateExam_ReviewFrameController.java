package lecturer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Random;

import ClientAndServerLogin.SceneManagment;
import Config.Exam;
import Config.Lecturer;
import Config.QuestionInExam;
import client.ClientUI;

public class CreateExam_ReviewFrameController implements Initializable {
	
	@FXML
	private VBox vbox;
	
	@FXML
	private Label lblStudentID;
	@FXML
	private Label lblSubjectName;
	@FXML
	private Label lblCourseName;
	@FXML
	private Label lblDate;
	@FXML
	private Label lblExamID;
	@FXML
	private Label lblAuthor;
	@FXML
	private Label lblExamDuration;
	@FXML
	private Label lblCommentsForLecturer;
	@FXML
	private Label lblCommentsForStudent;
	
	private static String maxExamIdInCourse;
	
	private static Lecturer lecturer;


	private ObservableList<RadioButton> checkBoxes;

	private static Exam exam;

    public static void start(Exam temp_exam, Lecturer temp_lecturer) throws IOException {
    	exam = temp_exam;
    	lecturer = temp_lecturer;
    	SceneManagment.createNewStage("/lecturer/CreateExam_ReviewGUI.fxml", null, "Create Exam").show();

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		getMaxIdOfExamInCourse();
		
		lblStudentID.setText("12345678");
		lblSubjectName.setText(exam.getSubjectName() + " (" + exam.getSubjectID() + ")");
		lblCourseName.setText(exam.getCourseName() + " (" + exam.getCourseID() + ")");
		lblDate.setText(LocalDate.now().toString());
		lblExamID.setText(maxExamIdInCourse);
		lblAuthor.setText(exam.getAuthor());
		lblExamDuration.setText(Integer.toString(exam.getDuration()));
		lblCommentsForLecturer.setText(exam.getCommentsForLecturer());
		lblCommentsForStudent.setText(exam.getCommentsForStudent());

		Random random = new Random();
		
        checkBoxes = FXCollections.observableArrayList();

        int i = 1;
        for (QuestionInExam question : exam.getQuestions()) {
            Label questionLabel = new Label(i + ") " + question.getQuestionText() + "( " + question.getPoints() + " points )");
            vbox.getChildren().add(questionLabel);
            
            ToggleGroup answers_group = new ToggleGroup();
            
            int correctAnswer_place = 0;
            String tempAnswer_Correct = question.getAnswers().get(0);
            
            int wrongAnswer_place = random.nextInt(question.getAnswers().size()-1) + 1;       
            
            question.getAnswers().set(correctAnswer_place, question.getAnswers().get(wrongAnswer_place));
            
            question.getAnswers().set(wrongAnswer_place, tempAnswer_Correct);
            
            for (String answer : question.getAnswers()) {
            	RadioButton checkBox = new RadioButton(answer);
                checkBoxes.add(checkBox);
                vbox.getChildren().add(checkBox);
                checkBox.setToggleGroup(answers_group);
            }
            
            Label spaceLabel = new Label("\n\n");
            vbox.getChildren().add(spaceLabel);
            i++;
        }
  

    }
	
	public void getBtnSaveExam(ActionEvent event) throws Exception {
		// saveExam();
		// saveAllQuestionsInExam();
	}
	
	public void getBtnBack(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		CreateExam_CommentsAndTimeFrameController.showStageFrom_Review();
	}
	
	public void getMaxIdOfExamInCourse(){
		ArrayList<String> getmaxexamidfromcourse_arr = new ArrayList<>();
		getmaxexamidfromcourse_arr.add("GetMaxExamIdFromCourse");
		getmaxexamidfromcourse_arr.add(exam.getCourseID());
		ClientUI.chat.accept(getmaxexamidfromcourse_arr);
	}
	
	public static void saveIdOfExamInCourse(String maxExamIdInCourse_temp) {
		maxExamIdInCourse = "" + exam.getSubjectID() + exam.getCourseID() + maxExamIdInCourse_temp;
	}
}

