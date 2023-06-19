package Config;

import java.util.ArrayList;

public class QuestionInExam extends Question{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double points;

	/**
	 * Constructs a QuestionInExam object with the specified attributes.
	 *
	 * @param questionID       The ID of the question.
	 * @param questionText     The text of the question.
	 * @param questionAnswers  The answers to the question.
	 * @param lecturer         The lecturer/author of the question.
	 */
	public QuestionInExam(String questionID, String questionText, ArrayList<String> questionAnswers, String lecturer) {
		super(questionID, null, null, questionText, questionAnswers, null, lecturer, null);
		this.points = 0.0;
	}

	/**
	 * Returns the points assigned to the question.
	 *
	 * @return The points assigned to the question.
	 */
	public Double getPoints() {
		return points;
	}

	/**
	 * Sets the points for the question.
	 *
	 * @param points The points to be set for the question.
	 */
	public void setPoints(Double points) {
		this.points = points;
	}

	/**
	 * Returns a string representation of the Question object, including its attributes and points.
	 *
	 * @return A string representation of the Question object.
	 */
	@Override
	public String toString() {
		return super.toString() + "points=" + points + '}';
	}

}
