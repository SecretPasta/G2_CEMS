package gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Config.Question;
import JDBC.DBController;
import javafx.scene.control.cell.PropertyValueFactory;

public class QuestionBankFrameController implements Initializable {
	@FXML
	private Button btnClose = null;

	@FXML
	private Button btnSelect = null;

	@FXML
	private Label lblMessage;

	@FXML
	private TableView<Question> tableView = new TableView<>();;

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

	private static QuestionBankFrameController instance;
	static Question questionSelected;

	public QuestionBankFrameController() {
		instance = this;
	}

	public static QuestionBankFrameController getInstance() {
		return instance;
	}
	
	//Close Button functionality
	public void getClosebtn(ActionEvent event) throws Exception {
		// Hiding the primary window
		((Node) event.getSource()).getScene().getWindow().hide();
		// Quitting the client
		ClientUI.chat.client.quit();
		// Running garbage collector
		System.gc();
	}
	
	//Select Button functionality  
	public void getSelectbtn(ActionEvent event) throws Exception {

		// Getting the selected question from the table view
		questionSelected = tableView.getSelectionModel().getSelectedItem();
		if (questionSelected == null) {
			lblMessage.setTextFill(Color.color(1, 0, 0));
			lblMessage.setText("[Error] No question was selected.");
		} else {
			lblMessage.setText("");

			// Hiding the primary window
			((Node) event.getSource()).getScene().getWindow().hide();
			
			// Creating and showing the UpdateQuestionGUI window
			
			UpdateQuestionFrameController.start();
		}
	}
	
	public static void start() throws IOException {
		SceneManagment.createNewStage("/gui/QuestionBankGUI.fxml", null, "Question Bank Management Tool").show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Setting up cell value factories for table columns
		idColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("id"));
		subjectColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("subject"));
		courseNameColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("courseName"));
		questionTextColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("questionText"));
		questionNumberColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("questionNumber"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("lecturer"));

		// Sending a request to the server to get all questions from the database
		ClientUI.chat.accept("GetAllQuestionsFromDB");
	}

	public void loadArrayQuestionsToTable(ArrayList<Question> questions) {
		// Loading the array of questions into the table view
		ObservableList<Question> observableList = FXCollections.observableArrayList();
		observableList.addAll(questions);
		tableView.setItems(observableList);
	}

}
