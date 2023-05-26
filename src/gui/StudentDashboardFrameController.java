package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class StudentDashboardFrameController implements Initializable{

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
    void getCloseBtn(ActionEvent event) {
    	
    }

    @FXML
    void getLogoutBtn(ActionEvent event) {

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
		// TODO Auto-generated method stub
		
	}

}
