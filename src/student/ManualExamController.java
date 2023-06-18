package student;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;

import ClientAndServerLogin.SceneManagment;
import Config.Exam;
import Config.MyFile;
import Config.Student;
import client.ClientUI;
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
import javafx.util.Duration;

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

	@FXML
	private JFXSnackbar snackbar;
	private JFXSnackbarLayout snackbarLayout;

	protected static Stage currentStage; // save current stage
	private static Student currentStudent;
	private static Exam currentExam;

	private ManualExamTimer manualExamTimer;

	private static ManualExamController instance;

	private String selectedDirPath;

	private MyFile myFile;

	/**
	 * Constructs a new instance of the ManualExamController.
	 * Sets the instance variable to reference this instance.
	 */
	public ManualExamController() {
		instance = this;
	}


	/**
	 * Returns the instance of the ManualExamController.
	 *
	 * @return The instance of the ManualExamController.
	 */
	public static ManualExamController getInstance() {
		return instance;
	}


	/**
	 * Handles the event when the "Download Manual Exam" button is clicked.
	 *
	 * @param event The action event triggered by clicking the button.
	 */
	@FXML
	void getDownloadManualExamBtn(ActionEvent event) {
		// Code to open window for the appropriate exam
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select a folder");
		File selectedDir = directoryChooser.showDialog(currentStage);

		selectedDirPath = selectedDir.getAbsolutePath();

		//System.out.println(selectedDirPath);

		ArrayList<String> exam = new ArrayList<>();
		exam.add("downloadManualExamFromServer");
		exam.add(currentExam.getExamID());
		ClientUI.chat.accept(exam);

		manualExamTimer = new ManualExamTimer(currentExam.getDuration(), instance);
		manualExamTimer.start();
	}



	/**
	 * Saves the exam file to the computer.
	 *
	 * @param myFile The exam file to be saved.
	 */
	public void saveExamToComputer(MyFile myFile) {
		//System.out.println("Entered Save to Computer");
		try {
			File file = new File(selectedDirPath + "/" + myFile.getFileName());

			FileOutputStream os = new FileOutputStream(file);
			BufferedOutputStream bis = new BufferedOutputStream(os);

			bis.write(myFile.getMybytearray(), 0, myFile.getSize());
			bis.close();
		} catch (Exception e) {
			displayErrorMessage("Error: Core Dumped :(");
			// Handle any exceptions that occur during file saving
			e.printStackTrace();
		}
		displaySuccessMessage("File has been downloaded successfully!");
	}


	/**
	 * Handles the event when the "Submit Manual Exam" button is clicked.
	 *
	 * @param event The action event triggered by clicking the button.
	 */
	@FXML
	void getSubmitManualExamBtn(ActionEvent event) {
		if (myFile == null) {
			displayErrorMessage("Error: No file has been chosen!");
		} else {
			//System.out.println("You have Submitted the Manual Exam!!!");

			if (manualExamTimer != null) {
				manualExamTimer.stopTimer();
			}

			// Send the MyFile object to the server
			ClientUI.chat.accept(myFile);

			((Node) event.getSource()).getScene().getWindow().hide();
			StudentDashboardFrameController.getInstance().showDashboardFrom_ManualExam();
		}
	}



	/**

	 Sets the updated exam timer value.
	 @param time The new time value to be displayed.
	 */
	public void setUpdateExamTimer(String time) {
		timer.setText(time);
	}

	/**
	 * Handles the end of the timer and submits the exam.
	 * This method is called when the timer runs out.
	 */
	public void endOfTimerSubmit() {
		currentStage.getScene().getWindow().hide();
		StudentDashboardFrameController.getInstance().showDashboardFrom_ManualExam();
	}


	/**
	 * Handles the event when the "Upload Manual Exam" button is clicked.
	 *
	 * @param event The action event triggered by clicking the button.
	 */
	@FXML
	void getUploadManualExamBtn(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open File");
		File selectedFile = chooser.showOpenDialog(root.getScene().getWindow());

		if (selectedFile != null) {
			//System.out.println("File Name: " + selectedFile.getName() + " File Path: " + selectedFile.getAbsolutePath());
			displaySuccessMessage("Your file has been uploaded successfully!");

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




	/**

	 Initializes the ManualExamController.
	 @param url The location used to resolve relative paths for the root object, or null if the location is not known.
	 @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		lblSubjectName.setText(currentExam.getSubjectName());
		lblCourseName.setText(currentExam.getCourseName());
		lblStudent.setText(currentStudent.getName() + "\n" + currentStudent.getId());
	}


	/**

	 Starts the manual exam for the given exam and student.

	 @param exam The Exam object representing the manual exam.

	 @param student The Student object representing the student.

	 @throws IOException If an error occurs while starting the manual exam.
	 */
	public static void start(Exam exam, Student student) throws IOException {
		currentExam = exam;
		currentStudent = student;

// Run the following code on the JavaFX Application Thread using Platform.runLater()
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					currentStage = SceneManagment.createNewStage("/student/ManualExam.fxml", null,
							"Student->ManualExam");
					currentStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Displays an error message using a Snackbar.
	 *
	 * @param message The error message to display.
	 */
	public void displayErrorMessage(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				snackbar = new JFXSnackbar(root);
				String css = this.getClass().getClassLoader().getResource("css/SnackbarError.css")
						.toExternalForm();
				snackbar.setPrefWidth(root.getPrefWidth() - 40);
				snackbarLayout = new JFXSnackbarLayout(message);
				snackbarLayout.getStylesheets().add(css);
				snackbar.getStylesheets().add(css);
				snackbar.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
			}
		});
	}

	/**
	 * Displays a success message using a Snackbar.
	 *
	 * @param message The success message to display.
	 */
	public void displaySuccessMessage(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				snackbar = new JFXSnackbar(root);
				String css = this.getClass().getClassLoader().getResource("css/SnackbarSuccess.css")
						.toExternalForm();
				snackbar.setPrefWidth(root.getPrefWidth() - 40);
				snackbarLayout = new JFXSnackbarLayout(message);
				snackbarLayout.getStylesheets().add(css);
				snackbar.getStylesheets().add(css);
				snackbar.fireEvent(new SnackbarEvent(snackbarLayout, Duration.millis(3000), null));
			}
		});
	}

}
