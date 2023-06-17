package student;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.ResourceBundle;

import Config.Exam;
import Config.MyFile;
import client.ClientUI;
import com.jfoenix.controls.JFXButton;

import ClientAndServerLogin.SceneManagment;
import Config.Student;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ManualExamController implements Initializable {

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
	private static Student currentStudent;
	private static Exam currentExam;

	private ManualExamTimer manualExamTimer;

	private static ManualExamController instance;

	private String selectedDirPath;

	private MyFile myFile;

	public ManualExamController(){
		instance = this;
	}

	public static ManualExamController getInstance(){
		return instance;
	}

	@FXML
	void getDownloadManualExamBtn(ActionEvent event) {
		// Code to open window for the appropriate exam
		DirectoryChooser directoryChooser = new DirectoryChooser();

		directoryChooser.setTitle("Select a folder");

		File selectedDir = directoryChooser.showDialog(currentStage);

		selectedDirPath = selectedDir.getAbsolutePath();

		System.out.println(selectedDirPath);
		ArrayList<String> exam = new ArrayList<>();
		exam.add("downloadManualExamFromServer");
		exam.add(currentExam.getExamID());
		ClientUI.chat.accept(exam);

		manualExamTimer =  new ManualExamTimer(currentExam.getDuration(),instance);
		manualExamTimer.start();
	}


	public void saveExamToComputer(MyFile myFile){
		System.out.println("Entered Save to Computer");
		try{
			File file = new File(selectedDirPath + "/" + myFile.getFileName());

			FileOutputStream os = new FileOutputStream(file);
			BufferedOutputStream bis = new BufferedOutputStream(os);

			bis.write(myFile.getMybytearray(),0,myFile.getSize());
			bis.close();
		}catch (Exception e){}


	}

	@FXML
	void getSubmitManualExamBtn(ActionEvent event) {

		if(myFile == null){
			System.out.println("Error, no file has been chosen! Please choose a file");
			//Add snackbar error for this
		}else{
			System.out.println("You have Submitted the Manual Exam!!!");

			if(manualExamTimer != null){
				manualExamTimer.stopTimer();
			}
			// Send the MyFile object to the server
			ClientUI.chat.accept(myFile);
			((Node) event.getSource()).getScene().getWindow().hide();
			StudentDashboardFrameController.getInstance().showDashboardFrom_ManualExam();
		}
	}


	public void setUpdateExamTimer(String time){
		timer.setText(time);
	}

	public void endOfTimerSubmit(){
		currentStage.getScene().getWindow().hide();
		StudentDashboardFrameController.getInstance().showDashboardFrom_ManualExam();
	}

	@FXML
	void getUploadManualExamBtn(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open File");
		File selectedFile = chooser.showOpenDialog(root.getScene().getWindow());

		if (selectedFile != null) {
			System.out.println("File Name: " + selectedFile.getName() + " File Path: " + selectedFile.getAbsolutePath());

			// Read the selected file and convert it to a byte array
			byte[] fileData;
			try {
				FileInputStream fileInputStream = new FileInputStream(selectedFile);
				fileData = new byte[(int) selectedFile.length()];
				fileInputStream.read(fileData);
				fileInputStream.close();
			} catch (IOException e) {
				// Handle any errors that occur during file reading
				e.printStackTrace();
				return;
			}

			// Create a new MyFile object
			myFile = new MyFile(selectedFile.getName());
			myFile.setSize(fileData.length);
			myFile.initArray(fileData.length);
			myFile.setMybytearray(fileData);


		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblSubjectName.setText(currentExam.getSubjectName());
		lblCourseName.setText(currentExam.getCourseName());
		lblStudent.setText(currentStudent.getName() + "\n" + currentStudent.getId());
	}

	public static void start(Exam exam, Student student) throws IOException {
		currentExam = exam;
		currentStudent = student;
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
