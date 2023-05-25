package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

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
    private JFXComboBox<String> departmentSelectBox;
    @FXML
    private ListView<String> courseSelectList;
    
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
    private TextField txtQuestionSubject;
    @FXML
    private TextField txtQuestionNumber;
    
    @FXML
    private JFXButton backBtn;
    
    @FXML
    private JFXButton addQuestionBtn;
    
    private Lecturer lecturer;
    
    private ArrayList<Question> newQuestion = new ArrayList<>();
    
    private static String maxIdOfQuestionInCurrentDepartment;
    
    private static Map<String, ArrayList<String>> departmentCoursesMap = new HashMap<>(); // map for departments and courses for the lecturer
    
	public static void start(Lecturer lecturer) throws IOException {

		getLecturerDepartmentsAndCoursesFromDB(lecturer); // get the departments and courses of the lecturer from the DB
		
		SceneManagment.createNewStage("/gui/AddQuestionGUI.fxml", null, "Question Add Managment Tool").show();

		
		
	}
	public static void getLecturerDepartmentsAndCoursesFromDB(Lecturer lecturer) {

		// send the server an ArrayList with the lecturer id to get his departments and courses that belong to each department
		// will return from the server and the DB an hashmap with department and its courses that belong to the lecturer
		ArrayList<String> getLecturerDepartmentsCoursesArr = new ArrayList<>();
		getLecturerDepartmentsCoursesArr.add("GetLecturerDepartmentsAndCourses");
		getLecturerDepartmentsCoursesArr.add(lecturer.getId());
		ClientUI.chat.accept(getLecturerDepartmentsCoursesArr);
	}

	// loading the hashmap for the departments and courses of the lecturer
	public static void loadLecturerDepartmentsAndCourses(Map<String, ArrayList<String>> map) {
		departmentCoursesMap = map;
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturer = LecturerDashboardFrameController.getLecturer(); // save the current lecturer from the dashboard
		
		courseSelectList.getItems().add("Please select a department first");
		courseSelectList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		// add departments to choose from the departmentSelectBox
		for(Map.Entry<String, ArrayList<String>> entry : departmentCoursesMap.entrySet()) {
			departmentSelectBox.getItems().add(entry.getKey());
		}
	}
	
	public void getBackBtn(ActionEvent event) throws Exception {
	    ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	    
	    // When getting back, update the edited question in the question's lecturer table in the dashboard screen
	    // Pass the updated questions details to the LecturerDashboardFrameController's showDashboardFrom_EditQuestions() method
	    LecturerDashboardFrameController.getInstance().showDashboardFrom_AddQuestion(newQuestion);
	}
	
	public void getDepartmentSelectBox(ActionEvent event) throws Exception {
		courseSelectList.getItems().clear();
		// add to the courseSelectBox all the courses in the map (the courses in the department that the lecture selected)
		for(Map.Entry<String, ArrayList<String>> entry : departmentCoursesMap.entrySet()) {
			if(departmentSelectBox.getSelectionModel().getSelectedItem() == entry.getKey()) {
				courseSelectList.getItems().addAll(entry.getValue());
				break;
			}
		}
	}
	
	public void getCourseSelectBox(ActionEvent event) throws Exception {
	}
	
	public static void saveMaxIdOfQuestionInSelectedDepartment(String maxId) {
		maxIdOfQuestionInCurrentDepartment = maxId;
	}

	public void getAddQuestionBtn(ActionEvent event) throws Exception {
		
		ObservableList<String> coursesSelect = courseSelectList.getSelectionModel().getSelectedItems();
		String departmentSelect = departmentSelectBox.getSelectionModel().getSelectedItem();
		try {
		    if (departmentSelect == null || coursesSelect.get(0).equals("Please select a department first") || coursesSelect == null ||
		    		txtQuestionSubject.getText().equals("") || textQuestionText.getText().equals("") || txtQuestionNumber.getText().equals("") ||
		    		txtAnswerCorrect.getText().equals("") || txtAnswerWrong1.getText().equals("") || txtAnswerWrong2.getText().equals("") ||
		    		txtAnswerWrong3.getText().equals("")) {
		        lblMessage.setTextFill(Color.color(1, 0, 0));
		        lblMessage.setText("[Error] Missing fields");
		    } else {
		        //lblMessage.setTextFill(Color.rgb(0, 102, 0));
		    	//lblMessage.setText("Question added Successfully");
		    	lblMessage.setText("");
		        
		        ArrayList<String> getMaxQuestionIdFromCurrentDepartmentArr = new ArrayList<>();
		        getMaxQuestionIdFromCurrentDepartmentArr.add("GetMaxQuestionIdFromProvidedDepartment");
		        getMaxQuestionIdFromCurrentDepartmentArr.add(departmentSelect);
		        ClientUI.chat.accept(getMaxQuestionIdFromCurrentDepartmentArr);
		        
		        System.out.println(maxIdOfQuestionInCurrentDepartment);
		        
		        ArrayList<String> answersArr = new ArrayList<>();
		        answersArr.add(txtAnswerCorrect.getText());
		        answersArr.add(txtAnswerWrong1.getText());
		        answersArr.add(txtAnswerWrong2.getText());
		        answersArr.add(txtAnswerWrong3.getText());
		        
		        
		        //String id = maxIdOfQuestionInCurrentDepartment;
		        String id = "03101"; //  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		        int i = 0;
		        ArrayList<Question> addQuestionToDBArr = new ArrayList<>();
		        addQuestionToDBArr.add(new Question("AddNewQuestionToDB", null, null, null, null, null, null));
		        for(String courses : coursesSelect) {
		        	id = Integer.toString(Integer.parseInt(id) + 1);
			        newQuestion.add(new Question(id, txtQuestionSubject.getText(), courses, textQuestionText.getText(), answersArr, txtQuestionNumber.getText(), lecturer.getName()));		        
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

