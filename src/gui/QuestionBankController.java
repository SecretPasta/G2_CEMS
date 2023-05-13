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
import Config.Question;
import JDBC.DBController;
import javafx.scene.control.cell.PropertyValueFactory;


public class QuestionBankController implements Initializable {
	@FXML
	private Button btnClose=null;
	
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
	
	private static QuestionBankController instance;
	static Question questionSelected;
	
    public QuestionBankController() {
        instance = this;
    }
    
    public static QuestionBankController getInstance() {
        return instance;
    }
	
	
	public void getClosebtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		ClientUI.chat.client.quit();
	}
	
	
	public void getSelectbtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		
		questionSelected = tableView.getSelectionModel().getSelectedItem();
		if(questionSelected == null) {
			lblMessage.setTextFill(Color.color(1, 0, 0));
			lblMessage.setText("[Error] No question was selected.");
		}
		else {
			lblMessage.setText("");
			
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/gui/UpdateQuestionGUI.fxml").openStream());		
		
			Scene scene = new Scene(root);			
			scene.getStylesheets().add(getClass().getResource("/gui/UpdateQuestion.css").toExternalForm());
			primaryStage.setTitle("Question Update Managment Tool");
	
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
	}
	
	
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/QuestionBankGUI.fxml"));
				
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/QuestionBank.css").toExternalForm());
		primaryStage.setTitle("Question Bank Managment Tool");
		primaryStage.setScene(scene);
		
		primaryStage.show();	 	   
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources){
		
		idColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("id"));
	    subjectColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("subject"));
	    courseNameColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("courseName"));
	    questionTextColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("questionText"));
	    questionNumberColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("questionNumber"));
	    authorColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("lecturer"));

		ClientUI.chat.accept("GetAllQuestionsFromDB");   
	}
	
	public void loadArrayQuestionsToTable(ArrayList<Question> questions) {
		ObservableList<Question> observableList = FXCollections.observableArrayList();
        observableList.addAll(questions);
        tableView.setItems(observableList);
	}

}
