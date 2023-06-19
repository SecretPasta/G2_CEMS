package Config;

public class HeadOfDepartment extends User {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a HeadOfDepartment object with the specified parameters.
	 *
	 * @param id the ID of the head of department
	 * @param username the username of the head of department
	 * @param password the password of the head of department
	 * @param name the name of the head of department
	 * @param email the email of the head of department
	 */
	public HeadOfDepartment(String id, String username, String password, String name, String email) {
		super(id, username, password, name, email);
	}
	/**
	 * Returns a string representation of the HeadOfDepartment object.
	 *
	 * @return the string representation of the HeadOfDepartment object
	 */
	public String toString() {
		return String.format("%s - %s", getName(), getId());
	}

}