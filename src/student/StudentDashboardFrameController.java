package student;

import java.io.IOException;
import java.lang.ref.Cleaner;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;

import ClientAndServerLogin.LoginFrameController;
import ClientAndServerLogin.SceneManagment;
import Config.Exam;
import Config.FinishedExam;
import Config.Student;
import client.ClientUI;
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
    private TableColumn<Exam,String> examIdColumn_ManualExams;

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

    @FXML
    private JFXButton btnRefreshManualExams;

    private ObservableList<Exam> manualExamsObservableList = FXCollections.observableArrayList();

    // End of Manual Exam Screen ###############################################

    // Computerized  Exam Screen #####################################################
    @FXML
    private TableView<Exam> tableView_UpcomingComputerizedExams = new TableView<>();

    @FXML
    private TableColumn<Exam,String> examIdColumn_ComputerizedExams;

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
    private TextField txtComputerizedExamCode;

    @FXML
    private TextField txtComputerizedExamStudentId;

    @FXML
    private JFXButton refreshComputerizedExams;

    // End of Computerized Exam Screen ###############################################

    // My Grades Screen ##############################################################
    @FXML
    private TableView<FinishedExam> tableView_MyGrades = new TableView<>();

    @FXML
    private TableColumn<FinishedExam, String> courseExamID_MyGrades;
    @FXML
    private TableColumn<FinishedExam,String> courseColumn_MyGrades;

    @FXML
    private TableColumn<FinishedExam,String> subjectColumn_MyGrades;

    @FXML
    private TableColumn<FinishedExam,String> lecturerColumn_MyGrades;

    @FXML
    private TableColumn<FinishedExam, Double> gradeColumn_MyGrades;

    private ObservableList<FinishedExam> myGradesObservableList = FXCollections.observableArrayList();

    @FXML
    private JFXSnackbar snackbar;
    private JFXSnackbarLayout snackbarLayout;
    
    @FXML
    private StackPane stackPane;

    @FXML
    private JFXButton refreshGradesBtn;

    // End of My Grades Screen #######################################################

    /**

     Constructs a new instance of the StudentDashboardFrameController.
     Sets the instance variable to reference this instance.
     */
    public StudentDashboardFrameController() {
        instance = this;
    }


    /**

     Returns the instance of the StudentDashboardFrameController.
     @return The instance of the StudentDashboardFrameController.
     */
    public static StudentDashboardFrameController getInstance() {
        return instance;
    }

    /**

     Loads the computerized exams into the table view.
     @param examList The list of Exam objects representing the computerized exams.
     */
    public void loadComputerizedExamsIntoTable(ArrayList<Exam> examList) {
        computerizedExamsObservableList.setAll(examList);
        tableView_UpcomingComputerizedExams.setItems(computerizedExamsObservableList);
        System.out.println(examList);
    }

    /**

     Loads the student grades into the table view.
     @param gradesList The list of FinishedExam objects representing the student's grades.
     */
    public void loadStudentGradesIntoTable(ArrayList<FinishedExam> gradesList) {
        myGradesObservableList.setAll(gradesList);
        tableView_MyGrades.setItems(myGradesObservableList);
    }
    /**

     Handles the event when the "Close" button is clicked.

     @param event The ActionEvent triggered by the button click.

     @throws Exception If an error occurs while closing the window.
     */
    @FXML
    public void getCloseBtn(ActionEvent event) throws Exception {
        // Hide the primary window
        ((Node) event.getSource()).getScene().getWindow().hide();
        // Send a quit message to the server using the client's ID and role
        ClientUI.chat.client.quit(student.getId(), "student");
    }


    /**

     Handles the event when the "Logout" button is clicked.

     @param event The ActionEvent triggered by the button click.

     @throws Exception If an error occurs while logging out.
     */
    @FXML
    public void getLogoutBtn(ActionEvent event) throws Exception {
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


    /**

     Handles the event when the "Start Computerized Exam" button is clicked.
     @param event The ActionEvent triggered by the button click.
     @throws Exception If an error occurs while starting the computerized exam.
     */
    public void getStartComputerizedExamBtn(ActionEvent event) throws Exception {
        selectedExam = tableView_UpcomingComputerizedExams.getSelectionModel().getSelectedItem();
        if (selectedExam == null) {
            displayErrorMessage("Error: No Exam has been Selected!");
        } else if (!selectedExam.getCode().equals(txtComputerizedExamCode.getText())) {
            displayErrorMessage("Error: Incorrect Code!");
        } else if (!student.getId().equals(txtComputerizedExamStudentId.getText())) {
            displayErrorMessage("Error: Incorrect ID!");
        } else {
// Hide primary Window
            ((Node) event.getSource()).getScene().getWindow().hide();
// Start the computerized exam
            ComputerizedExamController.start(selectedExam, student);
            selectedExam = null;
        }
    }

    /**
     * Initializes the student dashboard screen.
     *
     * @param url      The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resource The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resource) {
        lbluserNameAndID.setText(student.getName() + "\n(ID: " + student.getId() + ")"); // Initializing the label

        //--------------------- Computerized Exam -----------------------------------------------------------------
        // Setting up the data for table
        examIdColumn_ComputerizedExams.setCellValueFactory(new PropertyValueFactory<>("examID"));
        courseColumn_ComputerizedExams.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        subjectColumn_ComputerizedExams.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        descriptionColumn_ComputerizedExams.setCellValueFactory(new PropertyValueFactory<>("commentsForStudent"));
        durationColumn_ComputerizedExams.setCellValueFactory(new PropertyValueFactory<>("duration"));
        lecturerColumn_ComputerizedExams.setCellValueFactory(new PropertyValueFactory<>("author"));

        // Create an ArrayList of all Available Exams
        ArrayList<String> getExamArray = new ArrayList<>();
        getExamArray.add("GetAllComputerizedExamsFromDB");
        getExamArray.add(student.getId());
        ClientUI.chat.accept(getExamArray);
        tableView_UpcomingComputerizedExams.getSelectionModel().clearSelection();
        //--------------------- End of Computerized Exam ----------------------------------------------------------

        //--------------------- Manual Exam -----------------------------------------------------------------------
        examIdColumn_ManualExams.setCellValueFactory(new PropertyValueFactory<>("examID"));
        courseColumn_ManualExams.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        subjectColumn_ManualExams.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        descriptionColumn_ManualExams.setCellValueFactory(new PropertyValueFactory<>("commentsForStudent"));
        durationColumn_ManualExams.setCellValueFactory(new PropertyValueFactory<>("duration"));
        lecturerColumn_ManualExams.setCellValueFactory(new PropertyValueFactory<>("author"));

        ArrayList<String> getManualExamArray = new ArrayList<>();
        getManualExamArray.add("GetAllManualExamsFromDB");
        ClientUI.chat.accept(getManualExamArray);
        tableView_UpcomingManualExams.getSelectionModel().clearSelection();

        //--------------------- End of Manual Exam ----------------------------------------------------------------

        //--------------------- Grades Screen ---------------------------------------------------------------------
        // Setting up the data for table
        courseExamID_MyGrades.setCellValueFactory(new PropertyValueFactory<>("examID"));
        courseColumn_MyGrades.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        subjectColumn_MyGrades.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        lecturerColumn_MyGrades.setCellValueFactory(new PropertyValueFactory<>("author"));
        gradeColumn_MyGrades.setCellValueFactory(new PropertyValueFactory<>("grade"));

        ArrayList<String> getGradesArr = new ArrayList<>();
        getGradesArr.add("getStudentGradesById");
        getGradesArr.add(student.getId());
        ClientUI.chat.accept(getGradesArr);

        //--------------------- End of Grades Screen --------------------------------------------------------------

        currentPane = pnlGreeting;
        pnlGreeting.toFront();
    }



    /**

     Handles the event when the "Refresh Computerized Exams" button is clicked.
     @param action The ActionEvent triggered by the button click.
     */
    public void getBtnRefreshComputerizedExams(ActionEvent action) {
        ArrayList<String> getExamArray = new ArrayList<>();
        getExamArray.add("GetAllComputerizedExamsFromDB");
        getExamArray.add(student.getId());
        ClientUI.chat.accept(getExamArray);
        tableView_UpcomingComputerizedExams.getSelectionModel().clearSelection();
    }

    /**

     Handles the event when the "Refresh Grades" button is clicked.
     @param action The ActionEvent triggered by the button click.
     */
    public void getBtnRefreshGrades(ActionEvent action) {
        ArrayList<String> getGradesArr = new ArrayList<>();
        getGradesArr.add("getStudentGradesById");
        getGradesArr.add(student.getId());
        ClientUI.chat.accept(getGradesArr);
        tableView_UpcomingManualExams.getSelectionModel().clearSelection();
    }

//--------------------- Manual Exam -----------------------------------------------------------------------
    /**

     Shows the dashboard from the Manual Exam screen.
     This method shows the current stage, which brings the dashboard to the front.
     */
    public void showDashboardFrom_ManualExam() {
        currentStage.show();
    }

    /**

     Handles the event when the "Start Manual Exam" button is clicked.
     @param event The ActionEvent triggered by the button click.
     @throws IOException If an error occurs while starting the manual exam.
     */
    public void getStartManualExamBtn(ActionEvent event) throws IOException {
        selectedExam = tableView_UpcomingManualExams.getSelectionModel().getSelectedItem();
        if (selectedExam == null) {
            displayErrorMessage("Error: No Exam has been Selected!");
        } else {
            // Hide primary Window
            ((Node) event.getSource()).getScene().getWindow().hide();
            // Start the manual exam
            ManualExamController.start(selectedExam, student);
            selectedExam = null;
        }
    }


    /**

     Loads the manual exams into the table view.
     @param exams The list of manual exams to be loaded.
     */
    public void loadManualExamsIntoTable(ArrayList<Exam> exams) {
        manualExamsObservableList.setAll(exams);
        tableView_UpcomingManualExams.setItems(manualExamsObservableList);
    }

    /**

     Handles the event when the "Refresh Manual Exams" button is clicked.
     @param event The ActionEvent triggered by the button click.
     */
    public void getBtnRefreshManualExams(ActionEvent event) {
        ArrayList<String> getManualExamArray = new ArrayList<>();
        getManualExamArray.add("GetAllManualExamsFromDB");
        ClientUI.chat.accept(getManualExamArray);
    }

//--------------------- END Manual Exam -----------------------------------------------------------------------


    /**
     * Starts the student application with the provided student details.
     *
     * @param studentDetails The details of the student.
     *                       Index 0: login As
     *                       Index 1: user ID
     *                       Index 2: userName
     *                       Index 3: user Password
     *                       Index 4: user Name
     *                       Index 5: user Email
     *                       Index 6: Courses
     * @throws IOException If an error occurs while creating the stage.
     */
    public static void start(ArrayList<String> studentDetails) throws IOException {
        // Initialize the student with the provided details
        student = new Student(studentDetails.get(2), studentDetails.get(3), studentDetails.get(4), studentDetails.get(5), studentDetails.get(6), studentDetails.get(7));

        // Run the following code on the JavaFX Application Thread using Platform.runLater()
        Platform.runLater(() -> {
            try {
                // Create a new stage for the student dashboard
                currentStage = SceneManagment.createNewStage("/student/StudentDashboard.fxml");
                currentStage.setTitle("Student Dashboard");
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }




    /**

     Shows the main dashboard window.
     This method shows the current stage and triggers the refresh of computerized exams.
     */
    public void showDashboardWindow() {
        currentStage.show();
        getBtnRefreshComputerizedExams(null);
    }


    /**

     Handles the click events from different buttons.
     @param actionEvent The ActionEvent triggered by the button click.
     */
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

    /**
     * Handles the transition between panes when clicking on buttons on the right side.
     *
     * @param newPane     The new pane to transition to.
     * @param newSection  The new section button that triggers the transition.
     */
    public void handleAnimation(Pane newPane, JFXButton newSection) {
        if (newSection != currentSection) {
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
            if (currentSection != null && currentSection != newSection) {
                currentSection.setStyle("-fx-border-color: #242633");
            }

            currentPane = newPane;
            currentSection = newSection;
        }
    }



    /**

     Displays an error message using a snackbar.
     @param message The error message to be displayed.
     */
    public void displayErrorMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                snackbar = new JFXSnackbar(stackPane);
                String css = this.getClass().getClassLoader().getResource("lecturer/SnackbarError.css").toExternalForm();
                snackbar.setPrefWidth(754);
                snackbarLayout = new JFXSnackbarLayout(message);
                snackbarLayout.getStylesheets().add(css);
                snackbar.getStylesheets().add(css);
                snackbar.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
            }
        });
    }

    /**

     Displays a success message using a snackbar.
     @param message The message to be displayed.
     */
    public void displaySuccessMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                snackbar = new JFXSnackbar(stackPane);
                String css = this.getClass().getClassLoader().getResource("lecturer/SnackbarSuccess.css").toExternalForm();
                snackbar.setPrefWidth(754);
                snackbarLayout = new JFXSnackbarLayout(message);
                snackbarLayout.getStylesheets().add(css);
                snackbar.getStylesheets().add(css);
                snackbar.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
            }
        });
    }



}
