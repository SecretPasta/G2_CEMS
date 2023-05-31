package lecturer;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;

import Config.Lecturer;
import Config.Question;
import Config.QuestionInExam;
import client.ClientUI;
import ClientAndServerLogin.LoginFrameController;
import ClientAndServerLogin.SceneManagment;
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
	
	private JFXButton currentSection;
	
	@FXML
    private JFXButton btnSearch_CreateExam;
	@FXML
    private JFXButton btnRefresh_CreateExam;
	@FXML
    private JFXButton btcContinue_CreateExam;
	
	@FXML
	private JFXSnackbar snackbarError;
	
	@FXML
	private Label lblMessage1;
	@FXML
	private Label lblMessage2;
	@FXML
	private Label lbluserNameAndID;
	@FXML
	private Label lblTotalQuestionSelectedPoints;
	
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
	
	private static Lecturer lecturer; // current lecturer
	
	private static Map<String, String> subjects_ID_Name = new HashMap<>();
	
	private static Map<String, String> courses_ID_Name = new HashMap<>();
	
	private ObservableList<QuestionInExam> questionsToCreateExamObservableList2 = FXCollections.observableArrayList(); // list2 of questions to select for exam

	private ObservableList<Question> questionsToCreateExamObservableList = FXCollections.observableArrayList(); // list of questions to select for exam
	
	private ObservableList<Question> questionsToEditObservableList = FXCollections.observableArrayList(); // list of questions to select to Edit in the table
	
	protected static Stage currStage; // save current stage

	private static Question questionSelected; // question selected to edit or to delete
	
	private double total_points_CreateExam;
	
	private QuestionInExam questionInExamSelected;
	
	private static LecturerDashboardFrameController instance;
	
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
	    courseNameColumn_ManageQuestions.setCellValueFactory(new PropertyValueFactory<Question, String>("courseName"));
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
	    
	    // -------------- CreateExam --------------
	    
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
	    
	    
	    pointsColumn_CreateExam2.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {
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
	                snackbarError = new JFXSnackbar(pnlCreateExam);
	                snackbarError.setPrefWidth(754);
	                snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("[Error] Points should be only numbers"), Duration.millis(3000), null));
	                return null; // Return null to indicate a conversion error
	            }
	        }
	    }));    
	    //pointsColumn_CreateExam2.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

	    
	    tableView_CreateExam2.setEditable(true);
	    
	 // -------------- CreateExam --------------
	    

	}
	
	
	
    public static void setSubjectsNameById(Map<String, String> subjects_map_id_name) { 	
    	subjects_ID_Name = subjects_map_id_name;
    }
    
    public String getSubjectNameById(String subjectID) { 	
		return subjects_ID_Name.get(subjectID);
    }
    
    public static String getSubjectIdByName(String subjectName) { 	
    	for (Map.Entry<String, String> entry : subjects_ID_Name.entrySet()) {
    		if (subjectName.equals(entry.getValue())) {
    			return entry.getKey();
    		}
        }
    	return null; // Value not found
    }
    
    public static void setCoursesNameById(Map<String, String> courses_map_id_name) { 	
    	courses_ID_Name = courses_map_id_name;
    }
    
    public String getCourseNameById(String courseID) { 	
		return courses_ID_Name.get(courseID);
    }
    
    public static String getCourseIdByName(String courseName) { 	
    	for (Map.Entry<String, String> entry : courses_ID_Name.entrySet()) {
    		if (courseName.equals(entry.getValue())) {
    			return entry.getKey();
    		}
        }
    	return null; // Value not found
    }
	
	

	// -------------- ManageQuestions PANEL --------------
	
	/**
	 * Loads an array of questions into the table view.
	 *
	 * @param questions The ArrayList of questions to be loaded
	 */
	public void loadArrayQuestionsToTable_ManageQuestions(ArrayList<Question> questions) {
		
		for(Question question : questions) {
			question.setSubject(getSubjectNameById(question.getsubjectID()));
		}
		
		for(Question question : questions) {
			question.setCourseName(getCourseNameById(question.getCourseID()));
		}
		
	    // Add all questions from the ArrayList to the questionsToEditObservableList
	    questionsToEditObservableList.addAll(questions);

	    // Set the items of the table view to the questionsToEditObservableList
	    tableView_ManageQuestions.setItems(questionsToEditObservableList);
	}
	
	
	/**
	 * Opens the dashboard screen from the EditQuestionFrameController without creating it again.
	 *
	 * @param edited_QuestionID      The ID of the edited question
	 * @param edited_QuestionText    The edited question text
	 * @throws IOException If an I/O exception occurs during the execution
	 */
	public void showDashboardFrom_EditQuestions(String edited_QuestionID, String edited_QuestionText) throws IOException {
		// Update the edited question in the table of the lecturer's questions
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
	 * Handles the action when the lecturer clicks on the edit button for a question in the Manage Questions screen.
	 *
	 * @param event The action event
	 * @throws Exception If an exception occurs during the execution
	 */
	public void getEditBtn_ManageQuestions(ActionEvent event) throws Exception {
		
	    // Getting the selected question from the table view
	    questionSelected = tableView_ManageQuestions.getSelectionModel().getSelectedItem();
	    if (questionSelected == null) {
	    	snackbarError = new JFXSnackbar(pnlManageQuestions);
	    	snackbarError.setPrefWidth(754);
	        snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("No question was selected."), Duration.millis(3000), null));
	    } else {

	        // Hide the primary window
	        ((Node) event.getSource()).getScene().getWindow().hide();

	        // Create and show the EditQuestion screen window
	        EditQuestionFrameController.start(questionSelected);
	        questionSelected = null;
	    }
	}

	
	/**
	 * Event handler for when the lecturer clicks on the "Add" button in the "Manage Questions" screen.
	 * Hides the primary window and navigates to the "Add Question" screen.
	 *
	 * @param event The action event triggered by clicking the "Add" button.
	 * @throws Exception If an exception occurs during navigation to the "Add Question" screen.
	 */
	public void getAddBtn_ManageQuestions(ActionEvent event) throws Exception {
		
	    // Hide the primary window
	    ((Node) event.getSource()).getScene().getWindow().hide();

	    // Navigate to the "Add Question" screen and pass the lecturer object
	    AddQuestionFrameController.start(lecturer);
	}
	
	public void getSearchBox_ManageQuestions(ActionEvent event) throws Exception {
		
		String courseName_toSort = boxSearchbyCourse_ManageQuestions.getSelectionModel().getSelectedItem();
		
		filterQuestions(courseName_toSort);
		
		tableView_ManageQuestions.getSelectionModel().clearSelection();
		
	}

    public void filterQuestions(String selectedCourse) {
        if (selectedCourse.equals("All")) {
        	tableView_ManageQuestions.setItems(questionsToEditObservableList); // Show all questions
        } else {
            ObservableList<Question> filteredData = FXCollections.observableArrayList();
            for (Question question : questionsToEditObservableList) {
                if (question.getCourseName().equals(selectedCourse)) {
                    filteredData.add(question);
                }
            }
            tableView_ManageQuestions.setItems(filteredData); // Show questions for the selected course
        }
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
	    	snackbarError = new JFXSnackbar(pnlManageQuestions);
	    	snackbarError.setPrefWidth(754);
	        snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("No question was selected."), Duration.millis(3000), null));
	    } else {

	        // Send a message to the server to remove the question from the database
	        ArrayList<String> questionToRemoveArr = new ArrayList<>();
	        questionToRemoveArr.add("RemoveQuestionFromDB");
	        questionToRemoveArr.add(questionSelected.getId());
	        ClientUI.chat.accept(questionToRemoveArr);
	        
	        snackbarError = new JFXSnackbar(pnlManageQuestions);
	    	snackbarError.setPrefWidth(754);
	        snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Question (ID:" + questionSelected.getId() + ") removed succesfully"), Duration.millis(3000), null));    

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
	 * Displays the dashboard screen after adding a question from the "Add Question" screen or getting back.
	 *
	 * @param newQuestion The newly added question to be displayed in the dashboard.
	 */
	public void showDashboardFrom_AddQuestion(ArrayList<Question> newQuestion) {
		
	    if (newQuestion != null) {
	    	
	    	for(Question q : newQuestion) {
	    		q.setSubject(getSubjectNameById(q.getsubjectID()));
	    	}
	    	
	    	for(Question q : newQuestion) {
	    		q.setCourseName(getCourseNameById(q.getCourseID()));
	    	}
	    	
	        // Add the new question to the questionsToEditObservableList
	        questionsToEditObservableList.addAll(newQuestion);
	        snackbarError = new JFXSnackbar(pnlManageQuestions);
	    	snackbarError.setPrefWidth(754);
	    	JFXSnackbarLayout layout = new JFXSnackbarLayout("Question added successfully");
	        snackbarError.fireEvent(new SnackbarEvent(layout, Duration.millis(3000), null));
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
	 * Loads an ArrayList of questions into the table view for creating an exam.
	 *
	 * @param questions The ArrayList of questions to be loaded into the table view.
	 */
	public void loadArrayQuestionsToTable_CreateExam(ArrayList<Question> questions) {

	    // Add all questions from the ArrayList to the questionsToCreateExamObservableList
	    questionsToCreateExamObservableList.addAll(questions);

	    // Set the items of the table view to the questionsToCreateExamObservableList
	    tableView_CreateExam.setItems(questionsToCreateExamObservableList);
	    
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
	    String subjectSelect = subjectSelectBox_CreateExam.getSelectionModel().getSelectedItem();
	    String courseSelect = courseSelectBox_CreateExam.getSelectionModel().getSelectedItem();


	    if (subjectSelect == null || courseSelect == null) {
	        // Display an error message if any field is missing
	    	snackbarError = new JFXSnackbar(pnlCreateExam);
	    	snackbarError.setPrefWidth(754);
	        snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Error: Missing fields"), Duration.millis(3000), null));
	    } else {

	        // Prepare and send a request to the server to retrieve questions for the selected subject and course
	        ArrayList<String> getQuestionsArr = new ArrayList<>();
	        getQuestionsArr.add("GetQuestionsForLecturerBySubjectAndCourseToCreateExamTable");
	        getQuestionsArr.add(getSubjectIdByName(subjectSelect));
	        getQuestionsArr.add(getCourseIdByName(courseSelect));
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
	
	public void getRefreshBtn_CreateExam(ActionEvent event) throws Exception {
		questionsToCreateExamObservableList2.removeAll();
		tableView_CreateExam2.getItems().clear();
		tableView_CreateExam2.getSelectionModel().clearSelection();
		questionsToCreateExamObservableList.removeAll();
		tableView_CreateExam.getItems().clear();
		tableView_CreateExam.getSelectionModel().clearSelection();
		btnSearch_CreateExam.setDisable(false);
		subjectSelectBox_CreateExam.setDisable(false);
		courseSelectBox_CreateExam.setDisable(false);
    	courseSelectBox_CreateExam.getItems().clear();
    	subjectSelectBox_CreateExam.setValue(null);
    	total_points_CreateExam = 0.0;
    	lblTotalQuestionSelectedPoints.setText(String.valueOf(total_points_CreateExam));
    	btcContinue_CreateExam.setDisable(true);
	}
	
	public void getChooseQuestionBtn_CreateExam(ActionEvent event) throws Exception {
		
		questionSelected = tableView_CreateExam.getSelectionModel().getSelectedItem();
	
		if(questionsToCreateExamObservableList2.contains(questionSelected)) {
			snackbarError = new JFXSnackbar(pnlCreateExam);
	    	snackbarError.setPrefWidth(754);
	        snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Error: This question already added"), Duration.millis(3000), null));
		}
		else if(questionSelected == null) {
			snackbarError = new JFXSnackbar(pnlCreateExam);
	    	snackbarError.setPrefWidth(754);
	        snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("[Error] No question selected"), Duration.millis(3000), null));
		}
		else {
			questionInExamSelected = new QuestionInExam(questionSelected);
			questionsToCreateExamObservableList2.add(questionInExamSelected);
			tableView_CreateExam2.setItems(questionsToCreateExamObservableList2);
			
			questionsToCreateExamObservableList.remove(questionSelected);
			tableView_CreateExam.getSelectionModel().clearSelection();
			tableView_CreateExam.refresh();
			
			btnSearch_CreateExam.setDisable(true);
			subjectSelectBox_CreateExam.setDisable(true);
			courseSelectBox_CreateExam.setDisable(true);
			
			tableView_CreateExam2.getSelectionModel().clearSelection();
			tableView_CreateExam.getSelectionModel().clearSelection();
		}

	    

	    
		
		
		questionSelected = null;
	}
	
	
	public void getRemoveQuestion_CreateExam(ActionEvent event) throws Exception {
		
		questionInExamSelected = tableView_CreateExam2.getSelectionModel().getSelectedItem();
	
		if(questionInExamSelected == null) {
			snackbarError = new JFXSnackbar(pnlCreateExam);
	    	snackbarError.setPrefWidth(754);
	        snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("[Error] No question selected"), Duration.millis(3000), null));
		}
		else {
			
			total_points_CreateExam -= questionInExamSelected.getPoints();
			
			questionsToCreateExamObservableList.add((Question)questionInExamSelected);
			tableView_CreateExam.setItems(questionsToCreateExamObservableList);
			
			questionsToCreateExamObservableList2.remove(questionInExamSelected);
			tableView_CreateExam2.getSelectionModel().clearSelection();
			tableView_CreateExam2.refresh();
			
			lblTotalQuestionSelectedPoints.setText(String.valueOf(total_points_CreateExam));
			
			if(tableView_CreateExam2.getItems().isEmpty()) {
				btnSearch_CreateExam.setDisable(false);
				subjectSelectBox_CreateExam.setDisable(false);
				courseSelectBox_CreateExam.setDisable(false);
			}
			
			if(questionsToCreateExamObservableList2.isEmpty() || total_points_CreateExam != 100) {
				btcContinue_CreateExam.setDisable(true);
			}
		}
		
		tableView_CreateExam2.getSelectionModel().clearSelection();
		tableView_CreateExam.getSelectionModel().clearSelection();
		questionInExamSelected = null;
		questionSelected = null;
	}
	
	
    public void getEditPoints(CellEditEvent<QuestionInExam, Double> event) {
    	try {
	    	questionInExamSelected = tableView_CreateExam2.getSelectionModel().getSelectedItem();
	    	
	    	Double newPoints = event.getNewValue();
	    	
	        Double oldPoints = questionInExamSelected.getPoints();
	        
	        Double temp_total_points = total_points_CreateExam + newPoints - oldPoints;
	        
	        if(temp_total_points > 100) {	
				snackbarError = new JFXSnackbar(pnlCreateExam);
		    	snackbarError.setPrefWidth(754);
		        snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("[Error] Total points will be over 100"), Duration.millis(3000), null));       	
	        }
	        else {
	        	total_points_CreateExam = total_points_CreateExam - oldPoints + newPoints;
	            questionInExamSelected.setPoints(newPoints);
	
	        	
	        }
	        
	        tableView_CreateExam2.refresh();
	        lblTotalQuestionSelectedPoints.setText(String.valueOf(total_points_CreateExam));
	        
			tableView_CreateExam2.getSelectionModel().clearSelection();
			tableView_CreateExam.getSelectionModel().clearSelection();
	        
			if(total_points_CreateExam != 100) {
				btcContinue_CreateExam.setDisable(true);
			}
			else if(total_points_CreateExam == 100) {
				btcContinue_CreateExam.setDisable(false);
			}
        
    	}catch (NumberFormatException | NullPointerException e) {
			snackbarError = new JFXSnackbar(pnlCreateExam);
	    	snackbarError.setPrefWidth(754);
	        snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("[Error] Points sholud be only numbers"), Duration.millis(3000), null));   
    	}
    }
    
	public void getBtnContinue_CreateExam(ActionEvent event) throws Exception {
		if(questionsToCreateExamObservableList2.isEmpty()) {
			snackbarError = new JFXSnackbar(pnlCreateExam);
	    	snackbarError.setPrefWidth(754);
	        snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("[Error] No questions in the test"), Duration.millis(3000), null)); 
		}
		else if(total_points_CreateExam != 100){
			snackbarError = new JFXSnackbar(pnlCreateExam);
	    	snackbarError.setPrefWidth(754);
	        snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("[Error] Total points have to be 100"), Duration.millis(3000), null)); 
		}
		else {
			((Node) event.getSource()).getScene().getWindow().hide();
			CreateExam_CommentsAndTimeFrameController.start(lecturer, questionsToCreateExamObservableList2);
		}
	}

	// -------------- CreateExam PANEL --------------

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
	
	public static void getAllSubjectsFromDB() {
	    ClientUI.chat.accept("getAllSubjectsNamesAndIdsFromDB");
	}
	
	public static void loadAllSubjectsFromDB(Map<String, String> map) {
	    setSubjectsNameById(map);
	}
	
	public static void getAllCoursesFromDB() {
	    ClientUI.chat.accept("getAllCoursesNamesAndIdsFromDB");
	}
	
	public static void loadAllCoursesFromDB(Map<String, String> map) {
	    setCoursesNameById(map);
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
		
		questionSelected = null;
		
	    if (actionEvent.getSource() == btnShowReport) {
	    	handleAnimation(pnlShowReport, btnShowReport);
	        pnlShowReport.toFront();
	    }
	    if (actionEvent.getSource() == btnCheckExams) {	    	
	    	handleAnimation(pnlCheckExams, btnCheckExams);
	        pnlCheckExams.toFront();
	    }
	    if (actionEvent.getSource() == btnManageQuestions) {
	    	tableView_ManageQuestions.getSelectionModel().clearSelection(); // To unselect row in the questions table
	    	handleAnimation(pnlManageQuestions, btnManageQuestions);
	        pnlManageQuestions.toFront();    
	    }
	    if (actionEvent.getSource() == btnCreateExam) {
			try {
				getRefreshBtn_CreateExam(actionEvent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			handleAnimation(pnlCreateExam, btnCreateExam);
	        pnlCreateExam.toFront();
	    }
	    if (actionEvent.getSource() == btnManageExams) {
	    	handleAnimation(pnlManageExams, btnManageExams);
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
        if(currentSection != null) currentSection.setStyle("-fx-border-color: #242633");
        
        currentPane = newPane;
        currentSection = newSection;  
	}

}
