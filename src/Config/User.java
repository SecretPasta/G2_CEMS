package Config;

import java.io.Serializable;

public class User implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id; // ID of the user
    private String username; // Username of the user
    private String password; // Password of the user
    private String name; // Name of the user
    private String email; // Email of the user

    /**

     Constructs a User object with the specified ID, username, password, name, and email.
     @param id The ID of the user.
     @param username The username of the user.
     @param password The password of the user.
     @param name The name of the user.
     @param email The email of the user.
     */
    public User(String id, String username, String password, String name, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }
    
 // Getters and Setters to access and modify the attribute values

    /**

     Gets the ID of the student.
     @return The ID of the student.
     */
    public String getId() {
        return id;
    }
    /**

     Sets the ID of the student.
     @param id The ID to set for the student.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**

     Gets the username of the student.
     @return The username of the student.
     */
    public String getUsername() {
        return username;
    }
    /**

     Sets the username of the student.
     @param username The username to set for the student.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**

     Gets the password of the student.
     @return The password of the student.
     */
    public String getPassword() {
        return password;
    }
    /**

     Sets the password of the student.
     @param password The password to set for the student.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**

     Gets the name of the student.
     @return The name of the student.
     */
    public String getName() {
        return name;
    }
    /**

     Sets the name of the student.
     @param name The name to set for the student.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**

     Gets the email of the student.
     @return The email of the student.
     */
    public String getEmail() {
        return email;
    }
    /**

     Sets the email of the student.
     @param email The email to set for the student.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
