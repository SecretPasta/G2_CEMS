package Config;


public class QuestionInExam extends Question{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double points;

	public QuestionInExam(Question question) {
		super(question.getId(), question.getsubjectID(), question.getCourseID(), question.getQuestionText(), question.getAnswers(), 
				question.getQuestionNumber(), question.getLecturer(), question.getLecturerID());
		this.points = 0.0;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	
}
