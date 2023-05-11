package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Config.Question;
import client.ClientUI;

public class UpdateQuestionFrameController implements Initializable {
	private Question q;
	@FXML
	private Label lblidQuestion;
	@FXML
	private Label lblSubjectInfo;
	@FXML
	private Label lblCourseNameInfo;
	@FXML
	private Label lblQuestionTextInfo;
	@FXML
	private Label lblQuestionNumberInfo;
	@FXML
	private Label lblAuthorInfo;
	@FXML
	private Label lblMessage;
	
	
	@FXML
	private Label txtQuestionID;
	@FXML
	private Label txtSubject;
	@FXML
	private Label txtCoursename;
	@FXML
	private TextField txtQuestionText;
	@FXML
	private TextField txtQuestionNumber;
	@FXML
	private Label txtQuestionAuthor;
	
	@FXML
	private Button btnBack=null;
	
	@FXML
	private Button btnSave = null;
	
	public void loadQuestion(Question q1) {
		this.q=q1;
		this.txtQuestionID.setText(q.getId());
		this.txtSubject.setText(q.getSubject());
		this.txtCoursename.setText(q.getCourseName());		
		this.txtQuestionText.setText(q.getQuestionText());
		this.txtQuestionNumber.setText(q.getQuestionNumber());
		this.txtQuestionAuthor.setText(q.getLecturer());
	}
	
	public void getBackbtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/QuestionBankGUI.fxml").openStream());		
	
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/QuestionBank.css").toExternalForm());
		primaryStage.setTitle("Question Managment Tool");

		primaryStage.setScene(scene);		
		primaryStage.show();
	
	}
	
	
	public void getSavebtn(ActionEvent event) throws Exception {
		if( txtQuestionText.getText().equals("") || txtQuestionNumber.getText().equals("")) {
			lblMessage.setTextFill(Color.color(1, 0, 0));
			lblMessage.setText("[Error] Missing fields.");
		}
		
		else {
			lblMessage.setTextFill(Color.rgb(0, 102, 0));
			lblMessage.setText("Question Saved Successfully");
			ArrayList<String> sArr = new ArrayList<>();
			sArr.add(q.getId());
			sArr.add(q.getSubject());
			sArr.add(q.getCourseName());
			sArr.add(q.getQuestionText());
			sArr.add(q.getQuestionNumber());
			sArr.add(q.getLecturer());
			ClientUI.chat.client.sendToServer(sArr);
		}
	}
	
	
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/UpdateQuestionGUI.fxml"));
				
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/UpdateQuestion.css").toExternalForm());
		primaryStage.setTitle("Question Managment Tool");
		primaryStage.setScene(scene);
		
		primaryStage.show();	 	   
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

}
