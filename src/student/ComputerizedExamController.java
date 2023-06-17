package student;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

import Config.Exam;
import Config.FinishedExam;
import Config.QuestionInExam;
import Config.Student;

import client.ClientUI;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;

import ClientAndServerLogin.SceneManagment;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ComputerizedExamController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton back;
    @FXML
    private JFXButton next;
    @FXML
    private AnchorPane questionsPane;
    @FXML
    private JFXButton closeBtn;
    @FXML
    private HBox questionNumbers;
    @FXML
    private JFXButton submitExamBtn;
    @FXML
    private Text timer;
    @FXML

    private static Stage openStage;
    private ComputerizedExamTimer examTimer;

    private JFXTabPane tabPane;

    private ObservableList<QuestionInExam> questionsInExamObservableList = FXCollections.observableArrayList();

    private int currentQuestion = 0;

    private ArrayList<String> correctAnswers;

    private static Exam currentExam;

    private static Student participatingStudent;

    private static ComputerizedExamController instance;

    /**
     * Constructs a new instance of the ComputerizedExamController.
     * Sets the instance variable to reference this instance.
     */
    public ComputerizedExamController() {
        instance = this;
    }


    /**
     * Retrieves the instance of the ComputerizedExamController.
     *
     * @return The instance of the ComputerizedExamController.
     */
    public static ComputerizedExamController getInstance() {
        return instance;
    }


    /**
     * Initializes the computerized exam UI.
     *
     * @param arg0 The URL of the FXML file.
     * @param arg1 The ResourceBundle.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Load all the questions text and answers from the database to panes

        // Timer for Exam ----------------------------------------------------------------------
        examTimer = new ComputerizedExamTimer(currentExam.getDuration(), instance);
        examTimer.start();
        // End of Timer for Exam ------------------------------------------------------------------

        // tabPane that contains all tabs (one tab per one question)
        tabPane = new JFXTabPane();
        tabPane.setPrefSize(970, 448);
        tabPane.setTabMinWidth(977 / 20);

        ArrayList<String> getQuestionArr = new ArrayList<>();
        getQuestionArr.add("getQuestionsInExamById");
        getQuestionArr.add(currentExam.getExamID());
        ClientUI.chat.accept(getQuestionArr);

        // Loop that creates all question panes -> need to provide the relevant number of questions
        for (int i = 1; i < questionsInExamObservableList.size() + 1; i++) {
            // Creating the exam questions pane, the second parameter is the number of questions

            // Each question and its answers are saved in a VBox, which is saved in a Tab, which is saved in the JFXTabPane
            VBox questionPane = new VBox();
            questionPane.setSpacing(30);
            questionPane.setPadding(new Insets(50, 50, 50, 50));
            questionPane.setPrefSize(970, 448);
            questionPane.setStyle("-fx-background-color:#FAF9F6");

            // The question label
            Label questionLabel = new Label(String.format(questionsInExamObservableList.get(i - 1).getQuestionText()));
            questionLabel.setWrapText(true);
            questionLabel.setStyle("-fx-font-weight: bold;");
            questionLabel.setStyle("-fx-font-size: 18;");

            // VBox for answers
            VBox answers = new VBox();
            answers.setSpacing(15);

            // ToggleGroup for radio buttons
            ToggleGroup toggleGroup = new ToggleGroup();

            // Retrieve the question answers and randomize their order
            ArrayList<String> qAnswers = questionsInExamObservableList.get(i - 1).getAnswers();
            Collections.shuffle(qAnswers);

            // Creating the radio buttons for each answer
            JFXRadioButton answer1 = new JFXRadioButton(qAnswers.get(0));
            answer1.setWrapText(true);
            answer1.setToggleGroup(toggleGroup);
            JFXRadioButton answer2 = new JFXRadioButton(qAnswers.get(1));
            answer2.setWrapText(true);
            answer2.setToggleGroup(toggleGroup);
            JFXRadioButton answer3 = new JFXRadioButton(qAnswers.get(2));
            answer3.setWrapText(true);
            answer3.setToggleGroup(toggleGroup);
            JFXRadioButton answer4 = new JFXRadioButton(qAnswers.get(3));
            answer4.setWrapText(true);
            answer4.setToggleGroup(toggleGroup);

            // Add the radio buttons to the answers VBox
            answers.getChildren().addAll(answer1, answer2, answer3, answer4);

            // Add the question label and answers VBox to the question pane
            questionPane.getChildren().add(questionLabel);
            questionPane.getChildren().add(answers);

            // Create the tab with the question pane
            Tab tab = new Tab(Integer.toString(i), questionPane);

            // Change the background color of the answered question tab
            toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    if (toggleGroup.getSelectedToggle() != null) {
                        tab.setStyle("-fx-background-color: #5DD299");
                    }
                }
            });

            // Add the tab to the tabPane
            tabPane.getTabs().add(tab);
        }

        // Add the tabPane to the questionsPane
        questionsPane.getChildren().add(tabPane);

        // Notify the server that the student has started the exam
        notifyStartExam();
    }


    /**
     * Sets the updated exam timer value.
     *
     * @param time The new time value to be displayed.
     */
    public void setUpdateExamTimer(String time) {
        timer.setText(time);
    }


    /**
     * Starts the computerized exam for a student.
     *
     * @param exam    The exam to be started.
     * @param student The student taking the exam.
     * @throws IOException if an error occurs while loading the exam UI.
     */
    public static void start(Exam exam, Student student) throws IOException {
        currentExam = exam;
        participatingStudent = student;
        System.out.println(exam);

        try {
            openStage = SceneManagment.createNewStage("/student/ComputerizedExam.fxml", null, "ComputerizedExam");
            openStage.show();
        } catch (IOException e) {
            // Handle the exception (e.g., display an error message)
            //e.printStackTrace();
        }
    }


    /**
     * Notifies the server when a student starts an exam.
     */
    private void notifyStartExam() {
        ArrayList<String> notify = new ArrayList<>();
        notify.add("notifyServerStudentBegunExam");
        notify.add(currentExam.getExamID());
        ClientUI.chat.accept(notify);
    }


    /**
     * Notifies the server when a student submits an exam after answering all the questions.
     *
     * @param elapsedTime The elapsed time of the exam in minutes.
     */
    private void notifyFinishedExam(int elapsedTime) {
        String time = "";
        if (elapsedTime == -1) {
            time += Integer.toString(currentExam.getDuration());
        } else {
            time += Integer.toString(elapsedTime);
        }
        ArrayList<String> notify = new ArrayList<>();
        notify.add("notifyServerStudentFinishedExam");
        notify.add(currentExam.getExamID());
        notify.add(time);
        ClientUI.chat.accept(notify);
    }

    /**
     * Notifies the server when a student submits an exam without answering all the questions.
     *
     * @param elapsedTime The elapsed time of the exam in minutes.
     */
    private void notifyNotFinishedExam(int elapsedTime) {
        String time = "";
        if (elapsedTime == -1) {
            time += Integer.toString(currentExam.getDuration());
        } else {
            time += Integer.toString(elapsedTime);
        }
        ArrayList<String> notify = new ArrayList<>();
        notify.add("notifyServerStudentNotFinishedExam");
        notify.add(currentExam.getExamID());
        notify.add(time);
        ClientUI.chat.accept(notify);
    }


    /**
     * Saves the correct answers from the given list of questions.
     *
     * @param questions The list of questions to retrieve the correct answers from.
     */
    private void saveCorrectAnswers(ArrayList<QuestionInExam> questions) {
        correctAnswers = new ArrayList<>();
        for (QuestionInExam question : questions) {
            correctAnswers.add(question.getAnswers().get(0));
        }
    }

    /**
     * Loads the exam questions into the UI.
     *
     * @param questions The list of questions to be loaded.
     */
    public void loadExamQuestions(ArrayList<QuestionInExam> questions) {
        saveCorrectAnswers(questions);
        questionsInExamObservableList.addAll(questions);
    }

    /**
     * Grades the computerized exam based on the chosen answers.
     *
     * @return The grade achieved in the exam.
     */
    private double gradeComputerizedExam() {
        double grade = 0;
        ArrayList<String> studentAnswers = new ArrayList<>();
        studentAnswers.addAll(getChosenAnswers());
        for (int i = 0; i < correctAnswers.size(); i++) {
            if (studentAnswers.get(i).equals(correctAnswers.get(i))) {
                grade += questionsInExamObservableList.get(i).getPoints();
            }
        }
        System.out.println(getChosenAnswers());
        System.out.println(grade);
        return grade;
    }

    /**
     * Retrieves the chosen answers for each question in the exam.
     *
     * @return An ArrayList of Strings representing the chosen answers.
     */
    private ArrayList<String> getChosenAnswers() {
        ArrayList<String> selectedAnswers = new ArrayList<>();

        for (Tab tab : tabPane.getTabs()) {
            VBox questionPane = (VBox) tab.getContent();
            ToggleGroup toggleGroup = getToggleGroup(questionPane);

            if (toggleGroup != null) {
                JFXRadioButton selectedRadioButton = (JFXRadioButton) toggleGroup.getSelectedToggle();
                if (selectedRadioButton != null) {
                    String selectedAnswerText = selectedRadioButton.getText();
                    selectedAnswers.add(selectedAnswerText);
                } else {
                    selectedAnswers.add(" "); // No answer selected for this question
                }
            }
        }

        return selectedAnswers;
    }


    /**
     * Retrieves the ToggleGroup associated with the specified question pane.
     *
     * @param questionPane The VBox representing the question pane.
     * @return The ToggleGroup associated with the question pane, or null if not found.
     */
    private ToggleGroup getToggleGroup(VBox questionPane) {
        for (Node node : questionPane.getChildren()) {
            if (node instanceof VBox) {
                VBox answers = (VBox) node;
                for (Node answerNode : answers.getChildren()) {
                    if (answerNode instanceof JFXRadioButton) {
                        JFXRadioButton radioButton = (JFXRadioButton) answerNode;
                        return radioButton.getToggleGroup();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Submits the exam.
     */
    public void submitExam() {
        Platform.runLater(() -> {
            int elapsedTime = -1;
            if (examTimer != null) {
                elapsedTime = examTimer.getElapsedMinutes();
                examTimer.stopTimer();
            }
            double grade;
            System.out.println("You've Submitted the exam!");
            grade = gradeComputerizedExam();
            ArrayList<String> answers = new ArrayList<>();
            answers.addAll(getChosenAnswers());
            if (answers.contains(" ")) {
                notifyNotFinishedExam(elapsedTime);
            } else {
                notifyFinishedExam(elapsedTime);
            }
            String answerString = "";
            for (String ans : answers) {
                answerString += (ans + "|");
            }

            ArrayList<FinishedExam> finishedExamsList = new ArrayList<>();
            finishedExamsList.add(new FinishedExam("saveFinishedExamToDB", null, null, 0, null, null, null));
            FinishedExam finishedExam = new FinishedExam(currentExam.getExamID(), currentExam.getAuthor(),
                    participatingStudent.getId(), grade, answerString.substring(0, answerString.length() - 1), null, null);
            finishedExam.checkExam();
            finishedExamsList.add(finishedExam);
            System.out.println(finishedExam);
            openStage.hide();
            StudentDashboardFrameController.getInstance().showDashboardWindow();
            // Submitting Exam to the DB
            ClientUI.chat.accept(finishedExamsList);
        });
    }


    /**
     * Retrieves the ID of the current exam.
     *
     * @return The ID of the current exam.
     */
    public static String getExamId() {
        return currentExam.getExamID();
    }

    /**
     * Automatically submits the exam when the timer runs out.
     */
    public void endOfTimerSubmit() {
        Platform.runLater(() -> {
            // Create an information dialog
            root.setDisable(true);
            Alert alert = new Alert(AlertType.INFORMATION);

            // Set dialog properties
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("System message");
            alert.setHeaderText("Time's up");
            alert.setContentText("Dear Student,\r\n"
                    + "\r\n"
                    + "The allotted time for your exam has come to an end. Your exam has been automatically submitted. \nNo further changes or submissions "
                    + "can be made.\n\n");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/student/ComputerizedExam.css").toExternalForm());
            alert.getDialogPane().setPrefSize(700, 250);

            // Show the dialog and wait for user interaction
            alert.showAndWait();

            // Submit the exam
            submitExam();
        });
    }


    /**
     * Submits the exam when the "Submit" button is pressed.
     *
     * @param event The action event triggered by the "Submit" button.
     */
    @FXML
    public void getSubmitExamBtn(ActionEvent event) {
        // Create a confirmation alert dialog
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        root.setDisable(true);

        confirmationAlert.initStyle(StageStyle.UTILITY);
        confirmationAlert.setTitle("Submitting Exam");
        confirmationAlert.setHeaderText("Are you sure?");

        confirmationAlert.setContentText("You are going to submit your exam. You will not have the opportunity to change your answers.\n"
                + "Press OK to submit, to continue the exam press Cancel.\n");

        confirmationAlert.getDialogPane().getStylesheets().add(getClass().getResource("/student/ComputerizedExam.css").toExternalForm());
        confirmationAlert.getDialogPane().setPrefSize(700, 250);

        // Display the confirmation alert dialog and wait for the user's response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (!result.isPresent()) {
            root.setDisable(false);
        } else if (result.get() == ButtonType.OK) {
            // Perform exam submission in the JavaFX application thread
            Platform.runLater(() -> {
                examTimer.stopTimer();
                submitExam();
            });
        } else if (result.get() == ButtonType.CANCEL) {
            root.setDisable(false);
        }
    }

    /**
     * Updates the duration of an exam after it has been approved by the head of the department.
     *
     * @param examID  The ID of the exam to update the duration for.
     * @param minutes The new duration of the exam in minutes.
     */
    public void updateExamDuration(String examID, int minutes) {
        if (examID.equals(currentExam.getExamID())) {
            examTimer.updateTimer(minutes);
        }
    }

    /**
     * Goes back to the previous question when the "Back" button is clicked.
     *
     * @param event The action event triggered by the "Back" button.
     */
    @FXML
    private void back(ActionEvent event) {
        // Get the index of the currently selected tab
        currentQuestion = tabPane.getTabs().indexOf(tabPane.getSelectionModel().getSelectedItem());

        // Check if it's not the first question
        if (currentQuestion != 0) {
            // Select the previous question's tab
            tabPane.getSelectionModel().select(--currentQuestion);
        }
    }


    /**
     * Advances to the next question when the "Next" button is clicked.
     *
     * @param event The action event triggered by the "Next" button.
     */
    @FXML
    private void next(ActionEvent event) {
        // Get the index of the currently selected tab
        currentQuestion = tabPane.getTabs().indexOf(tabPane.getSelectionModel().getSelectedItem());
        // Check if it's not the last question
        if (currentQuestion != tabPane.getTabs().size() - 1) {
            // Select the next question's tab
            tabPane.getSelectionModel().select(++currentQuestion);
        }
    }
}
