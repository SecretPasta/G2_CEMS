package student;

import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import ClientAndServerLogin.SceneManagment;
import Config.Student;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ManualExamController {

	@FXML
	private JFXButton btnDownloadManualExam;

	@FXML
	private JFXButton btnSubmitManualExam;

	@FXML
	private JFXButton btnUploadManualExam;

	@FXML
	private Label lblCourseName;

	@FXML
	private Label lblStudent;

	@FXML
	private Label lblSubjectName;

	@FXML
	private AnchorPane questionsPane;

	@FXML
	private AnchorPane root;

	@FXML
	private Text timer;

	protected static Stage currentStage; // save current stage
	private static Student student;

	@FXML
	void getDownloadManualExamBtn(ActionEvent event) {
		System.out.println("You have started the Manual Exam!!!");
		// Code to open window for the appropriate exam
		DirectoryChooser directoryChooser = new DirectoryChooser();

		directoryChooser.setTitle("Select a folder");

		File selectedDir = directoryChooser.showDialog(currentStage);

		String selectedDirPath = selectedDir.getAbsolutePath();

		System.out.println(selectedDirPath);

		// FILE TRANSFER FROM DB TO COMPUTER
//		try {
//			File downloadedFile = new File(selectedDirPath + "\\" + "Manual_Exam.txt");
//			
//			
//			FileOutputStream os = new FileOutputStream(downloadedFile);
//			BufferedOutputStream bis = new BufferedOutputStream(os);
//
//			bis.write(((MyFile) msg).getMybytearray(), 0, ((MyFile) msg).getSize());
//			bis.close();
//			
//		} catch (Exception e) {
//		}

	}

	@FXML
	void getSubmitManualExamBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		StudentDashboardFrameController.getInstance().showDashboardFrom_ManualExam();

	}

	@FXML
	void getUploadManualExamBtn(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open File");
		chooser.showOpenDialog(root.getScene().getWindow());

	}

	public static void start(Student student) throws IOException {

		// Initialize the student with the provided details

		// student = new Student(studentDetails.get(2), studentDetails.get(3),
		// studentDetails.get(4),
		// studentDetails.get(5), studentDetails.get(6), studentDetails.get(7));

		// -- studentDetails --
		// 1 - login As
		// 2 - user ID
		// 3 - userName
		// 4 - user Password
		// 5 - user Name
		// 6 - user Email
		// 7 - Courses

		// Run the following code on the JavaFX Application Thread using
		// Platform.runLater()
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					// Save the current dashboard screen for returning back ,
					// "/student/StudentDashboard.css", "Student Dashboard"
					currentStage = SceneManagment.createNewStage("/student/ManualExam.fxml");
					currentStage.setTitle("Student->Manual Exam");
					currentStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
