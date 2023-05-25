package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

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
import javafx.scene.control.Button;
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
	private Label lblMessage;
	
	@FXML
	private Label lbluserNameAndID;

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
	private Button btnClose = null;

	@FXML
	private Button btnEdit = null;

	@FXML
	private TableView<Question> tableView = new TableView<>();

	@FXML
	private TableColumn<Question, String> idColumn;
	@FXML
	private TableColumn<Question, String> subjectColumn;
	@FXML
	private TableColumn<Question, String> courseNameColumn;
	@FXML
	private TableColumn<Question, String> questionTextColumn;
	@FXML
	private TableColumn<Question, String> authorColumn;
	@FXML
	private TableColumn<Question, String> questionNumberColumn;
	
	private static Lecturer lecturer; // current lecture
	
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
	    // ManageQuestions

	    // Setting up cell value factories for lecturer's questions management table columns
	    idColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("id"));
	    subjectColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("subject"));
	    courseNameColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("courseName"));
	    questionTextColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("questionText"));
	    questionNumberColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("questionNumber"));
	    authorColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("lecturer"));

	    lbluserNameAndID.setText(lecturer.getName() + "\n(" + lecturer.getId() + ")");
	    // Set lecturer name and id under in the frame

	    ArrayList<String> getQuestionArray = new ArrayList<>();
	    getQuestionArray.add("GetAllQuestionsFromDB");
	    getQuestionArray.add(lecturer.getName());
	    // Create an ArrayList to get all questions from the database for the current lecturer
	    // The lecturer's name is added as a parameter
	    ClientUI.chat.accept(getQuestionArray);
	    
	    pnlManageQuestions.toFront();
	    tableView.getSelectionModel().clearSelection();
	    // Show the ManageQuestions panel and clear the selection in the questions table
	}

	
	/**
	 * Loads an array of questions into the table view.
	 *
	 * @param questions The ArrayList of questions to be loaded
	 */
	public void loadArrayQuestionsToTable(ArrayList<Question> questions) {

	    // Add all questions from the ArrayList to the questionsToEditObservableList
	    questionsToEditObservableList.addAll(questions);

	    // Set the items of the table view to the questionsToEditObservableList
	    tableView.setItems(questionsToEditObservableList);
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
	    tableView.refresh();

	    // Clear the selection in the questions table
	    tableView.getSelectionModel().clearSelection();
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
	    questionSelected = tableView.getSelectionModel().getSelectedItem();
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
		
		
		// Show the current stage
	    currStage.show();

	    // Refresh the table view to reflect the changes
	    tableView.refresh();

	    // Clear the selection in the questions table
	    tableView.getSelectionModel().clearSelection();
	}
	
	/**
	 * Handles the action when the lecturer clicks on the remove button for a question in the Manage Questions screen.
	 *
	 * @param event The action event
	 * @throws Exception If an exception occurs during the execution
	 */
	public void getRemoveBtn_ManageQuestions(ActionEvent event) throws Exception {
	    // Getting the selected question from the table view
	    questionSelected = tableView.getSelectionModel().getSelectedItem();
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
	        tableView.refresh();
	    }
	}

	
	/**
	 * Handles the action when a tab button is clicked in the dashboard.
	 *
	 * @param actionEvent The action event
	 */
	public void handleClicks(ActionEvent actionEvent) {
	    if (actionEvent.getSource() == btnShowReport) {
	        pnlShowReport.toFront();
	    }
	    if (actionEvent.getSource() == btnCheckExams) {
	        pnlCheckExams.setStyle("-fx-background-color: #FFFFFF");
	        pnlCheckExams.toFront();
	    }
	    if (actionEvent.getSource() == btnManageQuestions) {
	        tableView.getSelectionModel().clearSelection(); // To unselect row in the questions table
	        pnlManageQuestions.toFront();    
	    }
	    if (actionEvent.getSource() == btnCreateExam) {
	        pnlCreateExam.setStyle("-fx-background-color: #FFFFFF");
	        pnlCreateExam.toFront();
	    }
	    if (actionEvent.getSource() == btnManageExams) {
	        pnlManageExams.setStyle("-fx-background-color: #FFFFFF");
	        pnlManageExams.toFront();
	    }
	}


}
