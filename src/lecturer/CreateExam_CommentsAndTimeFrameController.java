package lecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ClientAndServerLogin.SceneManagment;
import Config.Lecturer;
import Config.QuestionInExam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

public class CreateExam_CommentsAndTimeFrameController implements Initializable {
	
	private static Lecturer lecturer;
	private static ObservableList<QuestionInExam> questionsToCreateExamObservableList = FXCollections.observableArrayList();
	
	
    public static void start(Lecturer temp_lecturer, ObservableList<QuestionInExam> temp_questionsToCreateExamObservableList) throws IOException {
    	lecturer = temp_lecturer;
    	questionsToCreateExamObservableList = temp_questionsToCreateExamObservableList;
        SceneManagment.createNewStage("/lecturer/CreateExam_CommentsAndTimeGUI.fxml", null, "Create Exam").show();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
