package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import Config.Lecturer;
import Config.Question;
import client.ClientUI;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;



public class AddQuestionFrameController implements Initializable {
	
    @FXML
    private JFXComboBox<String> subjectSelectBox;
    @FXML
    private JFXListView<String> courseSelectList;
    
    @FXML
    private Label lblMessage;

	@FXML
    private TextField textQuestionText;
    @FXML
    private TextField txtAnswerCorrect;
    @FXML
    private TextField txtAnswerWrong1;
    @FXML
    private TextField txtAnswerWrong2;
    @FXML
    private TextField txtAnswerWrong3;
    @FXML
    private TextField txtQuestionNumber;
    
    @FXML
    private JFXButton backBtn;
    
    @FXML
    private JFXButton addQuestionBtn;
    
    private Lecturer lecturer;
    
    private ArrayList<Question> newQuestion;
    
    private static String maxIdOfQuestionInCurrentSubject;
    
    private static Map<String, ArrayList<String>> subjectsCoursesMap = new HashMap<>(); // map for subjects and courses for the lecturer
    
	public static void start(Lecturer lecturer, Map<String, ArrayList<String>> map) throws IOException {

		subjectsCoursesMap = map; // get the subjects and courses of the lecturer
		SceneManagment.createNewStage("/gui/AddQuestionGUI.fxml", null, "Question Add Managment Tool").show();

	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturer = LecturerDashboardFrameController.getLecturer(); // save the current lecturer from the dashboard
		
		courseSelectList.getItems().add("Please select a subject first");
		courseSelectList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		// add subjects to choose from the subjectSelectBox
		for(Map.Entry<String, ArrayList<String>> entry : subjectsCoursesMap.entrySet()) {
			subjectSelectBox.getItems().add(entry.getKey());
		}
	}
	
	public void getBackBtn(ActionEvent event) throws Exception {
	    ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	    
	    // When getting back, update the edited question in the question's lecturer table in the dashboard screen
	    // Pass the updated questions details to the LecturerDashboardFrameController's showDashboardFrom_EditQuestions() method
	    LecturerDashboardFrameController.getInstance().showDashboardFrom_AddQuestion(newQuestion);
	}
	
	public void getSubjectSelectBox(ActionEvent event) throws Exception {
		courseSelectList.getItems().clear();
		// add to the courseSelectBox all the courses in the map (the courses in the subject that the lecture selected)
		for(Map.Entry<String, ArrayList<String>> entry : subjectsCoursesMap.entrySet()) {
			if(subjectSelectBox.getSelectionModel().getSelectedItem() == entry.getKey()) {
				courseSelectList.getItems().addAll(entry.getValue());
				break;
			}
		}
	}
	
	public void getCourseSelectBox(ActionEvent event) throws Exception {
	}
	
	public static void saveMaxIdOfQuestionInSelectedSubject(String maxId) {
		maxIdOfQuestionInCurrentSubject = maxId;
	}

	public void getAddQuestionBtn(ActionEvent event) throws Exception {
		
		ObservableList<String> coursesSelect = courseSelectList.getSelectionModel().getSelectedItems();
		String subjectSelect = subjectSelectBox.getSelectionModel().getSelectedItem();
		try {
		    if (subjectSelect == null || coursesSelect.get(0).equals("Please select a subject first") || coursesSelect == null ||
		    		textQuestionText.getText().equals("") || txtQuestionNumber.getText().equals("") ||
		    		txtAnswerCorrect.getText().equals("") || txtAnswerWrong1.getText().equals("") || txtAnswerWrong2.getText().equals("") ||
		    		txtAnswerWrong3.getText().equals("")) {
		        lblMessage.setTextFill(Color.color(1, 0, 0));
		        lblMessage.setText("[Error] Missing fields");
		    } else {
		        //lblMessage.setTextFill(Color.rgb(0, 102, 0));
		    	//lblMessage.setText("Question added Successfully");
		    	lblMessage.setText("");
		        
		        ArrayList<String> getMaxQuestionIdFromCurrentSubjectArr = new ArrayList<>();
		        getMaxQuestionIdFromCurrentSubjectArr.add("GetMaxQuestionIdFromProvidedSubject");
		        getMaxQuestionIdFromCurrentSubjectArr.add(subjectSelect);
		        ClientUI.chat.accept(getMaxQuestionIdFromCurrentSubjectArr);
		        
		        //System.out.println(maxIdOfQuestionInCurrentSubject);
		        
		        ArrayList<String> answersArr = new ArrayList<>();
		        answersArr.add(txtAnswerCorrect.getText());
		        answersArr.add(txtAnswerWrong1.getText());
		        answersArr.add(txtAnswerWrong2.getText());
		        answersArr.add(txtAnswerWrong3.getText());
		        
		        newQuestion = new ArrayList<>();
		        //String id = maxIdOfQuestionInCurrentSubject;
		        String id = "03101"; //  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		        int i = 0;
		        ArrayList<Question> addQuestionToDBArr = new ArrayList<>();
		        addQuestionToDBArr.add(new Question("AddNewQuestionToDB", null, null, null, null, null, null));
		        for(String courses : coursesSelect) {
		        	id = "0" + Integer.toString(Integer.parseInt(id) + 1);
			        newQuestion.add(new Question(id, subjectSelect, courses, textQuestionText.getText(), answersArr, txtQuestionNumber.getText(), lecturer.getName()));		        
			        addQuestionToDBArr.add(newQuestion.get(i));	        
			        i++;
		        }
		        ClientUI.chat.accept(addQuestionToDBArr);
		        
		        //System.out.println(courseSelectList.getSelectionModel().getSelectedItems());
		        
		        //JOptionPane.showMessageDialog(null, "question added succesfully", "question managment", JOptionPane.INFORMATION_MESSAGE);
		        getBackBtn(event);
		        
		    }
		}catch (NullPointerException e) {
	        lblMessage.setTextFill(Color.color(1, 0, 0));
	        lblMessage.setText("[Error] Missing fields");	

		}
	}
	
}

