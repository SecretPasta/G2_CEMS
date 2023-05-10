package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateExamScreenController {
	
	public static FXMLLoader loader;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField amountQuestionsTxtField;

    @FXML
    private Button btnAddToExam;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeleteQuestion;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnNext;

    @FXML
    private ComboBox<?> cmbChooseCourse;

    @FXML
    private ComboBox<?> cmbChooseSubject;

    @FXML
    private TableColumn<?, ?> courseExamColumn;

    @FXML
    private TableColumn<?, ?> courseQuestionsColumn;

    @FXML
    private Text errorTextInExam;

    @FXML
    private Text errorTextQuestions;

    @FXML
    private TableView<?> inExamTable;

    @FXML
    private TableColumn<?, ?> questionExamColumn;

    @FXML
    private TableColumn<?, ?> questionQuestionsColumn;

    @FXML
    private TableView<?> questionsTable;

    @FXML
    private TableColumn<?, ?> subjectQuestionsColumn;

    @FXML
    void addQuestionToQuestionsList(ActionEvent event) {

    }

    @FXML
    void deleteQuestionFromQuestionList(ActionEvent event) {

    }

    @FXML
    void getBackBtn(ActionEvent event) {

    }

    @FXML
    void getNextBtn(ActionEvent event) {

    }

    @FXML
    void initCreateExamScreenController() {
    	loader = new FXMLLoader();
        final Stage primaryStage = new Stage();
        Pane root = null;
        //need to continue

    }

}
