package Config;


public class QuestionInExam extends Question{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String points;

	public QuestionInExam(Question question) {
		super(question.getId(), question.getsubjectID(), question.getCourseName(), question.getQuestionText(), question.getAnswers(), 
				question.getQuestionNumber(), question.getLecturer(), question.getLecturerID());
		this.points = "0";
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	
}
