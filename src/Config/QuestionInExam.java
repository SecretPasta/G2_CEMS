package Config;

import java.util.ArrayList;

public class QuestionInExam extends Question{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double points;

	public QuestionInExam(String questionID, String questionText, ArrayList<String> questionAnswers) {
		super(questionID, null, null, questionText, questionAnswers, null, null, null);
		this.points = 0.0;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	
}
