package headofdepartment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;

public class ViewReportFrameController {

	@FXML
	private BarChart<?, ?> barChart_ShowReport;

	@FXML
	private Label lblAverage;

	@FXML
	private Label lblDataReport;

	@FXML
	private Label lblMedian;

	@FXML
	void getBtnBack(ActionEvent event) {

	}

}
