package headofdepartment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.Collections;

import ClientAndServerLogin.SceneManagment;
import Config.FinishedExam;
import client.ClientUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ViewReportFrameController implements Initializable{

	@FXML
	private BarChart<String, Number> barChart_ShowReport;
	private XYChart.Series<String, Number> series1;

	@FXML
	private Label lblAverage;

	@FXML
	private Label lblDataReport;

	@FXML
	private Label lblMedian;
	
	private static String name;
	private static String id;
	private static String chosenReport;
	
	private static ArrayList<String> gradesList;
	
	private static ViewReportFrameController instance;
	
	public ViewReportFrameController() {
		instance = this;
	}
	
	public static ViewReportFrameController getInstance(){
		return instance;
	}

	@FXML
	public void getBtnBack(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        HODDashboardFrameController.getInstance().showStageFrom_ViewReport();
	}

	public static void start(String name_temp, String id_temp, String chosenReport_temp) throws IOException {
		name = name_temp;
		id = id_temp;
		chosenReport = chosenReport_temp;	
		SceneManagment.createNewStage("/headofdepartment/ViewReport.fxml", null, "Report Test").show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			lblAverage.setText("");
			lblMedian.setText("");
			getGradesForSelectedReport();
			
			series1 = new XYChart.Series<>();
			addData("0-54.9", 0);
			addData("55-64", 0);
			addData("65-69", 0);
			addData("70-74", 0);
			addData("75-79", 0);
			addData("80-84", 0);
	        addData("85-89", 0);		
	        addData("90-94", 0);
	        addData("95-100", 0);
	        barChart_ShowReport.getData().add(series1);
	        loadAllDataIntoHistogramReport();
	        set_average_median_grades();
	        
	        lblDataReport.setText("Report For: " + chosenReport + " - " + name + " (" + id + ")");
		}catch (Exception e) {
			lblAverage.setText("No Data");
			lblMedian.setText("No Data");
			lblDataReport.setText("Report For: " + chosenReport + " - " + name + " (" + id + ")\nno available data");
			e.getStackTrace();
		}
	}

	private void getGradesForSelectedReport() {
		ArrayList<String> grades_arr = new ArrayList<>();
		grades_arr.add("GetAllGradesForReport_HOD");
		grades_arr.add(chosenReport);
		grades_arr.add(id);
		ClientUI.chat.accept(grades_arr);	
	}
	
	public void set_average_median_grades(){

		double sumGrades = 0;
		
		// average
		for(String grade : gradesList) {
			sumGrades += Double.parseDouble(grade);
		}
		double average = sumGrades / gradesList.size();
		String formattedAverage = String.format("%.2f", average);
		lblAverage.setText(formattedAverage);

		
		// median
		Collections.sort(gradesList);
		int n = gradesList.size();
        if (n % 2 == 1) {  // odd number of grades
        	lblMedian.setText(gradesList.get(n / 2));
        } else {  // even number of grades
            int middleRight = n / 2;
            int middleLeft = middleRight - 1;
            double median = (Double.parseDouble(gradesList.get(middleLeft)) + Double.parseDouble(gradesList.get(middleRight))) / 2.0;
            String medianString = String.format("%.2f", median);
            lblMedian.setText(medianString);

        }    
		
	}

	private void loadAllDataIntoHistogramReport() {
		int maxBarValue = 0;
		int barValue = 0;	//barValue - amount of students in the specified range

		for(int i = 0; i < series1.getData().size();i++) {	//in this loop we are setting all the statistic per exam			
			switch (i) {
			case 0:
				barValue = getAmountGradesByRange(0.0, 54.9);
				maxBarValue = maxBarValue > barValue ? maxBarValue : barValue;
				series1.getData().get(i).setYValue(barValue);					//0-54.9
				break;
			case 1:
				barValue = getAmountGradesByRange(55.0, 64.0);
				maxBarValue = maxBarValue > barValue ? maxBarValue : barValue;
				series1.getData().get(i).setYValue(barValue);					//55-64
				break;
			case 2:
				barValue = getAmountGradesByRange(65.0, 69.0);
				maxBarValue = maxBarValue > barValue ? maxBarValue : barValue;
				series1.getData().get(i).setYValue(barValue);					//65-69
				break;
			case 3:
				barValue = getAmountGradesByRange(70.0, 74.0);
				maxBarValue = maxBarValue > barValue ? maxBarValue : barValue;
				series1.getData().get(i).setYValue(barValue);					//70-74
				break;
			case 4:
				barValue = getAmountGradesByRange(75.0, 79.0);
				maxBarValue = maxBarValue > barValue ? maxBarValue : barValue;
				series1.getData().get(i).setYValue(barValue);					//75-79
				break;
			case 5:
				barValue = getAmountGradesByRange(80.0, 84.0);
				maxBarValue = maxBarValue > barValue ? maxBarValue : barValue;
				series1.getData().get(i).setYValue(barValue);					//80-84
				break;
			case 6:
				barValue = getAmountGradesByRange(85.0, 89.0);
				maxBarValue = maxBarValue > barValue ? maxBarValue : barValue;
				series1.getData().get(i).setYValue(barValue);					//85-89
				break;
			case 7:
				barValue = getAmountGradesByRange(90.0, 94.0);
				maxBarValue = maxBarValue > barValue ? maxBarValue : barValue;
				series1.getData().get(i).setYValue(barValue);					//90-94
				break;
			case 8:
				barValue = getAmountGradesByRange(95.0, 100.0);
				maxBarValue = maxBarValue > barValue ? maxBarValue : barValue;
				series1.getData().get(i).setYValue(barValue);					//95-100
				break;
				
			default:
				break;
			}
		}
    
        // Set the upper bound of the Y-axis
		ValueAxis<Number> yAxis = (ValueAxis<Number>) barChart_ShowReport.getYAxis();
		int upperBound =  (int) (maxBarValue * 1.1);
		yAxis.setUpperBound(upperBound);
	}
	
	private int getAmountGradesByRange(double min, double max) {
		int gradeCnt = 0;
		for(String gradeStr : gradesList) {
			double grade = Double.parseDouble(gradeStr);
			if(grade >= min && grade <= max) {
				gradeCnt ++;
			}
		}
		return gradeCnt;		
	}
	
	private void displayLabelForData(Data<String, Number> data) {
	    final Node node = data.getNode();
	    final Text dataText = new Text(data.getYValue().toString());
	    
	    node.parentProperty().addListener(new ChangeListener<Parent>() {
	        @Override
	        public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
	            Group parentGroup = (Group) parent;
	            parentGroup.getChildren().add(dataText);
	        }
	    });
	
	    node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
	        @Override
	        public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
	            dataText.setLayoutX(Math.round(bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2));
	            dataText.setLayoutY(Math.round(bounds.getMinY() - dataText.prefHeight(-1) * 0.5));
	        }
	    });
	
	    // Add a listener to the data property
	    data.YValueProperty().addListener(new ChangeListener<Number>() {
	        @Override
	        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	            dataText.setText(newValue.toString()); // Update the text when the data changes
	        }
	    });
	}
	
	private void addData(String range, int amount) {
		final XYChart.Data<String, Number> data = new XYChart.Data<>(range, amount);
	    data.nodeProperty().addListener(new ChangeListener<Node>() {
	        @Override 
	        public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
	          if (node != null) {
	            displayLabelForData(data);
	          } 
	        }
	      });
	    series1.getData().add(data);
	}
	
	public void loadAllGradesToChart(ArrayList<String> grades) {
		gradesList = grades;
	}
	
	
	

}
