package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import ClientAndServerLogin.SceneManagment;
import Config.Exam;
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
import javafx.scene.layout.VBox;

public class CreateExam_ReviewFrameController implements Initializable {
	
	@FXML
	private VBox vbox;
	@FXML
	private Label lblSubjectName;
	@FXML
	private Label lblCourseName;
	@FXML
	private Label lblAuthor;
	@FXML
	private Label lblExamDuration;
	@FXML
	private Label lblCommentsForLecturer;
	@FXML
	private Label lblCommentsForStudent;
	
	private static String maxExamIdInCourse;

	private static Exam exam;

	
	/**
	 * Starts the Create Exam Review GUI.
	 * @param temp_exam The exam object to be reviewed and edited.
	 * @throws IOException If an error occurs during GUI creation.
	 */
	public static void start(Exam temp_exam) throws IOException {
	    exam = temp_exam; // save the exam
	    // Create a new stage for the Create Exam Review GUI
	    SceneManagment.createNewStage("/lecturer/CreateExam_ReviewGUI.fxml", null, "Create Exam").show();
	}


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadExamIntoGUI();

        Random random = new Random();


        int i = 1; // numbering the questions
        for (QuestionInExam question : exam.getQuestions()) {
        	
        	// place the question in the vbox
            Label questionLabel = new Label(i + ") " + question.getQuestionText() + "( " + question.getPoints() + " points )");
			questionLabel.setStyle("-fx-font-weight: bold");
            vbox.getChildren().add(questionLabel);

            // shuffling the answers
            int correctAnswer_place = 0; // Index of the correct answer in the list
            String tempAnswer_Correct = question.getAnswers().get(0); // Store the first answer as temporary correct answer
            int wrongAnswer_place = random.nextInt(question.getAnswers().size() - 1) + 1; // Generate a random index for a wrong answer
            question.getAnswers().set(correctAnswer_place, question.getAnswers().get(wrongAnswer_place)); // Swap the correct answer with a randomly chosen wrong answer
            question.getAnswers().set(wrongAnswer_place, tempAnswer_Correct); // Set the wrong answer index with the original correct answer

			char answerLetter = 'a';
            // place the answers in the vbox
            for (String answer : question.getAnswers()) {
				Label answerLabel = new Label("  " + answerLetter + ") " + answer);
				vbox.getChildren().add(answerLabel);
				answerLetter++;
            }

            Label spaceLabel = new Label("\n\n");
            vbox.getChildren().add(spaceLabel);
            i++;
        }

    }

	
    /*
     * load all the exam's details to the screen
     */
	private void loadExamIntoGUI() {
		lblSubjectName.setText(exam.getSubjectName() + " (" + exam.getSubjectID() + ")");
		lblCourseName.setText(exam.getCourseName() + " (" + exam.getCourseID() + ")");
		lblAuthor.setText(exam.getAuthor());
		lblExamDuration.setText(Integer.toString(exam.getDuration()) + " minutes");
		lblCommentsForLecturer.setText(exam.getCommentsForLecturer());
		lblCommentsForStudent.setText(exam.getCommentsForStudent());
	}

	/**
	 * Saves the exam and its questions.
	 *
	 * @param event The action event triggered by the "Save" button.
	 * @throws Exception If an error occurs during the save process.
	 */
	public void getBtnSaveExam(ActionEvent event) throws Exception {
	    get_update_MaxExamIdByCourse(); // Update the maximum exam ID by course

	    String formattedExamNum = String.format("%02d", Integer.parseInt(maxExamIdInCourse) + 1); // Format the exam number with leading zeros

	    exam.setExamID(formattedExamNum); // Set the formatted exam number as the exam ID

	    saveExam(); // Save the exam in DB
	    saveQuestionInExam(); // Save the questions in the exam in the DB

	    ((Node) event.getSource()).getScene().getWindow().hide(); // Hide the current window

	    // Show the lecturer dashboard and pass the updated exam object
	    LecturerDashboardFrameController.getInstance().showDashboardFrom_Review(exam);
	}

	
	/**
	 * Saves the questions in the exam in DB.
	 */
	public void saveQuestionInExam() {
	    // Create a new ArrayList to store the questions to be saved
	    ArrayList<QuestionInExam> questionsToSave_arr = new ArrayList<>();

	    // Add a "SaveAllQuestionsInExam" placeholder question with the exam ID
	    questionsToSave_arr.add(new QuestionInExam("SaveAllQuestionsInExam", exam.getExamID(), null, null));

	    // Add all the questions from the exam to the list
	    questionsToSave_arr.addAll(exam.getQuestions());

	    // Send the questions to be saved to the server
	    ClientUI.chat.accept(questionsToSave_arr);
	}



	/**
	 * Saves the exam in the database.
	 */
	public void saveExam() {
	    // Create a new ArrayList to store the exam to be saved
	    ArrayList<Exam> examToSave_arr = new ArrayList<>();

	    // Add a "SaveExamInDB" placeholder exam
	    examToSave_arr.add(new Exam("SaveExamInDB", null, null, null, null, null, null, null, 0, null, null, null));

	    // Add the current exam to the list
	    examToSave_arr.add(exam);

	    // Send the exam to be saved to the server
	    ClientUI.chat.accept(examToSave_arr);
	}

	
	/**
	 * Handles the action when the "Back" button is clicked.
	 *
	 * @param event The action event triggered by the "Back" button.
	 * @throws Exception If an error occurs during the process.
	 */
	public void getBtnBack(ActionEvent event) throws Exception {
	    // Hide the current window
	    ((Node) event.getSource()).getScene().getWindow().hide();

	    // Show the CreateExam_CommentsAndTimeFrameController stage from the review
	    CreateExam_CommentsAndTimeFrameController.showStageFrom_Review();
	}

	
	/**
	 * Gets and updates the maximum exam ID for a specific course.
	 */
	public void get_update_MaxExamIdByCourse() {
	    // Create a new ArrayList to send the request for the maximum exam ID
	    ArrayList<String> getmaxexamidfromcourse_arr = new ArrayList<>();

	    // Add the command to get and update the maximum exam ID
	    getmaxexamidfromcourse_arr.add("GetUpdateMaxExamIdFromCourse");

	    // Add the course ID to the list
	    getmaxexamidfromcourse_arr.add(exam.getCourseID());

	    // Send the request to the server to get and update the maximum exam ID for the course
	    ClientUI.chat.accept(getmaxexamidfromcourse_arr);
	}

	
	/**
	 * Saves the maximum exam ID for a course ( received from the server ).
	 *
	 * @param maxExamIdInCourse_temp The maximum exam ID to be saved.
	 */
	public static void saveIdOfExamInCourse(String maxExamIdInCourse_temp) {
	    maxExamIdInCourse = maxExamIdInCourse_temp;
	}

}

