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
import javafx.scene.layout.VBox;

public class LecturerDashboardFrameController implements Initializable{
	@FXML
    private JFXButton btnEditQuestion;
	
	@FXML
    private JFXButton btnAddQuestion;
	
	@FXML
    private JFXButton btnRemoveQuestion;
	
	@FXML
	private Label lblMessage;
	
	@FXML
	private Label lbluserNameAndID;
	
	@FXML
	private VBox pnItems;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlEditQuestion;

    @FXML
    private Pane pnlMenus;
	
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
		// Setting up cell value factories for connected clients table columns
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
		
	}
	
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
					SceneManagment.createNewStage("/gui/LecturerDashboardGUI.fxml", "/gui/HomeStyle.css", "Home Dashboard").show();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
	}
	
	// another start func for getting back
	public static void start() throws IOException {
		SceneManagment.createNewStage("/gui/LecturerDashboardGUI.fxml", "/gui/HomeStyle.css", "Home Dashboard").show();
	}
	
	// when lecturer click on close button
	public void getCloseBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		ClientUI.chat.client.quit();
	}
	
	// when lecturer click on edit on edit question 
	public void getEditBtn_EditQuestion(ActionEvent event) throws Exception {

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
	public void getAddBtn_EditQuestion(ActionEvent event) throws Exception {
		
	}
	
	// when lecturer click on Add on edit question 
	public void getRemoveBtn_EditQuestion(ActionEvent event) throws Exception {
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
			
		}
	}
	
	// handle the tabs in the dashboard
	public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color : #1620A1");
            pnlCustomer.toFront();
        }
        if (actionEvent.getSource() == btnMenus) {
            pnlMenus.setStyle("-fx-background-color : #53639F");
            pnlMenus.toFront();
        }
        if (actionEvent.getSource() == btnEditQuestion) {
            pnlEditQuestion.setStyle("-fx-background-color : #02030A");
            pnlEditQuestion.toFront();
            
        }
        if(actionEvent.getSource()==btnOrders)
        {
            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();
        }
    }

}
