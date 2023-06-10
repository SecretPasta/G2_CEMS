package Config;

import java.util.ArrayList;

public class QuestionInExam extends Question{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double points;

	public QuestionInExam(String questionID, String questionText, ArrayList<String> questionAnswers, String lecturer) {
		super(questionID, null, null, questionText, questionAnswers, null, lecturer, null);
		this.points = 0.0;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return super.toString() + "points=" + points + '}';
	}
}
