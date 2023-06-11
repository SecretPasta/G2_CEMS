package Config;

public class HeadOfDepartment extends User {
	
    public HeadOfDepartment(String id, String username, String password, String name, String email) {
		super(id, username, password, name, email);
	}
    
	public String toString() {
		return String.format("%s - %s", getName(), getId());
	}

}