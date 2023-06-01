package student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClientAndServerLogin.LoginFrameController;
import ClientAndServerLogin.SceneManagment;
import Config.Student;
import client.ClientUI;
import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StudentDashboardFrameController implements Initializable{

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

    @FXML
    private Pane pnlViewExam;

    @FXML
    public void getCloseBtn(ActionEvent event) throws Exception{
        // Hide the primary window
        ((Node) event.getSource()).getScene().getWindow().hide();

        // Send a quit message to the server using the client's ID and role
        ClientUI.chat.client.quit(student.getId(), "student");
    }

    @FXML
    void getLogoutBtn(ActionEvent event) throws Exception{
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

    @FXML
    void handleClicks(ActionEvent actionEvent) {
    	if (actionEvent.getSource() == btnComputerizedExam) {
	        pnlComputerizedExam.toFront();
	    }
	    if (actionEvent.getSource() == btnManualExam) {
	        pnlManualExam.toFront();
	    }
	    if (actionEvent.getSource() == btnMyGrades) {
	        pnlMyGrades.toFront();    
	    }
	    if (actionEvent.getSource() == btnViewExam) {
	        pnlViewExam.toFront();
	    }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        lbluserNameAndID.setText((student.getName() + "\n(ID: " + student.getId() + ")")); //Initializing the label
		// TODO Auto-generated method stub
		
	}




    public static void start(ArrayList<String> studentDetails) throws IOException {

        // Initialize the student with the provided details
        student = new Student(studentDetails.get(2), studentDetails.get(3), studentDetails.get(4), studentDetails.get(5), studentDetails.get(6));
        // -- studentDetails --
        // 1 - login As
        // 2 - user ID
        // 3 - userName
        // 4 - user Password
        // 5 - user Name
        // 6 - user Email

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

    // method to transition between panes when clicking on buttons on the right side
    public void handleAnimation(Pane newPane, JFXButton newSection) {
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
