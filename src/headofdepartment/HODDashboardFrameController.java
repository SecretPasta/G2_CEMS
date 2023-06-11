package headofdepartment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import ClientAndServerLogin.LoginFrameController;
import ClientAndServerLogin.SceneManagment;
import Config.HeadOfDepartment;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class HODDashboardFrameController implements Initializable{

    @FXML
    private JFXButton btnApproveExtraTime;
    @FXML
    private JFXButton btnShowReport;
    @FXML
    private JFXButton currentSection;
    @FXML
    private JFXButton btnAcceptRequest;

    @FXML
    private Label lbluserNameAndID;

    @FXML
    private Pane pnlApproveExtraTime;
    @FXML
    private Pane pnlShowReport;
    @FXML
    private Pane pnlGreeting;
    @FXML
    private Pane currentPane;

    @FXML
    private JFXSnackbar snackbarError;

    @FXML
    private StackPane stackPane;
    
    @FXML
    private JFXListView<String> listRequests;
    
    private ObservableList<String> requests_observablelist = FXCollections.observableArrayList();


    
    private static HODDashboardFrameController instance;
    private static HeadOfDepartment headofdepartment;
    protected static Stage currentStage; // save current stage
    
    public HODDashboardFrameController(){
        instance = this;
    }

    public static HODDashboardFrameController getInstance(){
        return instance;
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	lbluserNameAndID.setText((headofdepartment.getName() + "\n(ID: " + headofdepartment.getId() + ")")); //Initializing the label
    	
    	getAllrequests();
    	
    	currentPane = pnlGreeting;
        pnlGreeting.toFront();
		
	}
    
    public void getAllrequests() {
    	System.out.println(headofdepartment.getId());
		ArrayList<String> getallrequest_arr = new ArrayList<>();
		getallrequest_arr.add("GetAllRequestsOfHodFromDB");
		getallrequest_arr.add(headofdepartment.getId());
		ClientUI.chat.accept(getallrequest_arr);
	}

	public void loadRequestsFromDB(ArrayList<String> requests_arr) {
    	requests_observablelist.setAll(requests_arr);	
    	listRequests.setItems(requests_observablelist);
    	listRequests.refresh();
    	if(!listRequests.getItems().isEmpty()) { // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    		// send pop up message that extra time request recieved!!!!!
    		System.out.println("new new new");
    	}
    }
    
    public static void start(ArrayList<String> hodDetails) throws IOException {

        // Initialize the student with the provided details
        headofdepartment = new HeadOfDepartment(hodDetails.get(2), hodDetails.get(3), hodDetails.get(4), hodDetails.get(5), hodDetails.get(6));
        // -- hodDetails --
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
                    // Save the current dashboard screen for returning back  , "/headofdepartment/HODDashboardGUI.fxml", "HOD Dashboard"
                    currentStage = SceneManagment.createNewStage("/headofdepartment/HODDashboardGUI.fxml");
                    currentStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    @FXML
    public void getCloseBtn(ActionEvent event) throws Exception{
        // Hide the primary window
        ((Node) event.getSource()).getScene().getWindow().hide();

        // Send a quit message to the server using the client's ID and role
        ClientUI.chat.client.quit(headofdepartment.getId(), "headofdepartment");
    }

    @FXML
    public void getLogoutBtn(ActionEvent event) throws Exception{
        // Hide the primary window
        ((Node) event.getSource()).getScene().getWindow().hide();

        // Send a logout message to the server to update the user's logged-in status
        ArrayList<String> qArr = new ArrayList<>();
        qArr.add("UserLogout");
        qArr.add("headofdepartment");
        qArr.add(headofdepartment.getId());
        ClientUI.chat.accept(qArr);

        // Start the login screen after logout
        LoginFrameController.start();
    }
    
    @FXML
    void handleClicks(ActionEvent actionEvent) {
    	if (actionEvent.getSource() == btnShowReport) {
    		handleAnimation(pnlShowReport, btnShowReport);
	        pnlShowReport.toFront();
	    }
	    if (actionEvent.getSource() == btnApproveExtraTime) {
	    	handleAnimation(pnlApproveExtraTime, btnApproveExtraTime);
	    	getAllrequests();
	        pnlApproveExtraTime.toFront();
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
