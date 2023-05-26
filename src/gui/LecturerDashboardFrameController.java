package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Config.Lecturer;
import Config.Question;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LecturerDashboardFrameController implements Initializable{
	
	@FXML
	private JFXButton btnManageExams;
	
	@FXML
    private JFXButton btnManageQuestions;
	
	@FXML
    private JFXButton btnAddQuestion;
	
	@FXML
    private JFXButton btnRemoveQuestion;
	
	@FXML
    private JFXButton btnShowReport;
	
	@FXML
	private JFXButton btnCreateExam;
	
	@FXML
    private JFXButton btnCheckExams;
	
	@FXML
    private JFXButton btnSearch_CreateExam;

	@FXML
	private Label lblMessage;
	
	@FXML
	private Label lbluserNameAndID;
	
	@FXML
	private Pane pnlGreeting = new Pane();
	
    @FXML
    private Pane pnlShowReport = new Pane();

    @FXML
    private Pane pnlCreateExam = new Pane();

    @FXML
    private Pane pnlManageQuestions = new Pane();
    
    @FXML
    private Pane pnlManageExams = new Pane();

    @FXML
    private Pane pnlCheckExams = new Pane();
	
	@FXML
	private JFXComboBox<String> subjectSelectBox_CreateExam;
	@FXML
	private JFXComboBox<String> courseSelectBox_CreateExam;

	@FXML
	private TableView<Question> tableView_ManageQuestions = new TableView<>();
	@FXML
	private TableView<Question> tableView_CreateExam = new TableView<>();

	@FXML
	private TableColumn<Question, String> idColumn_ManageQuestions;
	@FXML
	private TableColumn<Question, String> subjectColumn_ManageQuestions;
	@FXML
	private TableColumn<Question, String> courseNameColumn_ManageQuestions;
	@FXML
	private TableColumn<Question, String> questionTextColumn_ManageQuestions;
	@FXML
	private TableColumn<Question, String> authorColumn_ManageQuestions;
	@FXML
	private TableColumn<Question, String> questionNumberColumn_ManageQuestions;
	
	@FXML
	private TableColumn<Question, String> idColumn_CreateExam;
	@FXML
	private TableColumn<Question, String> questionTextColumn_CreateExam;
	@FXML
	private TableColumn<Question, String> questionNumberColumn_CreateExam;
	@FXML
	private TableColumn<Question, String> authorColumn_CreateExam;
	
	private static Lecturer lecturer; // current lecture
	
	private ObservableList<Question> questionsToCreateExamObservableList = FXCollections.observableArrayList();
	
	private ObservableList<Question> questionsToEditObservableList = FXCollections.observableArrayList(); // list of questions to select to Edit in the table
	
	protected static Stage currStage; // save current stage

	static Question questionSelected; // question selected to save
	private static LecturerDashboardFrameController instance;
	
	public static Lecturer getLecturer() {
		return lecturer;
	}
	
	
	public LecturerDashboardFrameController() {
		instance = this;
	}

	public static LecturerDashboardFrameController getInstance() {
		return instance;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getLecturerSubjectsAndCoursesFromDB(lecturer);
	    lbluserNameAndID.setText(lecturer.getName() + "\n(ID: " + lecturer.getId() + ")");
	    // Set lecturer name and id under in the frame
		
	    // -------------- ManageQuestions --------------

	    // Setting up cell value factories for lecturer's questions management table columns
	    idColumn_ManageQuestions.setCellValueFactory(new PropertyValueFactory<Question, String>("id"));
	    subjectColumn_ManageQuestions.setCellValueFactory(new PropertyValueFactory<Question, String>("subject"));
	    courseNameColumn_ManageQuestions.setCellValueFactory(new PropertyValueFactory<Question, String>("courseName"));
	    questionTextColumn_ManageQuestions.setCellValueFactory(new PropertyValueFactory<Question, String>("questionText"));    
	    questionNumberColumn_ManageQuestions.setCellValueFactory(new PropertyValueFactory<Question, String>("questionNumber"));
	    authorColumn_ManageQuestions.setCellValueFactory(new PropertyValueFactory<Question, String>("lecturer"));

	    ArrayList<String> getQuestionArray = new ArrayList<>();
	    getQuestionArray.add("GetAllQuestionsFromDB");
	    getQuestionArray.add(lecturer.getName());
	    // Create an ArrayList to get all questions from the database for the current lecturer
	    // The lecturer's name is added as a parameter
	    ClientUI.chat.accept(getQuestionArray);
	    
	    tableView_ManageQuestions.getSelectionModel().clearSelection();
	    tableView_ManageQuestions.getSelectionModel().clearSelection();
	    // Show the ManageQuestions panel and clear the selection in the questions table
	    
	 // -------------- CreateExam --------------
	    tableView_CreateExam.getSelectionModel().clearSelection();
	    courseSelectBox_CreateExam.getItems().add("Please select a subject first");
		// add subjects to choose from the subjectSelectBox
		for(Map.Entry<String, ArrayList<String>> entry : lecturer.getLecturerSubjectsAndCourses().entrySet()) {
			subjectSelectBox_CreateExam.getItems().add(entry.getKey());
		}
	    idColumn_CreateExam.setCellValueFactory(new PropertyValueFactory<Question, String>("id"));
	    questionTextColumn_CreateExam.setCellValueFactory(new PropertyValueFactory<Question, String>("questionText"));    
	    questionNumberColumn_CreateExam.setCellValueFactory(new PropertyValueFactory<Question, String>("questionNumber"));
	    authorColumn_CreateExam.setCellValueFactory(new PropertyValueFactory<Question, String>("lecturer"));
	    
	    pnlGreeting.toFront();
	    
	    
	    
	}
	
	public static void getLecturerSubjectsAndCoursesFromDB(Lecturer lecturer) {

		// send the server an ArrayList with the lecturer id to get his subjects and courses that belong to each subject
		// will return from the server and the DB an hashmap with subjects and its courses that belong to the lecturer
		ArrayList<String> getLecturerSubjectsCoursesArr = new ArrayList<>();
		getLecturerSubjectsCoursesArr.add("GetLecturerSubjectsAndCourses");
		getLecturerSubjectsCoursesArr.add(lecturer.getId());
		ClientUI.chat.accept(getLecturerSubjectsCoursesArr);
	}
	
	// loading the hashmap for the subjects and courses of the lecturer
	public static void loadLecturerSubjectsAndCourses(Map<String, ArrayList<String>> map) {
		lecturer.setLecturerSubjectsAndCourses(map);
	}

	
	/**
	 * Loads an array of questions into the table view.
	 *
	 * @param questions The ArrayList of questions to be loaded
	 */
	public void loadArrayQuestionsToTable_ManageQuestions(ArrayList<Question> questions) {
		
	    // Add all questions from the ArrayList to the questionsToEditObservableList
	    questionsToEditObservableList.addAll(questions);

	    // Set the items of the table view to the questionsToEditObservableList
	    tableView_ManageQuestions.setItems(questionsToEditObservableList);
	}
	
	
	public void loadArrayQuestionsToTable_CreateExam(ArrayList<Question> questions) {
			
	    // Add all questions from the ArrayList to the questionsToCreateExamObservableList
	    questionsToCreateExamObservableList.addAll(questions);

	    // Set the items of the table view to the questionsToCreateExamObservableList
	    tableView_CreateExam.setItems(questionsToCreateExamObservableList);
	    
	    //tableView_CreateExam.refresh();
	}

	
	
	/**
	 * Starts the frame and initializes the lecturer details.
	 *
	 * @param lecturerDetails The ArrayList containing the lecturer details
	 * @throws IOException If an I/O exception occurs during the execution
	 */
	public static void start(ArrayList<String> lecturerDetails) throws IOException {
	    // Initialize the lecturer with the provided details
	    lecturer = new Lecturer(lecturerDetails.get(2), lecturerDetails.get(3), lecturerDetails.get(4), lecturerDetails.get(5), lecturerDetails.get(6));

	    // -- lecturerDetails --
	    // 1 - login As
	    // 2 - user ID
	    // 3 - userName
	    // 4 - user Password
	    // 5 - user Name
	    // 6 - user Email

	    // Run the following code on the JavaFX Application Thread using Platform.runLater()
	    Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            try {
	                // Save the current dashboard screen for returning back
	                currStage = SceneManagment.createNewStage("/gui/LecturerDashboardGUI.fxml", "/gui/LecturerDashboard.css", "Home Dashboard");
	                currStage.show();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}

	
	/**
	 * Opens the dashboard screen from the EditQuestionFrameController without creating it again.
	 *
	 * @param edited_QuestionID      The ID of the edited question
	 * @param edited_QuestionText    The edited question text
	 * @param edited_QuestionNumber  The edited question number
	 * @throws IOException If an I/O exception occurs during the execution
	 */
	public void showDashboardFrom_EditQuestions(String edited_QuestionID, String edited_QuestionText, String edited_QuestionNumber) throws IOException {
	    // Update the edited question in the table of the lecturer's questions
	    for (int i = 0; i < questionsToEditObservableList.size(); i++) {
	        if (questionsToEditObservableList.get(i).getId().equals(edited_QuestionID)) {
	            questionsToEditObservableList.get(i).setQuestionText(edited_QuestionText);
	            questionsToEditObservableList.get(i).setQuestionNumber(edited_QuestionNumber);
	        }
	    }

	    // Show the current stage
	    currStage.show();

	    // Refresh the table view to reflect the changes
	    tableView_ManageQuestions.refresh();

	    // Clear the selection in the questions table
	    tableView_ManageQuestions.getSelectionModel().clearSelection();
	    lblMessage.setText("");
	}

	
	/**
	 * Handles the action when the lecturer clicks on the close button.
	 *
	 * @param event The action event
	 * @throws Exception If an exception occurs during the execution
	 */
	public void getCloseBtn(ActionEvent event) throws Exception {
	    // Hide the primary window
	    ((Node) event.getSource()).getScene().getWindow().hide();

	    // Send a quit message to the server using the client's ID and role
	    ClientUI.chat.client.quit(lecturer.getId(), "lecturer");
	}

	
	/**
	 * Handles the action when the lecturer clicks on the logout button.
	 *
	 * @param event The action event
	 * @throws Exception If an exception occurs during the execution
	 */
	public void getLogoutBtn(ActionEvent event) throws Exception {
	    // Hide the primary window
	    ((Node) event.getSource()).getScene().getWindow().hide();

	    // Send a logout message to the server to update the user's logged-in status
	    ArrayList<String> qArr = new ArrayList<>();
	    qArr.add("UserLogout");
	    qArr.add("lecturer");
	    qArr.add(lecturer.getId());
	    ClientUI.chat.accept(qArr);

	    // Start the login screen after logout
	    LoginFrameController.start();
	}

	
	
	/**
	 * Handles the action when the lecturer clicks on the edit button for a question in the Manage Questions screen.
	 *
	 * @param event The action event
	 * @throws Exception If an exception occurs during the execution
	 */
	public void getEditBtn_ManageQuestions(ActionEvent event) throws Exception {
	    // Getting the selected question from the table view
	    questionSelected = tableView_ManageQuestions.getSelectionModel().getSelectedItem();
	    if (questionSelected == null) {
	        lblMessage.setText("[Error] No question was selected.");
	    } else {
	        lblMessage.setText("");

	        // Hide the primary window
	        ((Node) event.getSource()).getScene().getWindow().hide();

	        // Create and show the UpdateQuestionGUI window
	        EditQuestionFrameController.start();
	    }
	}

	
	// when lecturer click on Add on edit question 
	public void getAddBtn_ManageQuestions(ActionEvent event) throws Exception {
		// Hide the primary window
		((Node) event.getSource()).getScene().getWindow().hide();
		
		AddQuestionFrameController.start(lecturer); // send the lecturer to the add question screen
	}
	
	public void showDashboardFrom_AddQuestion(ArrayList<Question> newQuestion) {
		if(newQuestion != null) {
			questionsToEditObservableList.addAll(newQuestion);
			lblMessage.setText("The question added succesfully");
		}
		if(newQuestion != null) {
			lblMessage.setText("");
		}
		
		// Show the current stage
	    currStage.show();

	    // Refresh the table view to reflect the changes
	    tableView_ManageQuestions.refresh();

	    // Clear the selection in the questions table
	    tableView_ManageQuestions.getSelectionModel().clearSelection();
	}
	
	/**
	 * Handles the action when the lecturer clicks on the remove button for a question in the Manage Questions screen.
	 *
	 * @param event The action event
	 * @throws Exception If an exception occurs during the execution
	 */
	public void getRemoveBtn_ManageQuestions(ActionEvent event) throws Exception {
	    // Getting the selected question from the table view
	    questionSelected = tableView_ManageQuestions.getSelectionModel().getSelectedItem();
	    if (questionSelected == null) {
	        lblMessage.setText("[Error] No question was selected.");
	    } else {

	        // Send a message to the server to remove the question from the database
	        ArrayList<String> questionToRemoveArr = new ArrayList<>();
	        questionToRemoveArr.add("RemoveQuestionFromDB");
	        questionToRemoveArr.add(questionSelected.getId());
	        ClientUI.chat.accept(questionToRemoveArr);
	        
	        lblMessage.setText("Question (ID:" + questionSelected.getId() + ") removed succesfully");     

	        // Remove the question from the questionsToEditObservableList and refresh the table view
	        for (int i = 0; i < questionsToEditObservableList.size(); i++) {
	            if (questionsToEditObservableList.get(i).getId().equals(questionSelected.getId())) {
	                questionsToEditObservableList.remove(i);
	                break;
	            }
	        }
	        tableView_ManageQuestions.refresh();
	    }
	}
	
	public void getSearchBtn_CreateExam(ActionEvent event) throws Exception {
		
		String subjectSelect = subjectSelectBox_CreateExam.getSelectionModel().getSelectedItem();
		String courseSelect = courseSelectBox_CreateExam.getSelectionModel().getSelectedItem();
		tableView_CreateExam.getItems().clear();
		
		if(subjectSelect == null || courseSelect == null) {
			lblMessage.setText("[Error] Missing fields.");
		}
		else {
			lblMessage.setText("");
			ArrayList<String> getQuestionsArr = new ArrayList<>();
			getQuestionsArr.add("GetQuestionsForLecturerBySubjectAndCourseToCreateExamTable");
			getQuestionsArr.add(subjectSelect);
			getQuestionsArr.add(courseSelect);
			ClientUI.chat.accept(getQuestionsArr);
		}
	}

	public void getSubjectSelectBox_CreateExam(ActionEvent event) throws Exception {
		courseSelectBox_CreateExam.getItems().clear();
		// add to the courseSelectBox all the courses in the map (the courses in the subject that the lecture selected)
		for(Map.Entry<String, ArrayList<String>> entry : lecturer.getLecturerSubjectsAndCourses().entrySet()) {
			if(subjectSelectBox_CreateExam.getSelectionModel().getSelectedItem() == entry.getKey()) {
				courseSelectBox_CreateExam.getItems().addAll(entry.getValue());
				break;
			}
		}
	}
	
	/**
	 * Handles the action when a tab button is clicked in the dashboard.
	 *
	 * @param actionEvent The action event
	 */
	public void handleClicks(ActionEvent actionEvent) {
		lblMessage.setText("");
	    if (actionEvent.getSource() == btnShowReport) {
	        pnlShowReport.toFront();
	    }
	    if (actionEvent.getSource() == btnCheckExams) {
	        pnlCheckExams.toFront();
	    }
	    if (actionEvent.getSource() == btnManageQuestions) {
	    	tableView_ManageQuestions.getSelectionModel().clearSelection(); // To unselect row in the questions table
	        pnlManageQuestions.toFront();    
	    }
	    if (actionEvent.getSource() == btnCreateExam) {
	    	courseSelectBox_CreateExam.getItems().clear();
	        pnlCreateExam.toFront();
	    }
	    if (actionEvent.getSource() == btnManageExams) {
	        pnlManageExams.toFront();
	    }
	}


}
