package student;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import ClientServerComm.ChatIF;
import Config.Exam;
import Config.QuestionInExam;
import client.ClientUI;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;

import ClientAndServerLogin.SceneManagment;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ComputerizedExamController implements Initializable{
	
	@FXML
    private AnchorPane root;
    @FXML
    private JFXButton back;
    @FXML
    private JFXButton next;
    @FXML
    private AnchorPane questionsPane;
    @FXML
    private JFXButton closeBtn;
    @FXML
    private HBox questionNumbers;   
    @FXML
    private JFXButton submitExamBtn;
    @FXML
    private Text timer;
    
    private JFXTabPane tabPane;

	private ObservableList<QuestionInExam> questionsInExamObservableList = FXCollections.observableArrayList();
    
    private int currentQuestion = 0;

	private static Exam currentExam;

	private static ComputerizedExamController instance;
    
 	public ComputerizedExamController(){
		 instance = this;
	}

	public static ComputerizedExamController getInstance(){
		 return instance;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Here we need to load all the questions text and answers from db to panes

		// Timer for Exam ----------------------------------------------------------------------
		Time time = new Time("0:2:0");
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> 
		{
			time.oneSecondPassed();
	        timer.setText(time.getCurrentTime());
	    }));
		timer.setText(time.getCurrentTime());
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        // End of Timer for Exam ------------------------------------------------------------------

        //tabPane that contains all tabs (one tab per one question)
		tabPane = new JFXTabPane();
		tabPane.setPrefSize(970, 448);
		tabPane.setTabMinWidth(977/20);

		ArrayList<String> getQuestionArr = new ArrayList<>();
		getQuestionArr.add("getQuestionsInExamById");
		getQuestionArr.add(currentExam.getExamID());
		ClientUI.chat.accept(getQuestionArr);

		//loop that creating all question panes -> need to provide relevant amount of questions
		for(int i = 1;i < questionsInExamObservableList.size() + 1;i++) { // Creating the exam questions pane,  the second parameter is the number of questions
			
			//each question and its answers saved in vbox which saved in Tab which saved in JFXTabPane
			VBox questionPane = new VBox();
			questionPane.setSpacing(30);
			questionPane.setPadding(new Insets(50, 50, 50, 50));
			questionPane.setPrefSize(970, 448);
			questionPane.setStyle("-fx-background-color:#FAF9F6");
			
			//the question should be here
			Label questionLabel = new Label(String.format(questionsInExamObservableList.get(i-1).getQuestionText())); // load the question itself here
			questionLabel.setWrapText(true);
			questionLabel.setStyle("-fx-font-weight: bold;");
			questionLabel.setStyle("-fx-font-size: 18;");
			
			//vbox for answers
			VBox answers = new VBox();
			answers.setSpacing(15);
			
			//togglegroup for radiobuttons
			ToggleGroup toggleGroup = new ToggleGroup(); // for each pane the toggles for the question select
			ArrayList<String> qAnswers = questionsInExamObservableList.get(i-1).getAnswers();
			Collections.shuffle(qAnswers); //Randomizing Answers Order
			//the answers should be here
			JFXRadioButton answer1 = new JFXRadioButton(qAnswers.get(0));
			answer1.setWrapText(true);
			answer1.setToggleGroup(toggleGroup);
			JFXRadioButton answer2 = new JFXRadioButton(qAnswers.get(1));
			answer2.setWrapText(true);
			answer2.setToggleGroup(toggleGroup);
			JFXRadioButton answer3 = new JFXRadioButton(qAnswers.get(2));
			answer3.setWrapText(true);
			answer3.setToggleGroup(toggleGroup);
			JFXRadioButton answer4 = new JFXRadioButton(qAnswers.get(3));
			answer4.setWrapText(true);
			answer4.setToggleGroup(toggleGroup);		
			answers.getChildren().addAll(answer1, answer2, answer3, answer4);
			questionPane.getChildren().add(questionLabel);
			questionPane.getChildren().add(answers);
			Tab tab = new Tab(Integer.toString(i), questionPane); // Creating the specific pane
			//for changing answered question tab background
			toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
		        @Override
		        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
		        	if(toggleGroup.getSelectedToggle() != null) {
		        		tab.setStyle("-fx-background-color: #5DD299");
		        	}
		        }
		    });	
			tabPane.getTabs().add(tab);
			
		}
		questionsPane.getChildren().add(tabPane);
		
	}

	public static void start(Exam exam) throws IOException {
		currentExam = exam;
		System.out.println(exam);

	    SceneManagment.createNewStage("/student/ComputerizedExam.fxml", null, "ComputerizedExam").show(); // Creates and shows the login screen stage

	}

	public void loadExamQuestions(ArrayList<QuestionInExam> questions){
		questionsInExamObservableList.addAll(questions);
	}

	@FXML
    private void back(ActionEvent event) {
		currentQuestion = tabPane.getTabs().indexOf(tabPane.getSelectionModel().getSelectedItem());
		if(currentQuestion != 0) {
			tabPane.getSelectionModel().select(--currentQuestion);
		}
		
    }

    @FXML
    private void next(ActionEvent event) {
    	currentQuestion = tabPane.getTabs().indexOf(tabPane.getSelectionModel().getSelectedItem());
    	if(currentQuestion != tabPane.getTabs().size() - 1) {
    		tabPane.getSelectionModel().select(++currentQuestion);
    	}
    		
    }

    @FXML
    public void getCloseBtn(ActionEvent event) {

    }
}
