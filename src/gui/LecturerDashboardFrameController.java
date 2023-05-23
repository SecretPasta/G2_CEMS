package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Config.Lecturer;
import Config.Question;
import client.ChatClient;
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
import javafx.scene.layout.VBox;
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
	
	public LecturerDashboardFrameController() {
		instance = this;
	}

	public static LecturerDashboardFrameController getInstance() {
		return instance;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//ManageQuestions
		// Setting up cell value factories for lecturer's questions management table columns
		idColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("id"));
		subjectColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("subject"));
		courseNameColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("courseName"));
		questionTextColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("questionText"));
		questionNumberColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("questionNumber"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("lecturer"));
		
		lbluserNameAndID.setText(lecturer.getName() + "\n(" + lecturer.getId() + ")"); // set lecturer name and id under the picture
		
		ArrayList<String> getQuestionArray = new ArrayList<>();
		getQuestionArray.add("GetAllQuestionsFromDB");
		getQuestionArray.add(lecturer.getName());
		ClientUI.chat.accept(getQuestionArray);
		pnlManageQuestions.toFront();
		tableView.getSelectionModel().clearSelection(); // to unselect row in the questions table
		
		
		
	}
	
	// ManageQuestions
	public void loadArrayQuestionsToTable(ArrayList<Question> questions) {
		// Loading the array of questions into the table view
		questionsToEditObservableList.addAll(questions);
		tableView.setItems(questionsToEditObservableList);
	}
	
	
	// this function called also from non javafx class. starting the frame. get the lecturer details
	public static void start(ArrayList<String> lecturerDetails) throws IOException {
		
		lecturer = new Lecturer(lecturerDetails.get(2), lecturerDetails.get(3), lecturerDetails.get(4), lecturerDetails.get(5), lecturerDetails.get(6));
		// -- lecturerDetails --
		// 1 - login As
		// 2 - user ID
		// 3 - userName
		// 4 - user Password
		// 5 - user Name
		// 6 - user Email
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	try {
            		// saving the current dashboard screen for returning back
					currStage = SceneManagment.createNewStage("/gui/LecturerDashboardGUI.fxml", "/gui/LecturerDashboard.css", "Home Dashboard");
					currStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
	}
	
	// function to open the dashboard screen from EditQuestionFrameController without creating it again
	public void showDashboardFrom_EditQuestions(String edited_QuestionID, String edited_QuestionText, String edited_QuestionNumber) throws IOException {
		
		// updating the edited question in the table of the lecturer questions
		for(int i = 0; i < questionsToEditObservableList.size(); i++) {
			if(questionsToEditObservableList.get(i).getId() == edited_QuestionID) {
				questionsToEditObservableList.get(i).setQuestionText(edited_QuestionText);
				questionsToEditObservableList.get(i).setQuestionNumber(edited_QuestionNumber);
			}
		}
		currStage.show();
		tableView.refresh();
		tableView.getSelectionModel().clearSelection(); // to unselect row in the questions table
	}
	
	// when lecturer click on close button
	public void getCloseBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		ClientUI.chat.client.quit(lecturer.getId(), "lecturer");
	}
	
	public void getLogoutBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window

		ArrayList<String> qArr = new ArrayList<>();
		qArr.add("UserLogout");
		qArr.add("lecturer");
		qArr.add(lecturer.getId());
		//ClientUI.chat.accept(qArr);
		System.out.println("logged out");
		ChatClient.getInstance().sendToServer(qArr); // send to the server that the user logout the change his islogged to 0
		
		LoginFrameController.start(); // start the login screen after logout
		
	}
	// when lecturer click on edit on edit question 
	public void getEditBtn_ManageQuestions(ActionEvent event) throws Exception {

		// Getting the selected question from the table view
		questionSelected = tableView.getSelectionModel().getSelectedItem();
		if (questionSelected == null) {
			lblMessage.setText("[Error] No question was selected.");
		} else {
			lblMessage.setText("");

			// Hiding the primary window
			((Node) event.getSource()).getScene().getWindow().hide();
			
			// Creating and showing the UpdateQuestionGUI window
			EditQuestionFrameController.start();
		}
	}
	
	// when lecturer click on Add on edit question 
	public void getAddBtn_ManageQuestions(ActionEvent event) throws Exception {
		
	}
	
	// when lecturer click on Add on edit question 
	public void getRemoveBtn_ManageQuestions(ActionEvent event) throws Exception {
		// Getting the selected question from the table view
		questionSelected = tableView.getSelectionModel().getSelectedItem();
		if (questionSelected == null) {
			lblMessage.setText("[Error] No question was selected.");
		} else {
			lblMessage.setText("");
			
			// sending the server the id of the question to remove from DB
			ArrayList<String> questionToRemoveArr = new ArrayList<>();
			questionToRemoveArr.add("RemoveQuestionFromDB");
			questionToRemoveArr.add(questionSelected.getId());
			ClientUI.chat.accept(questionToRemoveArr);
			
			// remove also from the question table to edit
			for(int i = 0; i < questionsToEditObservableList.size(); i++) {
				if(questionsToEditObservableList.get(i).getId() == questionSelected.getId()) {
					questionsToEditObservableList.remove(i);
				}
			}
			tableView.refresh();
			
		}
	}
	
	// handle the tabs in the dashboard
	public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnShowReport) {
            pnlShowReport.toFront();
        }
        if (actionEvent.getSource() == btnCheckExams) {
        	pnlCheckExams.setStyle("-fx-background-color: #FFFFFF");
            pnlCheckExams.toFront();
        }
        if (actionEvent.getSource() == btnManageQuestions) {
        	tableView.getSelectionModel().clearSelection(); // to unselect row in the questions table
            pnlManageQuestions.toFront();    
        }
        if(actionEvent.getSource() == btnCreateExam){
        	pnlCreateExam.setStyle("-fx-background-color: #FFFFFF");
            pnlCreateExam.toFront();
        }
        if(actionEvent.getSource() == btnManageExams){
        	pnlManageExams.setStyle("-fx-background-color: #FFFFFF");
            pnlManageExams.toFront();
        }
    }

}
