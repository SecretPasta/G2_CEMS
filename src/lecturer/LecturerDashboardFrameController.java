package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;

import ClientAndServerLogin.LoginFrameController;
import ClientAndServerLogin.SceneManagment;
import Config.Exam;
import Config.Lecturer;
import Config.Question;
import Config.QuestionInExam;
import client.ClientUI;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

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
	private JFXButton currentSection;
	@FXML
    private JFXButton btnSearch_CreateExam;
	@FXML
    private JFXButton btnRefresh_CreateExam;
	@FXML
    private JFXButton btcContinue_CreateExam;
	
	@FXML
	private JFXSnackbar snackbar;
	
	@FXML
	private JFXSnackbarLayout snackbarLayout;
	
	@FXML
	private Label lbluserNameAndID;
	@FXML
	private Label lblTotalQuestionSelectedPoints;
	
	@FXML
	private StackPane stackPane;
	
	@FXML
	private Pane pnlGreeting;
    @FXML
    private Pane pnlShowReport;
    @FXML
    private Pane pnlCreateExam;
    @FXML
    private Pane pnlManageQuestions;
    @FXML
    private Pane pnlManageExams;
    @FXML
    private Pane pnlCheckExams;
    @FXML
    private Pane pnlEmpty;
    @FXML
    private Pane currentPane;
	
	@FXML
	private JFXComboBox<String> subjectSelectBox_CreateExam;
	@FXML
	private JFXComboBox<String> courseSelectBox_CreateExam;
	@FXML
	private JFXComboBox<String> boxSearchbyCourse_ManageQuestions;

	@FXML
	private TableView<Question> tableView_ManageQuestions = new TableView<>();
	@FXML
	private TableView<Question> tableView_CreateExam = new TableView<>();
	@FXML
	private TableView<QuestionInExam> tableView_CreateExam2 = new TableView<>();
	@FXML
	private TableView<Exam> tableView_inActiveExams = new TableView<>();
	@FXML
	private TableView<Exam> tableView_activeExams = new TableView<>();

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
	private TableColumn<Question, String> idColumn_CreateExam;
	@FXML
	private TableColumn<Question, String> questionTextColumn_CreateExam;
	@FXML
	private TableColumn<Question, String> authorColumn_CreateExam;
	
	@FXML
	private TableColumn<QuestionInExam, String> idColumn_CreateExam2;
	@FXML
	private TableColumn<QuestionInExam, String> questionTextColumn_CreateExam2;
	@FXML
	private TableColumn<QuestionInExam, String> authorColumn_CreateExam2;
	@FXML
	private TableColumn<QuestionInExam, Double> pointsColumn_CreateExam2;
	
	@FXML
	private TableColumn<Exam, String> examIdColumn_inActiveExams;
	@FXML
	private TableColumn<Exam, String> courseNameColumn_inActiveExams;
	@FXML
	private TableColumn<Exam, Integer> durationColumn_inActiveExams;
	
	@FXML
	private TableColumn<Exam, String> examIdColumn_activeExams;
	@FXML
	private TableColumn<Exam, String> courseNameColumn_activeExams;
	@FXML
	private TableColumn<Exam, String> codeColumn_activeExams;
	
	private static Lecturer lecturer; // current lecturer
	
	private static Map<String, String> subjects_ID_Name = new HashMap<>();
	
	private static Map<String, String> courses_ID_Name = new HashMap<>();
	
	private ObservableList<QuestionInExam> questionsToCreateExamObservableList2 = FXCollections.observableArrayList(); // list2 of questions to select for exam

	private ObservableList<Question> questionsToCreateExamObservableList = FXCollections.observableArrayList(); // list of questions to select for exam
	
	private ObservableList<Question> questionsToEditObservableList = FXCollections.observableArrayList(); // list of questions to select to Edit in the table
	
	private ObservableList<Exam> inActiveExamsObservableList = FXCollections.observableArrayList(); // exams to select in the table

	private ObservableList<Exam> activeExamsObservableList = FXCollections.observableArrayList(); // exams to select in the table

	protected static Stage currStage; // save current stage

	private static Question questionSelected; // question selected to edit or to delete or to add to exam
	
	private double total_points_CreateExam; // total question points for new exam
	
	private QuestionInExam questionInExamSelected;
	
	private Exam inActiveExamSelected;
	private Exam activeExamSelected;
	
	private static LecturerDashboardFrameController instance;
	
	private String subjectSelect_CreateExam;
	private String courseSelect_CreateExam;
	
	public LecturerDashboardFrameController() {
		instance = this;
	}

	public static LecturerDashboardFrameController getInstance() {
		return instance;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		getLecturerSubjectsAndCoursesFromDB(lecturer);
		getAllSubjectsFromDB();
		getAllCoursesFromDB();
	    lbluserNameAndID.setText(lecturer.getName() + "\n(ID: " + lecturer.getId() + ")"); // Set lecturer name and id under in the frame
	    currentPane = pnlGreeting;
	    pnlGreeting.toFront();
		
	    // -------------- ManageQuestions PANEL --------------

	    // Setting up cell value factories for lecturer's questions management table columns
	    idColumn_ManageQuestions.setCellValueFactory(new PropertyValueFactory<Question, String>("id"));
	    subjectColumn_ManageQuestions.setCellValueFactory(new PropertyValueFactory<Question, String>("subject"));
	    questionTextColumn_ManageQuestions.setCellValueFactory(new PropertyValueFactory<Question, String>("questionText"));    
	    authorColumn_ManageQuestions.setCellValueFactory(new PropertyValueFactory<Question, String>("lecturer"));
	      
	    // all courses of lecturer here to add to boxSearchbyCourse_ManageQuestions
	    boxSearchbyCourse_ManageQuestions.getItems().add("All");
		for(Map.Entry<String, ArrayList<String>> entry : lecturer.getLecturerSubjectsAndCourses().entrySet()) {
			boxSearchbyCourse_ManageQuestions.getItems().addAll(entry.getValue());
		} 

	    // Create an ArrayList to get all questions from the database for the current lecturer
	    ArrayList<String> getQuestionArray = new ArrayList<>();
	    getQuestionArray.add("GetAllQuestionsFromDB");
	    getQuestionArray.add(lecturer.getId()); // The lecturer's name is added as a parameter   
	    ClientUI.chat.accept(getQuestionArray);
	    
	    // clear the selection in the questions management table
	    tableView_ManageQuestions.getSelectionModel().clearSelection();
	    
	    // -------------- ManageQuestions PANEL --------------
	    
	    // -------------- CreateExam PANEL --------------
	    
	    // clear the selection in the create exam table
	    tableView_CreateExam.getSelectionModel().clearSelection();
	    courseSelectBox_CreateExam.getItems().add("Please select a subject first");
	    
		// add subjects to choose from the subjectSelectBox_CreateExam
		for(Map.Entry<String, ArrayList<String>> entry : lecturer.getLecturerSubjectsAndCourses().entrySet()) {
			subjectSelectBox_CreateExam.getItems().add(entry.getKey());
		}
		
		// Setting up cell value factories for lecturer's create exam tables columns
	    idColumn_CreateExam.setCellValueFactory(new PropertyValueFactory<Question, String>("id"));
	    questionTextColumn_CreateExam.setCellValueFactory(new PropertyValueFactory<Question, String>("questionText"));    
	    authorColumn_CreateExam.setCellValueFactory(new PropertyValueFactory<Question, String>("lecturer"));
	    
	    idColumn_CreateExam2.setCellValueFactory(new PropertyValueFactory<QuestionInExam, String>("id"));
	    questionTextColumn_CreateExam2.setCellValueFactory(new PropertyValueFactory<QuestionInExam, String>("questionText"));    
	    authorColumn_CreateExam2.setCellValueFactory(new PropertyValueFactory<QuestionInExam, String>("lecturer"));
	    pointsColumn_CreateExam2.setCellValueFactory(new PropertyValueFactory<QuestionInExam, Double>("points")); 
	    pointsColumn_CreateExam2.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() { // points column getting only double value
	        @Override
	        public String toString(Double value) {
	        	try {
	            return value.toString();
	        	}catch (NullPointerException e) {}
				return null;
	        }

	        @Override
	        public Double fromString(String str) {
	            try {
	                return Double.parseDouble(str);
	            } catch (NumberFormatException e) {
	            	displayErrorMessage("Error: Points should be only numbers");
	                return null; // Return null to indicate a conversion error
	            }
	        }
	    }));    
   
	    tableView_CreateExam2.setEditable(true); // editable points
	    
	 // -------------- CreateExam PANEL --------------
	    
	    
	 // -------------- ManageExam PANEL --------------
	    
		// Setting up cell value factories for lecturer's manage exam tables columns
	    examIdColumn_inActiveExams.setCellValueFactory(new PropertyValueFactory<Exam, String>("examID"));
	    courseNameColumn_inActiveExams.setCellValueFactory(new PropertyValueFactory<Exam, String>("courseName"));    
	    durationColumn_inActiveExams.setCellValueFactory(new PropertyValueFactory<Exam, Integer>("duration"));
	    
	    examIdColumn_activeExams.setCellValueFactory(new PropertyValueFactory<Exam, String>("examID"));
	    courseNameColumn_activeExams.setCellValueFactory(new PropertyValueFactory<Exam, String>("courseName"));    
	    codeColumn_activeExams.setCellValueFactory(new PropertyValueFactory<Exam, String>("code"));
	    
	    tableView_inActiveExams.getSelectionModel().clearSelection();
	    tableView_activeExams.getSelectionModel().clearSelection();
	    
	 // -------------- ManageExam PANEL --------------

	}
	
	

	// -------------- ManageQuestions PANEL --------------
	
	/**
	 * Loads an array of questions into the "Manage Questions" table view.
	 *
	 * @param questions The ArrayList of questions to be loaded into the table.
	 */
	public void loadArrayQuestionsToTable_ManageQuestions(ArrayList<Question> questions) {
	    // Add all questions from the ArrayList to the questionsToEditObservableList
	    questionsToEditObservableList.addAll(questions);

	    // Set the items of the table view to the questionsToEditObservableList
	    tableView_ManageQuestions.setItems(questionsToEditObservableList);
	}

	
	

	/**
	 * Shows the dashboard after editing a question in the EditQuestionFrameController.
	 *
	 * @param edited_QuestionID   The ID of the edited question.
	 * @param edited_QuestionText The updated text of the edited question.
	 * @throws IOException If an I/O error occurs while showing the dashboard.
	 */
	public void showDashboardFrom_EditQuestions(String edited_QuestionID, String edited_QuestionText) throws IOException {
	    // Update the edited question in the table of the lecturer questions
	    for (int i = 0; i < questionsToEditObservableList.size(); i++) {
	        if (questionsToEditObservableList.get(i).getId().equals(edited_QuestionID)) {
	            questionsToEditObservableList.get(i).setQuestionText(edited_QuestionText);
	        }
	    }
	
	    // Show the current stage
	    currStage.show();
	
	    // Refresh the table view to reflect the changes
	    tableView_ManageQuestions.refresh();
	
	    // Clear the selection in the questions table
	    tableView_ManageQuestions.getSelectionModel().clearSelection();
	}

	
	
	/**
	 * Handles the event when the Edit button is clicked in the "Manage Questions" view.
	 *
	 * @param event The action event triggered by clicking the Edit button.
	 * @throws Exception If an exception occurs during the process.
	 */
	public void getEditBtn_ManageQuestions(ActionEvent event) throws Exception {
	    // Getting the selected question from the table view
	    questionSelected = tableView_ManageQuestions.getSelectionModel().getSelectedItem();
	    if (questionSelected == null) {
	        // Show an error message using a snackbar if no question is selected
	        displayErrorMessage("Error: No question was selected.");
	    } else {
	        // Hide the primary window
	        ((Node) event.getSource()).getScene().getWindow().hide();

	        // Create and show the EditQuestion screen window with the selected question
	        EditQuestionFrameController.start(questionSelected);
	        questionSelected = null;
	    }
	}

	
	/**
	 * Handles the event when the Add button is clicked in the "Manage Questions" view.
	 *
	 * @param event The action event triggered by clicking the Add button.
	 * @throws Exception If an exception occurs during the process.
	 */
	public void getAddBtn_ManageQuestions(ActionEvent event) throws Exception {    
	    ((Node) event.getSource()).getScene().getWindow().hide(); // Hide the primary window
	    AddQuestionFrameController.start(lecturer); // Navigate to the "Add Question" screen and pass the lecturer object
	}

	
	/**
	 * Handles the event when the Search box selection is changed in the "Manage Questions" view.
	 *
	 * @param event The action event triggered by changing the Search box selection.
	 * @throws Exception If an exception occurs during the process.
	 */
	public void getSearchBox_ManageQuestions(ActionEvent event) throws Exception {
	    // Get the selected course name from the search box
	    String courseName_toSort = boxSearchbyCourse_ManageQuestions.getSelectionModel().getSelectedItem();

	    // Filter the questions based on the selected course name
	    filterQuestions(courseName_toSort);

	    // Clear the selection in the questions table view
	    tableView_ManageQuestions.getSelectionModel().clearSelection();
	}


	/**
	 * Filters the questions based on the selected course and updates the table view accordingly.
	 *
	 * @param selectedCourse The selected course name to filter the questions.
	 */
	public void filterQuestions(String selectedCourse) {
	    if (selectedCourse.equals("All")) {
	        tableView_ManageQuestions.setItems(questionsToEditObservableList); // Show all questions
	    } else {
	        // Filter questions based on the selected course
	        ObservableList<Question> filteredData = FXCollections.observableArrayList();
	        for (Question question : questionsToEditObservableList) {
	            if (question.getCourses().containsValue(selectedCourse)) {
	                filteredData.add(question);
	            }
	        }
	        tableView_ManageQuestions.setItems(filteredData); // Show questions for the selected course
	    }
	}

	
	/**
	 * Removes the selected question from the table view and database, and updates the table view.
	 *
	 * @param event The action event triggered by clicking the remove button.
	 * @throws Exception If an error occurs during the question removal process.
	 */
	public void getRemoveBtn_ManageQuestions(ActionEvent event) throws Exception {
	    // Getting the selected question from the table view
	    questionSelected = tableView_ManageQuestions.getSelectionModel().getSelectedItem();

	    if (questionSelected == null) {
	        // Display an error message if no question is selected
	        displayErrorMessage("Error: No question was selected.");
	    } else {
	        // Send a message to the server to remove the question from the database
	        ArrayList<String> questionToRemoveArr = new ArrayList<>();
	        questionToRemoveArr.add("RemoveQuestionFromDB");
	        questionToRemoveArr.add(questionSelected.getId());
	        ClientUI.chat.accept(questionToRemoveArr);

	        // Display a success message for the removed question
	        displaySuccessMessage("Question (ID:" + questionSelected.getId() + ") removed successfully");

	        // Remove the question from the questionsToEditObservableList and refresh the table view
	        for (int i = 0; i < questionsToEditObservableList.size(); i++) {
	            if (questionsToEditObservableList.get(i).getId().equals(questionSelected.getId())) {
	                questionsToEditObservableList.remove(i);
	                break;
	            }
	        }

	        tableView_ManageQuestions.refresh();
	        questionSelected = null;
	    }
	}

	
	/**
	 * Shows the dashboard after adding a new question in the AddQuestionFrameController.
	 * Updates the table view after adding a new question and displays a success message.
	 *
	 * @param newQuestion The new question object added to the table view.
	 */
	public void showDashboardFrom_AddQuestion(Question newQuestion) {
	    if (newQuestion != null) {
	        // Add the new question to the questionsToEditObservableList
	        questionsToEditObservableList.add(newQuestion);

	        // Display a success message for the added question
	        displaySuccessMessage("Question added successfully!");
	    }

	    // Show the current stage
	    currStage.show();

	    // Refresh the table view to reflect the changes
	    tableView_ManageQuestions.refresh();

	    // Clear the selection in the questions table
	    tableView_ManageQuestions.getSelectionModel().clearSelection();
	}
	
	
	// -------------- ManageQuestions PANEL --------------

	
	// -------------- CreateExam PANEL --------------
	
	
	/**
	 * Displays the dashboard after reviewing and saving a new exam and shows a success message with the exam ID.
	 *
	 * @param exam The exam object that was saved.
	 * @throws Exception If an exception occurs while refreshing the create exam view.
	 */
	public void showDashboardFrom_Review(Exam exam) throws Exception {
	    // Refresh the create exam view
	    getRefreshBtn_CreateExam(null);

	    // Show the current stage
	    currStage.show();

	    // Display a success message with the exam ID
	    displaySuccessMessage("Your exam has been created: Exam ID (" + exam.getExamID() + ")");
	}
	
	
	/**
	 * Loads an ArrayList of questions into the table view for creating an exam from DB.
	 * All the questions from selected subject and course
	 *
	 * @param questions The ArrayList of questions to load into the table view.
	 */
	public void loadArrayQuestionsToTable_CreateExam(ArrayList<Question> questions) {
	    // Add all questions from the ArrayList to the questionsToCreateExamObservableList
	    questionsToCreateExamObservableList.addAll(questions);

	    // Set the items of the table view to the questionsToCreateExamObservableList
	    tableView_CreateExam.setItems(questionsToCreateExamObservableList);

	    // Clear the selection in the table view
	    tableView_CreateExam.getSelectionModel().clearSelection();
	}

	
	/**
	 * Event handler for when the lecturer clicks on the "Search" button in the "Create Exam" screen.
	 * Retrieves questions based on the selected subject and course, and updates the table view accordingly.
	 *
	 * @param event The action event triggered by clicking the "Search" button.
	 * @throws Exception If an exception occurs during the retrieval of questions.
	 */
	public void getSearchBtn_CreateExam(ActionEvent event) throws Exception {
		
	    // Clear the items in the table view
	    tableView_CreateExam.getItems().clear();

	    // Get the selected subject and course from the selection boxes
	    subjectSelect_CreateExam = subjectSelectBox_CreateExam.getSelectionModel().getSelectedItem();
	    courseSelect_CreateExam = courseSelectBox_CreateExam.getSelectionModel().getSelectedItem();


	    if (subjectSelect_CreateExam == null || courseSelect_CreateExam == null) {
	        // Display an error message if any field is missing
	    	displayErrorMessage("Error: Missing fields");
	    } else {
	        // Prepare and send a request to the server to retrieve questions for the selected subject and course
	        ArrayList<String> getQuestionsArr = new ArrayList<>();
	        getQuestionsArr.add("GetQuestionsForLecturerBySubjectAndCourseToCreateExamTable");
	        getQuestionsArr.add(getSubjectIdByName(subjectSelect_CreateExam));
	        getQuestionsArr.add(getCourseIdByName(courseSelect_CreateExam));
	        ClientUI.chat.accept(getQuestionsArr);   
	    }
	}


	/**
	 * Event handler for when the lecturer selects a subject from the subject selection box in the "Create Exam" screen.
	 * Populates the course selection box with the courses corresponding to the selected subject.
	 *
	 * @param event The action event triggered by selecting a subject from the subject selection box.
	 * @throws Exception If an exception occurs during the retrieval of courses.
	 */
	public void getSubjectSelectBox_CreateExam(ActionEvent event) throws Exception {
		
	    courseSelectBox_CreateExam.getItems().clear();

	    // Retrieve the selected subject from the subject selection box
	    String selectedSubject = subjectSelectBox_CreateExam.getSelectionModel().getSelectedItem();

	    // Iterate through the subjects and courses map of the lecturer
	    for (Map.Entry<String, ArrayList<String>> entry : lecturer.getLecturerSubjectsAndCourses().entrySet()) {
	        if (selectedSubject != null && selectedSubject.equals(entry.getKey())) {
	            // Add the courses corresponding to the selected subject to the course selection box
	            courseSelectBox_CreateExam.getItems().addAll(entry.getValue());
	            break;
	        }
	    }
	}
	
	
	/**
	 * Event handler for the refresh button in the CreateExam panel.
	 * Clears and resets the table views, selection boxes, and other components.
	 *
	 * @param event The action event triggered by clicking the refresh button.
	 * @throws Exception if an error occurs during the process.
	 */
	public void getRefreshBtn_CreateExam(ActionEvent event) throws Exception {
		
	    // Clear the questions and table view in the first tab
	    questionsToCreateExamObservableList.removeAll();
	    tableView_CreateExam.getItems().clear();
	    tableView_CreateExam.getSelectionModel().clearSelection();
		
	    // Clear the questions and table view in the second tab
	    questionsToCreateExamObservableList2.removeAll();
	    tableView_CreateExam2.getItems().clear();
	    tableView_CreateExam2.getSelectionModel().clearSelection();

	    // Enable the search button and selection boxes
	    btnSearch_CreateExam.setDisable(false);
	    subjectSelectBox_CreateExam.setDisable(false);
	    courseSelectBox_CreateExam.setDisable(false);

	    // Clear the items in the course selection box and reset the subject selection box
	    courseSelectBox_CreateExam.getItems().clear();
	    subjectSelectBox_CreateExam.setValue(null);

	    // Reset the total points and disable the continue button
	    total_points_CreateExam = 0.0;
	    lblTotalQuestionSelectedPoints.setText(String.valueOf(total_points_CreateExam));
	    btcContinue_CreateExam.setDisable(true);
	}

	
	/**
	 * Event handler for the "Choose Question" button in the CreateExam panel.
	 * Adds the selected question to the exam being created.
	 *
	 * @param event The action event triggered by clicking the "Choose Question" button.
	 * @throws Exception if an error occurs during the process.
	 */
	public void getChooseQuestionBtn_CreateExam(ActionEvent event) throws Exception {
	    // Getting the selected question from the table view
	    questionSelected = tableView_CreateExam.getSelectionModel().getSelectedItem();

	    if (questionsToCreateExamObservableList2.contains(questionSelected)) {
	        // Display an error message if the question has already been added to the exam
	        displayErrorMessage("Error: This question already added");
	    } else if (questionSelected == null) {
	        // Display an error message if no question is selected
	        displayErrorMessage("Error: No question selected");
	    } else {
	        // Create a QuestionInExam object for the selected question and add it to the list
	        questionInExamSelected = new QuestionInExam(questionSelected.getId(), questionSelected.getQuestionText(), questionSelected.getAnswers(), questionSelected.getLecturer());
	        questionsToCreateExamObservableList2.add(questionInExamSelected);

	        // Set the items of the second table view to the updated list
	        tableView_CreateExam2.setItems(questionsToCreateExamObservableList2);

	        // Remove the selected question from the first table view and refresh it
	        questionsToCreateExamObservableList.remove(questionSelected);
	        tableView_CreateExam.getSelectionModel().clearSelection();
	        tableView_CreateExam.refresh();

	        // Disable the search button and selection boxes
	        btnSearch_CreateExam.setDisable(true);
	        subjectSelectBox_CreateExam.setDisable(true);
	        courseSelectBox_CreateExam.setDisable(true);

	        // Clear the selection in both table views
	        tableView_CreateExam.getSelectionModel().clearSelection();
	        tableView_CreateExam2.getSelectionModel().clearSelection();
	    }

	    questionSelected = null;
	}

	
	
	/**
	 * Event handler for the "Remove Question" button in the CreateExam panel.
	 * Removes the selected question from the exam being created.
	 *
	 * @param event The action event triggered by clicking the "Remove Question" button.
	 * @throws Exception if an error occurs during the process.
	 */
	public void getRemoveQuestion_CreateExam(ActionEvent event) throws Exception {
	    // Getting the selected question from the table view
	    questionInExamSelected = tableView_CreateExam2.getSelectionModel().getSelectedItem();

	    if (questionInExamSelected == null) {
	        // Display an error message if no question is selected
	        displayErrorMessage("Error: No question selected");
	    } else {
	        // Subtract the points of the selected question from the total points
	        total_points_CreateExam -= questionInExamSelected.getPoints();

	        // Add the selected question back to the first table view
	        questionsToCreateExamObservableList.add((Question) questionInExamSelected);
	        tableView_CreateExam.setItems(questionsToCreateExamObservableList);

	        // Remove the selected question from the second table view and refresh it
	        questionsToCreateExamObservableList2.remove(questionInExamSelected);
	        tableView_CreateExam2.getSelectionModel().clearSelection();
	        tableView_CreateExam2.refresh();

	        // Update the displayed total points
	        lblTotalQuestionSelectedPoints.setText(String.valueOf(total_points_CreateExam));

	        // Enable/disable buttons based on the current state
	        if (tableView_CreateExam2.getItems().isEmpty()) {
	            btnSearch_CreateExam.setDisable(false);
	            subjectSelectBox_CreateExam.setDisable(false);
	            courseSelectBox_CreateExam.setDisable(false);
	        }

	        if (questionsToCreateExamObservableList2.isEmpty() || total_points_CreateExam != 100) {
	            btcContinue_CreateExam.setDisable(true);
	        }
	    }

	    // Clear the selections in both table views
	    tableView_CreateExam2.getSelectionModel().clearSelection();
	    tableView_CreateExam.getSelectionModel().clearSelection();

	    // Reset the variables
	    questionInExamSelected = null;
	    questionSelected = null;
	}
	
	
	/**
	 * Event handler for editing the points of a question in the CreateExam panel.
	 *
	 * @param event The cell edit event triggered by editing the points of a question.
	 */
	public void getEditPoints(CellEditEvent<QuestionInExam, Double> event) {
	    try {
	        // Getting the selected question from the table view
	        questionInExamSelected = tableView_CreateExam2.getSelectionModel().getSelectedItem();

	        // Get the old points value of the selected question
	        Double oldPoints = questionInExamSelected.getPoints();
	        
	        // Get the new points value entered by the user
	        Double newPoints = event.getNewValue();

	        // Calculate the total points after updating the points of the selected question
	        Double temp_total_points = total_points_CreateExam + newPoints - oldPoints;

	        if (temp_total_points > 100) {
	            // Display an error message if the total points will exceed 100
	            displayErrorMessage("Error: Total points cannot be over 100");
	        } else {
	            // Update the total points and the points of the selected question
	            total_points_CreateExam = total_points_CreateExam - oldPoints + newPoints;
	            questionInExamSelected.setPoints(newPoints);
	        }

	        // Refresh the table view and update the displayed total points
	        tableView_CreateExam2.refresh();
	        lblTotalQuestionSelectedPoints.setText(String.valueOf(total_points_CreateExam));

	        // Clear the selections in both table views
	        tableView_CreateExam2.getSelectionModel().clearSelection();
	        tableView_CreateExam.getSelectionModel().clearSelection();

	        // Enable/disable the "Continue" button based on the current total points
	        if (total_points_CreateExam != 100) {
	            btcContinue_CreateExam.setDisable(true);
				lblTotalQuestionSelectedPoints.setTextFill(Color.rgb(254, 119, 76));
	        } else if (total_points_CreateExam == 100) {
	            btcContinue_CreateExam.setDisable(false);
				lblTotalQuestionSelectedPoints.setTextFill(Color.rgb(93, 210, 153));
	        }

	    } catch (NumberFormatException | NullPointerException e) {
	        // Display an error message if the entered points value is not a valid number
	        displayErrorMessage("Error: Points should be only numbers");
	    }
	}

    
	/**
	 * Event handler for the "Continue" button in the CreateExam panel.
	 *
	 * @param event The action event triggered by clicking the "Continue" button.
	 */
	public void getBtnContinue_CreateExam(ActionEvent event) throws Exception {
	    if (questionsToCreateExamObservableList2.isEmpty()) {
	        // Display an error message if there are no questions in the exam
	        displayErrorMessage("Error: No questions in the test");
	    } else if (total_points_CreateExam != 100) {
	        // Display an error message if the total points of the exam is not 100
	        displayErrorMessage("Error: Total points have to be 100");
	    } else {
	        // Hide the current window and start the CreateExam_CommentsAndTimeFrameController.
	    	// sending to the next screen: the lecturer, the questions, the subject and the course
	        ((Node) event.getSource()).getScene().getWindow().hide();
	        CreateExam_CommentsAndTimeFrameController.start(
	            lecturer, questionsToCreateExamObservableList2,
	            getSubjectIdByName(subjectSelect_CreateExam), subjectSelect_CreateExam,
	            getCourseIdByName(courseSelect_CreateExam), courseSelect_CreateExam
	        );
	    }
	}

	
	/**
	 * Shows the dashboard from the CreateExam Comments And Timer Frame screen.
	 */
	public void showDashboardFrom_CreateExam() throws IOException {
	    // Show the current stage
	    currStage.show();

	    // Clear the selection in the questions table
	    tableView_CreateExam2.getSelectionModel().clearSelection();
	}


	// -------------- CreateExam PANEL --------------

	
	// -------------- ManageExam PANEL --------------
	
	/**
	 * Retrieves all active and inactive exams from the database.
	 */
	private void getAllActiveInActiveExams() {
		ArrayList<String> lecturer_exams_arr_get = new ArrayList<>();
		lecturer_exams_arr_get.add("GetAllExamsFromDBtoManageExamsTablesByLecturerID");
		lecturer_exams_arr_get.add(lecturer.getId());
	    ClientUI.chat.accept(lecturer_exams_arr_get);
	}

	/**
	 * Loads all active exams into the active exams table.
	 *
	 * @param activeExams The list of active exams to be displayed.
	 */
	public void loadAllActiveExamsToTable(ArrayList<Exam> activeExams) {
	    activeExamsObservableList.setAll(activeExams);
	    tableView_activeExams.setItems(activeExamsObservableList);
	}

	/**
	 * Loads all inactive exams into the inactive exams table.
	 *
	 * @param inActiveExams The list of inactive exams to be displayed.
	 */
	public void loadAllInActiveExamsToTable(ArrayList<Exam> inActiveExams) {
	    inActiveExamsObservableList.setAll(inActiveExams);
	    tableView_inActiveExams.setItems(inActiveExamsObservableList);
	}
	
	/**
	 * Event handler for the "Open Exam" button in the Manage Exams UI.
	 * Moves the selected inactive exam to the active exams table and updates its status in the database.
	 *
	 * @param event The action event triggered by clicking the "Open Exam" button.
	 * @throws Exception If an exception occurs during the process.
	 */
	public void getBtnOpenExam_ManageExams(ActionEvent event) throws Exception {
	    inActiveExamSelected = tableView_inActiveExams.getSelectionModel().getSelectedItem();

	    try {
	        if (inActiveExamSelected == null) {
	            throw new NullPointerException();
	        }

	        activeExamsObservableList.add(inActiveExamSelected);
	        inActiveExamsObservableList.remove(inActiveExamSelected);

	        changeExamActivenessInDB(inActiveExamSelected.getExamID(), "1");

	    } catch (NullPointerException e) {
	        displayErrorMessage("Error: Exam not selected");
	    }
	    
	    tableView_inActiveExams.getSelectionModel().clearSelection();
	    tableView_activeExams.getSelectionModel().clearSelection();
	    tableView_activeExams.refresh();
	    tableView_inActiveExams.refresh();

	    inActiveExamSelected = null;
	}
	
	/**
	 * Event handler for the "Close Exam" button in the Manage Exams UI.
	 * Moves the selected active exam to the inActive exams table and updates its status in the database.
	 *
	 * @param event The action event triggered by clicking the "Close Exam" button.
	 * @throws Exception If an exception occurs during the process.
	 */
	public void getBtnCloseExam_ManageExams(ActionEvent event) throws Exception {
	    activeExamSelected = tableView_activeExams.getSelectionModel().getSelectedItem();

	    try {
	        if (activeExamSelected == null) {
	            throw new NullPointerException();
	        }
	        
	        activeExamsObservableList.remove(activeExamSelected);
	        inActiveExamsObservableList.add(activeExamSelected);

	        changeExamActivenessInDB(activeExamSelected.getExamID(), "0");

	    } catch (NullPointerException e) {
	        displayErrorMessage("Error: Exam not selected");
	    }
	    
	    tableView_inActiveExams.getSelectionModel().clearSelection();
	    tableView_activeExams.getSelectionModel().clearSelection();
	    tableView_activeExams.refresh();
	    tableView_inActiveExams.refresh();

	    activeExamSelected = null;
	}
	
	
	public void getBtnChangeTime_ManageExams(ActionEvent event) throws Exception {
		activeExamSelected = tableView_activeExams.getSelectionModel().getSelectedItem();
		
		try {
	        if (activeExamSelected == null) {
	            throw new NullPointerException();
	        }
	        ((Node) event.getSource()).getScene().getWindow().hide(); // Hide the primary window
	        ManageExam_ChangeTimeFrameController.start(activeExamSelected, lecturer);

	    } catch (NullPointerException e) {
	        displayErrorMessage("Error: Exam not selected");
	    }
	    
	    tableView_inActiveExams.getSelectionModel().clearSelection();
	    tableView_activeExams.getSelectionModel().clearSelection();
	    tableView_activeExams.refresh();
	    tableView_inActiveExams.refresh();

	    activeExamSelected = null;
	}
	
	public void showDashboardFrom_ChangeTime() throws IOException {
	    // Show the current stage
	    currStage.show();

	    // Clear the selection in the exam tables
	    tableView_inActiveExams.getSelectionModel().clearSelection();
	    tableView_activeExams.getSelectionModel().clearSelection();
	}

	
	/**
	 * Updates the activeness of an exam in the database.
	 *
	 * @param examID     The ID of the exam to update.
	 * @param activeness The new activeness status for the exam.
	 */
	private void changeExamActivenessInDB(String examID, String activeness) {
	    ArrayList<String> exam_arr_active_change = new ArrayList<>();
	    exam_arr_active_change.add("ChangeExamActiveness");
	    exam_arr_active_change.add(examID);
	    exam_arr_active_change.add(activeness);
	    ClientUI.chat.accept(exam_arr_active_change);
	}


	
	// -------------- ManageExam PANEL --------------
	
	

	/**
	 * Retrieves the subject name based on the given subject ID.
	 *
	 * @param subjectID The ID of the subject.
	 * @return The name of the subject corresponding to the given ID, or null if no matching subject is found.
	 */
	public String getSubjectNameById(String subjectID) { 	
	    return subjects_ID_Name.get(subjectID);
	}
 
	
	/**
	 * Retrieves the subject ID based on the given subject name.
	 *
	 * @param subjectName The name of the subject.
	 * @return The ID of the subject corresponding to the given name, or null if no matching subject is found.
	 */
	public static String getSubjectIdByName(String subjectName) { 	
	    for (Map.Entry<String, String> entry : subjects_ID_Name.entrySet()) {
	        if (subjectName.equals(entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}


    
	/**
	 * Retrieves the course name based on the given course ID.
	 *
	 * @param courseID The ID of the course.
	 * @return The name of the course corresponding to the given ID, or null if no matching course is found.
	 */
	public String getCourseNameById(String courseID) { 	
	    return courses_ID_Name.get(courseID);
	}

    
	/**
	 * Retrieves the course ID based on the given course name.
	 *
	 * @param courseName The name of the course.
	 * @return The ID of the course corresponding to the given name, or null if no matching course is found.
	 */
	public static String getCourseIdByName(String courseName) { 	
	    for (Map.Entry<String, String> entry : courses_ID_Name.entrySet()) {
	        if (courseName.equals(entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	
	/**
	 * Retrieves the subjects and courses of the given lecturer from the database.
	 * Sends a request to the server to fetch the data and updates the lecturer object accordingly.
	 *
	 * @param lecturer The lecturer object for which to retrieve the subjects and courses.
	 */
	public static void getLecturerSubjectsAndCoursesFromDB(Lecturer lecturer) {

	    // Prepare and send a request to the server to fetch the subjects and courses
	    // belonging to the given lecturer by providing their ID
	    ArrayList<String> getLecturerSubjectsCoursesArr = new ArrayList<>();
	    getLecturerSubjectsCoursesArr.add("GetLecturerSubjectsAndCourses");
	    getLecturerSubjectsCoursesArr.add(lecturer.getId());
	    ClientUI.chat.accept(getLecturerSubjectsCoursesArr);
	}
	
	/**
	 * Loads the subjects and courses map into the lecturer object.
	 *
	 * @param map The map containing subjects as keys and their corresponding courses as values.
	 */
	public static void loadLecturerSubjectsAndCourses(Map<String, ArrayList<String>> map) {
	    lecturer.setLecturerSubjectsAndCourses(map);
	}
	
	/**
	 * Retrieves all subjects names and IDs from the database.
	 */
	public static void getAllSubjectsFromDB() {
	    ClientUI.chat.accept("getAllSubjectsNamesAndIdsFromDB");
	}

	/**
	 * Loads all subjects from the database into a map.
	 * @param map A map containing subject names and their corresponding IDs.
	 */
	public static void loadAllSubjectsFromDB(Map<String, String> map) {
		subjects_ID_Name = map;
	}
	
	/**
	 * Retrieves all courses names and IDs from the database.
	 */
	public static void getAllCoursesFromDB() {
	    ClientUI.chat.accept("getAllCoursesNamesAndIdsFromDB");
	}

	/**
	 * Loads all courses from the database into a map.
	 * @param map A map containing course names and their corresponding IDs.
	 */
	public static void loadAllCoursesFromDB(Map<String, String> map) {
		courses_ID_Name = map;
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

	    // -- ArrayList<String> lecturerDetails --
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
	                currStage = SceneManagment.createNewStage("/lecturer/LecturerDashboardGUI.fxml", "/lecturer/LecturerDashboard.css", "Home Dashboard");
	                currStage.show();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    });
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
	 * Handles the action when a tab button is clicked in the dashboard.
	 *
	 * @param actionEvent The action event
	 */
	public void handleClicks(ActionEvent actionEvent) {
		
		questionSelected = null; // reset the selected question
		
	    if (actionEvent.getSource() == btnShowReport) {
	    	handleAnimation(pnlShowReport, btnShowReport);
	        pnlShowReport.toFront();
	    }
	    if (actionEvent.getSource() == btnCheckExams) {	    	
	    	handleAnimation(pnlCheckExams, btnCheckExams);
	        pnlCheckExams.toFront();
	    }
	    if (actionEvent.getSource() == btnManageQuestions) { // Manage Questions panel
	    	tableView_ManageQuestions.getSelectionModel().clearSelection(); // To unSelect row in the questions table
	    	handleAnimation(pnlManageQuestions, btnManageQuestions);
	        pnlManageQuestions.toFront();    
	    }
	    if (actionEvent.getSource() == btnCreateExam) { // Create Exam panel
			try {
				getRefreshBtn_CreateExam(actionEvent); // reset the tables and buttons in the create exam panel
			} catch (Exception e) {
				e.printStackTrace();
			}
			handleAnimation(pnlCreateExam, btnCreateExam);
	        pnlCreateExam.toFront();
	    }
	    if (actionEvent.getSource() == btnManageExams) { // Working screen
	    	handleAnimation(pnlManageExams, btnManageExams);
	    	getAllActiveInActiveExams(); // send to the server a request to get all the exams (active and inactive)
	    	tableView_inActiveExams.getSelectionModel().clearSelection();
	    	tableView_activeExams.getSelectionModel().clearSelection();
	        pnlManageExams.toFront();
	    }
	}
	

	public void handleAnimation(Pane newPane, JFXButton newSection) {
		FadeTransition outgoingPane = new FadeTransition(Duration.millis(125), currentPane);
        outgoingPane.setFromValue(1);
        outgoingPane.setToValue(0);
        
        FadeTransition comingPane = new FadeTransition(Duration.millis(125), newPane);
        comingPane.setFromValue(0);
        comingPane.setToValue(1);
            
        SequentialTransition transition = new SequentialTransition();
        transition.getChildren().addAll(outgoingPane, comingPane);
        transition.play();
        
        newSection.setStyle("-fx-border-color: #FAF9F6");
        if(currentSection != null && currentSection != newSection) currentSection.setStyle("-fx-border-color: #242633");
        
        currentPane = newPane;
        currentSection = newSection;  
	}
	
	private void displayErrorMessage(String message) {
		snackbar = new JFXSnackbar(stackPane);
		String css = this.getClass().getClassLoader().getResource("lecturer/SnackbarError.css").toExternalForm();
        snackbar.setPrefWidth(754);
        snackbarLayout = new JFXSnackbarLayout(message);
        snackbarLayout.getStylesheets().add(css);
        snackbar.getStylesheets().add(css);
        snackbar.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
	}
	
	public void displaySuccessMessage(String message) {
	    Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            snackbar = new JFXSnackbar(stackPane);
				String css = this.getClass().getClassLoader().getResource("lecturer/SnackbarSuccess.css").toExternalForm();
				snackbar.setPrefWidth(754);
				snackbarLayout = new JFXSnackbarLayout(message);
				snackbarLayout.getStylesheets().add(css);
				snackbar.getStylesheets().add(css);
				snackbar.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
	        }
	    });
	}

}
