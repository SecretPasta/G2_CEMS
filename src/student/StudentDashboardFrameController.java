package student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClientAndServerLogin.LoginFrameController;
import ClientAndServerLogin.SceneManagment;
import Config.Exam;
import Config.FinishedExam;
import Config.Student;
import client.ClientUI;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StudentDashboardFrameController implements Initializable{

    private static StudentDashboardFrameController instance;
    private static Student student;

    protected static Stage currentStage; // save current stage

    private Pane currentPane;

    private JFXButton currentSection;

    @FXML
    private JFXButton btnComputerizedExam;

    @FXML
    private JFXButton btnManualExam;

    @FXML
    private JFXButton btnMyGrades;

    @FXML
    private JFXButton btnViewExam;

    @FXML
    private Label lbluserNameAndID;

    @FXML
    private Pane pnlComputerizedExam;

    @FXML
    private Pane pnlGreeting;

    @FXML
    private Pane pnlManualExam;

    @FXML
    private Pane pnlMyGrades;


    // Manual Exam Screen #####################################################
    @FXML
    private TableView<Exam> tableView_UpcomingManualExams = new TableView<>();

    @FXML
    private TableColumn<Exam,String> courseColumn_ManualExams;

    @FXML
    private TableColumn<Exam,String> subjectColumn_ManualExams;

    @FXML
    private TableColumn<Exam,String> descriptionColumn_ManualExams; //Comment for Students goes here

    @FXML
    private TableColumn<Exam, Integer> durationColumn_ManualExams;

    @FXML
    private TableColumn<Exam,String> lecturerColumn_ManualExams; //Author goes here

    @FXML
    private JFXButton btnStartManualExam;

    private ObservableList<Exam> manualExamsObservableList = FXCollections.observableArrayList();

    // End of Manual Exam Screen ###############################################

    // Computerized  Exam Screen #####################################################
    @FXML
    private TableView<Exam> tableView_UpcomingComputerizedExams = new TableView<>();

    @FXML
    private TableColumn<Exam,String> courseColumn_ComputerizedExams;

    @FXML
    private TableColumn<Exam,String> subjectColumn_ComputerizedExams;

    @FXML
    private TableColumn<Exam,String> descriptionColumn_ComputerizedExams; //Comment for Students goes here

    @FXML
    private TableColumn<Exam, Integer> durationColumn_ComputerizedExams;

    @FXML
    private TableColumn<Exam,String> lecturerColumn_ComputerizedExams; //Author goes here

    @FXML
    private JFXButton btnStartComputerizedExam;

    private ObservableList<Exam> computerizedExamsObservableList = FXCollections.observableArrayList();

    private Exam selectedExam;

    @FXML
    private TextField txtExamCode;

    // End of Computerized Exam Screen ###############################################

    // My Grades Screen ##############################################################
    @FXML
    private TableView<Exam> tableView_MyGrades = new TableView<>();

    @FXML
    private TableColumn<Exam,String> courseExamID_MyGrades;
    @FXML
    private TableColumn<Exam,String> courseColumn_MyGrades;

    @FXML
    private TableColumn<Exam,String> subjectColumn_MyGrades;

//    @FXML
//    private TableColumn<Exam,String> descriptionColumn_MyGrades; //Comment for Students goes here

//    @FXML
//    private TableColumn<Exam, Integer> durationColumn_MyGrades;

    @FXML
    private TableColumn<Exam,String> lecturerColumn_MyGrades; //Author goes here

    @FXML
    private TableColumn<Exam,String> gradeColumn_MyGrades;

    private ObservableList<FinishedExam> myGradesObservableList = FXCollections.observableArrayList();
    

    
    @FXML
    private JFXSnackbar snackbarError;
    
    @FXML
    private StackPane stackPane;

    // End of My Grades Screen #######################################################

    public StudentDashboardFrameController(){
        instance = this;
    }

    public static StudentDashboardFrameController getInstance(){
        return instance;
    }

    public void loadComputerizedExamsIntoTable(ArrayList<Exam> examList){
        computerizedExamsObservableList.setAll(examList);
        tableView_UpcomingComputerizedExams.setItems(computerizedExamsObservableList);



        System.out.println(examList);
    }

    @FXML
    public void getCloseBtn(ActionEvent event) throws Exception{
        // Hide the primary window
        ((Node) event.getSource()).getScene().getWindow().hide();

        // Send a quit message to the server using the client's ID and role
        ClientUI.chat.client.quit(student.getId(), "student");
    }

    @FXML
    public void getLogoutBtn(ActionEvent event) throws Exception{
        // Hide the primary window
        ((Node) event.getSource()).getScene().getWindow().hide();

        // Send a logout message to the server to update the user's logged-in status
        ArrayList<String> qArr = new ArrayList<>();
        qArr.add("UserLogout");
        qArr.add("student");
        qArr.add(student.getId());
        ClientUI.chat.accept(qArr);

        // Start the login screen after logout
        LoginFrameController.start();
    }

    public void getStartComputerizedExamBtn(ActionEvent event) throws Exception{
        selectedExam = tableView_UpcomingComputerizedExams.getSelectionModel().getSelectedItem();
        if(selectedExam == null){
            System.out.println("Error, no Exam has been Selected!");
        } else if (!selectedExam.getCode().equals(txtExamCode.getText())) {
            System.out.println("Error, Incorrect Code!");
        } else{
            //Hide primary Window
            ((Node) event.getSource()).getScene().getWindow().hide();
            System.out.println("You have started the Computerized Exam!!!");
            ComputerizedExamController.start(selectedExam);
            selectedExam = null;
        }

    }

    public void getStartManualExamBtn(ActionEvent event) throws Exception{
        System.out.println("You have started the Manual Exam!!!");
        // Code to open window for the appropriate exam
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        lbluserNameAndID.setText((student.getName() + "\n(ID: " + student.getId() + ")")); //Initializing the label

        //--------------------- Computerized Exam -----------------------------------------------------------------
        // Setting up the data for table
        // PropertyValueFactory<This is the Class Name,Variable type inside the class>("variable name"))
        courseColumn_ComputerizedExams.setCellValueFactory(new PropertyValueFactory<Exam,String>("courseName"));
        subjectColumn_ComputerizedExams.setCellValueFactory(new PropertyValueFactory<Exam,String>("subjectName"));
        descriptionColumn_ComputerizedExams.setCellValueFactory(new PropertyValueFactory<Exam,String>("commentsForStudent"));
        durationColumn_ComputerizedExams.setCellValueFactory(new PropertyValueFactory<Exam,Integer>("duration"));
        lecturerColumn_ComputerizedExams.setCellValueFactory(new PropertyValueFactory<Exam,String>("author"));
        //Crate an ArrayList of all Available Exams
        ArrayList<String> getExamArray = new ArrayList<>();
        getExamArray.add("GetAllComputerizedExamsFromDB");
        getExamArray.add((student.getId()));
        ClientUI.chat.accept(getExamArray);
        tableView_UpcomingComputerizedExams.getSelectionModel().clearSelection();
        currentPane = pnlGreeting;
        pnlGreeting.toFront();

        //--------------------- End of Computerized Exam ----------------------------------------------------------

		// TODO Auto-generated method stub
		
	}




    public static void start(ArrayList<String> studentDetails) throws IOException {

        // Initialize the student with the provided details
        student = new Student(studentDetails.get(2), studentDetails.get(3), studentDetails.get(4), studentDetails.get(5), studentDetails.get(6),studentDetails.get(7));
        // -- studentDetails --
        // 1 - login As
        // 2 - user ID
        // 3 - userName
        // 4 - user Password
        // 5 - user Name
        // 6 - user Email
        // 7 - Courses

        // Run the following code on the JavaFX Application Thread using Platform.runLater()
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Save the current dashboard screen for returning back  , "/student/StudentDashboard.css", "Student Dashboard"
                    currentStage = SceneManagment.createNewStage("/student/StudentDashboard.fxml");
                    currentStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    @FXML
    void handleClicks(ActionEvent actionEvent) {
    	if (actionEvent.getSource() == btnComputerizedExam) {
    		handleAnimation(pnlComputerizedExam, btnComputerizedExam);
	        pnlComputerizedExam.toFront();
	    }
	    if (actionEvent.getSource() == btnManualExam) {
	    	handleAnimation(pnlManualExam, btnManualExam);
	        pnlManualExam.toFront();
	    }
	    if (actionEvent.getSource() == btnMyGrades) {
	    	handleAnimation(pnlMyGrades, btnMyGrades);
	        pnlMyGrades.toFront();    
	    }
    }

    // method to transition between panes when clicking on buttons on the right side
    public void handleAnimation(Pane newPane, JFXButton newSection) {
    	if(newSection != currentSection) {
    		FadeTransition outgoingPane = new FadeTransition(Duration.millis(125), currentPane);
            outgoingPane.setFromValue(1);
            outgoingPane.setToValue(0);

            FadeTransition comingPane = new FadeTransition(Duration.millis(125), newPane);
            comingPane.setFromValue(0);
            comingPane.setToValue(1);

            SequentialTransition transition = new SequentialTransition();
            transition.getChildren().addAll(outgoingPane, comingPane);
            transition.play();

            newSection.setStyle("-fx-border-color: #FAF9F6");
            if(currentSection != null) currentSection.setStyle("-fx-border-color: #242633");

            currentPane = newPane;
            currentSection = newSection;
    	}
        
    }
    
    //method to dispaly errors
    private void displayError(String message) {
    	snackbarError = new JFXSnackbar(stackPane);
        snackbarError.setPrefWidth(stackPane.getPrefWidth() - 40);
        snackbarError.fireEvent(new SnackbarEvent(new JFXSnackbarLayout(message), Duration.millis(3000), null));
    }



}
