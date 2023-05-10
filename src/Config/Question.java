package Config;


public class Question {

	private String qID;
	private String qSubject;
	private String qCourseName;
	private String qText;
	private String qNumber;
	private String qAuthor;
	
	/**
	 * @param question id
	 * @param question subject
	 * @param question course name
	 * @param question text
	 * @param question number
	 * @param question author
	 */
	public Question(String qID, String qSubject, String qCourseName, String qText, String qNumber, String qAuthor) {
		super();
		this.qID = qID;
		this.qSubject = qSubject;
		this.qCourseName = qCourseName;
		this.qText = qText;
		this.qNumber = qNumber;
		this.qAuthor = qAuthor;
	}
	
	/**
	 * @return the id
	 */
	public String getqID() {
		return qID;
	}
	/**
	 * @param id the id to set
	 */
	public void setqID(String qID) {
		this.qID = qID;
	}
	/**
	 * @return the qSubject
	 */
	public String getqSubject() {
		return this.qSubject;
	}
	/**
	 * @param name the qSubject to set
	 */
	public void setqSubject(String qSubject) {
		this.qSubject = qSubject;
	}
	/**
	 * @return the qCourseName
	 */
	public String getqCourseName() {
		return this.qCourseName;
	}
	/**
	 * @param name the qCourseName to set
	 */
	public void setqCourseName(String qCourseName) {
		this.qCourseName = qCourseName;
	}
	/**
	 * @return the qText
	 */
	public String getqText() {
		return this.qText;
	}
	/**
	 * @param name the qText to set
	 */
	public void setqText(String qText) {
		this.qText = qText;
	}
	/**
	 * @return the qNumber
	 */
	public String getqNumber() {
		return this.qNumber;
	}
	/**
	 * @param name the qNumber to set
	 */
	public void setqNumber(String qNumber) {
		this.qNumber = qNumber;
	}
	/**
	 * @return the qAuthor
	 */
	public String getqAuthor() {
		return this.qAuthor;
	}
	/**
	 * @param name the qAuthor to set
	 */
	public void setqAuthor(String qAuthor) {
		this.qAuthor = qAuthor;
	}
	
	
	
	public String toString(){
		return String.format("%s %s %s %s\n",qID,qSubject,qCourseName,qText,qNumber,qAuthor);
	}
	
}
