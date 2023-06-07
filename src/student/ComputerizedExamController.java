package student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import ClientAndServerLogin.SceneManagment;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private HBox questionNumbers;
    
    private int currentQuestion = 0;
    
    List<Pane> listOfQuestions = new ArrayList<>();
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Here we need to load all the questions text and answers from db to panes
		
		questionNumbers.setAlignment(Pos.CENTER);
		for(int i = 1;i < 21;i++) {
			Pane questionPane = new Pane();
			questionPane.setPrefSize(970, 448);
			questionPane.setStyle("-fx-background-color:#FAF9F6");
			Label lbl = new Label(String.format("Question number %d", i));
			questionPane.getChildren().add(lbl);		
			listOfQuestions.add(questionPane);
			JFXButton btnQuestion = new JFXButton(Integer.toString(i));
			btnQuestion.setOnAction(new EventHandler<ActionEvent>() {		
				@Override
				public void handle(ActionEvent arg0) {
					handleQuestionClicks(btnQuestion);		
				}
			});
			questionNumbers.getChildren().add(btnQuestion);
			if(i != 1)translateAnimation(questionPane, root.getPrefWidth());
			

		}
		questionsPane.getChildren().addAll(listOfQuestions);
		
	}

	public static void start() throws IOException {
	    SceneManagment.createNewStage("/student/ComputerizedExam.fxml", null, "ComputerizedExam").show(); // Creates and shows the login screen stage
	}
	
	private void translateAnimation(Node question, Double width) {
		TranslateTransition translate = new TranslateTransition(Duration.millis(500), question);
		translate.setByX(width);
		translate.play();
	}
	
	@FXML
    private void back(ActionEvent event) {
		if(currentQuestion == 0) return;
		translateAnimation(listOfQuestions.get(currentQuestion), root.getPrefWidth());
		currentQuestion--;	
    }

    @FXML
    private void next(ActionEvent event) {
    	if(currentQuestion == listOfQuestions.size() - 1) return;
		translateAnimation(listOfQuestions.get(currentQuestion + 1), -root.getPrefWidth());
		currentQuestion++;
		
    }
    
    private void handleQuestionClicks(JFXButton btn) {
    	
    	int counter = currentQuestion;
    	int btnText = Integer.parseInt(btn.getText()) - 1;
    	if(currentQuestion < btnText) {
    		for(int i = 1;i <= btnText - counter;i++) {
    			next(null);
    		}
    	}
    	else if (currentQuestion > btnText) {
    		for(int i = 1;i <= counter - btnText;i++) {
    			back(null);
    		}
		}
		
    }
}
